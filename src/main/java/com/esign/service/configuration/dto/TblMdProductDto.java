package com.esign.service.configuration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TblMdProductDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private long productId;
    private String productCode;
    private String productNameTh;
    private String productNameEn;
    private String productDescription;
    private Long productLevel;
    private Long productParent;
    private Long unitId;
    private Long productGroupId;
    private String recordStatus;
    private Long createdUser;
    private Date createdDate;
    private Long lastUpdatedBy;
    private Date lastUpdatedDate;
    private String unspscCode;

    private List<TblMdProductDto> tblMdProductDtoList;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDate;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDate;
}
