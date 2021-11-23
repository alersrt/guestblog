package org.student.guestblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
      .useDefaultResponseMessages(false)
      .select().apis(RequestHandlerSelectors.basePackage("org.student.guestblog"))
      .paths(PathSelectors.any())
      .build();
  }

  @Bean
  public UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
      .deepLinking(true)
      .displayOperationId(false)
      .defaultModelsExpandDepth(1)
      .defaultModelExpandDepth(1)
      .defaultModelRendering(ModelRendering.EXAMPLE)
      .displayRequestDuration(true)
      .docExpansion(DocExpansion.NONE)
      .filter(false)
      .maxDisplayedTags(null)
      .operationsSorter(OperationsSorter.ALPHA)
      .showExtensions(false)
      .tagsSorter(TagsSorter.ALPHA)
      .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
      .validatorUrl(null)
      .build();
  }
}
