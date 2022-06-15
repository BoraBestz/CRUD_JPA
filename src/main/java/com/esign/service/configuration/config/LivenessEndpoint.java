package com.esign.service.configuration.config;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Endpoint(id = "liveness")
@Component
public class LivenessEndpoint {

  @ReadOperation
  public Health alive() {
    return Health.up().build();
  }

}
