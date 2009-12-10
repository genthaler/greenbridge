/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ryan
 */
public class FormBean {
    private String inputText;
    private String checkbox1;
    private String radio1;
    private String select1;

    private Boolean inputText2;
    private Boolean checkbox2;
    private Boolean radio2;
    private Boolean select2;

    private Integer inputText3;
    private Integer checkbox3;
    private Integer radio3;
    private Integer select3;

    private Date inputText4;
    private Date checkbox4;
    private Date radio4;
    private Date select4;
    private String notShown;


    private SubBean subbean = new SubBean();

    private List<SubBean> list = new ArrayList<SubBean>();

    public FormBean() {
        // create two in the list by default
        list.add(new SubBean());
        list.add(new SubBean());
    }


    /**
     * @return the inputText
     */
    public String getInputText() {
        return inputText;
    }

    /**
     * @param inputText the inputText to set
     */
    public void setInputText(String inputText) {
        this.inputText = inputText;
    }


    /**
     * @return the radio1
     */
    public String getRadio1() {
        return radio1;
    }

    /**
     * @param radio1 the radio1 to set
     */
    public void setRadio1(String radio1) {
        this.radio1 = radio1;
    }

    /**
     * @return the select1
     */
    public String getSelect1() {
        return select1;
    }

    /**
     * @param select1 the select1 to set
     */
    public void setSelect1(String select1) {
        this.select1 = select1;
    }

    /**
     * @return the checkbox1
     */
    public String getCheckbox1() {
        return checkbox1;
    }

    /**
     * @param checkbox1 the checkbox1 to set
     */
    public void setCheckbox1(String checkbox1) {
        this.checkbox1 = checkbox1;
    }

    /**
     * @return the inputText2
     */
    public Boolean getInputText2() {
        return inputText2;
    }

    /**
     * @param inputText2 the inputText2 to set
     */
    public void setInputText2(Boolean inputText2) {
        this.inputText2 = inputText2;
    }



    /**
     * @return the radio2
     */
    public Boolean getRadio2() {
        return radio2;
    }

    /**
     * @param radio2 the radio2 to set
     */
    public void setRadio2(Boolean radio2) {
        this.radio2 = radio2;
    }

    /**
     * @return the select2
     */
    public Boolean getSelect2() {
        return select2;
    }

    /**
     * @param select2 the select2 to set
     */
    public void setSelect2(Boolean select2) {
        this.select2 = select2;
    }

    /**
     * @return the inputText3
     */
    public Integer getInputText3() {
        return inputText3;
    }

    /**
     * @param inputText3 the inputText3 to set
     */
    public void setInputText3(Integer inputText3) {
        this.inputText3 = inputText3;
    }

    /**
     * @return the checkbox3
     */
    public Integer getCheckbox3() {
        return checkbox3;
    }

    /**
     * @param checkbox3 the checkbox3 to set
     */
    public void setCheckbox3(Integer checkbox3) {
        this.checkbox3 = checkbox3;
    }

    /**
     * @return the radio3
     */
    public Integer getRadio3() {
        return radio3;
    }

    /**
     * @param radio3 the radio3 to set
     */
    public void setRadio3(Integer radio3) {
        this.radio3 = radio3;
    }

    /**
     * @return the select3
     */
    public Integer getSelect3() {
        return select3;
    }

    /**
     * @param select3 the select3 to set
     */
    public void setSelect3(Integer select3) {
        this.select3 = select3;
    }

    /**
     * @return the inputText4
     */
    public Date getInputText4() {
        return inputText4;
    }

    /**
     * @param inputText4 the inputText4 to set
     */
    public void setInputText4(Date inputText4) {
        this.inputText4 = inputText4;
    }

    /**
     * @return the checkbox4
     */
    public Date getCheckbox4() {
        return checkbox4;
    }

    /**
     * @param checkbox4 the checkbox4 to set
     */
    public void setCheckbox4(Date checkbox4) {
        this.checkbox4 = checkbox4;
    }

    /**
     * @return the radio4
     */
    public Date getRadio4() {
        return radio4;
    }

    /**
     * @param radio4 the radio4 to set
     */
    public void setRadio4(Date radio4) {
        this.radio4 = radio4;
    }

    /**
     * @return the select4
     */
    public Date getSelect4() {
        return select4;
    }

    /**
     * @param select4 the select4 to set
     */
    public void setSelect4(Date select4) {
        this.select4 = select4;
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

    /**
     * @return the checkbox2
     */
    public Boolean getCheckbox2() {
        return checkbox2;
    }

    /**
     * @param checkbox2 the checkbox2 to set
     */
    public void setCheckbox2(Boolean checkbox2) {
        this.checkbox2 = checkbox2;
    }

    /**
     * @return the notShown
     */
    public String getNotShown() {
        return notShown;
    }

    /**
     * @param notShown the notShown to set
     */
    public void setNotShown(String notShown) {
        this.notShown = notShown;
    }
}
