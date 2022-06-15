package com.esign.service.configuration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TblMdDocumentValidateCodeDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

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

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDate;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDate;
}
