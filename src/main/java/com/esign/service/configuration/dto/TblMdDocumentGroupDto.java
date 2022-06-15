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
public class TblMdDocumentGroupDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer documentGroupId;
    private String recordStatus;
    private String documentGroupCode;
    private String documentGroupName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TblMdMailTemplateDto> tblMdMailTemplate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TblMdMailboxTaskDto> tblMdMailboxTask;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDate;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDate;
}
