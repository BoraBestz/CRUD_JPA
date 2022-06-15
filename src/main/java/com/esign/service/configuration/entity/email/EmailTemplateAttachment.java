package com.esign.service.configuration.entity.email;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "email_template_attachment", schema = "conf")
public class EmailTemplateAttachment {
    private Long emailTemplateAttachmentId;
    private Long dmsId;
    private Integer status;
    private int createdUser;
    private Timestamp createdDate;
    private int lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private EmailTemplate emailTemplateByEmailTemplateId;
    private String attData;
    private String attDataName;

    @Id
    @Column(name = "email_template_attachment_id", nullable = false)
    public Long getEmailTemplateAttachmentId() {
        return emailTemplateAttachmentId;
    }

    public void setEmailTemplateAttachmentId(Long emailTemplateAttachmentId) {
        this.emailTemplateAttachmentId = emailTemplateAttachmentId;
    }

    @Basic
    @Column(name = "dms_id", nullable = true)
    public Long getDmsId() {
        return dmsId;
    }

    public void setDmsId(Long dmsId) {
        this.dmsId = dmsId;
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
    public int getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(int createdUser) {
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
    public int getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(int lastUpdatedBy) {
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

    @Basic
    @Column(name = "att_data")
    public String getAttData() {
        return attData;
    }

    public void setAttData(String attData) {
        this.attData = attData;
    }

    @Basic
    @Column(name = "att_data_name")
    public String getAttDataName() {
        return attDataName;
    }

    public void setAttDataName(String attDataName) {
        this.attDataName = attDataName;
    }

    @ManyToOne
    @JoinColumn(name = "email_template_id", referencedColumnName = "email_template_id", nullable = false)
    @JsonBackReference(value = "emailTemplateAttachmentsByEmailTemplateId")
    public EmailTemplate getEmailTemplateByEmailTemplateId() {
        return emailTemplateByEmailTemplateId;
    }

    public void setEmailTemplateByEmailTemplateId(
        EmailTemplate emailTemplateByEmailTemplateId) {
        this.emailTemplateByEmailTemplateId = emailTemplateByEmailTemplateId;
    }
}
