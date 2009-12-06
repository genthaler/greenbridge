/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.web;


import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author ryan
 */
@Controller(value="form.html")
public class FormController {

    private File outputDir;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

        binder.registerCustomEditor(Boolean.class, new CustomBooleanEditor("Y", "N", true));



    }

    @RequestMapping(method = RequestMethod.GET)
    public String get(ModelMap model) {
        FormBean bean = new FormBean();
        model.addAttribute("bean", bean);

        List<String> select1_values = Arrays.asList("a", "b", "c");
        model.addAttribute("select1_values", select1_values);

        List<String> select_YN_values = Arrays.asList("Y", "N");
        model.addAttribute("select_YN_values", select_YN_values);

        List<Integer> select_INT_values = Arrays.asList(1,2,3,4);
        model.addAttribute("select_INT_values", select_INT_values);

        List<String> select_date_values = Arrays.asList("01/12/2009","02/12/2009");
        model.addAttribute("select_date_values", select_date_values);


        return "form";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String post(@ModelAttribute("bean") FormBean bean, @RequestParam("testId") String testId) throws IOException {
        System.out.println("test id: " + testId);
        System.out.println("bean.inputText: " + bean.getInputText());

        File file = new File(outputDir, testId + ".xml");
        FileWriter writer = new FileWriter(file);
        XStream xstream = new XStream();
        xstream.toXML(bean, writer);
        writer.close();

        return "complete";
    }

    /**
     * @return the outputDir
     */
    public File getOutputDir() {
        return outputDir;
    }

    /**
     * @param outputDir the outputDir to set
     */
    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

}
