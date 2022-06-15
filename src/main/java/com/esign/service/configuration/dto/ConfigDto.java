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
public class ConfigDto {

    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer configId;
    private String configModule;
    private String configName;
    private String configValue;
    private String configDescription;
    private Date createDate;
    private String createBy;
    private Date updateDate;
    private String updateBy;
    private Boolean isActive;

    @JsonIgnore private String fullSearch;
}
