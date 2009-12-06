/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.springwebdriverbind;

import com.googlecode.greenbridge.web.FormBean;
import com.googlecode.greenbridge.web.SubBean;
import com.googlecode.greenbridge.springwebdriverbind.Binder.INPUT_TYPE;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class IntegrationTest {
    private static FirefoxDriver wd;
    private static String base = "http://localhost:8181/test/form.html";
    private static String output_dir_txt = "target/binding";
    private static File output_dir;

    public IntegrationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        wd = new FirefoxDriver();
        output_dir = new File(output_dir_txt);
        output_dir.mkdirs();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        wd.close();
    }

    @Test
    public void doTest() throws FileNotFoundException, ParseException {
        String testId = "test1";

        wd.get(base);
        wd.findElement(By.name("testId")).sendKeys(testId);
        Binder binder = new Binder();
        FormBean bean = new FormBean();
        BeanWrapperImpl wrap = new BeanWrapperImpl(bean);
        CustomBooleanEditor boolEdit = new CustomBooleanEditor("Y", "N", true);
        wrap.registerCustomEditor(FormBean.class, "inputText2", boolEdit);
        wrap.registerCustomEditor(FormBean.class, "radio2", boolEdit);
        wrap.registerCustomEditor(FormBean.class, "select2", boolEdit);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
        wrap.registerCustomEditor(FormBean.class, "inputText4", dateEditor);
        wrap.registerCustomEditor(FormBean.class, "radio4", dateEditor);
        wrap.registerCustomEditor(FormBean.class, "select4", dateEditor);



        bean.setInputText("A test");
        INPUT_TYPE inputText_type = binder.determineInputType("inputText", wd);
        assertEquals(INPUT_TYPE.inputText, inputText_type);
        binder.bindProperty("inputText", wrap, wd);

        bean.setCheckbox1("b");
        INPUT_TYPE checkbox_type = binder.determineInputType("checkbox1", wd);
        assertEquals(INPUT_TYPE.checkbox, checkbox_type);
        binder.bindProperty("checkbox1", wrap, wd);



        bean.setRadio1("c");
        INPUT_TYPE radio_type = binder.determineInputType("radio1", wd);
        assertEquals(INPUT_TYPE.radio, radio_type);
        binder.bindProperty("radio1", wrap, wd);


        bean.setSelect1("c");
        INPUT_TYPE select_type = binder.determineInputType("select1", wd);
        assertEquals(INPUT_TYPE.select, select_type);        
        binder.bindProperty("select1", wrap, wd);



        bean.setInputText2(Boolean.TRUE);
        INPUT_TYPE input2_type = binder.determineInputType("inputText2", wd);
        assertEquals(INPUT_TYPE.inputText, input2_type);
        binder.bindProperty("inputText2", wrap, wd);

        bean.setRadio2(Boolean.TRUE);
        INPUT_TYPE radio2_type = binder.determineInputType("radio2", wd);
        assertEquals(INPUT_TYPE.radio, radio2_type);
        binder.bindProperty("radio2", wrap, wd);

        bean.setSelect2(Boolean.FALSE);
        INPUT_TYPE select2_type = binder.determineInputType("select2", wd);
        assertEquals(INPUT_TYPE.select, select2_type);
        binder.bindProperty("select2", wrap, wd);


        bean.setInputText3(5);
        binder.bindProperty("inputText3", wrap, wd);

        bean.setRadio3(2);
        binder.bindProperty("radio3", wrap, wd);

        bean.setSelect3(3);
        binder.bindProperty("select3", wrap, wd);

        bean.setInputText4(dateFormat.parse("02/02/2001"));
        binder.bindProperty("inputText4", wrap, wd);

        bean.setRadio4(dateFormat.parse("31/12/2009"));
        binder.bindProperty("radio4", wrap, wd);

        bean.setSelect4(dateFormat.parse("02/12/2009"));
        binder.bindProperty("select4", wrap, wd);

        bean.getSubbean().setProp1("Sub 1");
        BeanWrapperImpl subbean = new BeanWrapperImpl(bean.getSubbean(), "subbean", bean);
        binder.bindProperty("prop1", subbean, wd);

        subbean.registerCustomEditor(SubBean.class, "date1", dateEditor);
        bean.getSubbean().setDate1(dateFormat.parse("01/01/2007"));
        binder.bindProperty("date1", subbean, wd);


        wd.findElement(By.id("next")).click();
        assertEquals("Done", wd.findElement(By.id("done")).getText());

        XStream xstream = new XStream();
        File resultFile = new File(output_dir, testId + ".xml");
        FileReader reader = new FileReader(resultFile);
        FormBean result = (FormBean)xstream.fromXML(reader);
        assertEquals(bean.getInputText(), result.getInputText());
        assertEquals(bean.getCheckbox1(), result.getCheckbox1());
        assertEquals(bean.getRadio1(), result.getRadio1());
        assertEquals(bean.getSelect1(), result.getSelect1());
        assertEquals(bean.getInputText2(), result.getInputText2());
        assertEquals(bean.getRadio2(), result.getRadio2());
        assertEquals(bean.getSelect2(), result.getSelect2());
        assertEquals(bean.getInputText3(), result.getInputText3());
        assertEquals(bean.getRadio3(), result.getRadio3());
        assertEquals(bean.getSelect3(), result.getSelect3());
        assertEquals(bean.getInputText4(), result.getInputText4());
        assertEquals(bean.getRadio4(), result.getRadio4());
        assertEquals(bean.getSelect4(), result.getSelect4());
        assertEquals(bean.getSubbean().getProp1(), result.getSubbean().getProp1());
        assertEquals(bean.getSubbean().getDate1(), result.getSubbean().getDate1());
    }



    @Test
    public void bindBean() throws FileNotFoundException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String testId = "bindBean";
        wd.get(base);
        wd.findElement(By.name("testId")).sendKeys(testId);
        Binder binder = new Binder();
        FormBean bean = new FormBean();
        bean.setInputText("Cool, this is!");
        bean.setCheckbox1("b");
        bean.setRadio1("b");
        bean.setSelect1("c");

        bean.setInputText2(Boolean.TRUE);
        bean.setRadio2(Boolean.TRUE);
        bean.setSelect2(Boolean.FALSE);

        bean.setInputText3(5);
        bean.setRadio3(2);
        bean.setSelect3(3);

        bean.setInputText4(dateFormat.parse("02/02/2001"));
        bean.setRadio4(dateFormat.parse("31/12/2009"));
        bean.setSelect4(dateFormat.parse("02/12/2009"));


        bean.getSubbean().setProp1("Super D Duper");
        bean.getSubbean().setDate1(dateFormat.parse("01/01/2007"));

        bean.getList().get(0).setProp1("A1");
        binder.addListCollectionListener("list.*", new ListCollectionListener() {
            @Override
            public void beforeListCollectionEntry(int index, Object element, WebDriver wd) {
                wd.findElement(By.id("addRow")).click();
            }
        });


        BeanWrapperImpl wrap = new BeanWrapperImpl(bean);
        CustomBooleanEditor boolEdit = new CustomBooleanEditor("Y", "N", true);
        binder.addPropertyEditor(Boolean.class, boolEdit);
        
        dateFormat.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
        binder.addPropertyEditor(Date.class, dateEditor);



        binder.bindBean(wrap, wd);

        wd.findElement(By.id("next")).click();
        assertEquals("Done", wd.findElement(By.id("done")).getText());

        XStream xstream = new XStream();
        File resultFile = new File(output_dir, testId + ".xml");
        FileReader reader = new FileReader(resultFile);
        FormBean result = (FormBean)xstream.fromXML(reader);
        assertEquals(bean.getInputText(), result.getInputText());
        assertEquals(bean.getCheckbox1(), result.getCheckbox1());
        assertEquals(bean.getRadio1(), result.getRadio1());
        assertEquals(bean.getSelect1(), result.getSelect1());
        assertEquals(bean.getInputText2(), result.getInputText2());
        assertEquals(bean.getRadio2(), result.getRadio2());
        assertEquals(bean.getSelect2(), result.getSelect2());
        assertEquals(bean.getInputText3(), result.getInputText3());
        assertEquals(bean.getRadio3(), result.getRadio3());
        assertEquals(bean.getSelect3(), result.getSelect3());

        assertEquals(bean.getSubbean().getProp1(), result.getSubbean().getProp1());
        assertEquals(bean.getSubbean().getDate1(), result.getSubbean().getDate1());
        assertEquals(bean.getList().get(0).getProp1(), result.getList().get(0).getProp1());
    }


    @Test
    public void testNulls() throws FileNotFoundException {
        String testId = "nullTestBinding";
        wd.get(base);
        wd.findElement(By.name("testId")).sendKeys(testId);
        Binder binder = new Binder();
        FormBean bean = new FormBean();
        bean.setInputText(null);
        bean.setCheckbox1(null);
        bean.setRadio1(null);
        bean.setSelect1(null);
        BeanWrapperImpl wrap = new BeanWrapperImpl(bean);


        binder.bindBean(wrap, wd);

        wd.findElement(By.id("next")).click();
        assertEquals("Done", wd.findElement(By.id("done")).getText());

        XStream xstream = new XStream();
        File resultFile = new File(output_dir, testId + ".xml");
        FileReader reader = new FileReader(resultFile);
        FormBean result = (FormBean)xstream.fromXML(reader);
        assertEquals("", result.getInputText());
        assertEquals(bean.getCheckbox1(), result.getCheckbox1());
        assertEquals(bean.getRadio1(), result.getRadio1());
        assertEquals("a", result.getSelect1());
    }

}