/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

import com.mycompany.conversation.domain.Conversation;
import com.mycompany.conversation.domain.Tag;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ryan
 */
public interface DocumentStorage {

    public void setStartDate(Date date);

    public Date getStartDate();
    public void setEndDate(Date date);

    public Date getEndDate();
    public File getFileLocation();


    public boolean saveCurrentDocument();

    public void setUploadURL(String url);
    public String getUploadURL();

    public Conversation convertToConversation();






}
