package com.esign.service.configuration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TblMdMailboxTaskDto {

    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer mailboxTaskId;
    private String mailboxTaskCode;
    private Integer documentTypeId;
    private Integer companyId;
    private Integer branchId;
    private Integer mailTemplateId;
    private String mailLink;
    private String smsLink;
    private Integer smsTemplateId;
    private Integer rdTemplateId;
    private String rdLink;
    private String recordStatus;
    private Long createdBy;
    private Date createdDate;
    private Long lastUpdateBy;
    private Date LastUpdateDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DocumentTypeDto documentType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TblMdMailTemplateDto tblMdMailTemplate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TblMdSmsTemplateDto tblMdSmsTemplate;


    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDate;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDate;
}
