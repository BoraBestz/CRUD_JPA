package com.esign.service.configuration.dto;

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
public class ComBranchDto {
  @JsonIgnore
  private int page;
  @JsonIgnore private Integer perPage;
  @JsonIgnore private String direction;
  @JsonIgnore private String sort;

  private long branchId;
  private String branchCode;
  private Long parentBranchId;
  private Long companyId;
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
  private Long countrySubDivisionCode;
  private String countrySubDivisionName;
  private String email;
  private String telephoneNo;
  private String fax;
  private Integer status;
  private Long createdUser;
  private Date createdDate;
  private Long lastUpdatedBy;
  private Date lastUpdatedDate;
  private Integer cmsFlag;

  @JsonIgnore private String fullSearch;
  @JsonIgnore private Integer createBy;
  @JsonIgnore private Date createDt;
  @JsonIgnore private Integer updateBy;
  @JsonIgnore private Date updateDt;


}
