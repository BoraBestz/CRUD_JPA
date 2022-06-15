package com.esign.service.configuration.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_md_mail_template_att", schema = "conf", catalog = "etax")
public class TblMdMailTemplateAttEntity {
    private Integer mailTemplateId;
    private String mailTemplateAttCode;
    private String mailTemplateAttName;
    private String recordStatus;
    private Long createdBy;
    private Date createdDate;
    private Long lastUpdateBy;
    private Date LastUpdateDate;

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
    @Column(name = "mail_template_att_code")
    public String getMailTemplateAttCode() {
        return mailTemplateAttCode;
    }
    public void setMailTemplateAttCode(String mail_template_att_code) {
        this.mailTemplateAttCode = mail_template_att_code;
    }

    @Basic
    @Column(name = "mail_template_att_name")
    public String getMailTemplateAttName() {
        return mailTemplateAttName;
    }
    public void setMailTemplateAttName(String mail_template_att_name) {
        this.mailTemplateAttName = mail_template_att_name;
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
