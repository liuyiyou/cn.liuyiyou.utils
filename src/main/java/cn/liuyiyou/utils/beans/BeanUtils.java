package cn.liuyiyou.utils.beans;

import java.lang.reflect.InvocationTargetException;

/***
 *
 * 调用apach的方法，不过顺序和Apache不一致
 * 除BeanUtils外还有一个名为PropertyUtils的工具类，它也提供copyProperties()方法，作用与BeanUtils的同名方法十分相似，
 * 主要的区别在于后者提供类型转换功能，即发现两个JavaBean的同名属性为不同类型时，在支持的数据类型范围内进行转换，而前者不支持这个功能，但是速度会更快一些
 * @see PropertyUtils
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public abstract class BeanUtils {

    /**
     * 拷贝属性
     *
     * @param target
     * @param propertyName
     * @param propertyValue
     */
    public static void copyProperty(Object target, String propertyName, Object propertyValue) {
        try {

            org.apache.commons.beanutils.BeanUtils.copyProperty(target, propertyName, propertyValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拷贝所有属性
     *
     * @param src
     * @param target
     */
    public static void copyProperties(Object src, Object target) {
        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(target, src);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
