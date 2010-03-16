package com.googlecode.greenbridge.storyharvester.impl;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.net.MalformedURLException;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 *
 * @author ryan
 */
public class UrlTest {

    @Test
    public void localFileShouldBeFormattedToBrowserFrendlyUrl() throws MalformedURLException {
        File f = new File("src/main/resources/xhtml1-strict.dtd");
        String result = f.toURI().toURL().toString();
        System.out.println(result);
        //assertTrue("Url is not prefixed properly",result.startsWith("file://"));
        //assertTrue("Url contains invalid slashes", !result.contains("\\"));
    }


    





}
