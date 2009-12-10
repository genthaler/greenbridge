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
    private Integer amount1;
    private Float amount2;
    private double amount3;

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

    /**
     * @return the amount1
     */
    public Integer getAmount1() {
        return amount1;
    }

    /**
     * @param amount1 the amount1 to set
     */
    public void setAmount1(Integer amount1) {
        this.amount1 = amount1;
    }

    /**
     * @return the amount2
     */
    public Float getAmount2() {
        return amount2;
    }

    /**
     * @param amount2 the amount2 to set
     */
    public void setAmount2(Float amount2) {
        this.amount2 = amount2;
    }

    /**
     * @return the amount
     */
    public double getAmount3() {
        return amount3;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount3(double amount3) {
        this.amount3 = amount3;
    }
}
