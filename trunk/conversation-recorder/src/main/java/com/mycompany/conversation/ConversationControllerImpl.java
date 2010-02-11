/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

import com.mycompany.conversation.domain.Conversation;
import com.mycompany.conversation.domain.Media;
import com.mycompany.conversation.upload.MediaUploader;
import com.mycompany.conversation.upload.PostMediaUploader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sound.sampled.LineUnavailableException;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;



/**
 *
 * @author ryan
 */
public class ConversationControllerImpl implements ConversationController {

    private PropertiesStorage properties;
    private DocumentStorage documentStorage;
    private List<AudioRecordingListener> audioListeners;
    private UploadListener uploadListener;
    private TagsUploader tagsUploader;
    private MediaUploader mediaUploader;
    private Pattern serverPattern;
    private AudioPlayer audioPlayer;


    public ConversationControllerImpl(DocumentStorage storage, PropertiesStorage properties, TagsUploader tagsUploader, AudioPlayer audioPlayer) {
        this.documentStorage = storage;
        this.properties = properties;
        this.audioListeners = new ArrayList<AudioRecordingListener>();
        this.tagsUploader = tagsUploader;
        this.mediaUploader = new PostMediaUploader();
        this.audioPlayer = audioPlayer;
        serverPattern = Pattern.compile("(https?://[^/]+)/(\\w+)");
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }


    private AudioRecordingState findPersistedAudioRecordingState() {
        AudioRecordingState state = new AudioRecordingState();
        state.setRecordingStarted(documentStorage.getStartDate());
        state.setRecordingStopped(documentStorage.getEndDate());
        return state;
    }



    public void startRecording() throws LineUnavailableException {
        documentStorage.saveCurrentDocument();
        Date start = new Date();
        documentStorage.setStartDate(start);

        File outputFile =  getFileForDocument(documentStorage.getFileLocation(), ".wav");
        SimpleAudioRecorder recorder = SimpleAudioRecorder.getInstance();

        String selectedMixerIndex = properties.loadProperty("selectedMixerIndex");
        if (selectedMixerIndex == null) selectedMixerIndex = "default";

        recorder.startRecording(outputFile, selectedMixerIndex, 0);
        AudioRecordingState state = new AudioRecordingState();
        state.setRecordingStarted(documentStorage.getStartDate());
        state.setRecordingStopped(null);
        fireAudioChange(state);
    }

    public void stopRecording() {
        SimpleAudioRecorder.getInstance().stopRecording();
        Date stop = new Date();
        documentStorage.setEndDate(stop);
        AudioRecordingState state = new AudioRecordingState();
        state.setRecordingStarted(documentStorage.getStartDate());
        state.setRecordingStopped(documentStorage.getEndDate()); 
        fireAudioChange(state);
    }

