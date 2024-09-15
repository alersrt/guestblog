package org.student.guestblog.profiling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;


/**
 * {@link BeanPostProcessor} for {@link Profiling} annotation. This idea was stolen from Spring
 * Ripper lectures by author of Yevgeniy Borisov.
 */
@Component
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Stores beans' classes between spring context states.
     */
    private final Map<String, Class<?>> classMap = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
        throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            log.info(format("Profiler detected on %s", beanClass));
            classMap.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
        throws BeansException {
        Class<?> beanClass = classMap.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                (proxy, method, args) -> {
                    log.info(format("Start profiling for %s...", method.toGenericString()));
                    LocalDateTime before = LocalDateTime.now();
                    Object value = method.invoke(bean, args);
                    LocalDateTime after = LocalDateTime.now();
                    log.info(format(
                        "Profiling result for %s: %s",
                        method.toGenericString(),
                        Duration.between(before, after).toString()
                    ));

                    return value;
                });
        }
        return bean;
    }
}
