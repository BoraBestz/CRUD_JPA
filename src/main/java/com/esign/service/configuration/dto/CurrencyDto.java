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
public class CurrencyDto {
    @JsonIgnore private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer currencyId;
    private Integer status;
    private String currencyCode;
    private String currencyNameTh;
    private String currencyNameEn;
    private String currencyDescription;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDt;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDt;
}
