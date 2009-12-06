/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.springwebdriverbind;

import org.openqa.selenium.WebDriver;

/**
 *
 * @author ryan
 */
public interface ListCollectionListener {
    public void beforeListCollectionEntry(int index, Object element, WebDriver wd);
}
