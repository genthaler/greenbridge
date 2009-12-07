/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.binder.webdriver;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.hamcrest.beans.PropertyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

/**
 *
 * @author ryan
 */
public class PathFinder {

    public List<String> findAllPaths(Object bean) {
        BeanWrapperImpl wrap = new BeanWrapperImpl(bean);
        return findAllPaths(wrap);
    }
    
    protected List<String> findAllPaths( BeanWrapperImpl wrap) {
        List<String> paths = new ArrayList<String>();
        PropertyDescriptor[] pds = wrap.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : pds) {
            List<String> propPaths = pathsForProperty(propertyDescriptor, wrap);
            paths.addAll(propPaths);
        }
        return paths;
    }

    protected List<String> pathsForProperty(PropertyDescriptor pd, BeanWrapperImpl wrap) {
        List<String> propertyPaths = new ArrayList();
        if (isList(pd)) {
            List list = (List)wrap.getPropertyValue(pd.getName());
            if (list == null) return propertyPaths;
            for (int i = 0; i < list.size(); i++) {
                Object object = list.get(i);
                BeanWrapperImpl subbeanWrap = new BeanWrapperImpl(object, pd.getName() + "[" + i + "]", wrap.getWrappedInstance());
                return findAllPaths(subbeanWrap);
            }
        }

        else if (isKnownSimpleProperty(pd)) {
            StringBuilder pathBuilder = new StringBuilder(wrap.getNestedPath());
            if (wrap.getNestedPath() != null && !"".equals(wrap.getNestedPath())) pathBuilder.append(".");
            pathBuilder.append(pd.getName());
            propertyPaths.add(pathBuilder.toString());
        }
        else if (isSubBean(pd)) {
           Object subbean = wrap.getPropertyValue(pd.getName());
           System.out.println("Subbean: " + subbean);
           BeanWrapperImpl subbeanWrap = new BeanWrapperImpl(subbean, pd.getName(), wrap.getWrappedInstance());
           return findAllPaths(subbeanWrap);
        }
        return propertyPaths;
    }

    protected boolean isKnownSimpleProperty(PropertyDescriptor pd) {
       Class prop_clazz = pd.getPropertyType();
       if (prop_clazz.isAssignableFrom(String.class) || prop_clazz.isAssignableFrom(Boolean.class) || prop_clazz.isAssignableFrom(Integer.class) || prop_clazz.isAssignableFrom(Date.class)) {
            return true;
        }
       return false;
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
}
