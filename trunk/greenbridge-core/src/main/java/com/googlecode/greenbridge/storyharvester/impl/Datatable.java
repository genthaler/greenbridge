/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ryan
 */
public class Datatable {
    private List<Map<String,String>> datatable;
    private List<String> datatableProperties;

    /**
     * @return the datatable
     */
    public List<Map<String, String>> getDatatable() {
        return datatable;
    }

    /**
     * @param datatable the datatable to set
     */
    public void setDatatable(List<Map<String, String>> datatable) {
        this.datatable = datatable;
    }

    /**
     * @return the datatableProperties
     */
    public List<String> getDatatableProperties() {
        return datatableProperties;
    }

    /**
     * @param datatableProperties the datatableProperties to set
     */
    public void setDatatableProperties(List<String> datatableProperties) {
        this.datatableProperties = datatableProperties;
    }
}
