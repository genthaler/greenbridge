/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.junit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ryan
 */
public class ResultHandler {
    public void handleResults(StoryResults results) {
        
        File root = setupOutputDir();
        String name = results.getStory().name();
        writeTextOutput(root, results, name);
        writeXmlOutput(root, results, name);
    }

    private void writeXmlOutput(File root, StoryResults results, String name) {
       JUnitXMLOutput output = new JUnitXMLOutput();
       File f = new File(root, name + output.getExtension());
        PrintWriter stream;
        try {
            stream = new PrintWriter(f);
            output.write(results, stream);
            stream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResultHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeTextOutput(File root, StoryResults results, String name) {
       TxtOutput output = new TxtOutput();
       File f = new File(root, name + output.getExtension());
        PrintWriter stream;
        try {
            stream = new PrintWriter(f);
            output.write(results, stream);
            stream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResultHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    protected File setupOutputDir() {
        File f =  new File("target/annob");
        f.mkdirs();
        return f;
    }

}
