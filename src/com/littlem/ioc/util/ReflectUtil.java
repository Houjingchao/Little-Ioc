package com.littlem.ioc.util;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LittleM on 2017/6/18.
 */
public class ReflectUtil {
    /**
     * 新建对象
     *
     * @param clazz
     * @return
     */
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

    public static Object newInstance(String className) {
        Object obj = null;
        try {
            Class<?> clazz = Class.forName(className);
            obj = clazz.newInstance();
        } catch (Exception e) {
        }
        return obj;
    }

    /**
     * 传入class 或者bean
     *
     * @param object
     * @param methodName
     * @return
     */
    public static Method getMethodByName(Object object, String methodName) {
        Method returnMe = null;
        if (object != null) {
            Class<?> clazz = null;
            if (object instanceof Class<?>) {
                clazz = (Class<?>) object;
            } else {
                clazz = object.getClass();
            }
            for (Method method : clazz.getMethods()) {
                if (method.getName().equals(methodName)) {
                    returnMe = method;
                    break;
                }
            }
        }
        return returnMe;
    }

    public static Method getMethodByName(Class<?> clazz, String methodName) {
        Method returnMet = null;
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                returnMet = method;
                break;
            }
        }
        return returnMet;
    }

    //判断对象是否是其中一个
    public static boolean is(Object obj, Object... mybe) {
        if (obj != null && mybe != null) {
            for (Object temObj : mybe) {
                if (obj.equals(temObj))
                    return true;
            }
        }
        return false;
    }

    /**
     * 扫描包下面所有的类
     */
    public static List<String> scanPackageClass(String rootPackageName) {
        List<String> className = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL url = classLoader.getResource(rootPackageName.replace('.', '/'));
            String protocol = url.getProtocol();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
