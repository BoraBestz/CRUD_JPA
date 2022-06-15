package com.esign.service.configuration.dto.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private Integer companyId;
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
    private String countrySubDivisionCode;
    private String countrySubDivisionName;
    private String email;
    private String telephoneNo;
    private String fax;
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private List<BranchDto> mapBranchByCompanyId;
    private List<BranchDto> branchesByCompanyId;
    private DocumentDto companyLogoFile;
    private Collection<CompanyLogoDto> companyLogosByCompanyId;
    private Collection<EMailServerDto> mailServersByCompanyId;
    private Collection<CaConfigDto> caConfigsByCompanyId;
    private Collection<CreateUserDto> setUserByCompanyId;

}
