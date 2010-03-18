/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.webdriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.util.StringUtils;

/**
 *
 * @author ryan
 */
public class SimpleBinder {
    private WebDriver cachedWebDriver;
    private Map<String,String> cachedPathToValues;
    private Map<String,PathListener> pathRegexToPathListener = new HashMap<String,PathListener>();
    private Map<String,WebElement> cachedPathToWebElement = new HashMap<String,WebElement>();
    private Map<String,INPUT_TYPE> cachedPathToInputType = new HashMap<String, INPUT_TYPE>();
    private List<String> regexPathsToIgnoreBinding = new ArrayList<String>();
    protected int seenMarkerCount = 0;

    public enum INPUT_TYPE {inputText,radio,checkbox,select};


    public void addPathListener(String pathRegex, PathListener listener) {
        pathRegexToPathListener.put(pathRegex, listener);
    }

    public void addPathsToIgnore(List<String> regexPathsToIngnore) {
        regexPathsToIgnoreBinding.addAll(regexPathsToIngnore);

    }

    public SimpleBinder bindOrdered(Map<String,String> pathsToValues,String pathOfFirstPropertyToBind, WebDriver wd) {
        return bindOrdered(pathsToValues, pathOfFirstPropertyToBind, wd, 1);
    }

    public SimpleBinder bindOrdered(Map<String,String> pathsToValues,String pathOfFirstPropertyToBind, WebDriver wd, int maxLoops) {
        this.cachedWebDriver = wd;
        cachedPathToValues = pathsToValues;
        List<String> remainingPaths = new ArrayList<String>(pathsToValues.keySet());
        prunePathsToIngnore(remainingPaths);
        prunePathsWithNullValues(remainingPaths);

        WebElement seenMarkerElement = null;
        seenMarkerCount = 0;

        WebElement current = bindInternal(pathOfFirstPropertyToBind, pathsToValues.get(pathOfFirstPropertyToBind));
        remainingPaths.remove(pathOfFirstPropertyToBind);

        while (remainingPaths.size() > 0 && seenMarkerCount <= maxLoops) {
            logRemainingPaths(remainingPaths);
            current = focusOnNextWebElement(current);
            String path = pathThatNextElementMatches(current, remainingPaths);
            if (path != null) {
                bindInternal(path, pathsToValues.get(path));
                remainingPaths.remove(path);
            } else {
                if (seenMarkerElement == null) {
                    seenMarkerElement = current;
                    seenMarkerCount++;
                }
                else if (current.equals(seenMarkerElement)) {
                    seenMarkerCount++;
                    Logger.getLogger(SimpleBinder.class.getName()).log(Level.INFO, "On loop " + seenMarkerCount + " of a max of " + maxLoops);
                }
            }

        }
        return this;
    }


    public SimpleBinder bind(String path, String value, WebDriver wd) {
        this.cachedWebDriver = wd;
        bindInternal(path, value);
        return this;
    }

    protected WebElement bindInternal(String path, String value) {
        INPUT_TYPE input_type = determineInputType(path);
        checkForPathListeners(path, value, false);
        WebElement result =  bind(input_type, path, value);
        checkForPathListeners(path, value, true);
        return result;
    }



    private void logRemainingPaths(List<String> remainingPaths) {
        String paths = StringUtils.collectionToCommaDelimitedString(remainingPaths);
        Logger.getLogger(Binder.class.getName()).log(Level.INFO, "Paths to locate: " + paths);
    }

    protected void prunePathsWithNullValues(List<String> remainingPaths) {
        List<String> toPrune = new ArrayList<String>();
        for (String path : remainingPaths) {
            String value = cachedPathToValues.get(path);
            if (value == null) toPrune.add(path);
        }
        remainingPaths.removeAll(toPrune);
    }

