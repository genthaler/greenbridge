/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.jargen.impl;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import org.junit.Test;
import org.rsquared.annob.storyharvester.impl.MockStoryHarvester;
import static org.junit.Assert.*;

/**
 *
 * @author ryan
 */
public class JarGenImplTest {
    

    @Test
    public void shouldParsePackageNames() throws IOException {
        String packageName = "org.rsquared.test";
        JarGenImpl jargen = new JarGenImpl("/", null, packageName);
        String[] packageNames = jargen.parsePackageNames();
        assertEquals(3, packageNames.length);
    }

    @Test
    public void shouldHaveTools() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        assertNotNull(compiler);

    }

    @Test
    public void shouldCompileGeneratedJava() throws IOException {
        File tempDir =   new File("target/annob");
        tempDir.mkdir();
        JarGenImpl impl = new JarGenImpl("/", tempDir, JavaFileWriterTest.packageName);
        impl.createFileStructure();
        File outJar = impl.generateRequrementsJar(MockStoryHarvester.getStories());
        assertTrue(outJar.exists());
        JarFile jar = new JarFile(outJar);
        JarEntry entry = jar.getJarEntry("org/rsquared/annob/annotation/scenarios/ENDM_130_2_2.class");
        assertNotNull(entry);
        
    }


}