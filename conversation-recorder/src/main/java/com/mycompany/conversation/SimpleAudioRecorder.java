/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

import java.io.IOException;
import java.io.File;

import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioFileFormat;


/**
 *
 * @author ryan
 */
public class SimpleAudioRecorder extends Thread {
	private TargetDataLine		    m_line;
	private AudioFileFormat.Type	m_targetType;
	private AudioInputStream	    m_audioInputStream;
	private File			        m_outputFile;

     private static class SingletonHolder {
         private static final SimpleAudioRecorder INSTANCE = new SimpleAudioRecorder();
       }

    private SimpleAudioRecorder() {};

    public static SimpleAudioRecorder getInstance() {
     return SingletonHolder.INSTANCE;
   }


    public void startRecording(File file) {
        if (m_line != null || (m_line != null &&  m_line.isOpen())) return;
        try {
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 1, 2, 44100.0F, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
            m_line = (TargetDataLine) AudioSystem.getLine(info);
            m_line.open(audioFormat);
            AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;
            m_audioInputStream = new AudioInputStream(m_line);
            m_targetType = targetType;
            m_outputFile = file;
            start();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }


	/** Starts the recording.
	    To accomplish this, (i) the line is started and (ii) the
	    thread is started.
	*/
	public void start() {
            m_line.start();
            super.start();
	}

	/** Stops the recording.
	*/
	public void stopRecording() {
		m_line.stop();
		m_line.close();
        m_line = null;
	}

	public void run()
	{
        try {
            AudioSystem.write(m_audioInputStream, m_targetType, m_outputFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}

}
