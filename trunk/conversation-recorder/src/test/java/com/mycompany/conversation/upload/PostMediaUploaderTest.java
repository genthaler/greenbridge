/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation.upload;

import com.mycompany.conversation.domain.Media;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author ryan
 */
public class PostMediaUploaderTest extends TestCase {
    
    public PostMediaUploaderTest(String testName) {
        super(testName);
    }

    public void testUpload() throws Exception {
        PostMediaUploader uploader = new PostMediaUploader();
        Map props = new HashMap();
        props.put(PostMediaUploader.POST_URL, "http://localhost:8080/simple-upload/upload");
        Media m = uploader.upload(new File("D:\\rtemp\\wake.mp3"), props);
        System.out.println("media: " + m.getMedia());
        System.out.println("url:   " + m.getUrl());
    }

}
