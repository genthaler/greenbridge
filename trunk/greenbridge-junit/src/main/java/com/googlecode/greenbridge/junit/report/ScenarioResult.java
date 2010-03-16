/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit.report;

import com.googlecode.greenbridge.junit.report.ModuleReport.STATE;

/**
 *
 * @author ryan
 */
public class ScenarioResult {
    private String clazzHref;
    private String clazz;
    private String method;
    private String methodHref;
    private STATE state = STATE.pending;
    private String errorMessage;
    private String errorTrace;

    /**
     * @return the clazz
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * @param clazz the clazz to set
     */
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the state
     */
    public STATE getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(STATE state) {
        this.state = state;
    }

    /**
     * @return the clazzHref
     */
    public String getClazzHref() {
        return clazzHref;
    }

    /**
     * @param clazzHref the clazzHref to set
     */
    public void setClazzHref(String clazzHref) {
        this.clazzHref = clazzHref;
    }

    /**
     * @param methodHref the methodHref to set
     */
    public void setMethodHref(String methodHref) {
        this.methodHref = methodHref;
    }

    /**
     * @return the methodHref
     */
    public String getMethodHref() {
        return methodHref;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorTrace
     */
    public String getErrorTrace() {
        return errorTrace;
    }

    /**
     * @param errorTrace the errorTrace to set
     */
    public void setErrorTrace(String errorTrace) {
        this.errorTrace = errorTrace;
    }
}
