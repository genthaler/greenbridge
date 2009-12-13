/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.list.GrowthList;
import org.apache.commons.collections.list.LazyList;

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
    private String nonNumber;
    private Integer number2;
    private List<Date> dates = new ArrayList<Date>();
    private List<SubBean> beans = new ArrayList<SubBean>();

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

    /**
     * @return the nonNumber
     */
    public String getNonNumber() {
        return nonNumber;
    }

    /**
     * @param nonNumber the nonNumber to set
     */
    public void setNonNumber(String nonNumber) {
        this.nonNumber = nonNumber;
    }

    /**
     * @return the number
     */
    public Integer getNumber2() {
        return number2;
    }

    /**
     * @param number the number to set
     */
    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }

    /**
     * @return the dates
     */
    public List<Date> getDates() {
        return dates;
    }

    /**
     * @param dates the dates to set
     */
    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    /**
     * @return the beans
     */
    public List<SubBean> getBeans() {
        return beans;
    }

    /**
     * @param beans the beans to set
     */
    public void setBeans(List<SubBean> beans) {
        this.beans = beans;
    }
}
