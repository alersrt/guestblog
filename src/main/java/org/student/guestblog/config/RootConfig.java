package org.student.guestblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.resource.WebJarsResourceResolver;

/** The root configuration of the application. */
@Configuration
public class RootConfig {

  /**
   * Register and configures {@link WebFluxConfigurer}. There is configured resource handler,
   * locations and resolver.
   *
   * @return {@link WebFluxConfigurer} bean.
   *
   * @see WebFluxConfigurer
   */
  @Bean
  public WebFluxConfigurer webFluxConfigurer() {
    return new WebFluxConfigurer() {
      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/**")
          .addResourceLocations("/webjars/", "classpath:/static/")
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
