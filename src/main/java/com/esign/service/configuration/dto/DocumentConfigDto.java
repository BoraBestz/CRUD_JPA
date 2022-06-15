package com.esign.service.configuration.dto;

import com.esign.service.configuration.dto.address.CityDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentConfigDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer documentConfigId;
    private String name;
    private String messageItemName;
    private String status;
    private String packageName;
    private String type;
    private String filter;
    private String attribute;
    private Integer documentConfigHeaderId;
    private Integer docTemplateId;
    private String groupData;
    private Long excelColumnIndex;
    private String isDocumentCode;
    private String dataFormat;
    private Long dataLength;
    private String tableValidate;
    private String columnValidate;
    private String templateCode;
    private String isEncrypt;
    private String isShow;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DocumentConfigHeaderDto documentConfigHeaderDto;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDt;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDt;
}

