package com.esign.service.configuration.dto.company;

import lombok.*;

import java.sql.Timestamp;
import java.util.Collection;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    private Integer branchId;
    private String branchCode;
    private CompanyDto companyId;
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
    private String cityCode;
    private String cityName;
    private String citySubDivisionCode;
    private String citySubDivisionName;
    private String countryCode;
    private String countryName;
    private String countrySchemeId;
    private Integer countrySubDivisionCode;
    private String countrySubDivisionName;
    private String email;
    private String telephoneNo;
    private String fax;
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private Integer cmsFlag;
}

