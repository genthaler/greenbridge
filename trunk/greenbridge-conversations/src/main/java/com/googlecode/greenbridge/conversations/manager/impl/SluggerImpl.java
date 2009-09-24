/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.manager.SlugGenerator;

/**
 *
 * @author ryan
 */
public class SluggerImpl implements SlugGenerator {

    @Override
    public String generateSlug(String text) {
       return text.toLowerCase().replaceAll("[^a-zA-Z0-9]", "-");
    }

}
