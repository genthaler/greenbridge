/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

import java.util.Date;

/**
 *
 * @author ryan
 */
public class AudioRecordingState {

    private Date recordingStarted;
    private Date recordingStopped;

    /**
     * @return the recordingStarted
     */
    public Date getRecordingStarted() {
        return recordingStarted;
    }

    /**
     * @param recordingStarted the recordingStarted to set
     */
    public void setRecordingStarted(Date recordingStarted) {
        this.recordingStarted = recordingStarted;
    }

    /**
     * @return the recordingStopped
     */
    public Date getRecordingStopped() {
        return recordingStopped;
    }

    /**
     * @param recordingStopped the recordingStopped to set
     */
    public void setRecordingStopped(Date recordingStopped) {
        this.recordingStopped = recordingStopped;
    }
    
}
