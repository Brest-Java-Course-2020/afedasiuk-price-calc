package com.epam.brest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.HashMap;

public class MainPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Main) {
            ((Main) bean).kgs = new HashMap<>();
        }
        return bean;
    }
}
