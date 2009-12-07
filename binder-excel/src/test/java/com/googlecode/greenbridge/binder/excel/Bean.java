/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ryan
 */
public class Bean {
    private String name;
    private Date birthday;
    private List<String> phone = new ArrayList<String>();
    private Boolean happy;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the phone
     */
    public List<String> getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    /**
     * @return the happy
     */
    public Boolean getHappy() {
        return happy;
    }

    /**
     * @param happy the happy to set
     */
    public void setHappy(Boolean happy) {
        this.happy = happy;
    }
}
