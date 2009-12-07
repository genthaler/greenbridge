/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class WriteExcelHeaderTest {

    public WriteExcelHeaderTest() {
    }



    @Test
    public void testWriteExcelHeader() throws Exception {
        Bean bean = new Bean();
        List<String> phones = new ArrayList<String>();
        phones.add("323-3232");
        bean.setPhone(phones);
        
        WriteExcelHeader writeHeader = new WriteExcelHeader();
        File f = new File("target/header.xls");
        writeHeader.writeExcelHeader(f, bean);

    }

}