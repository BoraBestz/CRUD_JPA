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
public class TblMdPdfPasswordDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private long pdfPasswordId;
    private String pdfPasswordCode;
    private String pdfPasswordName;
    private String pdfPasswordField;
    private Long defaultFlag;
    private String recordStatus;
    private Long createdBy;
    private Date createdDate;
    private Long lastUpdateBy;
    private Date LastUpdateDate;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDate;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDate;
}
