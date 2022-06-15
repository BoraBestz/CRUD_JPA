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
public class PasswordHistoryDto {

    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private Integer passwordHistoryId;
    private Integer userId;
    private String passwordHistory;
    private String status;
    private Date createDate;

    @JsonIgnore private String fullSearch;

}
