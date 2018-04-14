package org.student.guestblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

/** Spring MVC configuration. */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

  /** {@inheritDoc} */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/static/**")
        .addResourceLocations("/webjars/", "/static/")
        .resourceChain(true)
        .addResolver(webJarsResourceResolver());
  }

  /**
   * Registers {@link WebJarsResourceResolver} bean.
   *
   * @return registered bean.
   */
  @Bean
  public WebJarsResourceResolver webJarsResourceResolver() {
    return new WebJarsResourceResolver();
  }
}
