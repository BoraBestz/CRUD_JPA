package com.esign.service.configuration.entity;

import javax.persistence.*;

@Entity
@Table(name = "tbl_md_document_validate_code", schema = "conf", catalog = "etax")
public class TblMdDocumentValidateCodeEntity {
    private long documentValidateCodeId;
    private String conditionDocumentCode;
    private String codeType;
    private String codeMassage;
    private String codeMassageNameEn;
    private String codeMassageNameTh;
    private String conditionCode1;
    private String conditionCode2;
    private Long createdUser;
    private Long createdDate;
    private Long lastUpdateBy;
    private Long LastUpdateDate;

    @Id
    @Column(name = "document_validate_code_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_validate_code_seq")
    @SequenceGenerator(
            name = "document_validate_code_seq",
            sequenceName = "document_validate_code_seq",
            allocationSize = 1)

    public long getDocumentValidateCodeId() {
        return documentValidateCodeId;
    }
    public void setDocumentValidateCodeId(long document_validate_code_id) {
        this.documentValidateCodeId = document_validate_code_id;
    }

    @Basic
    @Column(name = "condition_document_code")
    public String getConditionDocumentCode() {
        return conditionDocumentCode;
    }
    public void setConditionDocumentCode(String condition_document_code) {
        this.conditionDocumentCode = condition_document_code;
    }

    @Basic
    @Column(name = "code_type")
    public String getCodeType() {
        return codeType;
    }
    public void setCodeType(String code_type) {
        this.codeType = code_type;
    }

    @Basic
    @Column(name = "code_massage")
    public String getCodeMassage() {
        return codeMassage;
    }
    public void setCodeMassage(String code_massage) {
        this.codeMassage = code_massage;
    }

    @Basic
    @Column(name = "code_massage_name_en")
    public String getCodeMassageNameEn() {
        return codeMassageNameEn;
    }
    public void setCodeMassageNameEn(String code_massage_name_en) {
        this.codeMassageNameEn = code_massage_name_en;
    }

    @Basic
    @Column(name = "code_massage_name_th")
    public String getCodeMassageNameTh() {
        return codeMassageNameTh;
    }
    public void setCodeMassageNameTh(String code_massage_name_th) {
        this.codeMassageNameTh = code_massage_name_th;
    }

    @Basic
    @Column(name = "condition_code_1")
    public String getConditionCode1() {
        return conditionCode1;
    }
    public void setConditionCode1(String condition_code_1) {
        this.conditionCode1 = condition_code_1;
    }

    @Basic
    @Column(name = "condition_code_2")
    public String getConditionCode2() {
        return conditionCode2;
    }
    public void setConditionCode2(String condition_code_2) {
        this.conditionCode2 = condition_code_2;
    }

    @Basic
    @Column(name = "created_user")
    public Long getCreatedUser() {
        return createdUser;
    }
    public void setCreatedUser(Long createdUser) {
        this.createdUser = createdUser;
    }

    @Basic
    @Column(name = "created_date")
    public Long getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Long createdDate) {
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
    public Long getLastUpdateDate() {
        return LastUpdateDate;
    }
    public void setLastUpdateDate(Long lastUpdateDate) {
        LastUpdateDate = lastUpdateDate;
    }
}
