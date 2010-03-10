/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ryan
 */
public class ParameterizableList<E> extends java.util.ArrayList<E> {

    Enum[] columns;


    public ParameterizableList(Class<? extends Enum> columns) {
        this.columns = columns.getEnumConstants();
    }


    public Collection generateParameters() {
        List params = new ArrayList();
        for (Iterator i = this.iterator(); i.hasNext() ;) {
            Map<String,String> row = (Map<String,String>)i.next();
            List<String> rowValues = new ArrayList();
            for (int j = 0; j < columns.length; j++) {
                Enum col = columns[j];
                rowValues.add(row.get(col.name()));
            }
            String[] param = rowValues.toArray(new String[rowValues.size()]);
            params.add(param);
        }
        return params;
    }

}
