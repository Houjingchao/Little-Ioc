package com.littlem.ioc;

import java.util.Set;

/**
 * Created by LittleM on 2017/6/18.
 */
public interface ContainerInf {
    public <T> T getBean(Class<T> clazz);

    public <T> T getBeanByName(String name);

    public Object registerBean(Object bean);

    public Object registerBean(Class<?> clazz);

    public Object registerBean(String name, Object bean);

    public void remove(Class<?> clazz);

    public void removeByName(String name);

    public Set<String> getBeanNames();

    /**
     * 初始化装配
     */
    public void init();
}
