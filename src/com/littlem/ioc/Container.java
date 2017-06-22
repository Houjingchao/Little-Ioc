package com.littlem.ioc;

import com.littlem.ioc.annotation.Autowired;
import com.littlem.ioc.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 接口ContainerInf的实现
 * Created by LittleM on 2017/6/18.
 */
public class Container implements ContainerInf {

    /**
     * 保存所有的beans 格式为：
     * com.littlem.ioc...:balabala
     */
    private Map<String, Object> beans;
    /**
     * 存储bean 和name的关系：
     * name:com.little.ioc....
     */
    private Map<String, String> beanKeys;

    public Container() {
        this.beans = new ConcurrentHashMap<String, Object>();
        this.beanKeys = new ConcurrentHashMap<String, String>();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        String name = clazz.getName();
        Object object = beans.get(name);
        if (null != object) {
            return (T) object;
        }
        return null;
    }

    @Override
    public <T> T getBeanByName(String name) {
        String className = beanKeys.get(name);
        Object object = beans.get(className);
        if (null != object) {
            return (T) object;
        }
        return null;
    }

    @Override
    public Object registerBean(Object bean) {
        String name = bean.getClass().getName();
        beanKeys.put(name, name);
        beans.put(name, bean);
        return bean;
    }

    @Override
    public Object registerBean(Class<?> clazz) {
        String name = clazz.getName();
        beanKeys.put(name, name);
        Object bean = ReflectUtil.newInstance(clazz);
        beans.put(name, bean);
        return bean;
    }

    @Override
    public Object registerBean(String name, Object bean) {
        String className = bean.getClass().getName();
        beanKeys.put(name, className);
        beans.put(className, bean);
        return bean;
    }

    @Override
    public void remove(Class<?> clazz) {
        String className = clazz.getName();
        if (null != className && !className.equals("")) {
            beanKeys.remove(className);
            beans.remove(className);
        }
    }

    @Override
    public void removeByName(String name) {
        String className = beanKeys.get(name);
        if (null != className && !className.equals("")) {
            beanKeys.remove(name);
            beans.remove(className);
        }
    }

    @Override
    public Set<String> getBeanNames() {
        return beanKeys.keySet();
    }

    /**
     * 初始化bean
     */
    @Override
    public void init() {
        beans.forEach((k, v) -> {
                    Object object = k;
                    inject(object);
                }
        );
    }

    /**
     * 注入对象
     * 所有字段
     */
    public void inject(Object object) {

        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                Autowired autowired = field.getAnnotation(Autowired.class);
                if (null != autowired) {
                    Object autoWiredField = null;
                    String name = autowired.name();
                    if (!name.equals("")) {//当不空的时候
                        String className = beanKeys.get(name);
                        if (null != className && !className.equals("")) {
                            autoWiredField = beans.get(className);
                        }

                        if (null == autoWiredField) {
                            throw new RuntimeException("load failed:" + name);
                        }
                    } else {
                        if (autowired.value() == Class.class) {
                            autoWiredField = recursiveAssembly(field.getType());
                        } else {
                            autoWiredField = this.getBean(autowired.value());
                            if (null == autoWiredField) {
                                autoWiredField = recursiveAssembly(autowired.value());
                            }
                        }
                    }
                    if (null == autoWiredField) {
                        throw new RuntimeException("Unable to load " + field.getType().getCanonicalName());
                    }
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    field.set(object, autoWiredField);
                    field.setAccessible(accessible);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object recursiveAssembly(Class<?> clazz) {
        if (null != clazz) {
            return this.registerBean(clazz);
        }
        return null;
    }

}
