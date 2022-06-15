package com.esign.service.configuration.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_md_product", schema = "conf", catalog = "etax")
public class TblMdProductEntity {

    private long productId;
    private String productCode;
    private String productNameTh;
    private String productNameEn;
    private String productDescription;
    private Long productLevel;
    private Long productParent;
    private Long unitId;
    private Long productGroupId;
    private String recordStatus;
    private Long createdUser;
    private Date createdDate;
    private Long lastUpdatedBy;
    private Date lastUpdatedDate;
    private String unspscCode;

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(
            name = "product_seq",
            sequenceName = "product_seq",
            allocationSize = 1)
    public long getProductId() {
        return productId;
    }
    public void setProductId(long product_id) {
        this.productId = product_id;
    }

    @Basic
    @Column(name = "product_code")
    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String product_code) {
        this.productCode = product_code;
    }

    @Basic
    @Column(name = "product_name_th")
    public String getProductNameTh() {
        return productNameTh;
    }
    public void setProductNameTh(String product_name_th) {
        this.productNameTh = product_name_th;
    }

    @Basic
    @Column(name = "product_name_en")
    public String getProductNameEn() {
        return productNameEn;
    }
    public void setProductNameEn(String product_name_en) {
        this.productNameEn = product_name_en;
    }

    @Basic
    @Column(name = "product_description")
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String product_description) {
        this.productDescription = product_description;
    }

    @Basic
    @Column(name = "product_level")
    public Long getProductLevel() {
        return productLevel;
    }
    public void setProductLevel(Long product_level) {
        this.productLevel = product_level;
    }

    @Basic
    @Column(name = "product_parent")
    public Long getProductParent() {
        return productParent;
    }
    public void setProductParent(Long product_parent) {
        this.productParent = product_parent;
    }

    @Basic
    @Column(name = "unit_id")
    public Long getUnitId() {
        return unitId;
    }
    public void setUnitId(Long unit_id) {
        this.unitId = unit_id;
    }

    @Basic
    @Column(name = "product_group_id")
    public Long getProductGroupId() {
        return productGroupId;
    }
    public void setProductGroupId(Long product_group_id) {
        this.productGroupId = product_group_id;
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

    @Basic
    @Column(name = "unspsc_code")
    public String getUnspscCode() {
        return unspscCode;
    }
    public void setUnspscCode(String unspsc_code) {
        this.unspscCode = unspsc_code;
    }

}
