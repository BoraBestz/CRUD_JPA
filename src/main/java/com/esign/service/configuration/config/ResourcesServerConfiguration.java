package com.esign.service.configuration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

@EnableResourceServer
@Configuration
public class ResourcesServerConfiguration extends ResourceServerConfigurerAdapter {

  @Value("${security.jwt.jwk-url}")
  String jwkUrl = "";

  @Override
  public void configure(ResourceServerSecurityConfigurer config) {
    config.tokenServices(tokenServices());
    config.resourceId("web_api");
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwkTokenStore(jwkUrl, accessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    return converter;
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    return defaultTokenServices;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        .antMatchers("/test",
            "/v2/api-docs",
            "/configuration/ui/**",
            "/swagger-resources/**",
            "/configuration/security/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/actuator/**")
        .permitAll()
        .antMatchers("/**").authenticated();
    /*.and()
    .headers().addHeaderWriter((request, response) -> {
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (request.getMethod().equals("OPTIONS")) {
            response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        }
    });*/
  }
}
