package com.esign.service.configuration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTypeDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer documentTypeId;
    private String documentTypeCode;
    private String documentTypeNameTh;
    private String documentTypeNameEn;
    private String documentTypeDescription;
    private Integer status;
    private Long documentGroupId;
    private Long genXml;
    private Long genPdf;
    private Long signDoc;
    private String isEtax;
    private String recordStatus;
    private String documentTypeCodeRd;
    private String isSendRd;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TblMdTemplateDto> tblMdTemplate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TblMdMailTemplateDto> tblMdMailTemplate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TblMdSmsTemplateDto> tblMdSmsTemplate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TblMdMailboxTaskDto> tblMdMailboxTask;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDt;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDt;
}

