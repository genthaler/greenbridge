/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.domain;

import java.util.Map;

/**
 *
 * @author ryan
 */
public class RemoteProject extends Project {

    private String url;
    private Class  syncClass;
    private long   refeshRateInSeconds;
    private Map    clazzProperties;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the syncClass
     */
    public Class getSyncClass() {
        return syncClass;
    }

    /**
     * @param syncClass the syncClass to set
     */
    public void setSyncClass(Class syncClass) {
        this.syncClass = syncClass;
    }

    /**
     * @return the refeshRateInSeconds
     */
    public long getRefeshRateInSeconds() {
        return refeshRateInSeconds;
    }

    /**
     * @param refeshRateInSeconds the refeshRateInSeconds to set
     */
    public void setRefeshRateInSeconds(long refeshRateInSeconds) {
        this.refeshRateInSeconds = refeshRateInSeconds;
    }

    /**
     * @return the clazzProperties
     */
    public Map getClazzProperties() {
        return clazzProperties;
    }

    /**
     * @param clazzProperties the clazzProperties to set
     */
    public void setClazzProperties(Map clazzProperties) {
        this.clazzProperties = clazzProperties;
    }

}
