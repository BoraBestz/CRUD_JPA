package com.esign.service.configuration.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class InternationalizationConfig extends WebMvcConfigurerAdapter {

  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver sessionLocaleResolver = new AcceptHeaderLocaleResolver();
    sessionLocaleResolver.setDefaultLocale(Locale.US);
    return sessionLocaleResolver;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
    lci.setParamName("lang");
    return lci;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages");
    messageSource.setCacheSeconds(3600);
    return messageSource;
  }

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
    if (CollectionUtils.isEmpty(interceptors)) {
      interceptors = new ArrayList<>();
    }
    interceptors.add(new HeaderRequestInterceptor());
    restTemplate.setInterceptors(interceptors);
    return restTemplate;
  }

  @Bean
  public FilterRegistrationBean getPeticionFilter() {

    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new InternationalRequestFilter());
    registration.addUrlPatterns("/*");
    registration.setName("requestFilter");

    return registration;
  }
}
