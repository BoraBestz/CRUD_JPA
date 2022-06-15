package com.esign.service.configuration.entity.company;

import com.esign.service.configuration.entity.email.MailServer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Company {

    private Integer companyId;
    private Integer parentCompanyId;
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
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private Collection<Branch> mapBranchByCompanyId;
    private String email;
    private String telephoneNo;
    private String fax;
    private Collection<Branch> branchesByCompanyId;
    private Company companyByParentCompanyId;
    private Collection<Company> companiesByCompanyId;
    private Collection<CompanyLogo> companyLogosByCompanyIdEntity;
    private Collection<CaConfig> caConfigsByCompanyId;
    private Collection<MailServer> mailServersByCompanyId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "country_code", nullable = true, length = 2)
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
    @Column(name = "country_sub_division_code", nullable = true, length = 35)
    public String getCountrySubDivisionCode() {
        return countrySubDivisionCode;
    }

    public void setCountrySubDivisionCode(String countrySubDivisionCode) {
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
        Company company = (Company) o;
        return Objects.equals(companyId, company.companyId) &&
                Objects.equals(globalId, company.globalId) &&
                Objects.equals(name, company.name) &&
                Objects.equals(taxId, company.taxId) &&
                Objects.equals(taxSchemeId, company.taxSchemeId) &&
                Objects.equals(postcodeCode, company.postcodeCode) &&
                Objects.equals(buildingNumber, company.buildingNumber) &&
                Objects.equals(buildingName, company.buildingName) &&
                Objects.equals(lineOne, company.lineOne) &&
                Objects.equals(lineTwo, company.lineTwo) &&
                Objects.equals(lineThree, company.lineThree) &&
                Objects.equals(lineFour, company.lineFour) &&
                Objects.equals(lineFive, company.lineFive) &&
                Objects.equals(streetName, company.streetName) &&
                Objects.equals(cityCode, company.cityCode) &&
                Objects.equals(cityName, company.cityName) &&
                Objects.equals(citySubDivisionCode, company.citySubDivisionCode) &&
                Objects.equals(citySubDivisionName, company.citySubDivisionName) &&
                Objects.equals(countryCode, company.countryCode) &&
                Objects.equals(countryName, company.countryName) &&
                Objects.equals(countrySchemeId, company.countrySchemeId) &&
                Objects.equals(countrySubDivisionCode, company.countrySubDivisionCode) &&
                Objects.equals(countrySubDivisionName, company.countrySubDivisionName) &&
                Objects.equals(status, company.status) &&
                Objects.equals(createdUser, company.createdUser) &&
                Objects.equals(createdDate, company.createdDate) &&
                Objects.equals(lastUpdatedBy, company.lastUpdatedBy) &&
                Objects.equals(lastUpdatedDate, company.lastUpdatedDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(companyId, globalId, name, taxId, taxSchemeId, postcodeCode, buildingNumber, buildingName, lineOne, lineTwo, lineThree, lineFour, lineFive, streetName, cityCode, cityName, citySubDivisionCode, citySubDivisionName, countryCode, countryName, countrySchemeId, countrySubDivisionCode, countrySubDivisionName, status, createdUser, createdDate, lastUpdatedBy, lastUpdatedDate);
    }

    @OneToMany(mappedBy = "companyByCompanyId",fetch = FetchType.LAZY)
    @JsonManagedReference
    @Where(clause = "status = 1")
    public Collection<Branch> getMapBranchByCompanyId() {
        return mapBranchByCompanyId;
    }

    public void setMapBranchByCompanyId(Collection<Branch> mapBranchByCompanyId) {
        this.mapBranchByCompanyId = mapBranchByCompanyId;
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

    @JsonManagedReference(value = "companyByCompanyId")
    @OneToMany(mappedBy = "companyByCompanyId")
    @Where(clause = "status = 1")
    public Collection<Branch> getBranchesByCompanyId() {
        return branchesByCompanyId;
    }

    public void setBranchesByCompanyId(Collection<Branch> branchesByCompanyId) {
        this.branchesByCompanyId = branchesByCompanyId;
    }

    @JsonBackReference(value = "companyByParentCompanyId")
    @ManyToOne
    @JoinColumn(name = "parent_company_id", referencedColumnName = "company_id", insertable = false, updatable = false)
    public Company getCompanyByParentCompanyId() {
        return companyByParentCompanyId;
    }

    public void setCompanyByParentCompanyId(Company companyByParentCompanyId) {
        this.companyByParentCompanyId = companyByParentCompanyId;
    }

    @JsonManagedReference(value = "companyByParentCompanyId")
    @OneToMany(mappedBy = "companyByParentCompanyId")
    @Where(clause = "status = 1")
    public Collection<Company> getCompaniesByCompanyId() {
        return companiesByCompanyId;
    }

    public void setCompaniesByCompanyId(Collection<Company> companiesByCompanyId) {
        this.companiesByCompanyId = companiesByCompanyId;
    }

    @JsonManagedReference(value = "companyByCompanyId")
    @OneToMany(mappedBy = "companyByCompanyId")
    @Where(clause = "status = 1")
    public Collection<CompanyLogo> getCompanyLogosByCompanyIdEntity() {
        return companyLogosByCompanyIdEntity;
    }

    public void setCompanyLogosByCompanyIdEntity(Collection<CompanyLogo> companyLogosByCompanyIdEntity) {
        this.companyLogosByCompanyIdEntity = companyLogosByCompanyIdEntity;
    }


    @JsonManagedReference(value = "caConfigsByCompanyId")
    @OneToMany(mappedBy = "companyByCompanyId")
    @Where(clause = "status = 1")
    public Collection<CaConfig> getCaConfigsByCompanyId() {
        return caConfigsByCompanyId;
    }

    public void setCaConfigsByCompanyId(Collection<CaConfig> caConfigsByCompanyId) {
        this.caConfigsByCompanyId = caConfigsByCompanyId;
    }

    @JsonManagedReference(value = "mailServersByCompanyId")
    @OneToMany(mappedBy = "companyByCompanyId")
    @Where(clause = "status = 1")
    public Collection<MailServer> getMailServersByCompanyId() {
        return mailServersByCompanyId;
    }

    public void setMailServersByCompanyId(Collection<MailServer> mailServersByCompanyId) {
        this.mailServersByCompanyId = mailServersByCompanyId;
    }

    @Basic
    @Column(name = "parent_company_id", nullable = true)
    public Integer getParentCompanyId() {
        return parentCompanyId;
    }

    public void setParentCompanyId(Integer parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }
}
