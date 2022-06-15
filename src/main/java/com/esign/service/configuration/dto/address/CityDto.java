package com.esign.service.configuration.dto.address;

import com.esign.service.configuration.dto.ComCompanyDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {
  @JsonIgnore
  private int page;
  @JsonIgnore private Integer perPage;
  @JsonIgnore private String direction;
  @JsonIgnore private String sort;

  private Integer cityId;
  private Integer countrySubDivisionId;
  private String cityCode;
  private String cityNameTh;
  private String cityNameEn;
  private int status;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<CountrySubDivisionDto> countrySubDivision;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<CitySubDivisionDto> citySubDivision;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<ComCompanyDto> comCompany;

  @JsonIgnore private String fullSearch;
  @JsonIgnore private Integer createBy;
  @JsonIgnore private Date createDt;
  @JsonIgnore private Integer updateBy;
  @JsonIgnore private Date updateDt;


}
