/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;

import jxl.Cell;
import jxl.Sheet;

/**
 *
 * @author ryan
 */
public class BindingRow {

    private Binder binder;
    private int row;
    private Sheet sheet;

    public BindingRow(Sheet sheet, Binder binder, int row) {
        this.sheet = sheet;
        this.binder = binder;
        this.row = row;
    }

    public Object bind(Class clazz) {
        return binder.bind(sheet, clazz, row);
    }

    public Cell[] getCells() {
        return sheet.getRow(row);
    }

}
