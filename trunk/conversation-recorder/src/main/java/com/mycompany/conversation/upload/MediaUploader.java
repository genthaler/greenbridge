/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation.upload;

import com.mycompany.conversation.domain.Media;
import java.io.File;
import java.util.Map;

/**
 *
 * @author ryan
 */
public interface MediaUploader {

    public Media upload(File mp3, Map properies) throws Exception;
}
