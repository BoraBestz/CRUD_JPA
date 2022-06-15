package com.esign.service.configuration.entity.email;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "document_type", schema = "conf", catalog = "")
public class DocumentType {
    private Integer documentTypeId;
    private String documentTypeCode;
    private String documentTypeNameTh;
    private String documentTypeNameEn;
    private String documentTypeDescription;
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;


    @Id
    @Column(name = "document_type_id", nullable = false)
    public Integer getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Integer documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    @Basic
    @Column(name = "document_type_code", nullable = true, length = 255)
    public String getDocumentTypeCode() {
        return documentTypeCode;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    @Basic
    @Column(name = "document_type_name_th", nullable = true, length = 255)
    public String getDocumentTypeNameTh() {
        return documentTypeNameTh;
    }

    public void setDocumentTypeNameTh(String documentTypeNameTh) {
        this.documentTypeNameTh = documentTypeNameTh;
    }

    @Basic
    @Column(name = "document_type_name_en", nullable = true, length = 255)
    public String getDocumentTypeNameEn() {
        return documentTypeNameEn;
    }

    public void setDocumentTypeNameEn(String documentTypeNameEn) {
        this.documentTypeNameEn = documentTypeNameEn;
    }

    @Basic
    @Column(name = "document_type_description", nullable = true, length = 500)
    public String getDocumentTypeDescription() {
        return documentTypeDescription;
    }

    public void setDocumentTypeDescription(String documentTypeDescription) {
        this.documentTypeDescription = documentTypeDescription;
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
        DocumentType that = (DocumentType) o;
        return Objects.equals(documentTypeId, that.documentTypeId) &&
                Objects.equals(documentTypeCode, that.documentTypeCode) &&
                Objects.equals(documentTypeNameTh, that.documentTypeNameTh) &&
                Objects.equals(documentTypeNameEn, that.documentTypeNameEn) &&
                Objects.equals(documentTypeDescription, that.documentTypeDescription) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdUser, that.createdUser) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lastUpdatedBy, that.lastUpdatedBy) &&
                Objects.equals(lastUpdatedDate, that.lastUpdatedDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(documentTypeId, documentTypeCode, documentTypeNameTh, documentTypeNameEn, documentTypeDescription, status, createdUser, createdDate, lastUpdatedBy, lastUpdatedDate);
    }
}
