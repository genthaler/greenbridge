/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;

import java.util.Iterator;
import java.util.Map;
import jxl.Sheet;
import org.apache.commons.collections.iterators.AbstractIteratorDecorator;
import org.apache.commons.collections.iterators.ArrayIterator;

/**
 *
 * @author ryan
 */
public class BindingIterator implements Iterator<BindingRow>{

    private Binder binder;
    private Sheet sheet;
    private int currentRow = 1;

    public BindingIterator(Sheet sheet, Binder binder) {
        this.binder = binder;
        this.sheet = sheet;
        if (sheet.getRows() ==0 ) throw new IllegalArgumentException("");
    }




    @Override
    public boolean hasNext() {
        if (currentRow < sheet.getRows()) return true;
        return false;
    }

    @Override
    public BindingRow next() {
        BindingRow row = new BindingRow(sheet, binder, currentRow++);
        return row;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cant remove a row.");
    }





}
