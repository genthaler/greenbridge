/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.junit;

import com.googlecode.greenbridge.annotation.RunWithDelegate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import com.googlecode.greenbridge.annotation.Story;
import com.googlecode.greenbridge.annotation.StoryRef;
import java.lang.reflect.Constructor;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;


/**
 *
 * @author ryan
 */
public class GreenbridgeRunner extends JUnit4ClassRunner {

    StoryRef storyRef;
    RunWithDelegate delegate;
    Runner delegateInstance;

    public GreenbridgeRunner(Class<?> arg) throws InitializationError, org.junit.internal.runners.InitializationError {
        super(arg);
        Story story = arg.getAnnotation(Story.class);
        if (story == null) throw new InitializationError("The test class '" + arg.getName() + "' is missing a @Story annotation.");
        try {
            storyRef = story.value().newInstance();
        } catch (Exception ex) {
            Logger.getLogger(GreenbridgeRunner.class.getName()).log(Level.SEVERE, null, ex);
            throw new InitializationError(ex.getMessage());
        }
        
        RunWithDelegate delegate = arg.getAnnotation(RunWithDelegate.class);
        if (delegate != null) {
            try {
                Class runnerClass = delegate.value();
                Constructor<Runner> constructor = runnerClass.getConstructor(Class.class);
                delegateInstance = constructor.newInstance(arg);
            } catch (Exception ex) {
                ex.printStackTrace();
                Logger.getLogger(GreenbridgeRunner.class.getName()).log(Level.SEVERE, null, ex);
                throw new InitializationError(ex.getMessage());
            }
        }
    }

    @Override
    protected void validate()  {
            // do nothing: validated before.
    }

    @Override
    public void run(RunNotifier rn) {
        StoryListener listener = new StoryListener(storyRef);
        rn.addListener(listener);
        if (delegateInstance != null) {
            delegateInstance.run(rn);
        } else {
            super.run(rn);
        }
        StoryResults results = listener.getStoryResults();
        results.setTestDescription(this.getDescription());
        new ResultHandler().handleResults(results);
    }

}
