package com.esign.service.configuration.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "com_branch", schema = "conf")
public class ComBranchEntity {

  private Long branchId;
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

  @Id
  @Column(name = "branch_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_seq")
  @SequenceGenerator(
      name = "branch_seq",
      sequenceName = "branch_seq",
      allocationSize = 1)
  public Long getBranchId() {
    return branchId;
  }

  public void setBranchId(Long branchId) {
    this.branchId = branchId;
  }

  @Basic
  @Column(name = "branch_code")
  public String getBranchCode() {
    return branchCode;
  }

  public void setBranchCode(String branchCode) {
    this.branchCode = branchCode;
  }

  @Basic
  @Column(name = "parent_branch_id")
  public Long getParentBranchId() {
    return parentBranchId;
  }

  public void setParentBranchId(Long parentBranchId) {
    this.parentBranchId = parentBranchId;
  }

  @Basic
  @Column(name = "company_id")
  public Long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(Long companyId) {
    this.companyId = companyId;
  }

  @Basic
  @Column(name = "global_id")
  public String getGlobalId() {
    return globalId;
  }

  public void setGlobalId(String globalId) {
    this.globalId = globalId;
  }

  @Basic
  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  @Column(name = "tax_id")
  public String getTaxId() {
    return taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  @Basic
  @Column(name = "tax_scheme_id")
  public String getTaxSchemeId() {
    return taxSchemeId;
  }

  public void setTaxSchemeId(String taxSchemeId) {
    this.taxSchemeId = taxSchemeId;
  }

  @Basic
  @Column(name = "postcode_code")
  public String getPostcodeCode() {
    return postcodeCode;
  }

  public void setPostcodeCode(String postcodeCode) {
    this.postcodeCode = postcodeCode;
  }

  @Basic
  @Column(name = "building_number")
  public String getBuildingNumber() {
    return buildingNumber;
  }

  public void setBuildingNumber(String buildingNumber) {
    this.buildingNumber = buildingNumber;
  }

  @Basic
  @Column(name = "building_name")
  public String getBuildingName() {
    return buildingName;
  }

  public void setBuildingName(String buildingName) {
    this.buildingName = buildingName;
  }

  @Basic
  @Column(name = "line_one")
  public String getLineOne() {
    return lineOne;
  }

  public void setLineOne(String lineOne) {
    this.lineOne = lineOne;
  }

  @Basic
  @Column(name = "line_two")
  public String getLineTwo() {
    return lineTwo;
  }

  public void setLineTwo(String lineTwo) {
    this.lineTwo = lineTwo;
  }

  @Basic
  @Column(name = "line_three")
  public String getLineThree() {
    return lineThree;
  }

  public void setLineThree(String lineThree) {
    this.lineThree = lineThree;
  }

  @Basic
  @Column(name = "line_four")
  public String getLineFour() {
    return lineFour;
  }

  public void setLineFour(String lineFour) {
    this.lineFour = lineFour;
  }

  @Basic
  @Column(name = "line_five")
  public String getLineFive() {
    return lineFive;
  }

  public void setLineFive(String lineFive) {
    this.lineFive = lineFive;
  }

  @Basic
  @Column(name = "street_name")
  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  @Basic
  @Column(name = "city_id")
  public Long getCityId() {
    return cityId;
  }

  public void setCityId(Long cityId) {
    this.cityId = cityId;
  }

  @Basic
  @Column(name = "city_code")
  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  @Basic
  @Column(name = "city_name")
  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  @Basic
  @Column(name = "city_sub_division_id")
  public Long getCitySubDivisionId() {
    return citySubDivisionId;
  }

  public void setCitySubDivisionId(Long citySubDivisionId) {
    this.citySubDivisionId = citySubDivisionId;
  }

  @Basic
  @Column(name = "city_sub_division_code")
  public String getCitySubDivisionCode() {
    return citySubDivisionCode;
  }

  public void setCitySubDivisionCode(String citySubDivisionCode) {
    this.citySubDivisionCode = citySubDivisionCode;
  }

  @Basic
  @Column(name = "city_sub_division_name")
  public String getCitySubDivisionName() {
    return citySubDivisionName;
  }

  public void setCitySubDivisionName(String citySubDivisionName) {
    this.citySubDivisionName = citySubDivisionName;
  }

  @Basic
  @Column(name = "country_id")
  public Long getCountryId() {
    return countryId;
  }

  public void setCountryId(Long countryId) {
    this.countryId = countryId;
  }

  @Basic
  @Column(name = "country_code")
  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  @Basic
  @Column(name = "country_name")
  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  @Basic
  @Column(name = "country_scheme_id")
  public String getCountrySchemeId() {
    return countrySchemeId;
  }

  public void setCountrySchemeId(String countrySchemeId) {
    this.countrySchemeId = countrySchemeId;
  }

  @Basic
  @Column(name = "country_sub_division_id")
  public Long getCountrySubDivisionId() {
    return countrySubDivisionId;
  }

  public void setCountrySubDivisionId(Long countrySubDivisionId) {
    this.countrySubDivisionId = countrySubDivisionId;
  }

  @Basic
  @Column(name = "country_sub_division_code")
  public Long getCountrySubDivisionCode() {
    return countrySubDivisionCode;
  }

  public void setCountrySubDivisionCode(Long countrySubDivisionCode) {
    this.countrySubDivisionCode = countrySubDivisionCode;
  }

  @Basic
  @Column(name = "country_sub_division_name")
  public String getCountrySubDivisionName() {
    return countrySubDivisionName;
  }

  public void setCountrySubDivisionName(String countrySubDivisionName) {
    this.countrySubDivisionName = countrySubDivisionName;
  }

  @Basic
  @Column(name = "email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Basic
  @Column(name = "telephone_no")
  public String getTelephoneNo() {
    return telephoneNo;
  }

  public void setTelephoneNo(String telephoneNo) {
    this.telephoneNo = telephoneNo;
  }

  @Basic
  @Column(name = "fax")
  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  @Basic
  @Column(name = "status")
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  @Basic
  @Column(name = "created_user")
  public Long getCreatedUser() {
    return createdUser;
  }

  public void setCreatedUser(Long createdUser) {
    this.createdUser = createdUser;
  }

  @Basic
  @Column(name = "created_date")
  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  @Basic
  @Column(name = "last_updated_by")
  public Long getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(Long lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  @Basic
  @Column(name = "last_updated_date")
  public Date getLastUpdatedDate() {
    return lastUpdatedDate;
  }

  public void setLastUpdatedDate(Date lastUpdatedDate) {
    this.lastUpdatedDate = lastUpdatedDate;
  }

  @Basic
  @Column(name = "cms_flag")
  public Integer getCmsFlag() {
    return cmsFlag;
  }

  public void setCmsFlag(Integer cmsFlag) {
    this.cmsFlag = cmsFlag;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ComBranchEntity that = (ComBranchEntity) o;

    if (branchId != that.branchId) {
      return false;
    }
    if (branchCode != null ? !branchCode.equals(that.branchCode) : that.branchCode != null) {
      return false;
    }
    if (parentBranchId != null ? !parentBranchId.equals(that.parentBranchId)
        : that.parentBranchId != null) {
      return false;
    }
    if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) {
      return false;
    }
    if (globalId != null ? !globalId.equals(that.globalId) : that.globalId != null) {
      return false;
    }
    if (name != null ? !name.equals(that.name) : that.name != null) {
      return false;
    }
    if (taxId != null ? !taxId.equals(that.taxId) : that.taxId != null) {
      return false;
    }
    if (taxSchemeId != null ? !taxSchemeId.equals(that.taxSchemeId) : that.taxSchemeId != null) {
      return false;
    }
    if (postcodeCode != null ? !postcodeCode.equals(that.postcodeCode)
        : that.postcodeCode != null) {
      return false;
    }
    if (buildingNumber != null ? !buildingNumber.equals(that.buildingNumber)
        : that.buildingNumber != null) {
      return false;
    }
    if (buildingName != null ? !buildingName.equals(that.buildingName)
        : that.buildingName != null) {
      return false;
    }
    if (lineOne != null ? !lineOne.equals(that.lineOne) : that.lineOne != null) {
      return false;
    }
    if (lineTwo != null ? !lineTwo.equals(that.lineTwo) : that.lineTwo != null) {
      return false;
    }
    if (lineThree != null ? !lineThree.equals(that.lineThree) : that.lineThree != null) {
      return false;
    }
    if (lineFour != null ? !lineFour.equals(that.lineFour) : that.lineFour != null) {
      return false;
    }
    if (lineFive != null ? !lineFive.equals(that.lineFive) : that.lineFive != null) {
      return false;
    }
    if (streetName != null ? !streetName.equals(that.streetName) : that.streetName != null) {
      return false;
    }
    if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) {
      return false;
    }
    if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) {
      return false;
    }
    if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null) {
      return false;
    }
    if (citySubDivisionId != null ? !citySubDivisionId.equals(that.citySubDivisionId)
        : that.citySubDivisionId != null) {
      return false;
    }
    if (citySubDivisionCode != null ? !citySubDivisionCode.equals(that.citySubDivisionCode)
        : that.citySubDivisionCode != null) {
      return false;
    }
    if (citySubDivisionName != null ? !citySubDivisionName.equals(that.citySubDivisionName)
        : that.citySubDivisionName != null) {
      return false;
    }
    if (countryId != null ? !countryId.equals(that.countryId) : that.countryId != null) {
      return false;
    }
    if (countryCode != null ? !countryCode.equals(that.countryCode) : that.countryCode != null) {
      return false;
    }
    if (countryName != null ? !countryName.equals(that.countryName) : that.countryName != null) {
      return false;
    }
    if (countrySchemeId != null ? !countrySchemeId.equals(that.countrySchemeId)
        : that.countrySchemeId != null) {
      return false;
    }
    if (countrySubDivisionId != null ? !countrySubDivisionId.equals(that.countrySubDivisionId)
        : that.countrySubDivisionId != null) {
      return false;
    }
    if (countrySubDivisionCode != null ? !countrySubDivisionCode.equals(that.countrySubDivisionCode)
        : that.countrySubDivisionCode != null) {
      return false;
    }
    if (countrySubDivisionName != null ? !countrySubDivisionName.equals(that.countrySubDivisionName)
        : that.countrySubDivisionName != null) {
      return false;
    }
    if (email != null ? !email.equals(that.email) : that.email != null) {
      return false;
    }
    if (telephoneNo != null ? !telephoneNo.equals(that.telephoneNo) : that.telephoneNo != null) {
      return false;
    }
    if (fax != null ? !fax.equals(that.fax) : that.fax != null) {
      return false;
    }
    if (status != null ? !status.equals(that.status) : that.status != null) {
      return false;
    }
    if (createdUser != null ? !createdUser.equals(that.createdUser) : that.createdUser != null) {
      return false;
    }
    if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) {
      return false;
    }
    if (lastUpdatedBy != null ? !lastUpdatedBy.equals(that.lastUpdatedBy)
        : that.lastUpdatedBy != null) {
      return false;
    }
    if (lastUpdatedDate != null ? !lastUpdatedDate.equals(that.lastUpdatedDate)
        : that.lastUpdatedDate != null) {
      return false;
    }
    if (cmsFlag != null ? !cmsFlag.equals(that.cmsFlag) : that.cmsFlag != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = (int) (branchId ^ (branchId >>> 32));
    result = 31 * result + (branchCode != null ? branchCode.hashCode() : 0);
    result = 31 * result + (parentBranchId != null ? parentBranchId.hashCode() : 0);
    result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
    result = 31 * result + (globalId != null ? globalId.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (taxId != null ? taxId.hashCode() : 0);
    result = 31 * result + (taxSchemeId != null ? taxSchemeId.hashCode() : 0);
    result = 31 * result + (postcodeCode != null ? postcodeCode.hashCode() : 0);
    result = 31 * result + (buildingNumber != null ? buildingNumber.hashCode() : 0);
    result = 31 * result + (buildingName != null ? buildingName.hashCode() : 0);
    result = 31 * result + (lineOne != null ? lineOne.hashCode() : 0);
    result = 31 * result + (lineTwo != null ? lineTwo.hashCode() : 0);
    result = 31 * result + (lineThree != null ? lineThree.hashCode() : 0);
    result = 31 * result + (lineFour != null ? lineFour.hashCode() : 0);
    result = 31 * result + (lineFive != null ? lineFive.hashCode() : 0);
    result = 31 * result + (streetName != null ? streetName.hashCode() : 0);
    result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
    result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
    result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
    result = 31 * result + (citySubDivisionId != null ? citySubDivisionId.hashCode() : 0);
    result = 31 * result + (citySubDivisionCode != null ? citySubDivisionCode.hashCode() : 0);
    result = 31 * result + (citySubDivisionName != null ? citySubDivisionName.hashCode() : 0);
    result = 31 * result + (countryId != null ? countryId.hashCode() : 0);
    result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
    result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
    result = 31 * result + (countrySchemeId != null ? countrySchemeId.hashCode() : 0);
    result = 31 * result + (countrySubDivisionId != null ? countrySubDivisionId.hashCode() : 0);
    result = 31 * result + (countrySubDivisionCode != null ? countrySubDivisionCode.hashCode() : 0);
    result = 31 * result + (countrySubDivisionName != null ? countrySubDivisionName.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (telephoneNo != null ? telephoneNo.hashCode() : 0);
    result = 31 * result + (fax != null ? fax.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (createdUser != null ? createdUser.hashCode() : 0);
    result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
    result = 31 * result + (lastUpdatedBy != null ? lastUpdatedBy.hashCode() : 0);
    result = 31 * result + (lastUpdatedDate != null ? lastUpdatedDate.hashCode() : 0);
    result = 31 * result + (cmsFlag != null ? cmsFlag.hashCode() : 0);
    return result;
  }
}
