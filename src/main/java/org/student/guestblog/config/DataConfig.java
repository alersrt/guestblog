package org.student.guestblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/** Configuration for SpringData MongoDB. */
@EnableMongoAuditing
@Configuration
public class DataConfig {

  @Autowired
  private ApplicationContext applicationContext;

  /**
   * Defines bean for {@link GridFsTemplate}.
   *
   * @return {@link GridFsTemplate} object.
   */
  @Bean
  public GridFsTemplate gridFsTemplate() {
    return new GridFsTemplate(
      applicationContext.getBean(MongoDbFactory.class),
      applicationContext.getBean(MappingMongoConverter.class)
    );
  }
}
