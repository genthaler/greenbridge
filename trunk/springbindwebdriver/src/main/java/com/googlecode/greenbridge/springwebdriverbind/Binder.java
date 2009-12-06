/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.springwebdriverbind;

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
import org.springframework.beans.BeanWrapperImplExposer;

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


    public void bindBean(BeanWrapperImpl bean, WebDriver wd) {
        PropertyDescriptor[] pds = bean.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : pds) {
            bindProperty(propertyDescriptor, bean, wd);
        }
    }

    public void bindProperty(String path, BeanWrapperImpl bean, WebDriver wd) {
        PropertyDescriptor pd = bean.getPropertyDescriptor(path);
        bindProperty(pd, bean, wd);
    }
    

    protected void bindProperty(PropertyDescriptor pd, BeanWrapperImpl bean, WebDriver wd) {
        if (isBindable(pd)) {
            String path = getPropertyPath(bean, pd);
            INPUT_TYPE input_type = determineInputType(path, wd);
            bind(input_type, pd, path, bean, wd);
        }
        else if (isList(pd)) {
            List list = (List)bean.getPropertyValue(pd.getName());
            if (list == null) return;
            for (int i = 0; i < list.size(); i++) {
                Object object = list.get(i);
                BeanWrapperImpl subbeanWrap = new BeanWrapperImpl(object, pd.getName() + "[" + i + "]", bean.getWrappedInstance());
                String indexPath = subbeanWrap.getNestedPath();
                checkForListCollectionListeners(i, indexPath, object, wd);
                bindBean(subbeanWrap, wd);
            }
        }
        else if (isSubBean(pd)) {
            //BeanWrapperImplExposer exposer = new BeanWrapperImplExposer(bean);
            //BeanWrapperImpl subbeanWrap = exposer.exposeBeanWrapperForPropertyPath(pd.getName());
            Object subbean = bean.getPropertyValue(pd.getName());
            BeanWrapperImpl subbeanWrap = new BeanWrapperImpl(subbean, pd.getName(), bean.getWrappedInstance());
            //exposer.exposeCopyCustomEditorsTo(subbeanWrap, pd.getName());
            bindBean(subbeanWrap, wd);
        }
    }


    protected void checkForListCollectionListeners(int index, String path, Object object, WebDriver wd) {
        for (String pathRegex : pathRegexToListCollectionListener.keySet()) {
            if (path.matches(pathRegex)) {
                ListCollectionListener listener = pathRegexToListCollectionListener.get(pathRegex);
                listener.beforeListCollectionEntry(index, object, wd);
            }
        }
    }


    protected boolean isList(PropertyDescriptor pd) {
        if (pd.getPropertyType().isAssignableFrom(List.class)) return true;
        return false;
    }


    protected boolean isSubBean(PropertyDescriptor pd) {
        if (isCollection(pd)) return false;
        if ("class".equals(pd.getName()) || "annotation".equals(pd.getName())) return false;
        return true;
    }

    protected boolean isCollection(PropertyDescriptor pd) {
        if (pd.getPropertyType().isAssignableFrom(Collection.class)) return true;
        else return false;
    }





    protected void bind(INPUT_TYPE input_type, PropertyDescriptor pd, String path, BeanWrapperImpl bean, WebDriver wd) {
        if (input_type == INPUT_TYPE.checkbox) {
            bindPathToCheckbox(path, pd, bean, wd);
        }
        if (input_type == INPUT_TYPE.radio) {
            bindPathToRadio(path, pd, bean, wd);
        }
        if (input_type == INPUT_TYPE.inputText) {
            bindPathToInputText(path, pd, bean, wd);
        }
        if (input_type == INPUT_TYPE.select) {
            bindPathToSelect(path, pd, bean, wd);
        }
    }

    protected String getPropertyPath(BeanWrapperImpl bean, PropertyDescriptor pd) {
        String path = bean.getNestedPath();
        if (path != null && !"".equals(path)) return path + "." + pd.getName();
        else return pd.getName();
    }

    protected boolean isBindable(PropertyDescriptor pd) {
       Class prop_clazz = pd.getPropertyType();
       if (prop_clazz.isAssignableFrom(String.class) || prop_clazz.isAssignableFrom(Boolean.class) || prop_clazz.isAssignableFrom(Integer.class) || prop_clazz.isAssignableFrom(Date.class)) {
            return true;
        }
       return false;
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


    protected String findPropertyValueAsString(String path,PropertyDescriptor pd, BeanWrapperImpl bean) {
        Object value = bean.getPropertyValue(pd.getName());
        if (value == null) return null;
        String value_str = value.toString();
        PropertyEditor pe = findPropertyEditor(path, pd, bean);
        if (pe != null) {
            pe.setValue(value);
            value_str = pe.getAsText();
        }
        return value_str;
    }

    protected PropertyEditor findPropertyEditor(String path, PropertyDescriptor pd, BeanWrapperImpl bean) {
        PropertyEditor pe = classToPropertyEditor.get(pd.getPropertyType());
        if (pe != null) return pe;

        for (String pathRegex : pathRegexToPropertyEditor.keySet()) {
            if (path.matches(pathRegex)) {
                return pathRegexToPropertyEditor.get(pathRegex);
            }
        }
        pe = bean.findCustomEditor(bean.getWrappedClass(), pd.getName());
        if (pe != null) return pe;
        return bean.findCustomEditor(pd.getPropertyType(), null);
    }



    protected void bindPathToRadio(String path, PropertyDescriptor pd, BeanWrapperImpl bean, WebDriver wd) {
        String value_str = findPropertyValueAsString(path, pd, bean);
        if (value_str != null) {
            wd.findElement(By.xpath("//input[@name='"+ path +"'][@type = 'radio'][@value = '" + value_str +"']")).click();
        }
    }

    protected void bindPathToSelect(String path, PropertyDescriptor pd, BeanWrapperImpl bean, WebDriver wd) {
        String value_str = findPropertyValueAsString(path, pd, bean);
        if (value_str != null) {
            new Select(wd.findElement(By.name(path))).selectByValue(value_str);
        }
    }
    protected void bindPathToInputText(String path, PropertyDescriptor pd, BeanWrapperImpl bean, WebDriver wd) {
        String value_str = findPropertyValueAsString(path, pd, bean);
        if (value_str != null) {
            wd.findElement(By.name(path)).sendKeys(value_str);
        }
    }

    protected void bindPathToCheckbox(String path, PropertyDescriptor pd, BeanWrapperImpl bean, WebDriver wd) {
        String value_str = findPropertyValueAsString(path, pd, bean);
        if (value_str != null) {
            wd.findElement(By.xpath("//input[@name='"+ path +"'][@type = 'checkbox'][@value = '" + value_str +"']")).click();
        }
    }

}
