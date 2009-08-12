/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

import java.io.IOException;
import java.io.File;

import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;


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


    public void startRecording(File file, String mixer, float gain) {
        if (m_line != null || (m_line != null &&  m_line.isOpen())) return;
        try {
            AudioFormat audioFormat = new AudioFormat(16000.0F, 16, 1, true, true);
            audioFormat = getBestAudioFormat(audioFormat, mixer);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
            Mixer selectedMixer = getSelectedMixer(mixer);
            if (selectedMixer == null) {
               
               m_line = (TargetDataLine) AudioSystem.getLine(info);
               selectedMixer = AudioSystem.getMixer(null);

            } else {
               m_line = (TargetDataLine) selectedMixer.getLine(info);
            }
            m_line.open(audioFormat);

            //FloatControl fc = (FloatControl) selectedMixer.getControl(FloatControl.Type.MASTER_GAIN);
            //System.out.println("Master Gain min: " + fc.getMinimum());
            //System.out.println("Master Gain min: " + fc.getMaximum());
            //ystem.out.println("Master Gain cur: " + fc.getValue());
            //fc.setValue(MIN_PRIORITY);
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

    private static Mixer getSelectedMixer(String selectedMixerIndex) {
           if (selectedMixerIndex.equals("default")) {
               return null;
           } else {
               Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
               if (selectedMixerIndex.equals("last")) {
                   return AudioSystem.getMixer(mixerInfo[mixerInfo.length - 1]);
               } else {
                   int index = Integer.parseInt(selectedMixerIndex);
                   return AudioSystem.getMixer(mixerInfo[index]);
               }
           }
      }


      private AudioFormat getBestAudioFormat(AudioFormat desiredFormat, String selectedMixerIndex) {
          AudioFormat finalFormat = desiredFormat;
          DataLine.Info info
                   = new DataLine.Info(TargetDataLine.class, desiredFormat);

           /* If we cannot get an audio line that matches the desired
            * characteristics, shoot for one that matches almost
            * everything we want, but has a higher sample rate.
            */
           //if (!AudioSystem.isLineSupported(info)) {
               AudioFormat nativeFormat = getNativeAudioFormat(desiredFormat, getSelectedMixer(selectedMixerIndex));
               if (nativeFormat == null) {
                   System.out.println("couldn't find suitable target audio format");
               } else {
                   finalFormat = nativeFormat;

                   /* convert from native to the desired format if supported */
                   boolean doConversion = AudioSystem.isConversionSupported
                           (desiredFormat, nativeFormat);

                   if (doConversion) {
                       System.out.println
                               ("Converting from " + finalFormat.getSampleRate()
                                       + "Hz to " + desiredFormat.getSampleRate() + "Hz");
                   } else {
                       System.out.println
                               ("Using native format: Cannot convert from " +
                                       finalFormat.getSampleRate() + "Hz to " +
                                       desiredFormat.getSampleRate() + "Hz");
                   }
               }
           //} else {
           //    System.out.println("Desired format: " + desiredFormat + " supported.");
           //    finalFormat = desiredFormat;
           //}
          return finalFormat;
      }



      
       public static AudioFormat getNativeAudioFormat(AudioFormat format,
                                                      Mixer mixer) {
           Line.Info[] lineInfos;

           if (mixer != null) {
               lineInfos = mixer.getTargetLineInfo
                       (new Line.Info(TargetDataLine.class));
           } else {
               lineInfos = AudioSystem.getTargetLineInfo
                       (new Line.Info(TargetDataLine.class));
           }

           AudioFormat nativeFormat = null;

           // find a usable target line
           for (int i = 0; i < lineInfos.length; i++) {

               AudioFormat[] formats =
                       ((TargetDataLine.Info) lineInfos[i]).getFormats();

               for (int j = 0; j < formats.length; j++) {

                   // for now, just accept downsampling, not checking frame
                   // size/rate (encoding assumed to be PCM)

                   AudioFormat thisFormat = formats[j];
                   if (thisFormat.getEncoding() == format.getEncoding()
                           && thisFormat.isBigEndian() == format.isBigEndian()
                           && thisFormat.getSampleSizeInBits() ==
                           format.getSampleSizeInBits()
                           && thisFormat.getSampleRate() >= format.getSampleRate()) {
                       nativeFormat = thisFormat;
                       break;
                   }
               }
               if (nativeFormat != null) {
                   //no need to look through remaining lineinfos
                   break;
               }
           }
           return nativeFormat;
       }


       public static final String dumpLineInfo(String selectedMixerIndex) {
           System.out.println("Selected Mixer: " + selectedMixerIndex);
           if ("".equals(selectedMixerIndex) || selectedMixerIndex == null) return "";
           Mixer mixer = getSelectedMixer(selectedMixerIndex);
           if (mixer == null) {
               return "Default Audio device.";
           }
           Line.Info[] lineInfo = mixer.getTargetLineInfo();
           StringBuilder info = new StringBuilder("");
           if (lineInfo != null) {
                for (int i = 0; i < lineInfo.length; i++) {
                    if (lineInfo[i] instanceof DataLine.Info) {
                        AudioFormat[] formats =
                                ((DataLine.Info) lineInfo[i]).getFormats();
                        for (int j = 0; j < formats.length; j++) {
                            info.append("-" + formats[j] + "\n");
                        }
                    } else if (lineInfo[i] instanceof Port.Info) {
                        info.append("-" + lineInfo[i] + "\n");
                    }
                }
           }
           return info.toString();

       }


       public static final List<String> dumpMixers() {
           /** Lists all the available audio devices. */
           Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
           List<String> results= new ArrayList<String>();
           for (int i = 0; i < mixerInfo.length; i++) {
               Mixer mixer = AudioSystem.getMixer(mixerInfo[i]);
               StringBuilder builder = new StringBuilder();
               builder.append("Mixer[" + i + "]: \""
                       + mixerInfo[i].getName() + "\"");
               builder.append(" "
                       + mixerInfo[i].getDescription());
               results.add(builder.toString());
           }
           return results;
       }
}
