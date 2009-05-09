/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import com.googlecode.greenbridge.annotation.Story;
import com.googlecode.greenbridge.annotation.StoryRef;


/**
 *
 * @author ryan
 */
public class GreenbridgeRunner extends BlockJUnit4ClassRunner {

    StoryRef storyRef;

    public GreenbridgeRunner(Class<?> arg) throws InitializationError {
        super(arg);
        Story story = arg.getAnnotation(Story.class);
        if (story != null) {
            try {
                storyRef = story.value().newInstance();
            } catch (Exception ex) {
                Logger.getLogger(GreenbridgeRunner.class.getName()).log(Level.SEVERE, null, ex);
                throw new InitializationError(ex.getMessage());
            }
        }
    }

    @Override
    public void run(RunNotifier rn) {
        StoryListener listener = new StoryListener(storyRef);
        rn.addListener(listener);
        super.run(rn);
        new ResultHandler().handleResults(listener.getStoryResults());
    }

}
