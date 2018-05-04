package org.student.guestblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/** JavaConfig for Data. */
@Configuration
@EnableMongoRepositories(basePackages = "org.student.guestblog.repository")
public class DataConfig {}
