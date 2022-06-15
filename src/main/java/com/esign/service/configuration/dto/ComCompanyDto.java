package com.esign.service.configuration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComCompanyDto {
  @JsonIgnore
  private int page;
  @JsonIgnore private Integer perPage;
  @JsonIgnore private String direction;
  @JsonIgnore private String sort;

  private long companyId;
  private Long parentCompanyId;
  private String globalId;
  private String name;
  private String taxId;
  private String taxSchemeId;
  private String postcodeCode;
  private String buildingNumber;
  private String buildingName;
  private String lineOne;
  private String lineTwo;
  private String lineThree;
  private String lineFour;
  private String lineFive;
  private String streetName;
  private Long cityId;
  private String cityCode;
  private String cityName;
  private Long citySubDivisionId;
  private String citySubDivisionCode;
  private String citySubDivisionName;
  private Long countryId;
  private String countryCode;
  private String countryName;
  private String countrySchemeId;
  private Long countrySubDivisionId;
  private String countrySubDivisionCode;
  private String countrySubDivisionName;
  private String email;
  private String telephoneNo;
  private String fax;
  private Integer status;
  private Long createdUser;
  private Date createdDate;
  private Long lastUpdatedBy;
  private Date lastUpdatedDate;

  @JsonInclude(Include.NON_NULL)
  private List<ComBranchDto> branch;

  @JsonIgnore private String showBranch;

  @JsonIgnore private String fullSearch;
  @JsonIgnore private Integer createBy;
  @JsonIgnore private Date createDt;
  @JsonIgnore private Integer updateBy;
  @JsonIgnore private Date updateDt;


}
