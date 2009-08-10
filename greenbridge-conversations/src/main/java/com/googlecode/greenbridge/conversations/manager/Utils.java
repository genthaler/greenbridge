/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import com.googlecode.greenbridge.conversations.domain.Media;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import java.util.List;

/**
 *
 * @author ryan
 */
public class Utils {
   /**
    * Convert a formatted time string to a count in milliseconds. The method will accept time strings of the form:
    *         [s]s[.nn]
    *       [m]m:ss[.nn]
    *    [h]h:mm:ss[.nn]
    *
    * or:
    *
    *    N * s + [.nn]
    *
    * @param timeString a formatted time string representing hours, minutes, seconds & centiseconds (e.g. "1:05:17.32") or seconds & centiseconds (e.g. "1572.32").
    * @return a time in milliseconds
    */
   public static long getTimeInMilliseconds(String timeString) {
     long hours = 0;
     long minutes = 0;
     long seconds = 0;
     long milliseconds = 0;
     int firstColonIndex = timeString.indexOf(":");
     int lastColonIndex = timeString.lastIndexOf(":");
     int periodIndex = timeString.lastIndexOf(".");

     if(periodIndex != -1) {
       // Determine centiseconds.
       if(timeString.length() - periodIndex == 2) {
         milliseconds = Integer.parseInt(timeString.substring(periodIndex + 1, periodIndex + 2)) * 100;
       } else if(timeString.length() - periodIndex == 3) {
           milliseconds = Integer.parseInt(timeString.substring(periodIndex + 1, periodIndex + 3)) * 10;
       } else if(timeString.length() - periodIndex > 3) {
         milliseconds = Integer.parseInt(timeString.substring(periodIndex + 1, periodIndex + 4));
       }
     } else {
       // There are no centiseconds.
       periodIndex =  timeString.length();
     }

     if(lastColonIndex == -1) {
       // There are no hours or minutes. Seconds may exceed 59.
       seconds = Integer.parseInt(timeString.substring(0, periodIndex));
     } else {
       // Determine seconds.
       seconds = Integer.parseInt(timeString.substring(lastColonIndex + 1, periodIndex));
       // Determine minutes.
       if(firstColonIndex == lastColonIndex) {
         minutes = Integer.parseInt(timeString.substring(0, lastColonIndex));
       } else {
         minutes = Integer.parseInt(timeString.substring(firstColonIndex + 1, lastColonIndex));
       }
       // Determine hours.
       if(firstColonIndex != lastColonIndex && firstColonIndex != 0) {
         hours = Integer.parseInt(timeString.substring(0, firstColonIndex));
       }
     }

     long time = hours * 3600000L + minutes * 60000L + seconds * 1000L + milliseconds;
     return time;
   }


   public static final void setAllMediaTagsMedia(List<MediaTag> mediaTags, Media media) {
       for (MediaTag mediaTag : mediaTags) {
           mediaTag.setMedia(media);
       }
   }





}
