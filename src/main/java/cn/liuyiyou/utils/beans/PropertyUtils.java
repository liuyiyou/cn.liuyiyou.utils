package cn.liuyiyou.utils.beans;

import java.lang.reflect.InvocationTargetException;

/***
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public class PropertyUtils {

    public void copyProperty(Object src,Object target){
        try {
            org.apache.commons.beanutils.PropertyUtils.copyProperties(src,target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
