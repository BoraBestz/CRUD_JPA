package com.esign.service.configuration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

  @Override
  protected MethodSecurityExpressionHandler createExpressionHandler() {
    // final DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
    final CustomMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler();
    return expressionHandler;
  }
}
