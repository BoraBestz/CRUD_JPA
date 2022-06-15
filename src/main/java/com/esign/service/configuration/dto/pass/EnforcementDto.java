package com.esign.service.configuration.dto.pass;

import com.esign.service.configuration.dto.address.CitySubDivisionDto;
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
public class EnforcementDto {

    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer enforceId;
    private String enforceName;
    private String isDefault;
    private String status;
    private Date createDate;
    private Integer createBy;
    private Date updateDate;
    private Integer updateBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<EnforcementPolicyDto> enforcementPolicy;

    @JsonIgnore private String fullSearch;

}
