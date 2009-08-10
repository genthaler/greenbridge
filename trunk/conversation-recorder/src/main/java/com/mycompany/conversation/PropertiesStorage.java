/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.conversation;

import java.util.Map;

/**
 *
 * @author ryan
 */
public interface PropertiesStorage {

    public void storeProperty(String property, String value);
    public String loadProperty(String property);

}
