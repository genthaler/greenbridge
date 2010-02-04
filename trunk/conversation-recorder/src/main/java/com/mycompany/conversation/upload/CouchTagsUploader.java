/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation.upload;

import com.mycompany.conversation.TagsUploader;
import com.mycompany.conversation.domain.Conversation;
import com.mycompany.conversation.domain.Tag;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;
import net.sf.json.util.JSONUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author ryan
 */
public class CouchTagsUploader implements TagsUploader{

    SimpleDateFormat dateFormat_conversation = new SimpleDateFormat("MM/dd/yyyy HH:mm:SS");
    SimpleDateFormat dateFormat_tag          = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'");


    @Override
    public void upload(String server, String db, Conversation conversation) {

        JSON json_conversation = convert(conversation);

        try {
            postConversation(conversation, json_conversation, server, db);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CouchTagsUploader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CouchTagsUploader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }






    private void postConversation(Conversation c, JSON json_conversation, String server, String db) throws UnsupportedEncodingException, IOException {
        String post_url = server + "/" + db + "/_bulk_docs"; // buld upload
        postJSON(json_conversation, post_url);
    }

    private void postTag(JSON json_tag, String server, String db) throws UnsupportedEncodingException, IOException {
        String post_url = server + "/" + db;
        postJSON(json_tag, post_url);
    }

    private String postJSON(JSON json, String post_url) throws UnsupportedEncodingException, IOException {
        HttpPost httppost = new HttpPost(post_url);
        System.out.println("Posting: " + json.toString());

        String result = doAction(httppost, json, post_url);
        System.out.println("The result: " + result);
        return result;
    }
    
    private String putJSON(JSON json, String post_url) throws UnsupportedEncodingException, IOException {
        HttpPut httpput = new HttpPut(post_url);
        String result = doAction(httpput, json, post_url);
        assert(result.contains("\"ok\":true"));
        return result;
    }


    private String doAction(HttpEntityEnclosingRequestBase type, JSON json, String post_url) throws UnsupportedEncodingException, IOException {
        HttpClient httpclient = new DefaultHttpClient();
        System.out.println("Posting to: " + post_url);
        StringEntity entity = new StringEntity(json.toString());
        entity.setContentType("application/json");
        type.setEntity(entity);
        HttpResponse response = httpclient.execute(type);
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
            ByteArrayOutputStream str_out = new ByteArrayOutputStream();
            resEntity.writeTo(str_out);
            return str_out.toString("utf-8");
        }
        return "";
    }
    
    
    protected JSON convert(Conversation conversation) {
        if (conversation == null) return null;
        Map bulk_insert = new HashMap();
        List docs = new ArrayList();
        bulk_insert.put("docs", docs);
        Map json_conversation = new HashMap();
        docs.add(json_conversation);
        json_conversation.put("_id", conversation.getId());
        if (conversation.getDescription() != null) {
            json_conversation.put("description", conversation.getDescription());
        }
        json_conversation.put("type", "conversation");
        if (conversation.getMedia() != null) {
            Map media = new HashMap();

            media.put("media", conversation.getMedia().getMedia());
            media.put("mediaLength", conversation.getMedia().getMediaLength());
            media.put("startdate", dateFormat_conversation.format(conversation.getMedia().getStartdate()));
            media.put("url", conversation.getMedia().getUrl());
            json_conversation.put("media", media);
        }

        if (conversation.getTags() != null) {
            for (Tag tag : conversation.getTags()) {
                tag.setConversation(conversation);
                Map tag_map = convert(tag);
                docs.add(tag_map);
            }
         }



        return JSONObject.fromObject(bulk_insert);
    }

    protected Map convert(Tag tag) {
        if (tag == null) return null;
        Map json_tag = new HashMap();
        if (tag.getConversation() == null || tag.getConversation().getId() == null) {
            throw new IllegalArgumentException("Tag needs a conversation with an ID set.");
        }
        json_tag.put("conversation", tag.getConversation().getId());
        json_tag.put("duration", tag.getDuration());
        json_tag.put("mediaOffset", tag.getMediaOffset());
        if (tag.getStart() != null) {
            json_tag.put("start", dateFormat_tag.format(tag.getStart()));
        }
        json_tag.put("tag", tag.getTag());
        json_tag.put("type", "tag");
        if (tag.getIcons() != null && tag.getIcons().size() > 0) {
            //String[] icons = (String[]) tag.getIcons().toArray(new String[tag.getIcons().size()]);
            json_tag.put("icons", tag.getIcons());
        }

        if (tag.getDescription() != null) {
            json_tag.put("description", tag.getDescription());
        }

        return json_tag;
    }





}
