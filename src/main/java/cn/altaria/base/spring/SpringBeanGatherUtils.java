package cn.altaria.base.spring;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Spring自定义Bean集合
 * {@link SpringBeanGatherUtils#springBeanGather}，key-类名称，value-对应类的所有实现类
 *
 * @author xuzhou
 * @since 2022/8/25
 */
@Component
public class SpringBeanGatherUtils implements ApplicationListener<ContextRefreshedEvent> {

    protected static final Logger log = LoggerFactory.getLogger(SpringBeanGatherUtils.class);

    protected static final Map<String, Set<Object>> springBeanGather = new HashMap<>();

    private static final AtomicBoolean IS_INIT = new AtomicBoolean(false);

    private static final AtomicBoolean IS_INIT_EVENT = new AtomicBoolean(false);

    private static boolean isInitComplete = false;

    private static ApplicationContext context;

    /**
     * Springboot所有Bean加载完成
     *
     * @return 加载完成
     */
    public static boolean isSpringBeanInitCompeted() {
        if (isInitComplete) {
            log.info("系统加载完成.");
        } else {
            log.info("系统加载中...");
        }
        return isInitComplete;
    }

    /**
     * 加载所有需要放入定制Bean容器集合的类
     * 通过{@link ISpringBeanGatherDefinition#loadBean()}，放入类Set集合
     *
     * @param context Spring上下文
     */
    public static void loadBean(ApplicationContext context) {

        if (!IS_INIT.compareAndSet(false, true)) {
            return;
        }
        log.info("开始系统定义Bean容器集合初始化...");
        // 自定义加载类
        ISpringBeanGatherDefinition gatherDefinition = context.getBean(ISpringBeanGatherDefinition.class);
        if (gatherDefinition == null) {
            return;
        }
        Set<Class<?>> classes = gatherDefinition.loadBean();
        for (Class<?> clazz : classes) {

            Map<String, ?> beansOfType = context.getBeansOfType(clazz, false, true);
            Set<Object> beanList = new LinkedHashSet<>();
            for (Map.Entry<String, ?> bean : beansOfType.entrySet()) {
                beanList.add(beansOfType.get(bean.getKey()));
                log.info("系统加载 {} 类: {}", clazz.getName(), bean.getKey());
            }
            springBeanGather.put(clazz.getName(), beanList);
        }
        log.info("系统定义Bean容器集合初始化完成...");
    }

    /**
     * 加载系统初始化任务
     * {@link ISpringBeanGatherDefinition#loadEvent()}
     *
     * @param context Spring上下文
     */
    public static void loadEvent(ApplicationContext context) {

        if (!IS_INIT_EVENT.compareAndSet(false, true)) {
            return;
        }
        log.info("开始系统定义初始化事件执行...");
        // 自定义加载类
        ISpringBeanGatherDefinition gatherDefinition = context.getBean(ISpringBeanGatherDefinition.class);
        if (gatherDefinition == null) {
            return;
        }
        gatherDefinition.loadEvent();
        log.info("开始系统定义初始化事件执行完成...");
    }

    /**
     * 通过类获取Bean容器中的所有实现类型
     *
     * @param clazz 类
     * @param <T>   T
     * @return Bean容器中所有对应的实现类
     */
    public static <T> Set<Object> getSpringBeans(Class<T> clazz) {
        if (!isInitComplete) {
            loadBean(context);
        }
        return springBeanGather.get(clazz.getName());
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        context = event.getApplicationContext();
        log.info("系统上下文加载完成");

        // 加载自定义的所有类
        loadBean(context);

        // 加载自定义的所有类
        loadEvent(context);

        isInitComplete = true;
    }
}
