package com.esign.service.configuration.entity.email;

import com.esign.service.configuration.entity.company.Company;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mail_server", schema = "etax", catalog = "")
public class MailServer {
    private Integer mailServerId;
    private String incomingHostname;
    private String incomingEmail;
    private String incomingPort;
    private String incomingUsername;
    private String incomingPassword;
    private String outgoingHostname;
    private String outgoingEmail;
    private String outgoingPort;
    private String outgoingUsername;
    private String outgoingPassword;
    private Integer incomingMailServiceId;
    private Integer outgoingMailServiceId;
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private Company companyByCompanyId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mail_server_id", nullable = false)
    public Integer getMailServerId() {
        return mailServerId;
    }

    public void setMailServerId(Integer mailServerId) {
        this.mailServerId = mailServerId;
    }

    @Basic
    @Column(name = "incoming_hostname", nullable = true, length = 255)
    public String getIncomingHostname() {
        return incomingHostname;
    }

    public void setIncomingHostname(String incomingHostname) {
        this.incomingHostname = incomingHostname;
    }

    @Basic
    @Column(name = "incoming_email", nullable = true, length = 255)
    public String getIncomingEmail() {
        return incomingEmail;
    }

    public void setIncomingEmail(String incomingEmail) {
        this.incomingEmail = incomingEmail;
    }

    @Basic
    @Column(name = "incoming_port", nullable = true, length = 255)
    public String getIncomingPort() {
        return incomingPort;
    }

    public void setIncomingPort(String incomingPort) {
        this.incomingPort = incomingPort;
    }

    @Basic
    @Column(name = "incoming_username", nullable = true, length = 255)
    public String getIncomingUsername() {
        return incomingUsername;
    }

    public void setIncomingUsername(String incomingUsername) {
        this.incomingUsername = incomingUsername;
    }

    @Basic
    @Column(name = "incoming_password", nullable = true, length = 255)
    public String getIncomingPassword() {
        return incomingPassword;
    }

    public void setIncomingPassword(String incomingPassword) {
        this.incomingPassword = incomingPassword;
    }

    @Basic
    @Column(name = "outgoing_hostname", nullable = true, length = 255)
    public String getOutgoingHostname() {
        return outgoingHostname;
    }

    public void setOutgoingHostname(String outgoingHostname) {
        this.outgoingHostname = outgoingHostname;
    }

    @Basic
    @Column(name = "outgoing_email", nullable = true, length = 255)
    public String getOutgoingEmail() {
        return outgoingEmail;
    }

    public void setOutgoingEmail(String outgoingEmail) {
        this.outgoingEmail = outgoingEmail;
    }

    @Basic
    @Column(name = "outgoing_port", nullable = true, length = 255)
    public String getOutgoingPort() {
        return outgoingPort;
    }

    public void setOutgoingPort(String outgoingPort) {
        this.outgoingPort = outgoingPort;
    }

    @Basic
    @Column(name = "outgoing_username", nullable = true, length = 255)
    public String getOutgoingUsername() {
        return outgoingUsername;
    }

    public void setOutgoingUsername(String outgoingUsername) {
        this.outgoingUsername = outgoingUsername;
    }

    @Basic
    @Column(name = "outgoing_password", nullable = true, length = 255)
    public String getOutgoingPassword() {
        return outgoingPassword;
    }

    public void setOutgoingPassword(String outgoingPassword) {
        this.outgoingPassword = outgoingPassword;
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
        MailServer that = (MailServer) o;
        return Objects.equals(mailServerId, that.mailServerId) &&
            Objects.equals(incomingHostname, that.incomingHostname) &&
            Objects.equals(incomingEmail, that.incomingEmail) &&
            Objects.equals(incomingPort, that.incomingPort) &&
            Objects.equals(incomingUsername, that.incomingUsername) &&
            Objects.equals(incomingPassword, that.incomingPassword) &&
            Objects.equals(outgoingHostname, that.outgoingHostname) &&
            Objects.equals(outgoingEmail, that.outgoingEmail) &&
            Objects.equals(outgoingPort, that.outgoingPort) &&
            Objects.equals(outgoingUsername, that.outgoingUsername) &&
            Objects.equals(outgoingPassword, that.outgoingPassword) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdUser, that.createdUser) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastUpdatedBy, that.lastUpdatedBy) &&
            Objects.equals(lastUpdatedDate, that.lastUpdatedDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mailServerId, incomingHostname, incomingEmail, incomingPort, incomingUsername, incomingPassword, outgoingHostname, outgoingEmail, outgoingPort, outgoingUsername, outgoingPassword, status, createdUser, createdDate, lastUpdatedBy, lastUpdatedDate);
    }

    @Basic
    @Column(name = "incoming_mail_service_id", nullable = true)
    public Integer getIncomingMailServiceId() {
        return incomingMailServiceId;
    }

    public void setIncomingMailServiceId(Integer incomingMailServiceId) {
        this.incomingMailServiceId = incomingMailServiceId;
    }

    @Basic
    @Column(name = "outgoing_mail_service_id", nullable = true)
    public Integer getOutgoingMailServiceId() {
        return outgoingMailServiceId;
    }

    public void setOutgoingMailServiceId(Integer outgoingMailServiceId) {
        this.outgoingMailServiceId = outgoingMailServiceId;
    }


    @JsonBackReference(value = "mailServersByCompanyId")
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    public Company getCompanyByCompanyId() {
        return companyByCompanyId;
    }

    public void setCompanyByCompanyId(Company companyByCompanyId) {
        this.companyByCompanyId = companyByCompanyId;
    }
}
