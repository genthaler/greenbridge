/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sound.sampled.LineUnavailableException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;


/**
 *
 * @author ryan
 */
public class ConversationControllerImpl implements ConversationController {

    private PropertiesStorage properties;
    private DocumentStorage documentStorage;
    private List<AudioRecordingListener> audioListeners;
    private UploadListener uploadListener;


    public ConversationControllerImpl(DocumentStorage storage, PropertiesStorage properties) {
        this.documentStorage = storage;
        this.properties = properties;
        this.audioListeners = new ArrayList<AudioRecordingListener>();
        
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

    public String uploadConversation(String server) throws InterruptedException, IOException {
        File outputFile =  getFileForDocument(documentStorage.getFileLocation(), ".wav");
        fireUploadChange("Coverting to MP3...", 60);
        File mp3 = convertToMP3(outputFile);
        fireUploadChange("Saving document...", 70);
        documentStorage.saveCurrentDocument();
        fireUploadChange("Uploading to Server...", 90);

        String location = uploadData(server, mp3, documentStorage.getFileLocation());
        documentStorage.setUploadURL(location);
        return location;
    }


    protected String uploadData(String server, File mp3, File document) throws FileNotFoundException, IOException {
        PostMethod post = new PostMethod(server);

        FilePart[] fileparts = new FilePart[] {
          new FilePart("audio", mp3),
          new FilePart("document", document)
        };

        NameValuePair[] params =  new NameValuePair[2];
        String tagStartOffset = properties.loadProperty("tagStartOffset");
        if (tagStartOffset != null) {
            NameValuePair offset = new NameValuePair("tagStartOffset", tagStartOffset);
            params[0] = offset;
        } else {
            NameValuePair offset = new NameValuePair("tagStartOffset", "10");
            params[0] = offset;
        }
        String tagDuration = properties.loadProperty("tagDuration");
        if (tagDuration != null) {
            NameValuePair duration = new NameValuePair("tagDuration", tagDuration);
            params[1] = duration;
        } else {
            NameValuePair duration = new NameValuePair("tagDuration", "20");
            params[1] = duration;
        }

        post.setQueryString(params);


        post.setRequestEntity(new MultipartRequestEntity(fileparts, post.getParams()));

        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
        int status = client.executeMethod(post);
        String location;
        Header locationHeader = post.getResponseHeader("location");
        if (locationHeader != null) {
            location = locationHeader.getValue();
            return location;
        }
        if (status == HttpStatus.SC_OK) {
            return "/success";
        }
        else return null;
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

        FFMpegConverter converter = new FFMpegConverter(ffmpegcmd);
        converter.convert(outputFile, bitrate, frequency, mp3, true);
        return mp3;
    }


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

}
