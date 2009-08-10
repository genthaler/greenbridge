/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author ryan
 */
public interface ConversationController {

    void addAudioRecordingListener(AudioRecordingListener listener);

    void addUploadListener(UploadListener listener);

    void removeUploadListener(UploadListener listener);

    void removerAudioRecordingListener(AudioRecordingListener listener);

    void startRecording() throws LineUnavailableException;

    void stopRecording();

    String uploadConversation(String server) throws InterruptedException, IOException;

}
