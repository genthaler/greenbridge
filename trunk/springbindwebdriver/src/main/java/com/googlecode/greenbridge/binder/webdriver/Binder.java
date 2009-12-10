/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.webdriver;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

/**
 *
 * @author ryan
 */
public class Binder {

    private BeanWrapperImpl cachedWrapper;
    private WebDriver cachedWebDriver;
    private Map<String,PathListener> pathRegexToPathListener = new HashMap<String,PathListener>();
    private Map<String,PropertyEditor> pathRegexToPropertyEditor = new HashMap<String,PropertyEditor>();
    private Map<Class,PropertyEditor> classToPropertyEditor = new HashMap<Class,PropertyEditor>();
    private Map<String,WebElement> cachedPathToWebElement = new HashMap<String,WebElement>();
    private Map<String,INPUT_TYPE> cachedPathToInputType = new HashMap<String, INPUT_TYPE>();
    protected int seenMarkerCount = 0;

    public enum INPUT_TYPE {inputText,radio,checkbox,select};


    public void addPathListener(String pathRegex, PathListener listener) {
        pathRegexToPathListener.put(pathRegex, listener);
    }

    public void addPropertyEditor(String pathRegex, PropertyEditor pe) {
        pathRegexToPropertyEditor.put(pathRegex, pe);
    }

    public void addPropertyEditor(Class clazz, PropertyEditor pe) {
        classToPropertyEditor.put(clazz, pe);
    }


    public Binder bind(Object bean, String path, WebDriver wd) {
        System.out.println("Binding 1.1.1");
        BeanWrapperImpl wrap = new BeanWrapperImpl(bean);
        this.cachedWrapper = wrap;
        this.cachedWebDriver = wd;
        bindProperty(path);
        return this;
    }

    public Binder bind(String path) {
        if (this.cachedWebDriver == null || this.cachedWrapper == null) throw new IllegalStateException();
        bindProperty(path);
        return this;
    }


    public void bindUnordered(Object bean, WebDriver wd) {
        BeanWrapperImpl wrap = new BeanWrapperImpl(bean);
        this.cachedWrapper = wrap;
        this.cachedWebDriver = wd;
        PathFinder finder = new PathFinder();
        List<String> paths = finder.findAllPaths(wrap);
        for (String path : paths) {
            bindProperty(path);
        }
    }

    public void bindOrdered(Object bean, String pathOfFirstPropertyToBind, WebDriver wd)  {
        bindOrdered(bean, pathOfFirstPropertyToBind, wd, 3);
    }

    public void bindOrdered(Object bean, String pathOfFirstPropertyToBind, WebDriver wd, int maxLoops)  {
        BeanWrapperImpl wrap = new BeanWrapperImpl(bean);
        this.cachedWebDriver = wd;
        this.cachedWrapper = wrap;
        PathFinder finder = new PathFinder();
        List<String> remainingPaths = finder.findAllPaths(wrap);
        prunePathsWithNullValues(remainingPaths);

        WebElement seenMarkerElement = null;
        seenMarkerCount = 0;

        WebElement current = bindProperty(pathOfFirstPropertyToBind);
        remainingPaths.remove(pathOfFirstPropertyToBind);
        
        while (remainingPaths.size() > 0 && seenMarkerCount <= maxLoops) {
            logRemainingPaths(remainingPaths);
            current = focusOnNextWebElement(current);
            String path = pathThatNextElementMatches(current, remainingPaths);
            if (path != null) {
                bindProperty(path);
                remainingPaths.remove(path);
            } else {
                if (seenMarkerElement == null) {
                    seenMarkerElement = current;
                    seenMarkerCount++;
                }
                else if (current.equals(seenMarkerElement)) {
                    seenMarkerCount++;
                    Logger.getLogger(Binder.class.getName()).log(Level.INFO, "On loop " + seenMarkerCount + " of a max of " + maxLoops);
                }
            }
            
        }
    }

    private void logRemainingPaths(List<String> remainingPaths) {
        String paths = StringUtils.collectionToCommaDelimitedString(remainingPaths);
        Logger.getLogger(Binder.class.getName()).log(Level.INFO, "Paths to locate: " + paths);
    }


