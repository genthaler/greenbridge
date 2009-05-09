package com.googlecode.greenbridge.jargen.impl;

import java.io.File;
import java.util.List;
import com.googlecode.greenbridge.StoryNarrative;
import com.googlecode.greenbridge.jargen.JarGen;

/**
 * Mock impl. Simply gives a file reference to
 * a jar on the resource path.
 *
 * @author ryan
 */
public class MockJarGen implements JarGen {
    private File jar;

    public MockJarGen(File jar) {
        this.jar = jar;
    }

    /**
     * Returns a jar on the resource path.
     * @param stories the stories. Note, these are ignored!
     * @return a jar on the resource path
     */
    @Override
    public File generateRequrementsJar(List<StoryNarrative> stories) {
        return jar;
    }

}
