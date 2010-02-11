/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AudioControlPanel.java
 *
 * Created on Feb 8, 2010, 4:28:35 PM
 */

package com.mycompany.conversation.gui;

import com.mycompany.conversation.AudioPlayerListener;
import com.mycompany.conversation.AudioRecordingListener;
import com.mycompany.conversation.AudioRecordingState;
import com.mycompany.conversation.ConversationController;
import com.mycompany.conversation.PropertiesStorage;
import com.mycompany.conversation.TimeUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 *
 * @author ryan
 */
public class AudioControlPanel extends javax.swing.JPanel  implements AudioRecordingListener, AudioPlayerListener{
    private ConversationController controller;
    private PropertiesStorage propStorage;
    private Date lastKnownStartDate;
    private boolean internalMediaLengthChange = false;
    private long mediaDuration;
    private boolean paused = false;
    Timer updateLength;
    ImageIcon pause_icon;
    ImageIcon play_icon;

    public static boolean PANEL_CREATED = false;

    /** Creates new form AudioControlPanel */
    public AudioControlPanel() {
        initComponents();
    }

    public AudioControlPanel(ConversationController controller, final PropertiesStorage propStorage) {
        this();
        PANEL_CREATED = true;
        pause_icon = new javax.swing.ImageIcon(getClass().getResource("/control_pause.png"));
        play_icon = new javax.swing.ImageIcon(getClass().getResource("/control_play.png"));
        playButton.setIcon(play_icon);
        updateLength = new Timer(1000, new updateLengthThread());
        this.propStorage = propStorage;
        this.controller = controller;
        controller.addAudioRecordingListener(this);
        controller.getAudioPlayer().addListener(this);
    }


    @Override
    public void recordingChanged(AudioRecordingState state) {
        if (state.getRecordingStarted() == null) {
            notStartedRecording();
        }
        if (state.getRecordingStarted() != null && state.getRecordingStopped() == null) {
            recordingStarted(state.getRecordingStarted());
        }
        if (state.getRecordingStarted() != null && state.getRecordingStopped() != null) {
            recordingFinished(state.getRecordingStarted(), state.getRecordingStopped());
        }
    }

    private void notStartedRecording() {
        recordingButton.setEnabled(true);
        playButton.setEnabled(false);
        stopButton.setEnabled(false);
        mediaLengthSlider.setEnabled(false);
        adjustMediaLengthSlider(0);
        uploadButton.setEnabled(false);

    }

    private void recordingStarted(Date startDate) {
        this.lastKnownStartDate = startDate;
        updateLength.start();
        playButton.setEnabled(false);
        stopButton.setEnabled(false);
        mediaLengthSlider.setEnabled(false);
        adjustMediaLengthSlider(0);
        uploadButton.setEnabled(false);
    }

    private void recordingFinished(Date startDate, Date endDate) {
        updateLength.stop();
        recordingButton.setEnabled(false);
        playButton.setEnabled(true);
        stopButton.setEnabled(false);
        mediaLengthSlider.setEnabled(true);
        adjustMediaLengthSlider(0);
        uploadButton.setEnabled(true);
    }

    
    private void adjustMediaLengthSlider(int value) {
        internalMediaLengthChange = true;
        mediaLengthSlider.setValue(value);
    }




    @Override
    public void started() {
       playButton.setIcon(pause_icon);
       playButton.setToolTipText("Pause");
       stopButton.setEnabled(true);
       paused = false;
    }

    @Override
    public void stopped() {
        System.out.println("Stopped!!!");
        playButton.setIcon(play_icon);
        playButton.setToolTipText("Play");
        stopButton.setEnabled(false);
        currentTimeLabel.setText(formatMillisecs(0));
        adjustMediaLengthSlider(0);
        paused = false;
    }



    @Override
    public void playing(long currentSecond, long totalSeconds) {
        mediaDuration = totalSeconds;
        currentTimeLabel.setText(formatSeconds(currentSecond));
        totalDurationLabel.setText(formatSeconds(totalSeconds));
        if (!mediaLengthSlider.getValueIsAdjusting()) {
            long pos = Math.round(((double)currentSecond / (double)totalSeconds) * 100);
            adjustMediaLengthSlider((int)(pos));
        }

    }

