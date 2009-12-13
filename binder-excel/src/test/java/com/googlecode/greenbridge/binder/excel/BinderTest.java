/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;

import org.junit.Test;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
        b.addPathToCellType("dates.*", DateCell.class);

        Bean bean = new Bean();
        bean.setBeans(LazyList.decorate(bean.getBeans(), new Factory() {

            @Override
            public Object create() {
                return new SubBean();
            }
        }));

        b.bind(s, bean, 1);
        assertEquals("Ryan", bean.getName());
        assertEquals("08/08/1975", dateFormat.format(bean.getBirthday()));
        assertEquals(2, bean.getPhone().size());
        assertEquals(true, bean.getHappy());
        assertEquals(1d, (double)bean.getAmount1(), 0.2);
        assertEquals(2.3, (float)bean.getAmount2(), 0.2);
        assertEquals("1", bean.getNonNumber());
        assertEquals(new Integer(1), (Integer)bean.getNumber2());
        assertEquals("12/12/2007", dateFormat.format(bean.getDates().get(0)));
    }

    @Test
    public void testReadPaths() throws IOException, BiffException {
        Workbook wb = Workbook.getWorkbook(BinderTest.class.getResourceAsStream("/test.xls"));
        Sheet s = wb.getSheet(0);

        Binder b = new Binder();
        List<String> results = b.readPaths(s);
        assertEquals(12, results.size());
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
        b.addPathToCellType("dates.*", DateCell.class);
        BindingIterator it = b.interate(s);
        {
            BindingRow row = it.next();
            Bean bean = new Bean();
            bean.setBeans(LazyList.decorate(bean.getBeans(), new Factory() {
                public Object create() {
                    return new SubBean();
                }
            }));
            bean = (Bean)row.bind(bean);
            assertEquals("Ryan", bean.getName());
            assertEquals(2, bean.getPhone().size());
            assertEquals(true, bean.getHappy());
        }
        {
            BindingRow row = it.next();
            Bean bean = new Bean();
            bean.setBeans(LazyList.decorate(bean.getBeans(), new Factory() {
                public Object create() {
                    return new SubBean();
                }
            }));
            bean = (Bean)row.bind(bean);
            assertEquals("Bill", bean.getName());
            assertEquals(0, bean.getPhone().size());
            assertEquals(false, bean.getHappy());
        }

        
    }


}