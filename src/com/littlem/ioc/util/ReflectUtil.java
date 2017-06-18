package com.littlem.ioc.util;

/**
 * Created by LittleM on 2017/6/18.
 */
public class ReflectUtil {
    public static Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
