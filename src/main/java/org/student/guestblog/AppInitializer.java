package org.student.guestblog;

import javax.servlet.Filter;
import org.apache.logging.log4j.web.Log4jServletFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.student.guestblog.config.MvcConfig;
import org.student.guestblog.config.RootConfig;

/** @see AbstractAnnotationConfigDispatcherServletInitializer */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  /** {@inheritDoc} */
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] {RootConfig.class};
  }

  /** {@inheritDoc} */
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] {MvcConfig.class};
  }

  /** {@inheritDoc} */
  @Override
  protected String[] getServletMappings() {
    return new String[] {"/"};
  }

  /** {@inheritDoc} */
  @Override
  protected Filter[] getServletFilters() {
    return new Filter[] {
      new CharacterEncodingFilter("UTF-8"),
      new DelegatingFilterProxy("springSecurityFilterChain"),
      new Log4jServletFilter()
    };
  }
}
