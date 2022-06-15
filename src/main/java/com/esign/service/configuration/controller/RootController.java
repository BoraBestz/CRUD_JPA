package com.esign.service.configuration.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@PreAuthorize("isAuthenticated()")
@Controller
public class RootController {
  @Value("${e-signing-service.podsname:configuration-service}")
  String podsname = "";

  //@PreAuthorize("hasAnyAuthorityCustom('U')")
  //@PreAuthorize("hasAnyAuthorityCustom('AECML')")
  @GetMapping("/")
  @ResponseBody
  public String getRoot() {
    return "Alive ---> @ ---> " + new Date();
  }

  @SneakyThrows
  @GetMapping("/test")
  @ResponseBody
  public String test() {
    String curl = "curl --location --request POST 'http://10.10.10.208:8080/elt-client/api/etl/upload' --header 'Content-Type: application/json' --data-raw '{\"config\":\"L2RhdGEva2Nn\",\"serverUrl\":\"aHR0cHM6Ly9rY2cuZGlnaWJpei5jbG91ZC9zdGFnaW5nL2FwaS9pbXBvcnQtZmlsZS91cGxvYWQtZmlsZQ==\",\"userId\":\"4\",\"companyId\": \"27\",\"companyCode\":\"010553307701300000\"}'";
    //String curl = "curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{\"field1\": \"value1\", \"field2\": \"value2\"}' 'http://localhost:8080/service'";
    ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", curl);
    builder.redirectErrorStream(true);
    Process p = builder.start();
    StringBuilder sb = new StringBuilder();
    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
    String line;
    int linenum = 0;
    while (true) {
      linenum++;
      line = r.readLine();
      if (line == null) {
        break;
      }
      sb.append(line);
    }
    System.out.println(sb);
    return sb.toString();
  }
}