    //not the fastest...double loop ouch!
    private void prunePathsToIngnore(List<String> remainingPaths) {

        for (String pathRegex : regexPathsToIgnoreBinding) {
            for (Iterator<String> pathIt = remainingPaths.iterator(); pathIt.hasNext();) {
                String path = pathIt.next();
                if (path.matches(pathRegex)) {
                    pathIt.remove();
                }
            }

        }

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
            WebElement match = getWebElement(found_type, path, cachedPathToValues.get(path));
            if (isRadio(next) && found_type == INPUT_TYPE.radio && isSameName(next, match)) {
                return path;
            } else {
                if (match != null && match.equals(next)) return path;
            }
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

    protected WebElement bind(INPUT_TYPE input_type, String path, String value) {
        if (input_type == INPUT_TYPE.checkbox) {
            return bindPathToCheckbox(path, value);
        }
        if (input_type == INPUT_TYPE.radio) {
            return bindPathToRadio(path, value);
        }
        if (input_type == INPUT_TYPE.inputText) {
            return bindPathToInputText(path, value);
        }
        if (input_type == INPUT_TYPE.select) {
            return bindPathToSelect(path, value);
        }
        return null;
    }
    protected WebElement getWebElement(INPUT_TYPE input_type, String path, String value) {

        if (input_type == INPUT_TYPE.checkbox) {
            return findWebElementForCheckbox(path, value);
        }
        if (input_type == INPUT_TYPE.radio) {
            return findWebElementForRadio(path, value);
        }
        if (input_type == INPUT_TYPE.inputText) {
            return findWebElementForInputText(path, value);
        }
        if (input_type == INPUT_TYPE.select) {
            return findWebElementForSelect(path, value);
        }
        return null;
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

    protected void checkForPathListeners(String path, Object object, boolean after) {
        for (String pathRegex : pathRegexToPathListener.keySet()) {
            if (path.matches(pathRegex)) {
                PathListener listener = pathRegexToPathListener.get(pathRegex);
                if (!after) listener.beforePathDataBind(path, object, cachedWebDriver);
                else listener.afterPathDataBind(path, object, cachedWebDriver);
            }
        }
    }

 

    protected WebElement findWebElementForRadio(String path, String value_str) {
        if (cachedPathToWebElement.containsKey(path)) return cachedPathToWebElement.get(path);
        WebElement we = null;
        if (value_str != null) {
            try {
                we = cachedWebDriver.findElement(By.xpath("//input[@name='"+ path +"'][@type = 'radio'][@value = '" + value_str +"']"));
                cachedPathToWebElement.put(path, we);
            } catch(Exception ignore) {}
        }
        return we;
    }

    protected WebElement bindPathToRadio(String path, String value_str) {
        WebElement we = findWebElementForRadio(path, value_str);
        if (value_str != null && we != null) {
            we.click();
        }
        return we;
    }

     protected WebElement findWebElementForSelect(String path, String value_str) {
        if (cachedPathToWebElement.containsKey(path)) return cachedPathToWebElement.get(path);
        WebElement we = null;
        if (value_str != null) {
            try {
                we = cachedWebDriver.findElement(By.name(path));
                cachedPathToWebElement.put(path, we);
            } catch(Exception ignore) {}
        }
        return we;
    }

    protected WebElement bindPathToSelect(String path, String value_str) {
        WebElement we = findWebElementForSelect(path, value_str);
        if (value_str != null && we != null) {
            new Select(we).selectByValue(value_str);
        }
        return we;
    }

     protected WebElement findWebElementForInputText(String path, String value_str) {
        if (cachedPathToWebElement.containsKey(path)) return cachedPathToWebElement.get(path);
        WebElement we = null;
        if (value_str != null) {
            try {
                we = cachedWebDriver.findElement(By.name(path));
                cachedPathToWebElement.put(path, we);
            } catch(Exception ignore) {}
        }
        return we;
    }

    protected WebElement bindPathToInputText(String path, String value_str) {
        WebElement we = findWebElementForInputText(path, value_str);
        if (value_str != null && we != null) {
            we.sendKeys(value_str);
        }
        return we;
    }

    protected WebElement findWebElementForCheckbox(String path, String value_str) {
        if (cachedPathToWebElement.containsKey(path)) return cachedPathToWebElement.get(path);
        WebElement we = null;
        if (value_str != null) {
            try {
                we = cachedWebDriver.findElement(By.xpath("//input[@name='"+ path +"'][@type = 'checkbox'][@value = '" + value_str +"']"));
                cachedPathToWebElement.put(path, we);
            } catch(Exception ignore) {}
        }
        return we;
    }
    protected WebElement bindPathToCheckbox(String path, String value_str) {
        WebElement we = findWebElementForCheckbox(path, value_str);
        if (value_str != null && we != null) {
            we.click();
        }
        return we;
    }

}
