/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;

import java.util.ArrayList;
import java.util.List;
import jxl.BooleanCell;
import jxl.Cell;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import org.springframework.beans.BeanWrapperImpl;

/**
 *
 * @author ryan
 */
public class Binder {


    private List<String> cachedPaths;

    public BindingIterator interate(Sheet sheet) {
        return new BindingIterator(sheet, this);
    }

    public Object bind(Sheet sheet, Object bean, int row) {
        BeanWrapperImpl wrap = new BeanWrapperImpl(bean);
        return bind(sheet, wrap, row);
    }

    public Object bind(Sheet sheet, Class clazz, int row) {
        BeanWrapperImpl wrap = new BeanWrapperImpl(clazz);
        return bind(sheet, wrap, row);
    }


    protected Object bind(Sheet sheet, BeanWrapperImpl wrap, int row) {
        if (cachedPaths == null) {
            cachedPaths = readPaths(sheet);
        }

        Cell[] cols = sheet.getRow(row);
        int i = 0;
        for (String path : cachedPaths) {
            String beanPath = findBeanPath(path, wrap.getWrappedInstance());
            if (beanPath != null) {
                Object value = getValue(cols[i]);
                if (value != null)
                    wrap.setPropertyValue(beanPath, value);
            }
            i++;
        }
        return wrap.getWrappedInstance();
    }




    protected String findBeanPath(String cell, Object bean) {
        String prefix  = "#" + bean.getClass().getSimpleName() + ".";
        if (!cell.startsWith(prefix)) return null;
        String beanPath = cell.substring(prefix.length());
        return beanPath;
    }

    protected List<String> readPaths(Sheet sheet) {
        List<String> results = new ArrayList<String>();
        Cell[] paths = sheet.getRow(0);
        for (int i = 0; i < paths.length; i++) {
            Cell cell = paths[i];
            if (cell.getContents().startsWith("#")) {
                results.add(cell.getContents());
            }
        }


        return results;
    }

    private Object getValue(Cell cell) {
        if (cell.getContents().equals("")) return null;
        if (cell instanceof BooleanCell) return ((BooleanCell)cell).getValue();
        if (cell instanceof DateCell) return ((DateCell)cell).getDate();
        if (cell instanceof NumberCell) return((NumberCell)cell).getValue();
        return cell.getContents();
    }
}
