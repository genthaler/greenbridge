/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.runner.Runner;

/**
 *
 * @author ryan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface RunWithDelegate {
        /**
	 * @return a Runner class (must have a constructor that takes a single Class to run)
	 */
	Class<? extends Runner> value();
}
