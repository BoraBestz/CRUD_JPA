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
public class TblMdSmsTemplateDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private long smsTemplateId;
    private String smsTemplateName;
    private String smsTemplateSubject;
    private String smsTemplateBody;
    private String recordStatus;
    private Integer companyId;
    private Integer documentTypeId;
    private String smsTemplateCode;
    private Long createdBy;
    private Date createdDate;
    private Long lastUpdatedBy;
    private Date lastUpdatedDate;
    private Integer branchId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DocumentTypeDto documentType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TblMdMailboxTaskDto> tblMdMailboxTask;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDate;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDate;
}
