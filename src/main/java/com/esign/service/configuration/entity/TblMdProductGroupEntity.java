package com.esign.service.configuration.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tbl_md_product_group", schema = "conf", catalog = "etax")
public class TblMdProductGroupEntity {
    private long productGroupId;
    private String productGroupCode;
    private String productGroupName;
    private String recordStatus;
    private Long createdUser;
    private Date createdDate;
    private Long lastUpdatedBy;
    private Date lastUpdatedDate;

    @Id
    @Column(name = "product_group_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_group_seq")
    @SequenceGenerator(
            name = "product_group_seq",
            sequenceName = "product_group_seq",
            allocationSize = 1)

    public long getProductGroupId() {
        return productGroupId;
    }
    public void setProductGroupId(long product_group_id) {
        this.productGroupId = product_group_id;
    }

    @Basic
    @Column(name = "product_group_code")
    public String getProductGroupCode() {
        return productGroupCode;
    }
    public void setProductGroupCode(String product_group_code) {
        this.productGroupCode = product_group_code;
    }

    @Basic
    @Column(name = "product_group_name")
    public String getProductGroupName() {
        return productGroupName;
    }
    public void setProductGroupName(String product_group_name) {
        this.productGroupName = product_group_name;
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
    @Column(name = "created_user")
    public Long getCreatedUser() {
        return createdUser;
    }
    public void setCreatedUser(Long created_user) {
        this.createdUser = created_user;
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

}
