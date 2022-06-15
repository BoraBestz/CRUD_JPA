package com.esign.service.configuration.config;

import io.swagger.models.HttpMethod;
import java.io.IOException;
import java.util.Base64;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class InternationalRequestFilter implements Filter {

  @Autowired OauthUser oauthUser;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String curentIp = "";
    boolean isPass = true;
    if (!httpServletRequest.getMethod().toString().equals(HttpMethod.OPTIONS.toString())) {
      ///Forward Token
      String token = httpServletRequest.getHeader(RequestContext.REQUEST_HEADER_NAME);
      if (token == null || "".equals(token)) {
        //throw new IllegalArgumentException("Can't retrieve JWT Token");
        token = "";
      }
      RequestContext.getContext().setToken(token);

      //Check IP
      /*token = token.split("\\.")!=null&&token.split("\\.").length>1?token.split("\\.")[1]:"";
      try {
        JSONParser parser = new JSONParser();
        if(!StringUtils.isEmpty(token)){
          JSONObject json = (JSONObject) parser.parse(Base64.getDecoder().decode(token));
          String ip = json.getAsString("ip");
          if(ip != null){
            curentIp = request.getRemoteAddr();
            for (String header : HEADERS_TO_TRY) {
              String clientIp = httpServletRequest.getHeader(header);
              if (clientIp != null && clientIp.length() != 0 && !"unknown".equalsIgnoreCase(clientIp)) {
                curentIp = clientIp;
              }
            }
            if(!ip.equalsIgnoreCase(curentIp)){
              isPass = false;
            }
          }else{
            isPass = false;
          }
        }else{
          isPass = false;
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }*/
    }
    if(isPass){
      chain.doFilter(request, response);
    }else{
      ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "IP is not valid.(Current IP: "+curentIp+")");
    }
  }

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
      "REMOTE_ADDR" };

  @Override
  public void destroy() {}

  @Override
  public void init(FilterConfig arg0) throws ServletException {}
}
