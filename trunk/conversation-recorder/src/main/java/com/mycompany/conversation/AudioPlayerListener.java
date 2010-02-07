/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

/**
 *
 * @author ryan
 */
public interface AudioPlayerListener {
    void started();
    void stopped();
    void playing(long currentSecond, long totalSeconds);

}
