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

public class PurposeDto {
    @JsonIgnore private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer purposeId;
    private int status;
    private Long createdBy;
    private Date createdDate;
    private Long lastUpdatedBy;
    private Date lastUpdatedDate;
    private String purposeCode;
    private String purposeNameTh;
    private String purposeNameEn;
    private String purposeDescription;
    private String documentTypecode;
    private Long createdUser;
    private String usingType;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDt;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDt;

}