    @Override
    public String uploadConversation(String server) throws InterruptedException, IOException {
        File outputFile =  getFileForDocument(documentStorage.getFileLocation(), ".wav");

        fireUploadChange("Saving document...", 10);

        documentStorage.saveCurrentDocument();
        String server_url = findServerURL(server);
        String db     = findDB(server);
        Conversation c = documentStorage.convertToConversation();
        
        fireUploadChange("Coverting to MP3...", 20);
        File mp3 = convertToMP3(outputFile);
        try {
            fireUploadChange("Uploading MP3...", 50);
            Media media = uploadMedia(mp3, "");
            media.setStartdate(documentStorage.getStartDate());
            int mediaLength = (int)(documentStorage.getEndDate().getTime() - documentStorage.getStartDate().getTime()) / 1000;
            media.setMediaLength(mediaLength);
            c.setMedia(media);

        } catch (Exception ex) {
            Logger.getLogger(ConversationControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        fireUploadChange("Uploading Tags..", 70);
        tagsUploader.upload(server_url, db, c);
        

        //String location = uploadData(server, mp3, documentStorage.getFileLocation());
        String location = server_url + "/_" + db + "/conversation.html?" + c.getId();
        documentStorage.setUploadURL(location);
        return location;
    }


    public Media uploadMedia(File mp3, String server) throws Exception {

        // find server base to upload to
        server = "http://localhost:8080/simple-upload/upload";
        Map properties = new HashMap();
        properties.put(PostMediaUploader.POST_URL, server);
        return mediaUploader.upload(mp3, properties);
    }


    protected String findServerURL(String string) {
        Matcher m = serverPattern.matcher(string);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }

    protected String findDB(String string) {
        Matcher m = serverPattern.matcher(string);
        if (m.find()) {
            return m.group(2);
        }
        return "";
    }

    private File convertToMP3(File outputFile) throws InterruptedException, IOException {
        String ffmpegcmd = properties.loadProperty("ffmpegcmd");
        long bitrate;
        try {
            bitrate = Long.parseLong(properties.loadProperty("bitrate")) * 1000;
        } catch (Exception e) {
            bitrate = 24000L;
        }
        long frequency;
        try {
            frequency = Long.parseLong(properties.loadProperty("frequency"));
        } catch (Exception e) {
            frequency = 22050L;
        }

        File mp3 = getFileForDocument(outputFile, ".mp3");
        if (mp3.exists()) return mp3;

        FFMpegConverter converter = new FFMpegConverter(ffmpegcmd);
        converter.convert(outputFile, bitrate, frequency, mp3, true);
        return mp3;
    }

//    protected List<File> findPicturesInDirBetweenDates(File dir, Date start, Date end) {
//        assert(dir.exists() && dir.isDirectory());
//        ArrayList result = new ArrayList<File>();
//        File[] children = dir.listFiles();
//        for (File child : children) {
//           if (child.getName().endsWith(".jpg") || child.getName().endsWith(".jpeg") || child.getName().endsWith(".JPG")) {
//               if (isPictureBetweenDates(child, start, end)) {
//                   result.add(child);
//               }
//            }
//        }
//        return result;
//    }


//    protected boolean isPictureBetweenDates(File picture, Date start, Date end) {
//        Date picDate = null;
//        try {
//            Metadata metadata = JpegMetadataReader.readMetadata(picture);
//            Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
//            picDate = exifDirectory.getDate(ExifDirectory.TAG_DATETIME_ORIGINAL) ;
//            if (picDate == null) picDate = exifDirectory.getDate(ExifDirectory.TAG_DATETIME);
//        } catch (JpegProcessingException e) {
//        } catch (MetadataException e) {
//        }
//        if (picDate == null) picDate = new Date(picture.lastModified());
//        if (picDate.after(start) && picDate.before(end)) return true;
//
//        return false;
//    }


    private File getFileForDocument(File file, String newExtenstion) {
        String name = file.getName() + newExtenstion;
        File parent = file.getParentFile();
        return new File(parent, name);
    }


    // Right now, only a one to one broadcast! will change if needed
    public void addAudioRecordingListener(AudioRecordingListener listener) {
        this.audioListeners.add(listener);
        AudioRecordingState state = findPersistedAudioRecordingState();
        fireAudioChange(state);
    }

    public void removerAudioRecordingListener(AudioRecordingListener listener) {
        this.audioListeners.remove(listener);
        AudioRecordingState state = findPersistedAudioRecordingState();
        fireAudioChange(state);
    }

    @Override
    public void addUploadListener(UploadListener listener) {
        this.uploadListener = listener;
        String uploadURL = documentStorage.getUploadURL();
        if (uploadURL != null ) {
            fireUploadComplete(uploadURL);
        }
    }

    @Override
    public void removeUploadListener(UploadListener listener) {
        this.uploadListener = null;
    }

    private void fireAudioChange(AudioRecordingState state) {
        for (AudioRecordingListener audioRecordingListener : audioListeners) {
            audioRecordingListener.recordingChanged(state);
        }
    }


    private void fireUploadComplete(String url) {
        UploadState state = new UploadState();
        state.setMinorTaskName("Upload Complete...");
        state.setMinorTaskIndeterminate(false);
        state.setMinorTaskPercentComplete(100);
        state.setOverallPercentComplete(100);
        state.setUploadURL(url);
        fireUploadChange(state);
    }

    protected void fireUploadChange(String minorTaskName,int overallPercentComplete) {
        UploadState state = new UploadState();
        state.setMinorTaskName(minorTaskName);
        state.setMinorTaskIndeterminate(true);
        state.setOverallPercentComplete(overallPercentComplete);
        fireUploadChange(state);
    }



    private void fireUploadChange(String minorTaskName,int minorTaskPercentComplete,int overallPercentComplete) {
        UploadState state = new UploadState();
        state.setMinorTaskName(minorTaskName);
        state.setMinorTaskIndeterminate(false);
        state.setMinorTaskPercentComplete(minorTaskPercentComplete);
        state.setOverallPercentComplete(overallPercentComplete);
        fireUploadChange(state);
    }


    private void fireUploadChange(UploadState state) {
        if (uploadListener != null ) {
            uploadListener.uploadChange(state);
        }
    }




    @Override
    public void startPlayback() {
        audioPlayer.play(getFileForDocument(documentStorage.getFileLocation(), ".wav"));
    }

    @Override
    public void pausePlayback() {
        audioPlayer.pause();
    }

    @Override
    public void resumePlayback() {
        audioPlayer.resume();
    }

    @Override
    public void stopPlayback() {
        audioPlayer.stop();
    }

    @Override
    public void seekPlayback(long seconds) {
        audioPlayer.seek(seconds);
    }


}
