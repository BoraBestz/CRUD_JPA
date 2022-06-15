package com.esign.service.configuration.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_md_pdf_password", schema = "conf", catalog = "etax")
public class TblMdPdfPasswordEntity {
    private long pdfPasswordId;
    private String pdfPasswordCode;
    private String pdfPasswordName;
    private String pdfPasswordField;
    private Long defaultFlag;
    private String recordStatus;
    private Long createdBy;
    private Date createdDate;
    private Long lastUpdateBy;
    private Date LastUpdateDate;

    @Id
    @Column(name = "pdf_password_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pdf_password_seq")
    @SequenceGenerator(
            name = "pdf_password_seq",
            sequenceName = "pdf_password_seq",
            allocationSize = 1)
    public long getPdfPasswordId() {
        return pdfPasswordId;
    }
    public void setPdfPasswordId(long pdf_password_id) {
        this.pdfPasswordId = pdf_password_id;
    }

    @Basic
    @Column(name = "pdf_password_code")
    public String getPdfPasswordCode() {
        return pdfPasswordCode;
    }
    public void setPdfPasswordCode(String pdf_password_code) {
        this.pdfPasswordCode = pdf_password_code;
    }

    @Basic
    @Column(name = "pdf_password_name")
    public String getPdfPasswordName() {
        return pdfPasswordName;
    }
    public void setPdfPasswordName(String pdf_password_name) {
        this.pdfPasswordName = pdf_password_name;
    }

    @Basic
    @Column(name = "pdf_password_field")
    public String getPdfPasswordField() {
        return pdfPasswordField;
    }
    public void setPdfPasswordField(String pdf_password_field) {
        this.pdfPasswordField = pdf_password_field;
    }

    @Basic
    @Column(name = "default_flag")
    public Long getDefaultFlag() {
        return defaultFlag;
    }
    public void setDefaultFlag(Long default_flag) {
        this.defaultFlag = default_flag;
    }

    @Basic
    @Column(name = "record_status")
    public String getRecordStatus() {
        return recordStatus;
    }
    public void setRecordStatus(String record_status) {
        this.recordStatus = record_status;
    }

    @Basic
    @Column(name = "created_by")
    public Long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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
    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    @Basic
    @Column(name = "last_updated_date")
    public Date getLastUpdateDate() {
        return LastUpdateDate;
    }
    public void setLastUpdateDate(Date lastUpdateDate) {
        LastUpdateDate = lastUpdateDate;
    }
}
