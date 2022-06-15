package com.esign.service.configuration.dto.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitySubDivisionDto {
  @JsonIgnore
  private int page;
  @JsonIgnore private Integer perPage;
  @JsonIgnore private String direction;
  @JsonIgnore private String sort;

  private Integer citySubDivisionId;
  private Integer cityId;
  private String citySubDivisionCode;
  private String citySubDivisionNameTh;
  private String citySubDivisionNameEn;
  private Integer citySubDivisionPostCode;
  private int status;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private CityDto city;

  @JsonIgnore private String fullSearch;
  @JsonIgnore private Integer createBy;
  @JsonIgnore private Date createDt;
  @JsonIgnore private Integer updateBy;
  @JsonIgnore private Date updateDt;


}
