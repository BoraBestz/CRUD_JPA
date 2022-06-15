package com.esign.service.configuration.dto;

import com.esign.service.configuration.dto.company.BranchDto;
import com.esign.service.configuration.dto.company.CompanyDto;
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
public class TblMdMailTemplateDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer mailTemplateId;
    private Integer companyId;
    private Integer documentTypeId;
    private String mailTemplateFrom;
    private String mailTemplateTo;
    private String mailTemplateCc;
    private String mailTemplateSubject;
    private String mailTemplateBody;
    private Long effectiveDate;
    private String recordStatus;
    private Long createdBy;
    private Long createdDate;
    private Long lastUpdateBy;
    private Long LastUpdateDate;
    private String mailTemplateBcc;
    private String mailTemplateName;
    private String mailTemplateCode;
    private Integer branchId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DocumentTypeDto documentType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CompanyDto company;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BranchDto Branch;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TblMdMailboxTaskDto> tblMdMailboxTask;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDate;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDate;
}
