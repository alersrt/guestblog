package org.student.guestblog;

import javax.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.student.guestblog.config.GuestblogConfig;
import org.student.guestblog.config.MvcConfig;

/**
 * @see AbstractAnnotationConfigDispatcherServletInitializer
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  /** {@inheritDoc} */
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[]{GuestblogConfig.class};
  }

  /** {@inheritDoc} */
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[]{MvcConfig.class};
  }

  /** {@inheritDoc} */
  @Override
  protected String[] getServletMappings() {
    return new String[]{"/"};
  }

  /** {@inheritDoc} */
  @Override
  protected Filter[] getServletFilters() {
    return new Filter[]{
        new HiddenHttpMethodFilter(),
        new CharacterEncodingFilter()
    };
  }
}
