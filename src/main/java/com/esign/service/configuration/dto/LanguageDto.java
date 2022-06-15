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
public class LanguageDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer languageId;
    private Integer status;
    private Integer createBy;
    private Date createDate;
    private Integer lastUpdateBy;
    private Date lastUpdateDate;
    private String languageCode;
    private String languageNameTh;
    private String languageNameEn;
    private String languageDescription;

    @JsonIgnore private String fullSearch;

}
