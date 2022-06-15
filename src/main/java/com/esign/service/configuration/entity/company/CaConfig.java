package com.esign.service.configuration.entity.company;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ca_config", schema = "etax", catalog = "")
public class CaConfig {
    private Integer caId;
    private String caLibPath;
    private String providerName;
    private String password;
    private Integer slotId;
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private Company companyByCompanyId;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ca_id", nullable = false)
    public Integer getCaId() {
        return caId;
    }

    public void setCaId(Integer caId) {
        this.caId = caId;
    }

    @Basic
    @Column(name = "ca_lib_path", nullable = true, length = 255)
    public String getCaLibPath() {
        return caLibPath;
    }

    public void setCaLibPath(String caLibPath) {
        this.caLibPath = caLibPath;
    }

    @Basic
    @Column(name = "provider_name", nullable = true, length = 255)
    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "slot_id", nullable = true)
    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
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
        CaConfig caConfig = (CaConfig) o;
        return Objects.equals(caId, caConfig.caId) &&
                Objects.equals(caLibPath, caConfig.caLibPath) &&
                Objects.equals(providerName, caConfig.providerName) &&
                Objects.equals(password, caConfig.password) &&
                Objects.equals(slotId, caConfig.slotId) &&
                Objects.equals(status, caConfig.status) &&
                Objects.equals(createdUser, caConfig.createdUser) &&
                Objects.equals(createdDate, caConfig.createdDate) &&
                Objects.equals(lastUpdatedBy, caConfig.lastUpdatedBy) &&
                Objects.equals(lastUpdatedDate, caConfig.lastUpdatedDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(caId, caLibPath, providerName, password, slotId, status, createdUser, createdDate, lastUpdatedBy, lastUpdatedDate);
    }

    @JsonBackReference(value = "caConfigsByCompanyId")
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    public Company getCompanyByCompanyId() {
        return companyByCompanyId;
    }

    public void setCompanyByCompanyId(Company companyByCompanyId) {
        this.companyByCompanyId = companyByCompanyId;
    }
}
