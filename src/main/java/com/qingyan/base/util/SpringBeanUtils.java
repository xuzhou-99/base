package com.qingyan.base.util;

import java.util.Map;

import org.springframework.context.ConfigurableApplicationContext;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * SpringBeanUtils
 * 实现依赖查找
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2021/7/22 17:10
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringBeanUtils {

    private static final SpringBeanUtils INSTANCE = new SpringBeanUtils();

    private ConfigurableApplicationContext context;

    /**
     * get SpringBeanUtils INSTANCE
     *
     * @return SpringBeanUtils
     */
    public static SpringBeanUtils getInstance() {
        return INSTANCE;
    }

    /**
     * set ConfigurableApplicationContext
     *
     * @param context ConfigurableApplicationContext
     */
    public void setContext(ConfigurableApplicationContext context) {
        this.context = context;
    }


    /**
     * acquire spring bean
     *
     * @param requiredType class
     * @param <T>          type
     * @return bean bean
     */
    public <T> T getBean(final Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    /**
     * acquire spring bean
     *
     * @param name         bean name
     * @param requiredType class
     * @param <T>          type
     * @return bean
     */
    public <T> T getBean(final String name, final Class<T> requiredType) {
        return context.getBean(name, requiredType);
    }

    /**
     * acquire spring beans
     *
     * @param requiredType class
     * @param <T>          type
     * @return {@linkplain Map}
     */
    public <T> Map<String, T> getBeansOfType(final Class<T> requiredType) {
        return context.getBeansOfType(requiredType);
    }
}