    private class updateLengthThread implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            long milli_duration = (new Date().getTime()) - lastKnownStartDate.getTime();
            totalDurationLabel.setText(formatMillisecs(milli_duration));
        }
    }


    private String formatMillisecs(long milli) {
        return TimeUtils.formatTimeBySec(milli/1000, false);
    }

    private String formatSeconds(long seconds) {
        return TimeUtils.formatTimeBySec(seconds, false);
    }





    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        recordingButton = new javax.swing.JToggleButton();
        playButton = new javax.swing.JButton();
        totalDurationLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        currentTimeLabel = new javax.swing.JLabel();
        mediaLengthSlider = new javax.swing.JSlider();
        spectrumPanel = new javax.swing.JPanel();
        stopButton = new javax.swing.JButton();
        uploadButton = new javax.swing.JButton();
        settingsButton = new javax.swing.JButton();

        recordingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_sound.png"))); // NOI18N
        recordingButton.setText("Record");
        recordingButton.setMargin(new java.awt.Insets(2, 12, 2, 12));
        recordingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordingButtonActionPerformed(evt);
            }
        });

        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_play.png"))); // NOI18N
        playButton.setToolTipText("Play");
        playButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        totalDurationLabel.setText("00:00:00");

        jLabel3.setText("/");

        currentTimeLabel.setText("00:00:00");

        mediaLengthSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mediaLengthSliderStateChanged(evt);
            }
        });

        javax.swing.GroupLayout spectrumPanelLayout = new javax.swing.GroupLayout(spectrumPanel);
        spectrumPanel.setLayout(spectrumPanelLayout);
        spectrumPanelLayout.setHorizontalGroup(
            spectrumPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 263, Short.MAX_VALUE)
        );
        spectrumPanelLayout.setVerticalGroup(
            spectrumPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        stopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_stop.png"))); // NOI18N
        stopButton.setToolTipText("Stop");
        stopButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        uploadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/server_add.png"))); // NOI18N
        uploadButton.setToolTipText("Upload to Greenbridge Server.");
        uploadButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });

        settingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/control_equalizer.png"))); // NOI18N
        settingsButton.setToolTipText("Advanced Settings...");
        settingsButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(recordingButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stopButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spectrumPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(currentTimeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalDurationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mediaLengthSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(uploadButton)
                    .addComponent(settingsButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(recordingButton)
                            .addComponent(playButton)
                            .addComponent(stopButton)
                            .addComponent(spectrumPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mediaLengthSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(totalDurationLabel)
                                .addComponent(jLabel3)
                                .addComponent(currentTimeLabel))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(uploadButton)
                        .addGap(2, 2, 2)
                        .addComponent(settingsButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
        if (playButton.getIcon() == play_icon && !paused) {
            controller.startPlayback();
            playButton.setToolTipText("Pause");
            paused = false;
        } else {
            if (paused) {
                controller.resumePlayback();
                playButton.setIcon(pause_icon);
                playButton.setToolTipText("Pause");
            } else {
                controller.pausePlayback();
                playButton.setIcon(play_icon);
                playButton.setToolTipText("Play");
            }
            paused = !paused;
        }
    }//GEN-LAST:event_playButtonActionPerformed

    private void recordingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordingButtonActionPerformed
        if (controller != null ) {
            if (recordingButton.isSelected()) {
                System.out.println("Record!");
                try {
                    controller.startRecording();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            }
            else {
                System.out.println("Stop Recordiing!");
                controller.stopRecording();
            }
        }
    }//GEN-LAST:event_recordingButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        controller.stopPlayback();
        paused = false;
    }//GEN-LAST:event_stopButtonActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
         SettingsDialog dialog = new SettingsDialog(propStorage,null, true);
         dialog.setLocationRelativeTo(null);
         dialog.setVisible(true);
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void mediaLengthSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mediaLengthSliderStateChanged
        if (!mediaLengthSlider.getValueIsAdjusting() && !internalMediaLengthChange) {
            int value = mediaLengthSlider.getValue();
            double pos = ((double)value / 100d) * mediaDuration;
            controller.seekPlayback((long) pos);
        }
        if (internalMediaLengthChange) internalMediaLengthChange = false;
    }//GEN-LAST:event_mediaLengthSliderStateChanged

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed
        UploadDialog dialog = new UploadDialog(controller, propStorage, null, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_uploadButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel currentTimeLabel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSlider mediaLengthSlider;
    private javax.swing.JButton playButton;
    private javax.swing.JToggleButton recordingButton;
    private javax.swing.JButton settingsButton;
    private javax.swing.JPanel spectrumPanel;
    private javax.swing.JButton stopButton;
    private javax.swing.JLabel totalDurationLabel;
    private javax.swing.JButton uploadButton;
    // End of variables declaration//GEN-END:variables

}
