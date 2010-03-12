/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.registration;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ryan
 */
public class StoryRegistration {

    private static Set<Class<? extends StoryPackage>> packages = new HashSet<Class<? extends StoryPackage>>();

    public static void registerStoryPackage(Class<? extends StoryPackage> storyPackage) {
        System.out.println("Registering story! : " + storyPackage.getName());
        packages.add(storyPackage);
    }

    public static Set<Class<? extends StoryPackage>> getStoryPackages() {
        return packages;
    }
}
