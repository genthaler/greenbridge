/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation.upload;

import com.mycompany.conversation.domain.Media;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;
import java.util.UUID;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author ryan
 */
public class PostMediaUploader implements MediaUploader {

    public static final String POST_URL = "POST_URL";

    @Override
    public Media upload(File mp3, Map properies)  throws Exception {

        String uuid = UUID.randomUUID().toString();
        String server_url = (String)properies.get(POST_URL);
        String post_url =  server_url + "/" + uuid;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(post_url);

        FileBody bin = new FileBody(mp3);

        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("bin", (ContentBody) bin);

        // It may be more appropriate to use FileEntity class in this particular
        // instance but we are using a more generic InputStreamEntity to demonstrate
        // the capability to stream out data from any arbitrary source
        //
        httppost.setEntity(reqEntity);
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        Media m = new Media();
        if (resEntity != null) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            resEntity.writeTo(baos);
            System.out.println("From Server: " + baos.toString());
            JSONObject result = JSONObject.fromObject(baos.toString());
            m.setMedia(result.getString("media"));
            m.setUrl(result.getString("url"));
            
        }

        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();
        return m;
    }

}
