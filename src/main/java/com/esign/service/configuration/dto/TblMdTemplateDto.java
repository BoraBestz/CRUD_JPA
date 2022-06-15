package com.esign.service.configuration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.Date;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TblMdTemplateDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer templateId;
    private String templateCode;
    private String templateName;
    private String status;
    private String isDefault;
    private Integer documentTypeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DocumentTypeDto documentTypeDto;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDt;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDt;
}
