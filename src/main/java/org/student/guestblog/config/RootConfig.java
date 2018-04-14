package org.student.guestblog.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/** The root configuration of the application. */
@Configuration
public class RootConfig {

  /**
   * Configures Gson library.
   *
   * @param gson param of gson.
   * @return org.springframework.http.converter.json.GsonHttpMessageConverter bean.
   */
  @Bean
  public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setGson(gson);
    return gsonHttpMessageConverter;
  }
}
