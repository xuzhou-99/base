package cn.altaria.base.spring;

import org.springframework.core.env.Environment;

import lombok.experimental.UtilityClass;

/**
 * ConfigUtil
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2021/12/27 16:51
 */
@UtilityClass
public class ConfigUtil {

    private static Environment env;

    /**
     * 获得配置服务
     *
     * @return 环境
     */
    public static Environment getEnvironment() {
        if (env == null) {
            env = SpringBeanUtils.getBean(Environment.class);
        }
        return env;
    }

    /**
     * 获取字符串配置
     *
     * @param key 获取配置
     * @return 配置值
     */
    public static String getProperty(String key) {
        return getEnvironment().getProperty(key);
    }

    /**
     * 获取字符串配置
     *
     * @param key          获取配置
     * @param defaultValue 默认值
     * @return 配置值
     */
    public static String getProperty(String key, String defaultValue) {
        return getEnvironment().getProperty(key, defaultValue);
    }
}
