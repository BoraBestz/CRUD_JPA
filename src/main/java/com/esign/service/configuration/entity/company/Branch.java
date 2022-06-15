package com.esign.service.configuration.entity.company;

import com.esign.service.configuration.entity.address.CityEntity;
import com.esign.service.configuration.entity.address.CitySubDivisionEntity;
import com.esign.service.configuration.entity.address.CountrySubDivisionEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Branch {
    private Integer branchId;
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
    private Integer countrySubDivisionCode;
    private String countrySubDivisionName;
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private Company companyByCompanyId;
    private String email;
    private String telephoneNo;
    private String fax;
    private CityEntity cityByCityId;
    private CitySubDivisionEntity citySubDivisionByCitySubDivisionId;
    private CountrySubDivisionEntity countrySubDivisionByCountrySubDivisionId;
    private String branchCode;
    private Integer cmsFlag;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id", nullable = false)
    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    @Basic
    @Column(name = "company_id", nullable = false)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "global_id", nullable = true, length = 70)
    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "tax_id", nullable = true, length = 35)
    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    @Basic
    @Column(name = "tax_scheme_id", nullable = true, length = 4)
    public String getTaxSchemeId() {
        return taxSchemeId;
    }

    public void setTaxSchemeId(String taxSchemeId) {
        this.taxSchemeId = taxSchemeId;
    }

    @Basic
    @Column(name = "postcode_code", nullable = true, length = 16)
    public String getPostcodeCode() {
        return postcodeCode;
    }

    public void setPostcodeCode(String postcodeCode) {
        this.postcodeCode = postcodeCode;
    }

    @Basic
    @Column(name = "building_number", nullable = true, length = 16)
    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    @Basic
    @Column(name = "building_name", nullable = true, length = 70)
    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    @Basic
    @Column(name = "line_one", nullable = true, length = 256)
    public String getLineOne() {
        return lineOne;
    }

    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    @Basic
    @Column(name = "line_two", nullable = true, length = 256)
    public String getLineTwo() {
        return lineTwo;
    }

    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    @Basic
    @Column(name = "line_three", nullable = true, length = 70)
    public String getLineThree() {
        return lineThree;
    }

    public void setLineThree(String lineThree) {
        this.lineThree = lineThree;
    }

    @Basic
    @Column(name = "line_four", nullable = true, length = 70)
    public String getLineFour() {
        return lineFour;
    }

    public void setLineFour(String lineFour) {
        this.lineFour = lineFour;
    }

    @Basic
    @Column(name = "line_five", nullable = true, length = 70)
    public String getLineFive() {
        return lineFive;
    }

    public void setLineFive(String lineFive) {
        this.lineFive = lineFive;
    }

    @Basic
    @Column(name = "street_name", nullable = true, length = 70)
    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Basic
    @Column(name = "city_code", nullable = true, length = 35)
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Basic
    @Column(name = "city_name", nullable = true, length = 70)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Basic
    @Column(name = "city_sub_division_code", nullable = true, length = 35)
    public String getCitySubDivisionCode() {
        return citySubDivisionCode;
    }

    public void setCitySubDivisionCode(String citySubDivisionCode) {
        this.citySubDivisionCode = citySubDivisionCode;
    }

    @Basic
    @Column(name = "city_sub_division_name", nullable = true, length = 70)
    public String getCitySubDivisionName() {
        return citySubDivisionName;
    }

    public void setCitySubDivisionName(String citySubDivisionName) {
        this.citySubDivisionName = citySubDivisionName;
    }

    @Basic
    @Column(name = "country_code", nullable = true, length = 35)
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Basic
    @Column(name = "country_name", nullable = true, length = 70)
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Basic
    @Column(name = "country_scheme_id", nullable = true, length = 35)
    public String getCountrySchemeId() {
        return countrySchemeId;
    }

    public void setCountrySchemeId(String countrySchemeId) {
        this.countrySchemeId = countrySchemeId;
    }

    @Basic
    @Column(name = "country_sub_division_code", nullable = true)
    public Integer getCountrySubDivisionCode() {
        return countrySubDivisionCode;
    }

    public void setCountrySubDivisionCode(Integer countrySubDivisionCode) {
        this.countrySubDivisionCode = countrySubDivisionCode;
    }

    @Basic
    @Column(name = "country_sub_division_name", nullable = true, length = 70)
    public String getCountrySubDivisionName() {
        return countrySubDivisionName;
    }

    public void setCountrySubDivisionName(String countrySubDivisionName) {
        this.countrySubDivisionName = countrySubDivisionName;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "created_user", nullable = true)
    public Integer getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
    }

    @Basic
    @Column(name = "created_date", nullable = true)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "last_updated_by", nullable = true)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Basic
    @Column(name = "last_updated_date", nullable = true)
    public Timestamp getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return Objects.equals(branchId, branch.branchId) &&
                Objects.equals(globalId, branch.globalId) &&
                Objects.equals(name, branch.name) &&
                Objects.equals(taxId, branch.taxId) &&
                Objects.equals(taxSchemeId, branch.taxSchemeId) &&
                Objects.equals(postcodeCode, branch.postcodeCode) &&
                Objects.equals(buildingNumber, branch.buildingNumber) &&
                Objects.equals(buildingName, branch.buildingName) &&
                Objects.equals(lineOne, branch.lineOne) &&
                Objects.equals(lineTwo, branch.lineTwo) &&
                Objects.equals(lineThree, branch.lineThree) &&
                Objects.equals(lineFour, branch.lineFour) &&
                Objects.equals(lineFive, branch.lineFive) &&
                Objects.equals(streetName, branch.streetName) &&
                Objects.equals(cityCode, branch.cityCode) &&
                Objects.equals(cityName, branch.cityName) &&
                Objects.equals(citySubDivisionCode, branch.citySubDivisionCode) &&
                Objects.equals(citySubDivisionName, branch.citySubDivisionName) &&
                Objects.equals(countryCode, branch.countryCode) &&
                Objects.equals(countryName, branch.countryName) &&
                Objects.equals(countrySchemeId, branch.countrySchemeId) &&
                Objects.equals(countrySubDivisionCode, branch.countrySubDivisionCode) &&
                Objects.equals(countrySubDivisionName, branch.countrySubDivisionName) &&
                Objects.equals(status, branch.status) &&
                Objects.equals(createdUser, branch.createdUser) &&
                Objects.equals(createdDate, branch.createdDate) &&
                Objects.equals(lastUpdatedBy, branch.lastUpdatedBy) &&
                Objects.equals(lastUpdatedDate, branch.lastUpdatedDate)&&
                Objects.equals(cmsFlag, branch.cmsFlag);
    }

    @Override
    public int hashCode() {

        return Objects.hash(branchId, globalId, name, taxId, taxSchemeId, postcodeCode, buildingNumber, buildingName, lineOne, lineTwo, lineThree, lineFour, lineFive, streetName, cityCode, cityName, citySubDivisionCode, citySubDivisionName, countryCode, countryName, countrySchemeId, countrySubDivisionCode, countrySubDivisionName, status, createdUser, createdDate, lastUpdatedBy, lastUpdatedDate,cmsFlag);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id", insertable = false,updatable = false)
    @JsonBackReference
    public Company getCompanyByCompanyId(){
        return companyByCompanyId;
    }

    public void setCompanyByCompanyId(Company companyByCompanyId) {
        this.companyByCompanyId = companyByCompanyId;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 70)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "telephone_no", nullable = true, length = 20)
    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    @Basic
    @Column(name = "fax", nullable = true, length = 20)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "cms_flag", nullable = true)
    public Integer getCmsFlag() {
        return cmsFlag;
    }

    public void setCmsFlag(Integer cmsFlag) {
        this.cmsFlag = cmsFlag;
    }

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    public CityEntity getCityByCityId() {
        return cityByCityId;
    }

    public void setCityByCityId(CityEntity cityByCityId) {
        this.cityByCityId = cityByCityId;
    }

    @ManyToOne
    @JoinColumn(name = "city_sub_division_id", referencedColumnName = "city_sub_division_id")
    public CitySubDivisionEntity getCitySubDivisionByCitySubDivisionId() {
        return citySubDivisionByCitySubDivisionId;
    }

    public void setCitySubDivisionByCitySubDivisionId(CitySubDivisionEntity citySubDivisionByCitySubDivisionId) {
        this.citySubDivisionByCitySubDivisionId = citySubDivisionByCitySubDivisionId;
    }

    @ManyToOne
    @JoinColumn(name = "country_sub_division_id", referencedColumnName = "country_sub_division_id")
    public CountrySubDivisionEntity getCountrySubDivisionByCountrySubDivisionId() {
        return countrySubDivisionByCountrySubDivisionId;
    }

    public void setCountrySubDivisionByCountrySubDivisionId(CountrySubDivisionEntity countrySubDivisionByCountrySubDivisionId) {
        this.countrySubDivisionByCountrySubDivisionId = countrySubDivisionByCountrySubDivisionId;
    }

    @Basic
    @Column(name = "branch_code", nullable = true, length = 16)
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
}
