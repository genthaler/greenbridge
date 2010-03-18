/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.Cell;
import jxl.Sheet;

/**
 *
 * @author ryan
 */
public class SimpleBinder {

    private Map<String,Integer> columnHeaderToColumn = new HashMap<String,Integer>();
    private Sheet sheet;
    private int currentRow = 1;
    private Map<String,List<Integer>> columnsForGroup = new HashMap<String,List<Integer>>();
    private Map<Integer,String> columnToSimplePath = new HashMap<Integer,String>();
    private Map<String,Integer> nonBindingNameToColumn = new HashMap<String,Integer>();


    public SimpleBinder(Sheet sheet) {
        this.sheet = sheet;
        Cell[] cols = sheet.getRow(0);
        for (int i = 0; i < cols.length; i++) {
            Cell cell = cols[i];
             if (cell.getContents().startsWith("#")) {
                String cellName = cell.getContents();
                String groupName = findGroupName(cellName);
                if (groupName != null) {
                    addToColumnsForGroup(groupName, i);
                    String simplePath = findSimplePath(cellName, groupName);
                    columnToSimplePath.put(i, simplePath);
                }
                else {
                    nonBindingNameToColumn.put(cellName, i);
                }

            }
        }
    }

    protected String findGroupName(String cellName) {
        if (!cellName.startsWith("#")) return null;
        String name = cellName.substring(1, cellName.indexOf("."));
        return name;
    }
    
    private void addToColumnsForGroup(String groupName, Integer column) {
        List<Integer> columns = columnsForGroup.get(groupName);
        if (columns == null) {
            columns = new ArrayList<Integer>();
            columnsForGroup.put(groupName, columns);
        }
        columns.add(column);
    }
    protected String findSimplePath(String cell, String group) {
        String prefix  = "#" + group + ".";
        if (!cell.startsWith(prefix)) return null;
        String beanPath = cell.substring(prefix.length());
        return beanPath;
    }

    public Map<String,String> getRow(int row, String group) {
        Map<String,String> rowData = new HashMap<String,String>();
        List<Integer> columns = findColumnsForGroup(group);
        for (Integer column : columns) {
            String path = findPathForColumn(column);
            String value = findCellValue(row, column);
            if (value != null) rowData.put(path, value);
        }
        return rowData;
    }

    public String getNonBindingEntry(int row, String name) {
        Integer column = nonBindingNameToColumn.get(name);
        return findCellValue(row, column);
    }


    private List<Integer> findColumnsForGroup(String group) {
        return columnsForGroup.get(group);
    }

    private String findPathForColumn(Integer column) {
        return columnToSimplePath.get(column);
    }

    private String findCellValue(int row, Integer column) {
        Cell cell = sheet.getCell(column, row);
        return cell.getContents();
    }




}
