package com.esign.service.configuration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UnitDto {
    @JsonIgnore private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer unitId;
    private Integer status;
    private String unitCode;
    private String unitNameTh;
    private String unitNameEn;
    private String unitDescription;
    private String convertionFactor;
    private String symbol;
    private Long createdUser;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDt;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDt;
}
