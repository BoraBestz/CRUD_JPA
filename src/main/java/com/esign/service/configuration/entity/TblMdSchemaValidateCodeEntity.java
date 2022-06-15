package com.esign.service.configuration.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_md_schema_validate_code", schema = "conf", catalog = "etax")
public class TblMdSchemaValidateCodeEntity {
    private long schemaValidateCodeId;
    private String schemaConditionName;
    private String schemaCodeType;
    private String schemaCode;
    private String schemaCodeMassage;
    private String schemaCodeMassage2;
    private String conditionName;
    private Long createdBy;
    private Date createdDate;
    private Long lastUpdateBy;
    private Date LastUpdateDate;
    private String conditionGrorpName;

    @Id
    @Column(name = "schema_validate_code_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schema_validate_code_seq")
    @SequenceGenerator(
            name = "schema_validate_code_seq",
            sequenceName = "schema_validate_code_seq",
            allocationSize = 1)
    public long getSchemaValidateCodeId() {
        return schemaValidateCodeId;
    }
    public void setSchemaValidateCodeId(long schema_validate_code_id) {
        this.schemaValidateCodeId = schema_validate_code_id;
    }

    @Basic
    @Column(name = "schema_condition_name")
    public String getSchemaConditionName() {
        return schemaConditionName;
    }
    public void setSchemaConditionName(String schema_condition_name) {
        this.schemaConditionName = schema_condition_name;
    }

    @Basic
    @Column(name = "schema_code_type")
    public String getSchemaCodeType() {
        return schemaCodeType;
    }
    public void setSchemaCodeType(String schema_code_type) {
        this.schemaCodeType = schema_code_type;
    }

    @Basic
    @Column(name = "schema_code")
    public String getSchemaCode() {
        return schemaCode;
    }
    public void setSchemaCode(String schema_code) {
        this.schemaCode = schema_code;
    }

    @Basic
    @Column(name = "schema_code_massage")
    public String getSchemaCodeMassage() {
        return schemaCodeMassage;
    }
    public void setSchemaCodeMassage(String schema_code_massage) {
        this.schemaCodeMassage = schema_code_massage;
    }

    @Basic
    @Column(name = "schema_code_massage2")
    public String getSchemaCodeMassage2() {
        return schemaCodeMassage2;
    }
    public void setSchemaCodeMassage2(String schema_code_massage2) {
        this.schemaCodeMassage2 = schema_code_massage2;
    }

    @Basic
    @Column(name = "condition_name")
    public String getConditionName() {
        return conditionName;
    }
    public void setConditionName(String condition_name) {
        this.conditionName = condition_name;
    }

    @Basic
    @Column(name = "created_user")
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
    @Column(name = "condition_grorp_name")
    public String getConditionGrorpName() {
        return conditionGrorpName;
    }
    public void setConditionGrorpName(String condition_grorp_name) {
        this.conditionGrorpName = condition_grorp_name;
    }
}
