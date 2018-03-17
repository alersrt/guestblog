package org.student.guestblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

/**
 * Spring MVC configuration
 */
@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

  /** {@inheritDoc} */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/resources/**")
        .addResourceLocations("/resources/, /webjars/")
        .resourceChain(true)
        .addResolver(webJarsResourceResolver());
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.jsp("/WEB-INF/views/", ".jsp");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("hello");
  }

  /**
   * Defines {@link WebJarsResourceResolver} bean.
   *
   * @return {@link WebJarsResourceResolver} bean.
   */
  @Bean
  public WebJarsResourceResolver webJarsResourceResolver() {
    return new WebJarsResourceResolver();
  }
}
