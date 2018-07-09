package org.student.guestblog.profiling;

import java.lang.reflect.Proxy;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link BeanPostProcessor} for {@link Profiling} annotation. This idea was stolen from Spring
 * Ripper lectures by author of Yevgeniy Borisov.
 */
@Slf4j
@Component
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

  /** Stores beans' classes between spring context states. */
  private Map<String, Class> classMap = new HashMap<>();

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
    throws BeansException {
    Class<?> beanClass = bean.getClass();
    if (beanClass.isAnnotationPresent(Profiling.class)) {
      log.info("Profiler detected on {}", beanClass.toString());
      classMap.put(beanName, beanClass);
    }
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Class beanClass = classMap.get(beanName);
    if (beanClass != null) {
      return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
        (proxy, method, args) -> {
          log.info("Start profiling for {}...", method.toGenericString());
          LocalDateTime before = LocalDateTime.now();
          Object value = method.invoke(bean, args);
          LocalDateTime after = LocalDateTime.now();
          log.info("Profiling result for {}: {}", method.toGenericString(),
            Duration.between(before, after).toString());

          return value;
        });
    }
    return bean;
  }
}
