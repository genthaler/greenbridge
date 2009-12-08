/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.webdriver;

import org.openqa.selenium.WebDriver;

/**
 *
 * @author ryan
 */
public interface PathListener {
    public void beforePathDataBind(String path, Object pathValue, WebDriver wd);
    public void afterPathDataBind(String path, Object pathValue, WebDriver wd);
    
}
