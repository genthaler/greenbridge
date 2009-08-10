/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

import java.io.File;
import java.util.Date;

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







}
