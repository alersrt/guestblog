package org.student.guestblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.student.guestblog.service.PostService;

import com.google.gson.Gson;

/** The root configuration of the application. */
@Configuration
public class RootConfig {

  /**
   * Defines {@link PostService} bean.
   *
   * @return bean of the {@link PostService}.
   */
  @Bean
  public PostService postService() {
    return new PostService();
  }

  /**
   * Configures Gson library.
   *
   * @param gson param of gson.
   * @return {@link GsonHttpMessageConverter} bean.
   */
  @Bean
  public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setGson(gson);
    return gsonHttpMessageConverter;
  }
}
