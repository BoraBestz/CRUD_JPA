package com.esign.service.configuration.config;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    String token = RequestContext.getContext().getToken();
    request.getHeaders().add(RequestContext.REQUEST_HEADER_NAME, token);
    return execution.execute(request, body);
  }
}
