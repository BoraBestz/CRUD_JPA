package com.esign.service.eigning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ParameterCriteria {
  private int page = 1;
  private int perPage = 10;
  private Sort.Direction direction;
  private String status;
}
