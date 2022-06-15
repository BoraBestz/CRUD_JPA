package com.esign.service.configuration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "readiness")
public class ReadinessEndpoint {

  private final ReadinessProbe readinessProbe;
  private boolean isEnabled = true;

  @Autowired
  public ReadinessEndpoint(final ReadinessProbe readinessProbe) {
    this.readinessProbe = readinessProbe;
  }

  @ReadOperation
  public Health alive() {
    return isEnabled && readinessProbe.isReady() ? Health.up().build() : Health.down().build();
  }

  @WriteOperation
  public void triggerReadiness() {
    this.isEnabled = !isEnabled;
  }
}

@Configuration
class MonitoringConfig {

  @ConditionalOnMissingBean
  @Bean
  public ReadinessProbe readinessProbe() {
    return new ReadinessProbeImp();
  }

}

interface ReadinessProbe {
  boolean isReady();
}

class ReadinessProbeImp implements ReadinessProbe{

  @Override
  public boolean isReady() {
    return true;
  }
}
