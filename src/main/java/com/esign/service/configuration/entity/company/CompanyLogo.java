package com.esign.service.configuration.entity.company;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "company_logo", schema = "etax", catalog = "")
public class CompanyLogo {
    private Integer companyLogoId;
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private Integer companyId;
    private Long dmsId;
    private Company companyByCompanyId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_logo_id", nullable = false)
    public Integer getCompanyLogoId() {
        return companyLogoId;
    }

    public void setCompanyLogoId(Integer companyLogoId) {
        this.companyLogoId = companyLogoId;
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
        CompanyLogo that = (CompanyLogo) o;
        return Objects.equals(companyLogoId, that.companyLogoId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdUser, that.createdUser) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastUpdatedBy, that.lastUpdatedBy) &&
                Objects.equals(lastUpdatedDate, that.lastUpdatedDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(companyLogoId, status, createdUser, createdDate, lastUpdatedBy, lastUpdatedDate);
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
    @Column(name = "dms_id", nullable = true)
    public Long getDmsId() {
        return dmsId;
    }

    public void setDmsId(Long dmsId) {
        this.dmsId = dmsId;
    }

    @JsonBackReference(value = "companyByCompanyId")
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id", nullable = false,insertable=false ,updatable=false)
    public Company getCompanyByCompanyId() {
        return companyByCompanyId;
    }

    public void setCompanyByCompanyId(Company companyByCompanyId) {
        this.companyByCompanyId = companyByCompanyId;
    }
}
