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
import java.util.List;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TblMdSourceGroupDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer sourceGroupId;
    private String sourceGroupCode;
    private String sourceGroupName;
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TblMdSourceDataDto> tblMdSourceDataDto;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDt;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDt;
}
