package com.esign.service.configuration.dto.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountrySubDivisionDto {
  @JsonIgnore
  private int page;
  @JsonIgnore private Integer perPage;
  @JsonIgnore private String direction;
  @JsonIgnore private String sort;

  private Integer countrySubDivisionId;
  private Integer countryId;
  private String countrySubDivisionCode;
  private String countrySubDivisionNameTh;
  private String countrySubDivisionNameEn;
  private int status;

  @JsonIgnore private String fullSearch;
  @JsonIgnore private Integer createBy;
  @JsonIgnore private Date createDt;
  @JsonIgnore private Integer updateBy;
  @JsonIgnore private Date updateDt;


}
