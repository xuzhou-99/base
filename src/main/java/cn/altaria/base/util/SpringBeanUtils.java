package cn.altaria.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtils.applicationContext == null) {
            SpringBeanUtils.applicationContext = applicationContext;
        }
    }

    /**
     * @apiNote 获取applicationContext
     * @author hongsir 2017/11/3 19:40:00
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @apiNote 通过name获取 Bean.
     * @author hongsir 2017/11/3 19:39:00
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * @apiNote 通过class获取Bean.
     * @author hongsir 2017/11/3 19:39:00
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * @apiNote 通过name, 以及Clazz返回指定的Bean
     * @author hongsir 2017/11/3 19:39:00
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
