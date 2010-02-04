/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

/**
 *
 * @author ryan
 */
public class Slugger {

    public static String generateSlug(String text) {
       return text.toLowerCase().replaceAll("[^a-zA-Z0-9]", "-");
    }

}
