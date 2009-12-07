/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.webdriver;

import com.googlecode.greenbridge.binder.webdriver.PathFinder;
import com.googlecode.greenbridge.web.FormBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class PathFinderTest {

    public PathFinderTest() {
    }



    @Test
    public void testFindAllPaths() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
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

        PathFinder finder = new PathFinder();
        List<String> paths = finder.findAllPaths(bean);
        for (String string : paths) {
            System.out.println("Found: " + string);
        }

    }

}