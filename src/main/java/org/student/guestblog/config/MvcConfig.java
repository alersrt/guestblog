package org.student.guestblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

/** Spring MVC configuration. */
@Configuration
public class MvcConfig {

  /**
   * Register and configures {@link WebMvcConfigurer}. There is configured resource handler,
   * locations and resolver.
   *
   * @return {@link WebMvcConfigurer} bean.
   * @see WebMvcConfigurer
   */
  @Bean
  public WebMvcConfigurer webMvcConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/**")
            .addResourceLocations("/webjars/", "/static/")
            .resourceChain(true)
            .addResolver(webJarsResourceResolver());
      }
    };
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
