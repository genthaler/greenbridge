/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class BinderTest {

    public BinderTest() {
    }


    @Test
    public void testBind() throws IOException, BiffException, ParseException {
        Workbook wb = Workbook.getWorkbook(BinderTest.class.getResourceAsStream("/test.xls"));
        Sheet s = wb.getSheet(0);
        Binder b = new Binder();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        Bean bean = new Bean();
        b.bind(s, bean, 1);
        assertEquals("Ryan", bean.getName());
        assertEquals("08/08/1975", dateFormat.format(bean.getBirthday()));
        assertEquals(2, bean.getPhone().size());
        assertEquals(true, bean.getHappy());
        assertEquals(1d, (double)bean.getAmount1(), 0.2);
        assertEquals(2.3, (float)bean.getAmount2(), 0.2);
    }

    @Test
    public void testReadPaths() throws IOException, BiffException {
        Workbook wb = Workbook.getWorkbook(BinderTest.class.getResourceAsStream("/test.xls"));
        Sheet s = wb.getSheet(0);

        Binder b = new Binder();
        List<String> results = b.readPaths(s);
        assertEquals(8, results.size());
        for (String string : results) {
            System.out.println("path: " + string);
        }
    }


    @Test
    public void testPathMatchesType() {
        Bean bean = new Bean();
        String path = "#Bean.name";
        Binder b = new Binder();
        String beanPath = b.findBeanPath(path, bean);
        assertEquals("name", beanPath);
    }
 

    @Test
    public void testIterator() throws IOException, BiffException {
        Workbook wb = Workbook.getWorkbook(BinderTest.class.getResourceAsStream("/test.xls"));
        Sheet s = wb.getSheet(0);
        Binder b = new Binder();
        BindingIterator it = b.interate(s);
        {
            BindingRow row = it.next();
            Bean bean = (Bean)row.bind(Bean.class);
            assertEquals("Ryan", bean.getName());
            assertEquals(2, bean.getPhone().size());
            assertEquals(true, bean.getHappy());
        }
        {
            BindingRow row = it.next();
            Bean bean = (Bean)row.bind(Bean.class);
            assertEquals("Bill", bean.getName());
            assertEquals(0, bean.getPhone().size());
            assertEquals(false, bean.getHappy());
        }

        
    }


}