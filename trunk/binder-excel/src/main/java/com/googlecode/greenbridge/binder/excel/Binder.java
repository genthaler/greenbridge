/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private Map<String,Integer> pathToColumn = new HashMap<String,Integer>();

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
        for (String path : cachedPaths) {
            String beanPath = findBeanPath(path, wrap.getWrappedInstance());
            if (beanPath != null) {
                String cachedPath = generatedCachedName(path, wrap.getWrappedInstance());
                
                int colIndex = pathToColumn.get(cachedPath);
                Object value = getValue(cols[colIndex]);
                if (value != null) {
                    
                    wrap.setPropertyValue(beanPath, value);
                }
            }
        }
        return wrap.getWrappedInstance();
    }




    protected String findBeanPath(String cell, Object bean) {
        String prefix  = "#" + bean.getClass().getSimpleName() + ".";
        if (!cell.startsWith(prefix)) return null;
        String beanPath = cell.substring(prefix.length());
        return beanPath;
    }


    protected String generatedCachedName(String path, Object bean) {
        return  path;
    }


    protected List<String> readPaths(Sheet sheet) {
        List<String> results = new ArrayList<String>();
        Cell[] cols = sheet.getRow(0);
        for (int i = 0; i < cols.length; i++) {
            Cell cell = cols[i];
            if (cell.getContents().startsWith("#")) {
                String path = cell.getContents();
                results.add(path);
                
                pathToColumn.put(path, i);

            }
        }


        return results;
    }

    private Object getValue(Cell cell) {
       
        if (cell.getContents().equals("")) return null;
        if (cell instanceof NumberCell) return((NumberCell)cell).getValue();
        if (cell instanceof BooleanCell) return ((BooleanCell)cell).getValue();
        if (cell instanceof DateCell) return ((DateCell)cell).getDate();
        
        return cell.getContents();
    }
}
