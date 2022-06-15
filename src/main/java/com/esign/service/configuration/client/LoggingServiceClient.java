package com.esign.service.configuration.client;

import com.esign.service.configuration.config.OauthUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.Visit;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class LoggingServiceClient {

  private final RestTemplate restTemplate;
  @Value("${spring.application.name}")
  String serviceName = "";
  @Autowired private OauthUser oauthUser;
  @Value("${url-client.logging-service}")
  private String loggingServiceContext;

  @Autowired
  public LoggingServiceClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Async
  public void activityLog(
      ActivityType activityType,
      Object oldData,
      Object newData,
      String remark,
      String activityName,
      String clientIp,
      String requestId,
      String methodName,
      String token,
      String screenName,
      int status) {
    try {
      List<ActivityLogDto> diff = new ArrayList<>();
      ActivityLogDto dto =
          ActivityLogDto.builder()
              .activityName(activityName)
              .activityType(activityType.name().trim())
              .clientIp(clientIp)
              .newData("")
              .oldData("")
              .requestId(requestId)
              .screenName(screenName)
              .serviceName(serviceName + "." + methodName)
              .remark(remark)
              .status(String.valueOf(status))
              .build();

      if (ActivityType.UPDATE.name().equals(activityType.name())) {
        diff = findDiff(oldData, newData, dto);
      } else {
        diff.add(dto);
      }

      if (diff.size() > 0) {
        HttpHeaders headers = new HttpHeaders();
        ObjectMapper mapper = new ObjectMapper();
        for (ActivityLogDto activityLogDto : diff) {
          String url = loggingServiceContext + "api/log/activity";
          headers.setContentType(MediaType.APPLICATION_JSON);
          headers.set("Authorization", token);
          HttpEntity<String> request =
              new HttpEntity<String>(mapper.writeValueAsString(activityLogDto), headers);
          restTemplate.postForObject(url, request, String.class);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      log.error(e.getMessage());
    }
  }

  private List<ActivityLogDto> findDiff(Object oldData, Object newData, ActivityLogDto dto) {
    List<ActivityLogDto> activityLogDtos = new ArrayList<>();
    if (oldData != null && newData != null) {

      DiffNode diff = ObjectDifferBuilder.buildDefault().compare(oldData, newData);
      if (diff.hasChanges()) {
        diff.visit(
            new DiffNode.Visitor() {
              public void node(DiffNode node, Visit visit) {
                if (!node.hasChildren()) { // Only print if the property has no child
                  final Object oldValue = node.canonicalGet(oldData);
                  final Object newValue = node.canonicalGet(newData);

                  /*final String message =
                      node.getPropertyName() + " changed from " + oldValue + " to " + newValue;
                  log.info(message);*/
                  ActivityLogDto build = dto.toBuilder().build();
                  build.setOldData(oldValue.toString());
                  build.setNewData(newValue.toString());
                  build.setChangeField(node.getPropertyName());
                  activityLogDtos.add(build);
                }
              }
            });
      } else {
        activityLogDtos.add(dto);
        log.info("No differences");
      }
    } else {
      activityLogDtos.add(dto);
    }
    return activityLogDtos;
  }

  public enum ActivityType {
    CREATE,
    UPDATE,
    DELETE,
    VIEW,
    SEARCH
  }
}

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
class ActivityLogDto {
  private String activityType;
  private String activityName;
  private String screenName;
  private String serviceName;
  private String requestId;
  private String clientIp;
  private String changeField;
  private String oldData;
  private String newData;
  private String remark;
  private String status;
}
