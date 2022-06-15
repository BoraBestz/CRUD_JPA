package com.esign.service.configuration.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements
    MethodSecurityExpressionOperations {

  private Object filterObject;
  private Object returnObject;

  public CustomMethodSecurityExpressionRoot(
      Authentication authentication) {
    super(authentication);
  }

  public boolean hasAnyAuthorityCustom(String... authorities) {
    boolean isPass = false;
    if(authorities != null && authorities.length > 0){
      for (String authority : authorities) {
        for (GrantedAuthority x : authentication.getAuthorities()) {
          if (x.getAuthority().substring(0, 4).equalsIgnoreCase(authority.substring(0, 4))) {
             List<String> splitA = Arrays.asList(x.getAuthority().substring(4).split("(?!^)"));
            if(splitA.size() > 0){
              splitA = new ArrayList<>(splitA);
              splitA.add("V");}
            List<String> splitB = Arrays.asList(authority.substring(4).split("(?!^)"));
            if (splitA.containsAll(splitB)) {
              isPass = true;
            }
          }
        }
      }
    }
    return isPass;
  }


  @Override
  public Object getFilterObject() {
    return this.filterObject;
  }

  @Override
  public Object getReturnObject() {
    return this.returnObject;
  }

  @Override
  public Object getThis() {
    return this;
  }

  @Override
  public void setFilterObject(Object obj) {
    this.filterObject = obj;
  }

  @Override
  public void setReturnObject(Object obj) {
    this.returnObject = obj;
  }
}
