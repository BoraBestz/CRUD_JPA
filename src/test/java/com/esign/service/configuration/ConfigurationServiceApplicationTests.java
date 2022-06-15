package com.esign.service.configuration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ConfigurationServiceApplicationTests {

  private TestRestTemplate restTemplate = new TestRestTemplate();

  @Test
  void contextLoads() {
    /*ResponseEntity<String> res =
        restTemplate.getForEntity("http://localhost:8080/configuration-service/v2/api-docs?group=uam-api", String.class);
    String swagger = res.getBody();
    this.writeFile("swagger.json", swagger);*/
  }

  public void writeFile(String fileName, String content) {
    File theDir = new File("target/generated-sources");
    if (!theDir.exists()) {
      try {
        theDir.mkdir();
      } catch (SecurityException se) {
      }
    }
    BufferedWriter bw = null;
    FileWriter fw = null;
    try {
      fw = new FileWriter(theDir + "/" + fileName);
      bw = new BufferedWriter(fw);
      bw.write(content);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bw != null) bw.close();
        if (fw != null) fw.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
}
