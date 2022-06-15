package com.esign.service.configuration.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_md_sms_template", schema = "conf", catalog = "etax")
public class TblMdSmsTemplateEntity {
    private long smsTemplateId;
    private String smsTemplateName;
    private String smsTemplateSubject;
    private String smsTemplateBody;
    private String recordStatus;
    private Integer companyId;
    private Integer documentTypeId;
    private String smsTemplateCode;
    private Long createdBy;
    private Date createdDate;
    private Long lastUpdatedBy;
    private Date lastUpdatedDate;
    private Integer branchId;

    @Id
    @Column(name = "sms_template_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sms_template_seq")
    @SequenceGenerator(
            name = "sms_template_seq",
            sequenceName = "sms_template_seq",
            allocationSize = 1)
    public long getSmsTemplateId() {
        return smsTemplateId;
    }
    public void setSmsTemplateId(long sms_template_id) {
        this.smsTemplateId = sms_template_id;
    }

    @Basic
    @Column(name = "sms_template_name")
    public String getSmsTemplateName() {
        return smsTemplateName;
    }
    public void setSmsTemplateName(String sms_template_name) {
        this.smsTemplateName = sms_template_name;
    }

    @Basic
    @Column(name = "sms_template_subject")
    public String getSmsTemplateSubject() {
        return smsTemplateSubject;
    }
    public void setSmsTemplateSubject(String sms_template_subject) {
        this.smsTemplateSubject = sms_template_subject;
    }

    @Basic
    @Column(name = "sms_template_body")
    public String getSmsTemplateBody() {
        return smsTemplateBody;
    }
    public void setSmsTemplateBody(String sms_template_body) {
        this.smsTemplateBody = sms_template_body;
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
    @Column(name = "company_id")
    public Integer getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Integer company_id) {
        this.companyId = company_id;
    }

    @Basic
    @Column(name = "document_type_id")
    public Integer getDocumentTypeId() {
        return documentTypeId;
    }
    public void setDocumentTypeId(Integer document_type_id) {
        this.documentTypeId = document_type_id;
    }

    @Basic
    @Column(name = "sms_template_code")
    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }
    public void setSmsTemplateCode(String sms_template_code) {
        this.smsTemplateCode = sms_template_code;
    }

    @Basic
    @Column(name = "created_by")
    public Long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Long created_user) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_date")
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date created_date) {
        this.createdDate = created_date;
    }

    @Basic
    @Column(name = "last_updated_by")
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    public void setLastUpdatedBy(Long last_updated_by) {
        this.lastUpdatedBy = last_updated_by;
    }

    @Basic
    @Column(name = "last_updated_date")
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }
    public void setLastUpdatedDate(Date last_updated_date) {
        this.lastUpdatedDate = last_updated_date;
    }

    @Basic
    @Column(name = "branch_id")
    public Integer getBranchId() {
        return branchId;
    }
    public void setBranchId(Integer branch_id) {
        this.branchId = branch_id;
    }
}
