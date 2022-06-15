package com.esign.service.configuration.entity.email;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.sql.Timestamp;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "email_template", schema = "conf")
public class EmailTemplate {
    private Integer emailTemplateId;
    private Integer companyId;
    private String mailFrom;
    private String mailTo;
    private String cc;
    private String bcc;
    private String subject;
    private String body;
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private Integer documentTypeId;
    private Collection<EmailTemplateAttachment> emailTemplateAttachmentsByEmailTemplateIdEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_template_id", nullable = false)
    public Integer getEmailTemplateId() {
        return emailTemplateId;
    }

    public void setEmailTemplateId(Integer emailTemplateId) {
        this.emailTemplateId = emailTemplateId;
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
    @Column(name = "mail_from", nullable = true, length = 255)
    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    @Basic
    @Column(name = "mail_to", nullable = true, length = 255)
    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    @Basic
    @Column(name = "cc", nullable = true, length = 255)
    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    @Basic
    @Column(name = "bcc", nullable = true, length = 500)
    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    @Basic
    @Column(name = "subject", nullable = true, length = 500)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "body", nullable = true, length = -1)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    @Basic
    @Column(name = "document_type_id", nullable = false)
    public Integer getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Integer documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    @JsonManagedReference(value = "emailTemplateAttachmentsByEmailTemplateIdEntity")
    @OneToMany(mappedBy = "emailTemplateByEmailTemplateId", fetch = FetchType.EAGER)
    public Collection<EmailTemplateAttachment> getEmailTemplateAttachmentsByEmailTemplateIdEntity() {
        return emailTemplateAttachmentsByEmailTemplateIdEntity;
    }

    public void setEmailTemplateAttachmentsByEmailTemplateIdEntity(Collection<EmailTemplateAttachment> emailTemplateAttachmentsByEmailTemplateIdEntity) {
        this.emailTemplateAttachmentsByEmailTemplateIdEntity = emailTemplateAttachmentsByEmailTemplateIdEntity;
    }
}
