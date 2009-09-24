
package com.googlecode.greenbridge.conversations.domain;

/**
 *
 * @author ryan
 */
public class RemoteProject extends Project {

    private String url;
    private long   refeshRateInSeconds;
    private String groovyScript;

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
     * @return the groovyScript
     */
    public String getGroovyScript() {
        return groovyScript;
    }

    /**
     * @param groovyScript the groovyScript to set
     */
    public void setGroovyScript(String groovyScript) {
        this.groovyScript = groovyScript;
    }

}
