package com.esign.service.configuration.dto.pass;

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
public class EnforcementPolicyDto {

    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer epId;
    private Integer enforcementId;
    private Integer policyId;
    private String policyValue;
    private String status;
    private Date createDate;
    private Integer createBy;
    private Date updateDate;
    private Integer updateBy;

    @JsonIgnore private String fullSearch;

}
