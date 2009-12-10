/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.web;

import java.util.List;

/**
 *
 * @author ryan
 */
public class BeanWithNullStuff {
    private String name;
    private SubBean subbean;
    private List<SubBean> list;

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
     * @return the subbean
     */
    public SubBean getSubbean() {
        return subbean;
    }

    /**
     * @param subbean the subbean to set
     */
    public void setSubbean(SubBean subbean) {
        this.subbean = subbean;
    }

    /**
     * @return the list
     */
    public List<SubBean> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<SubBean> list) {
        this.list = list;
    }
}
