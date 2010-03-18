/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;

import java.io.IOException;
import java.util.Map;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class SimpleBinderTest {

    public SimpleBinderTest() {
    }



    @Test
    public void testGetRow() throws IOException, BiffException {
        Workbook wb = Workbook.getWorkbook(BinderTest.class.getResourceAsStream("/test.xls"));
        Sheet s = wb.getSheet(0);
        SimpleBinder b = new SimpleBinder(s);
        Map<String,String> result = b.getRow(1, "Bean");
        String name = result.get("name");
        assertEquals("Ryan", name);
        System.out.println("Birthday: " +  result.get("birthday"));
        System.out.println("Amount: " +  result.get("amount1"));
    }



}