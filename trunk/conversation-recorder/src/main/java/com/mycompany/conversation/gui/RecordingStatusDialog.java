/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RecordingStatusDialog.java
 *
 * Created on Aug 4, 2009, 7:43:09 AM
 */

package com.mycompany.conversation.gui;

import com.mycompany.conversation.AudioRecordingListener;
import com.mycompany.conversation.AudioRecordingState;
import com.mycompany.conversation.ConversationController;
import com.mycompany.conversation.ConversationControllerImpl;
import com.mycompany.conversation.DocumentStorage;
import com.mycompany.conversation.PropertiesStorage;
import com.mycompany.conversation.UploadListener;
import com.mycompany.conversation.UploadState;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.jdesktop.swingx.JXErrorPane;

/**
 *
 * @author ryan
 */
public class RecordingStatusDialog extends javax.swing.JDialog implements AudioRecordingListener, UploadListener{

    private ConversationController controller;
    private PropertiesStorage propStorage;
    SimpleDateFormat formater = new SimpleDateFormat("H:mm:ss a");
    private Date lastKnownStartDate;
    Timer updateLength;


    /** Creates new form RecordingStatusDialog */
    public RecordingStatusDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        updateLength = new Timer(1000, new updateLengthThread());
        initComponents();
    }

    /** Creates new form RecordingStatusDialog */
    public RecordingStatusDialog(ConversationController controller, final PropertiesStorage propStorage, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        updateLength = new Timer(1000, new updateLengthThread());
        this.propStorage = propStorage;
        this.controller = controller;
        initComponents();
        controller.addAudioRecordingListener(this);
        controller.addUploadListener(this);
        uploadURLEditorPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent hle) {
               if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
                    propStorage.showURL(hle.getURL().toString());
                }
            }
        });
        String uploadServerUrl = propStorage.loadProperty("uploadServerUrl");
        if (uploadServerUrl != null) {
            serverComboBox.setSelectedItem(uploadServerUrl);
        }
    }


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
        recordingStartedLabel.setText("");
        recordingStartButton.setEnabled(true);
        recordingLengthLabel.setText("");
        recordingStopButton.setEnabled(false);
        serverComboBox.setEnabled(false);
        uploadButton.setEnabled(false);
        taskProgressBar.setEnabled(false);
        overallProgressBar.setEnabled(false);
        uploadCancelButton.setEnabled(false);
        uploadURLEditorPane.setText("");
        uploadPanel.setEnabled(false);
    }

    private void recordingStarted(Date startDate) {
        this.lastKnownStartDate = startDate;
        updateLength.start();
        recordingStartedLabel.setText(formater.format(startDate));
        recordingStartButton.setEnabled(false);
        recordingLengthLabel.setText("");
        recordingStopButton.setEnabled(true);
        serverComboBox.setEnabled(false);
        uploadButton.setEnabled(false);
        taskProgressBar.setEnabled(false);
        overallProgressBar.setEnabled(false);
        uploadCancelButton.setEnabled(false);
        uploadURLEditorPane.setText("");
        uploadPanel.setEnabled(false);
    }

    private void recordingFinished(Date startDate, Date endDate) {
        updateLength.stop();
        String label = calculateLengthLabel(startDate,endDate);
        recordingLengthLabel.setText(label);

        recordingStartedLabel.setText(formater.format(startDate));
        recordingStartButton.setEnabled(false);
        // TODO
        recordingLengthLabel.setText("");
        recordingStopButton.setEnabled(false);
        serverComboBox.setEnabled(true);
        uploadButton.setEnabled(true);
        taskProgressBar.setEnabled(false);
        overallProgressBar.setEnabled(false);
        uploadCancelButton.setEnabled(false);
        uploadURLEditorPane.setText("");
        uploadPanel.setEnabled(true);
    }



    public void uploadChange(UploadState state) {
        if (state.getUploadURL() != null) {
            uploadComplete(state.getUploadURL());
        } else {
            this.overallProgressBar.setValue(state.getOverallPercentComplete());
            this.taskProgressBar.setString(state.getMinorTaskName());
            this.taskProgressBar.setStringPainted(true);
            taskProgressBar.setIndeterminate(state.isMinorTaskIndeterminate());
            if (!state.isMinorTaskIndeterminate()) {
                this.taskProgressBar.setValue(state.getMinorTaskPercentComplete());
            }
            

        }
    }

    private String calculateLengthLabel(Date start) {
        long millisecs = new Date().getTime()  - start.getTime();
        return formatMillisecs(millisecs);
    }

    private String calculateLengthLabel(Date start, Date end) {
        long millisecs = end.getTime()  - start.getTime();
        return formatMillisecs(millisecs);
    }

    private String formatMillisecs(long milli) {
        return (milli/1000) + "";
    }


    private class updateLengthThread implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String label = calculateLengthLabel(lastKnownStartDate);
            recordingLengthLabel.setText(label);
        }
    }


    private void uploadComplete(String uploadURL) {
        uploadURLEditorPane.setText(createUploadHtml(uploadURL));
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        uploadPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        serverComboBox = new javax.swing.JComboBox();
        uploadButton = new javax.swing.JButton();
        taskProgressBar = new javax.swing.JProgressBar();
        overallProgressBar = new javax.swing.JProgressBar();
        uploadCancelButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        uploadURLEditorPane = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        recordingStartedLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        recordingLengthLabel = new javax.swing.JLabel();
        otherRecordingLabel = new javax.swing.JLabel();
        recordingStartButton = new javax.swing.JButton();
        recordingStopButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        editSettingsMenu = new javax.swing.JMenu();
        editSettingsMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        uploadPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Upload Conversation"));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Server");

        serverComboBox.setEditable(true);
        serverComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "http://localhost:5984/greenbridge" }));
        serverComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverComboBoxActionPerformed(evt);
            }
        });

        uploadButton.setText("Upload");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });

        uploadCancelButton.setText("Cancel");

        jLabel8.setText("Task");

        jLabel9.setText("Overall");

        jLabel7.setText("URL");

        jScrollPane1.setBorder(null);

        uploadURLEditorPane.setContentType("text/html");
        uploadURLEditorPane.setEditable(false);
        jScrollPane1.setViewportView(uploadURLEditorPane);

        javax.swing.GroupLayout uploadPanelLayout = new javax.swing.GroupLayout(uploadPanel);
        uploadPanel.setLayout(uploadPanelLayout);
        uploadPanelLayout.setHorizontalGroup(
            uploadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadPanelLayout.createSequentialGroup()
                .addGroup(uploadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(uploadPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(serverComboBox, 0, 546, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(uploadButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uploadPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(uploadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(uploadPanelLayout.createSequentialGroup()
                                .addGroup(uploadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(uploadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(overallProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                                    .addComponent(taskProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(uploadCancelButton))
                            .addGroup(uploadPanelLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        uploadPanelLayout.setVerticalGroup(
            uploadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadPanelLayout.createSequentialGroup()
                .addGroup(uploadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(serverComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uploadButton))
                .addGap(18, 18, 18)
                .addGroup(uploadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(uploadCancelButton)
                    .addGroup(uploadPanelLayout.createSequentialGroup()
                        .addGroup(uploadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(taskProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(uploadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(overallProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(uploadPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Audio Recording"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Started");

        recordingStartedLabel.setText("8:50:25 AM");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Length");

        recordingLengthLabel.setText("00:25:25");

        otherRecordingLabel.setForeground(java.awt.Color.red);

        recordingStartButton.setText("Start");
        recordingStartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordingStartButtonActionPerformed(evt);
            }
        });

        recordingStopButton.setText("Stop");
        recordingStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordingStopButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(recordingStartedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recordingLengthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(recordingStartButton)
                        .addGap(18, 18, 18)
                        .addComponent(otherRecordingLabel))
                    .addComponent(recordingStopButton))
                .addContainerGap(472, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(recordingStartedLabel)
                    .addComponent(recordingStartButton)
                    .addComponent(otherRecordingLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(recordingLengthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(recordingStopButton))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        editSettingsMenu.setText("Settings");

        editSettingsMenuItem.setText("Edit settings...");
        editSettingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSettingsMenuItemActionPerformed(evt);
            }
        });
        editSettingsMenu.add(editSettingsMenuItem);

        jMenuBar1.add(editSettingsMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(uploadPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(closeButton)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(uploadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closeButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed
        uploadButton.setEnabled(false);
        new Uploader().execute();
}//GEN-LAST:event_uploadButtonActionPerformed


        private class Uploader extends SwingWorker<String,Void> {

            @Override
            protected String doInBackground()  {
                try {
                    return controller.uploadConversation((String) serverComboBox.getSelectedItem());
                } catch (final Exception e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            overallProgressBar.setValue(0);
                            taskProgressBar.setStringPainted(false);
                            taskProgressBar.setIndeterminate(false);
                            taskProgressBar.setValue(0);
                            JXErrorPane.showDialog(e);
                        }
                    });
                    return e.getMessage();
                }
            }

            @Override
            protected void done() {
                try {
                    String location = get();
                    uploadURLEditorPane.setText(createUploadHtml(location));
                    uploadButton.setEnabled(true);
                    taskProgressBar.setStringPainted(false);
                    taskProgressBar.setIndeterminate(false);
                    taskProgressBar.setValue(100);
                    overallProgressBar.setValue(100);
                    propStorage.storeProperty("uploadServerUrl", (String)serverComboBox.getSelectedItem());
                } catch (Exception ex) {
                    Logger.getLogger(RecordingStatusDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


        }


    private String createUploadHtml(String url) {
        return "<html><a href=\"" + url + "\">" + url + "</a></html>";
    }

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        updateLength.stop();
        this.dispose();

}//GEN-LAST:event_closeButtonActionPerformed

    private void recordingStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordingStartButtonActionPerformed
        if (controller != null ) {
            try {
                controller.startRecording();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            }
        }
}//GEN-LAST:event_recordingStartButtonActionPerformed

    private void recordingStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordingStopButtonActionPerformed
        if (controller != null ) {
            controller.stopRecording();
        }
}//GEN-LAST:event_recordingStopButtonActionPerformed

    private void editSettingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSettingsMenuItemActionPerformed
         SettingsDialog dialog = new SettingsDialog(propStorage,this, true);
         dialog.setLocationRelativeTo(null);
         dialog.setVisible(true);
}//GEN-LAST:event_editSettingsMenuItemActionPerformed

    private void serverComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serverComboBoxActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
       
        System.out.println("Running!");
        final RecordingStatusDialog dialog =  new RecordingStatusDialog(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
        dialog.recordingStarted(new Date());
        dialog.setVisible(true);
        

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JMenu editSettingsMenu;
    private javax.swing.JMenuItem editSettingsMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel otherRecordingLabel;
    private javax.swing.JProgressBar overallProgressBar;
    private javax.swing.JLabel recordingLengthLabel;
    private javax.swing.JButton recordingStartButton;
    private javax.swing.JLabel recordingStartedLabel;
    private javax.swing.JButton recordingStopButton;
    private javax.swing.JComboBox serverComboBox;
    private javax.swing.JProgressBar taskProgressBar;
    private javax.swing.JButton uploadButton;
    private javax.swing.JButton uploadCancelButton;
    private javax.swing.JPanel uploadPanel;
    private javax.swing.JEditorPane uploadURLEditorPane;
    // End of variables declaration//GEN-END:variables





}
