package cn.altaria.base.spring;

import java.util.Collections;
import java.util.Set;


/**
 * 自定义Bean容器集合加载定义
 *
 * @author 许周
 */
public abstract class ISpringBeanGatherDefinition {

    /**
     * 自定义所有需要加载到Bean集合中的类
     *
     * @return 自定义类集合
     */
    Set<Class<?>> loadBean() {
        return Collections.emptySet();
    }

    /**
     * 自定义系统初始化事件
     */
    void loadEvent() {

    }
}
