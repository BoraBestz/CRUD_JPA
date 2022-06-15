package com.esign.service.configuration.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_md_mailbox_task", schema = "conf", catalog = "etax")
public class TblMdMailboxTaskEntity {
    private Integer mailboxTaskId;
    private String mailboxTaskCode;
    private Integer documentTypeId;
    private Integer companyId;
    private Integer branchId;
    private Integer mailTemplateId;
    private String mailLink;
    private String smsLink;
    private Integer smsTemplateId;
    private Integer rdTemplateId;
    private String rdLink;
    private String recordStatus;
    private Long createdBy;
    private Date createdDate;
    private Long lastUpdateBy;
    private Date LastUpdateDate;


    @Id
    @Column(name = "mailbox_task_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mailbox_task_seq")
    @SequenceGenerator(
            name = "mailbox_task_seq",
            sequenceName = "mailbox_task_seq",
            allocationSize = 1)

    public Integer getMailboxTaskId() {
        return mailboxTaskId;
    }
    public void setMailboxTaskId(Integer mailbox_task_id) {
        this.mailboxTaskId = mailbox_task_id;
    }

    @Basic
    @Column(name = "mailbox_task_code")
    public String getMailboxTaskCode() {
        return mailboxTaskCode;
    }
    public void setMailboxTaskCode(String mailbox_task_code) {
        this.mailboxTaskCode = mailbox_task_code;
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
    @Column(name = "company_id")
    public Integer getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Integer company_id) {
        this.companyId = company_id;
    }

    @Basic
    @Column(name = "branch_id")
    public Integer getBranchId() {
        return branchId;
    }
    public void setBranchId(Integer branch_id) {
        this.branchId = branch_id;
    }

    @Basic
    @Column(name = "mail_link")
    public String getMailLink() {
        return mailLink;
    }
    public void setMailLink(String mail_link) {
        this.mailLink = mail_link;
    }

    @Basic
    @Column(name = "sms_link")
    public String getSmsLink() {
        return smsLink;
    }
    public void setSmsLink(String sms_link) {
        this.smsLink = sms_link;
    }

    @Basic
    @Column(name = "sms_template_id")
    public Integer getSmsTemplateId() {
        return smsTemplateId;
    }
    public void setSmsTemplateId(Integer sms_template_id) {
        this.smsTemplateId = sms_template_id;
    }

    @Basic
    @Column(name = "rd_template_id")
    public Integer getRdTemplateId() {
        return rdTemplateId;
    }
    public void setRdTemplateId(Integer rd_template_id) {
        this.rdTemplateId = rd_template_id;
    }

    @Basic
    @Column(name = "rd_link")
    public String getRdLink() {
        return rdLink;
    }
    public void setRdLink(String rd_link) {
        this.rdLink = rd_link;
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
    @Column(name = "mail_template_id")
    public Integer getMailTemplateId() {
        return mailTemplateId;
    }
    public void setMailTemplateId(Integer mail_template_id) {
        this.mailTemplateId = mail_template_id;
    }
}
