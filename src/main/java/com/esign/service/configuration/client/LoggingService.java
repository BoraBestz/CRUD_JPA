package com.esign.service.configuration.client;

import io.opentracing.Tracer;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoggingService {
  private static final String[] HEADERS_TO_TRY = {
    "X-Forwarded-For",
    "Proxy-Client-IP",
    "WL-Proxy-Client-IP",
    "HTTP_X_FORWARDED_FOR",
    "HTTP_X_FORWARDED",
    "HTTP_X_CLUSTER_CLIENT_IP",
    "HTTP_CLIENT_IP",
    "HTTP_FORWARDED_FOR",
    "HTTP_FORWARDED",
    "HTTP_VIA",
    "REMOTE_ADDR"
  };
  private HttpServletRequest request;
  private Tracer tracer;

  public void setLoggingService(HttpServletRequest request, Tracer tracer) {
    this.request = request;
    this.tracer = tracer;
  }

  public String getApiOperationValue(Object ojb) {
    if (ojb != null) {
      return ojb.getClass().getEnclosingMethod().getAnnotation(ApiOperation.class).value();
    }
    return "";
  }

  public String getClientIp() {
    for (String header : HEADERS_TO_TRY) {
      if (request != null) {
        String ip = request.getHeader(header);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
          return ip;
        }
      }
    }

    return request != null
        ? (!StringUtils.isEmpty(request.getRemoteAddr()) ? request.getRemoteAddr() : "")
        : "127.0.0.1";
  }

  public String getMethodName(Object ojb) {
    return ojb.getClass().getEnclosingMethod().getName();
  }

  public String getToken() {
    return request != null
        ? (!StringUtils.isEmpty(request.getHeader("Authorization"))
            ? request.getHeader("Authorization")
            : "")
        : "";
  }

  public String getTracer() {
    try {
      return tracer.activeSpan().context().toTraceId();
    } catch (Exception e) {
      return "";
    }
  }
}
