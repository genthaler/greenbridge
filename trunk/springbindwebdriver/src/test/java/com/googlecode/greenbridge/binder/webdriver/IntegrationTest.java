/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.webdriver;

import com.googlecode.greenbridge.binder.webdriver.PathListener;
import com.googlecode.greenbridge.web.FormBean;
import com.googlecode.greenbridge.web.SubBean;
import com.googlecode.greenbridge.binder.webdriver.Binder.INPUT_TYPE;
import com.thoughtworks.xstream.XStream;
import java.io.File; 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Speed;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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
    private static WebDriver wd;
    private static String base = "http://localhost:8181/test/form.html";
    private static String output_dir_txt = "target/binding";
    private static File output_dir;
    private SimpleDateFormat dateFormat;

    public IntegrationTest() {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        //wd = new HtmlUnitDriver(true);
        //wd = new InternetExplorerDriver();
        wd = new FirefoxDriver();
        //wd.manage().setSpeed(Speed.FAST);
        output_dir = new File(output_dir_txt);
        output_dir.mkdirs();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        wd.close();
    }
    @Ignore
    @Test
    public void testBindingIndividualProperties() throws FileNotFoundException, ParseException {
        String testId = "test1";

        wd.get(base);
        wd.findElement(By.name("testId")).sendKeys(testId);

        Binder binder = new Binder();
        CustomBooleanEditor boolEdit = new CustomBooleanEditor("Y", "N", true);
        binder.addPropertyEditor(Boolean.class, boolEdit);
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
        binder.addPropertyEditor(Date.class, dateEditor);

        FormBean bean = createFormBean();

        binder.bind( bean,"inputText", wd)
                .bind("checkbox1")
                .bind("radio1")
                .bind("select1")
                .bind("inputText2")
                .bind("radio2")
                .bind("checkbox2")
                .bind("select2")
                .bind("inputText3")
                .bind("radio3")
                .bind("select3")
                .bind("inputText4")
                .bind("radio4")
                .bind("select4")
                .bind("subbean.prop1")
                .bind("subbean.date1");

        wd.findElement(By.id("addRow")).click();
        binder.bind("list[0].prop1");
        binder.bind("list[0].date1");

        wd.findElement(By.id("next")).click();
        assertEquals("Done", wd.findElement(By.id("done")).getText());

        verifyBinding(testId, bean);

    }

    @Ignore
    @Test
    public void bindBean() throws FileNotFoundException, ParseException {
        startTest("bindBean", wd);

        // Setup the binder
        Binder binder = new Binder();
        registerCustomEditors(binder);
        // add a listener to the path so the addRow button will be clicked, so
        // a new table row will be created in prep for the list binding.
        binder.addPathListener("subbean\\.date1", new AbstractPathListener() {
            public void afterPathDataBind(String path, Object pathValue, WebDriver wd) {
                wd.findElement(By.id("addRow")).click();
            }
        });

        // bind the bean!
        FormBean bean = createFormBean();
        binder.bindOrdered(bean, "inputText", wd);

        wd.findElement(By.id("next")).click();
        assertEquals("Done", wd.findElement(By.id("done")).getText());

        verifyBinding("bindBean", bean);
    }
    @Ignore
    @Test
    public void testLoopAsRequestedOnFieldMissingOnForm() throws ParseException, FileNotFoundException {
         startTest("loopOnMissingField", wd);

        // Setup the binder
        Binder binder = new Binder();
        registerCustomEditors(binder);
        // add a listener to the path so the addRow button will be clicked, so
        // a new table row will be created in prep for the list binding.
        binder.addPathListener("subbean\\.date1", new AbstractPathListener() {
            public void afterPathDataBind(String path, Object pathValue, WebDriver wd) {
                wd.findElement(By.id("addRow")).click();
            }
        });

        // bind the bean!
        FormBean bean = createFormBean();
        bean.setNotShown("Not on screen, should loop only once to find me");
        binder.bindOrdered(bean, "inputText", wd, 1);

        wd.findElement(By.id("next")).click();
        assertEquals("Done", wd.findElement(By.id("done")).getText());

        verifyBinding("bindBean", bean);
        assertEquals(2, binder.seenMarkerCount);
    }
    @Ignore
    @Test
    public void testIgnore() throws ParseException, FileNotFoundException {
         startTest("ignore", wd);

        // Setup the binder
        Binder binder = new Binder();
        registerCustomEditors(binder);
        binder.addPathsToIgnore(Arrays.asList("notShown"));
        // add a listener to the path so the addRow button will be clicked, so
        // a new table row will be created in prep for the list binding.
        binder.addPathListener("subbean\\.date1", new AbstractPathListener() {
            public void afterPathDataBind(String path, Object pathValue, WebDriver wd) {
                wd.findElement(By.id("addRow")).click();
            }
        });

        // bind the bean!
        FormBean bean = createFormBean();
        bean.setNotShown("Not on screen, now should ignore");
        binder.bindOrdered(bean, "inputText", wd, 1);

        wd.findElement(By.id("next")).click();
        assertEquals("Done", wd.findElement(By.id("done")).getText());

        verifyBinding("bindBean", bean);
        assertEquals(1, binder.seenMarkerCount);
    }


    @Test
    public void testSimpleBinder() {
        startTest("simpleBinder", wd);
        Map<String,String> data = new HashMap<String,String>();
        data.put("inputText","A test");
        data.put("checkbox1","c");
        data.put("radio1","c");
        data.put("select1","c");
        data.put("inputText2","Y");
        data.put("radio2","Y");
        data.put("select2","Y");
        data.put("inputText3","5");
        data.put("radio3","2");
        data.put("select3","3");
        data.put("inputText4","02/02/2001");
        data.put("radio4","31/12/2009");
        data.put("select4","31/12/2009");
        data.put("subbean.prop1","Sub 1");
        data.put("subbean.date1","01/01/2006");
        data.put("list[0].prop1","A1");
        data.put("list[0].date1","01/01/2006");
        SimpleBinder simple = new SimpleBinder();
        simple.bindOrdered(data, "inputText", wd);
        wd.findElement(By.id("next")).click();
        assertEquals("Done", wd.findElement(By.id("done")).getText());
    }



    protected void registerCustomEditors(Binder binder) {
        CustomBooleanEditor boolEdit = new CustomBooleanEditor("Y", "N", true);
        binder.addPropertyEditor(Boolean.class, boolEdit);
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
        binder.addPropertyEditor(Date.class, dateEditor);
    }

    protected void startTest(String testID, WebDriver wd) {
        wd.get(base);
        wd.findElement(By.name("testId")).sendKeys(testID);
    }


    protected FormBean createFormBean() throws ParseException {
        FormBean bean = new FormBean();
        bean.setInputText("A test");
        bean.setCheckbox1("b");
        bean.setRadio1("c");
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
        bean.getSubbean().setProp1("Sub 1");
        bean.getSubbean().setDate1(dateFormat.parse("01/01/2007"));
        bean.getList().get(0).setProp1("A1");
        bean.getList().get(0).setDate1(dateFormat.parse("01/01/2006"));
        return bean;
    }

    protected void verifyBinding(String testId, FormBean bean) throws FileNotFoundException {
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
        assertEquals(bean.getList().get(0).getProp1(), result.getList().get(0).getProp1());
        assertEquals(bean.getList().get(0).getDate1(), result.getList().get(0).getDate1());
    }

}