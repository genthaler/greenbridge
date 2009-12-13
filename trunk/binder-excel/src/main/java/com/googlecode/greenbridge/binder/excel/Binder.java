/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.Date;
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
    private Map<String,PropertyEditor> pathRegexToPropertyEditor = new HashMap<String,PropertyEditor>();
    private Map<Class,PropertyEditor> classToPropertyEditor = new HashMap<Class,PropertyEditor>();
    private Map<String,Class> pathToCellType = new HashMap<String,Class>();
    
    public void addPropertyEditor(String pathRegex, PropertyEditor pe) {
        pathRegexToPropertyEditor.put(pathRegex, pe);
    }

    public void addPathToCellType(String path, Class cellType) {
        pathToCellType.put(path, cellType);
    }



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

                Object value = getValue(cols[colIndex], wrap, beanPath);
                System.out.println("path: " + beanPath + " is value " + value);

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

    private Object getValue(Cell cell, BeanWrapperImpl wrap, String path) {
        if (cell.getContents().equals("")) return null;
        Class cellType = findCellTypeForPath(path);
        if (cellType != null) {
            if (cellType == DateCell.class) return ((DateCell)cell).getDate();
            if (cellType == NumberCell.class) return ((NumberCell)cell).getValue();
            if (cellType == BooleanCell.class) return ((BooleanCell)cell).getValue();
        }
        PropertyEditor pe = findPropertyEditor(wrap, path);
        if (pe != null) {
            pe.setAsText(cell.getContents());
            return pe.getValue();
        }
        else {
            Class type = wrap.getPropertyType(path);
            if (type == null) return cell.getContents();
            if (cell instanceof NumberCell  && type.isInstance(Number.class))  return ((NumberCell)cell).getValue();
            if (cell instanceof BooleanCell && type.isInstance(Boolean.class)) return ((BooleanCell)cell).getValue();
            if (cell instanceof DateCell    && type.isAssignableFrom(Date.class))    return ((DateCell)cell).getDate();
            return cell.getContents();
        }
    }

    protected Class findCellTypeForPath(String path) {
        for (String pathRegex : pathToCellType.keySet()) {
            if (path.matches(pathRegex)) {
                return pathToCellType.get(pathRegex);
            }
        }
        return null;
    }

    protected PropertyEditor findPropertyEditor(BeanWrapperImpl wrap, String path) {
        PropertyEditor pe = classToPropertyEditor.get(wrap.getPropertyType(path));
        if (pe != null) return pe;

        for (String pathRegex : pathRegexToPropertyEditor.keySet()) {
            if (path.matches(pathRegex)) {
                return pathRegexToPropertyEditor.get(pathRegex);
            }
        }
        return null;
    }
}
