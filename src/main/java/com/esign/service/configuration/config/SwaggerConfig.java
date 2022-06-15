package com.esign.service.configuration.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket docket(ApiInfo apiInfo) {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("uam-api")
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo)
        .select().paths(PathSelectors.regex("/api/.*"))
        .build();
  }

  @Bean
  public ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("UAM API")
        .description("API for fetching UAM information")
        .version("1.0.0")
        .build();
  }

  @Bean
  public UiConfiguration uiConfiguration() {
    return UiConfigurationBuilder.builder()
        .deepLinking(true)
        .validatorUrl(null)
        .build();
  }
}
