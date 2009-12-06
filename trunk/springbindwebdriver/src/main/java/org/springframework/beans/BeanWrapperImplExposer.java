/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.springframework.beans;

/**
 *
 * @author ryan
 */
public class BeanWrapperImplExposer extends BeanWrapperImpl {

    public BeanWrapperImplExposer(BeanWrapperImpl beanWrapper) {
        super(beanWrapper.getWrappedInstance());

    }

    public void exposeCopyCustomEditorsTo(PropertyEditorRegistry target, String path){
        super.copyCustomEditorsTo(target, null);
    }

     public BeanWrapperImpl exposeBeanWrapperForPropertyPath(String propertyPath) {
         return super.getBeanWrapperForPropertyPath(propertyPath);
     }
}
