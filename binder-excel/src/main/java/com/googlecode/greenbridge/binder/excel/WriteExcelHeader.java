/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import jxl.Cell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;





/**
 *
 * @author ryan
 */
public class WriteExcelHeader {

    public void writeExcelHeader(File f,Object bean) throws IOException, WriteException {
        WritableWorkbook workbook = Workbook.createWorkbook(f);
        WritableSheet sheet = workbook.createSheet("Sheet1", 0);
        Cell[] row = sheet.getRow(0);
        PathFinder pf = new PathFinder(); 
        List<String> paths = pf.findAllPaths(bean);
        for (int i = 0; i < paths.size(); i++) {
            String path  = paths.get(i);
            String cellVal = "#" + bean.getClass().getSimpleName() + "." + path;
            Label label = new Label(i, 0, cellVal);
            sheet.addCell(label);
        }
        workbook.write(); 
        workbook.close();

    }
}
