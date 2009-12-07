/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.webdriver;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.BeanWrapperImpl;

/**
 *
 * @author ryan
 */
public class Binder {

    private Map<String,ListCollectionListener> pathRegexToListCollectionListener = new HashMap<String,ListCollectionListener>();
    private Map<String,PropertyEditor> pathRegexToPropertyEditor = new HashMap<String,PropertyEditor>();
    private Map<Class,PropertyEditor> classToPropertyEditor = new HashMap<Class,PropertyEditor>();

    public enum INPUT_TYPE {inputText,radio,checkbox,select};


    public void addListCollectionListener(String pathRegex, ListCollectionListener listener) {
        pathRegexToListCollectionListener.put(pathRegex, listener);
    }

    public void addPropertyEditor(String pathRegex, PropertyEditor pe) {
        pathRegexToPropertyEditor.put(pathRegex, pe);
    }

    public void addPropertyEditor(Class clazz, PropertyEditor pe) {
        classToPropertyEditor.put(clazz, pe);
    }


    public void bindBean(Object bean, WebDriver wd) {
        BeanWrapperImpl wrap = new BeanWrapperImpl(bean);
        PathFinder finder = new PathFinder();
        List<String> paths = finder.findAllPaths(wrap);
        for (String path : paths) {
            bindProperty(path, wrap, wd);
        }
    }

    public Binder bindProperty(String path, Object bean, WebDriver wd) {
        BeanWrapperImpl wrap = new BeanWrapperImpl(bean);
        bindProperty(path, wrap, wd);
        return this;
    }



    protected void bindProperty(String path, BeanWrapperImpl bean, WebDriver wd) {
        INPUT_TYPE input_type = determineInputType(path, wd);
        checkForListCollectionListeners(0, path, bean.getPropertyValue(path), wd);
        bind(input_type, path, bean, wd);

    }




    protected void checkForListCollectionListeners(int index, String path, Object object, WebDriver wd) {
        for (String pathRegex : pathRegexToListCollectionListener.keySet()) {
            if (path.matches(pathRegex)) {
                ListCollectionListener listener = pathRegexToListCollectionListener.get(pathRegex);
                listener.beforeListCollectionEntry(index, object, wd);
            }
        }
    }


    protected void bind(INPUT_TYPE input_type, String path, BeanWrapperImpl bean, WebDriver wd) {
        if (input_type == INPUT_TYPE.checkbox) {
            bindPathToCheckbox(path, bean, wd);
        }
        if (input_type == INPUT_TYPE.radio) {
            bindPathToRadio(path,  bean, wd);
        }
        if (input_type == INPUT_TYPE.inputText) {
            bindPathToInputText(path, bean, wd);
        }
        if (input_type == INPUT_TYPE.select) {
            bindPathToSelect(path, bean, wd);
        }
    }





    public INPUT_TYPE determineInputType(String path, WebDriver wd ) {
        if (isCheckbox(path, wd)) return INPUT_TYPE.checkbox;
        if (isRadio(path, wd)) return INPUT_TYPE.radio;
        if (isSelect(path, wd)) return INPUT_TYPE.select;
        if (isInputText(path, wd)) return INPUT_TYPE.inputText;

        return null;
    }

    protected boolean isCheckbox(String path, WebDriver wd) {
        try {
            List<WebElement> elements = wd.findElements(By.xpath("//input[@name='"+ path +"'][@type = 'checkbox']"));
            if (elements.size() > 0 ) return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    protected boolean isRadio(String path, WebDriver wd) {
        try {
            List<WebElement> elements = wd.findElements(By.xpath("//input[@name='"+ path +"'][@type = 'radio']"));
            if (elements.size() > 0 ) return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    protected boolean isInputText(String path, WebDriver wd) {
        try {
            List<WebElement> elements = wd.findElements(By.xpath("//input[@name='"+ path +"'][@type = 'text']"));
            if (elements.size() > 0 ) return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    protected boolean isSelect(String path, WebDriver wd) {
        try {
            List<WebElement> elements = wd.findElements(By.xpath("//select[@name='"+ path +"']"));
            if (elements.size() > 0 ) return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    protected String findPropertyValueAsString(String path, BeanWrapperImpl bean) {
        Object value = bean.getPropertyValue(path);
        if (value == null) return null;
        String value_str = value.toString();
        PropertyEditor pe = findPropertyEditor(path, bean);
        if (pe != null) {
            pe.setValue(value);
            value_str = pe.getAsText();
        }
        return value_str;
    }

    protected PropertyEditor findPropertyEditor(String path, BeanWrapperImpl bean) {
        PropertyEditor pe = classToPropertyEditor.get(bean.getPropertyType(path));
        if (pe != null) return pe;

        for (String pathRegex : pathRegexToPropertyEditor.keySet()) {
            if (path.matches(pathRegex)) {
                return pathRegexToPropertyEditor.get(pathRegex);
            }
        }
        return null;
    }



    protected void bindPathToRadio(String path, BeanWrapperImpl bean, WebDriver wd) {
        String value_str = findPropertyValueAsString(path, bean);
        if (value_str != null) {
            wd.findElement(By.xpath("//input[@name='"+ path +"'][@type = 'radio'][@value = '" + value_str +"']")).click();
        }
    }

    protected void bindPathToSelect(String path, BeanWrapperImpl bean, WebDriver wd) {
        String value_str = findPropertyValueAsString(path, bean);
        if (value_str != null) {
            new Select(wd.findElement(By.name(path))).selectByValue(value_str);
        }
    }
    protected void bindPathToInputText(String path, BeanWrapperImpl bean, WebDriver wd) {
        String value_str = findPropertyValueAsString(path, bean);
        if (value_str != null) {
            wd.findElement(By.name(path)).sendKeys(value_str);
        }
    }

    protected void bindPathToCheckbox(String path, BeanWrapperImpl bean, WebDriver wd) {
        String value_str = findPropertyValueAsString(path, bean);
        if (value_str != null) {
            wd.findElement(By.xpath("//input[@name='"+ path +"'][@type = 'checkbox'][@value = '" + value_str +"']")).click();
        }
    }

}
