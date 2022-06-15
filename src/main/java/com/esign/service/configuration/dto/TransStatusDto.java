package com.esign.service.configuration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransStatusDto {

    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private String statusCode;
    private String statusName;
    private Boolean isActive;
    private String statusDescription;
    private Boolean showSearch;

    @JsonIgnore private String fullSearch;

}
