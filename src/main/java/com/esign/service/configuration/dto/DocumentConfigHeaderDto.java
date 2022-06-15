package com.esign.service.configuration.dto;

import com.esign.service.configuration.dto.address.CountrySubDivisionDto;
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
public class DocumentConfigHeaderDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer documentConfigHeaderId;
    private String documentConfigHeaderCode;
    private String documentConfigHeaderName;
    private String companyCode;
    private String isValidate;
    private String status;
    private String isSel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<DocumentConfigDto> documentConfigDto;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDt;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDt;
}