    protected void prunePathsWithNullValues(List<String> remainingPaths) {
        List<String> toPrune = new ArrayList<String>();
        for (String path : remainingPaths) {
            Object value = cachedWrapper.getPropertyValue(path);
            if (value == null) toPrune.add(path);
        }
        remainingPaths.removeAll(toPrune);
    }


    // pull one odd
    protected WebElement focusOnNextWebElement(WebElement current)  {
        if (current == null) return null;
        try {
            Thread.sleep(100);
            current.sendKeys(Keys.TAB);
            Thread.sleep(100);
        } catch (InterruptedException ex) {}

        WebElement next = cachedWebDriver.switchTo().activeElement();
        return next;
    }

    protected String pathThatNextElementMatches(WebElement next, List<String> remainingPaths) {
        for (String path : remainingPaths) {
            INPUT_TYPE found_type = determineInputType(path);
            WebElement match = getWebElement(found_type, path);
            if (isRadio(next) && found_type == INPUT_TYPE.radio && isSameName(next, match)) {
                return path;
            } else {
                if (match != null && match.equals(next)) return path;
            }
        }
        return null;
    }

    private boolean isSameName(WebElement we1, WebElement we2) {
        try {
            if (we1.getAttribute("name").equalsIgnoreCase(we2.getAttribute("name"))) return true;
        } catch (Exception ignore) {}
        return false;
    }

    private boolean isRadio(WebElement we) {
        try {
            if (we.getAttribute("type").equalsIgnoreCase("radio")) return true;
        } catch (Exception ignore) {}
        return false;
    }

    protected WebElement bindProperty(String path) {
        INPUT_TYPE input_type = determineInputType(path);
        checkForPathListeners(path, cachedWrapper.getPropertyValue(path), false);
        WebElement result =  bind(input_type, path);
        checkForPathListeners(path, cachedWrapper.getPropertyValue(path), true);
        return result;

    }




    protected void checkForPathListeners(String path, Object object, boolean after) {
        for (String pathRegex : pathRegexToPathListener.keySet()) {
            if (path.matches(pathRegex)) {
                PathListener listener = pathRegexToPathListener.get(pathRegex);
                if (!after) listener.beforePathDataBind(path, object, cachedWebDriver);
                else listener.afterPathDataBind(path, object, cachedWebDriver);
            }
        }
    }


    protected WebElement bind(INPUT_TYPE input_type, String path) {
        if (input_type == INPUT_TYPE.checkbox) {
            return bindPathToCheckbox(path);
        }
        if (input_type == INPUT_TYPE.radio) {
            return bindPathToRadio(path);
        }
        if (input_type == INPUT_TYPE.inputText) {
            return bindPathToInputText(path);
        }
        if (input_type == INPUT_TYPE.select) {
            return bindPathToSelect(path);
        }
        return null;
    }

    protected WebElement getWebElement(INPUT_TYPE input_type, String path) {
        
        if (input_type == INPUT_TYPE.checkbox) {
            return findWebElementForCheckbox(path);
        }
        if (input_type == INPUT_TYPE.radio) {
            return findWebElementForRadio(path);
        }
        if (input_type == INPUT_TYPE.inputText) {
            return findWebElementForInputText(path);
        }
        if (input_type == INPUT_TYPE.select) {
            return findWebElementForSelect(path);
        }
        return null;
    }



    protected INPUT_TYPE determineInputType(String path ) {
        if (cachedPathToInputType.containsKey(path)) return cachedPathToInputType.get(path);
        INPUT_TYPE result = null;
        if (isCheckbox(path)) result =  INPUT_TYPE.checkbox;
        if (isRadio(path)) result =  INPUT_TYPE.radio;
        if (isSelect(path)) result =  INPUT_TYPE.select;
        if (isInputText(path)) result =  INPUT_TYPE.inputText;
        if (result != null) {
            cachedPathToInputType.put(path, result);
        }
        return result;
    }

