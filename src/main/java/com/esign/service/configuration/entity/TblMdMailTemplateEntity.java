package com.esign.service.configuration.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_md_mail_template", schema = "conf", catalog = "etax")
public class TblMdMailTemplateEntity {
    private Integer mailTemplateId;
    private Integer companyId;
    private Integer documentTypeId;
    private String mailTemplateFrom;
    private String mailTemplateTo;
    private String mailTemplateCc;
    private String mailTemplateSubject;
    private String mailTemplateBody;
    private Date effectiveDate;
    private String recordStatus;
    private Long createdBy;
    private Date createdDate;
    private Long lastUpdateBy;
    private Date LastUpdateDate;
    private String mailTemplateBcc;
    private String mailTemplateName;
    private String mailTemplateCode;
    private Integer branchId;

    @Id
    @Column(name = "mail_template_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_template_seq")
    @SequenceGenerator(
            name = "mail_template_seq",
            sequenceName = "mail_template_seq",
            allocationSize = 1)
    public Integer getMailTemplateId() {
        return mailTemplateId;
    }
    public void setMailTemplateId(Integer mail_template_id) {
        this.mailTemplateId = mail_template_id;
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
    @Column(name = "mail_template_from")
    public String getMailTemplateFrom() {
        return mailTemplateFrom;
    }
    public void setMailTemplateFrom(String mail_template_from) {
        this.mailTemplateFrom = mail_template_from;
    }

    @Basic
    @Column(name = "mail_template_to")
    public String getMailTemplateTo() {
        return mailTemplateTo;
    }
    public void setMailTemplateTo(String mail_template_to) {
        this.mailTemplateTo = mail_template_to;
    }

    @Basic
    @Column(name = "mail_template_cc")
    public String getMailTemplateCc() {
        return mailTemplateCc;
    }
    public void setMailTemplateCc(String mail_template_cc) {
        this.mailTemplateCc = mail_template_cc;
    }

    @Basic
    @Column(name = "mail_template_subject")
    public String getMailTemplateSubject() {
        return mailTemplateSubject;
    }
    public void setMailTemplateSubject(String mail_template_subject) {
        this.mailTemplateSubject = mail_template_subject;
    }

    @Basic
    @Column(name = "mail_template_body")
    public String getMailTemplateBody() {
        return mailTemplateBody;
    }
    public void setMailTemplateBody(String mail_template_body) {
        this.mailTemplateBody = mail_template_body;
    }

    @Basic
    @Column(name = "effective_date")
    public Date getEffectiveDate() {
        return effectiveDate;
    }
    public void setEffectiveDate(Date effective_date) {
        this.effectiveDate = effective_date;
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

    @Basic
    @Column(name = "mail_template_bcc")
    public String getMailTemplateBcc() {
        return mailTemplateBcc;
    }
    public void setMailTemplateBcc(String mail_template_bcc) {
        this.mailTemplateBcc = mail_template_bcc;
    }

    @Basic
    @Column(name = "mail_template_name")
    public String getMailTemplateName() {
        return mailTemplateName;
    }
    public void setMailTemplateName(String mail_template_name) {
        this.mailTemplateName = mail_template_name;
    }

    @Basic
    @Column(name = "mail_template_code")
    public String getMailTemplateCode() {
        return mailTemplateCode;
    }
    public void setMailTemplateCode(String mail_template_code) {
        this.mailTemplateCode = mail_template_code;
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
