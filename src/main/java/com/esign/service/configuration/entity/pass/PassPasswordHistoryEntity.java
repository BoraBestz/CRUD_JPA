package com.esign.service.configuration.entity.pass;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "pass_password_history", schema = "", catalog = "")
public class PassPasswordHistoryEntity {
    private int passwordHistoryId;
    private int userId;
    private String passwordHistory;
    private String status;
    private Date createdDate;

    @Id
    @Column(name = "PASSWORD_HISTORY_ID")
    @GeneratedValue(generator = "pass_password_history_seq")
    @SequenceGenerator(name = "pass_password_history_seq", sequenceName = "pass_password_history_seq", allocationSize = 1 )
    public int getPasswordHistoryId() {
        return passwordHistoryId;
    }

    public void setPasswordHistoryId(int passwordHistoryId) {
        this.passwordHistoryId = passwordHistoryId;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "password_history")
    public String getPasswordHistory() {
        return passwordHistory;
    }

    public void setPasswordHistory(String passwordHistory) {
        this.passwordHistory = passwordHistory;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "created_date")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PassPasswordHistoryEntity that = (PassPasswordHistoryEntity) o;

        if (passwordHistoryId != that.passwordHistoryId) return false;
        if (userId != that.userId) return false;
        if (passwordHistory != null ? !passwordHistory.equals(that.passwordHistory) : that.passwordHistory != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = passwordHistoryId;
        result = 31 * result + userId;
        result = 31 * result + (passwordHistory != null ? passwordHistory.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        return result;
    }
}
