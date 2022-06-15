package com.esign.service.configuration.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OauthUser {
  private TokenStore tokenStore;

  @Autowired
  public OauthUser(TokenStore tokenStore) {
    this.tokenStore = tokenStore;
  }

  public String getToken() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication != null && authentication.getDetails() != null){
      OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
      return details.getTokenValue();
    }
    return null;
  }

  public String oauthUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String principal = (String) authentication.getPrincipal();
    return principal;
  }

  public int oauthUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Map<String, Object> additionalInfo = getAdditionalInfo(authentication);
    if(additionalInfo != null){
      int userId = (int) additionalInfo.get("userId");
      return userId;
    }
    return 0;
  }

  private Map<String, Object> getAdditionalInfo(Authentication authentication) {
    if(authentication != null && authentication.getDetails() != null){
      OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
      OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
      return accessToken.getAdditionalInformation();
    }
    return null;
  }

  public List<String> getAllAgency() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Map<String, Object> additionalInfo = getAdditionalInfo(authentication);
    ObjectMapper mapper = new ObjectMapper();
    String userSys ;
    List<String> myList = new ArrayList<String>();
    if(additionalInfo != null){
      userSys =   (String) additionalInfo.get("agen");
      if(!StringUtils.isEmpty(userSys))
        myList = new ArrayList<String>(Arrays.asList(userSys.split(",")));
    }
    return myList;
  }

  public List<String> getAllBranchType() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Map<String, Object> additionalInfo = getAdditionalInfo(authentication);
    ObjectMapper mapper = new ObjectMapper();
    String userSys ;
    List<String> myList = new ArrayList<String>();
    if(additionalInfo != null){
      userSys =   (String) additionalInfo.get("brat");
      if(!StringUtils.isEmpty(userSys))
        myList = new ArrayList<String>(Arrays.asList(userSys.split(",")));
    }
    return myList;
  }

  public List<Integer> getAllBranch() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Map<String, Object> additionalInfo = getAdditionalInfo(authentication);
    ObjectMapper mapper = new ObjectMapper();
    String userSys ;
    List<Integer> myList = new ArrayList<Integer>();
    if(additionalInfo != null){
      userSys =   (String) additionalInfo.get("branch");
      if(!StringUtils.isEmpty(userSys)){
        String[] split = userSys.split(",");
        for (String s : split) {
          myList.add(Integer.parseInt(s));
        }
      }
    }
    return myList;
  }

  public List<String> getAllDepartment() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Map<String, Object> additionalInfo = getAdditionalInfo(authentication);
    ObjectMapper mapper = new ObjectMapper();
    String userSys ;
    List<String> myList = new ArrayList<String>();
    if(additionalInfo != null){
      userSys =   (String) additionalInfo.get("depa");
      if(!StringUtils.isEmpty(userSys))
        myList = new ArrayList<String>(Arrays.asList(userSys.split(",")));
    }
    return myList;
  }

  public List<String> getAllProductCode() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Map<String, Object> additionalInfo = getAdditionalInfo(authentication);
    ObjectMapper mapper = new ObjectMapper();
    String userSys ;
    List<String> myList = new ArrayList<String>();
    if(additionalInfo != null){
      userSys =   (String) additionalInfo.get("proc");
      if(!StringUtils.isEmpty(userSys))
        myList = new ArrayList<String>(Arrays.asList(userSys.split(",")));
    }
    return myList;
  }

  public String getIp() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Map<String, Object> additionalInfo = getAdditionalInfo(authentication);
    ObjectMapper mapper = new ObjectMapper();
    String userSys = "";
    if(additionalInfo != null){
      userSys =   (String) additionalInfo.get("ip");
    }
    return userSys;
  }
}

@Data
class PermissionDto {
  private String branchCode;
  private List<String> permissionCode;
}