    protected boolean isCheckbox(String path) {
        try {
            List<WebElement> elements = cachedWebDriver.findElements(By.xpath("//input[@name='"+ path +"'][@type = 'checkbox']"));
            if (elements.size() > 0 ) return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    protected boolean isRadio(String path) {
        try {
            List<WebElement> elements = cachedWebDriver.findElements(By.xpath("//input[@name='"+ path +"'][@type = 'radio']"));
            if (elements.size() > 0 ) return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    protected boolean isInputText(String path) {
        try {
            List<WebElement> elements = cachedWebDriver.findElements(By.xpath("//input[@name='"+ path +"'][@type = 'text']"));
            if (elements.size() > 0 ) return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    protected boolean isSelect(String path) {
        try {
            List<WebElement> elements = cachedWebDriver.findElements(By.xpath("//select[@name='"+ path +"']"));
            if (elements.size() > 0 ) return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    protected String findPropertyValueAsString(String path) {
        Object value = cachedWrapper.getPropertyValue(path);
        if (value == null) return null;
        String value_str = value.toString();
        PropertyEditor pe = findPropertyEditor(path);
        if (pe != null) {
            pe.setValue(value);
            value_str = pe.getAsText();
        }
        return value_str;
    }

    protected PropertyEditor findPropertyEditor(String path) {
        PropertyEditor pe = classToPropertyEditor.get(cachedWrapper.getPropertyType(path));
        if (pe != null) return pe;

        for (String pathRegex : pathRegexToPropertyEditor.keySet()) {
            if (path.matches(pathRegex)) {
                return pathRegexToPropertyEditor.get(pathRegex);
            }
        }
        return null;
    }


    protected WebElement findWebElementForRadio(String path) {
        if (cachedPathToWebElement.containsKey(path)) return cachedPathToWebElement.get(path);
        String value_str = findPropertyValueAsString(path);
        WebElement we = null;
        if (value_str != null) {
            try {
                we = cachedWebDriver.findElement(By.xpath("//input[@name='"+ path +"'][@type = 'radio'][@value = '" + value_str +"']"));
                cachedPathToWebElement.put(path, we);
            } catch(Exception ignore) {}
        }
        return we;
    }

    protected WebElement bindPathToRadio(String path) {
        String value_str = findPropertyValueAsString(path);
        WebElement we = findWebElementForRadio(path);
        if (value_str != null && we != null) {
            we.click();
        }
        return we;
    }

     protected WebElement findWebElementForSelect(String path) {
        if (cachedPathToWebElement.containsKey(path)) return cachedPathToWebElement.get(path);
        String value_str = findPropertyValueAsString(path);
        WebElement we = null;
        if (value_str != null) {
            try {
                we = cachedWebDriver.findElement(By.name(path));
                cachedPathToWebElement.put(path, we);
            } catch(Exception ignore) {}
        }
        return we;
    }

    protected WebElement bindPathToSelect(String path) {
        String value_str = findPropertyValueAsString(path);
        WebElement we = findWebElementForSelect(path);
        if (value_str != null && we != null) {
            new Select(we).selectByValue(value_str);
        }
        return we;
    }

     protected WebElement findWebElementForInputText(String path) {
        if (cachedPathToWebElement.containsKey(path)) return cachedPathToWebElement.get(path);
        String value_str = findPropertyValueAsString(path);
        WebElement we = null;
        if (value_str != null) {
            try {
                we = cachedWebDriver.findElement(By.name(path));
                cachedPathToWebElement.put(path, we);
            } catch(Exception ignore) {}
        }
        return we;
    }

    protected WebElement bindPathToInputText(String path) {
        String value_str = findPropertyValueAsString(path);
        WebElement we = findWebElementForInputText(path);
        if (value_str != null && we != null) {
            we.sendKeys(value_str);
        }
        return we;
    }

    protected WebElement findWebElementForCheckbox(String path) {
        if (cachedPathToWebElement.containsKey(path)) return cachedPathToWebElement.get(path);
        String value_str = findPropertyValueAsString(path);
        WebElement we = null;
        if (value_str != null) {
            try {
                we = cachedWebDriver.findElement(By.xpath("//input[@name='"+ path +"'][@type = 'checkbox'][@value = '" + value_str +"']"));
                cachedPathToWebElement.put(path, we);
            } catch(Exception ignore) {}
        }
        return we;
    }
    protected WebElement bindPathToCheckbox(String path) {
        String value_str = findPropertyValueAsString(path);
        WebElement we = findWebElementForCheckbox(path);
        if (value_str != null && we != null) {
            we.click();
        }
        return we;
    }

}
