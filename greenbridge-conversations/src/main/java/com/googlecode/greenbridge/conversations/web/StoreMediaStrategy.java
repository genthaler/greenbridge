/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.web;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ryan
 */
public interface StoreMediaStrategy {

    String store(MultipartFile media) throws IOException;

}
