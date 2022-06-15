package com.esign.service.configuration.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    private Long id;
    @JsonIgnore
    private Long dmid;
    @ApiModelProperty(name = "filename", required = true)
    private String filename;
    @JsonIgnore
    private Integer version;
    @JsonIgnore
    private Integer owner;
    @JsonIgnore
    private Long dmidparent;
    @JsonIgnore
    private String status;
    @ApiModelProperty(name = "contentType", required = true)
    private String contentType;
    @JsonIgnore
    private String memo;
    @ApiModelProperty(name = "data", required = true)
    private String data;
    @JsonIgnore
    private byte[] secret;
    @JsonIgnore
    private Integer lastUpdatedUser;
    @JsonIgnore
    private Timestamp lastUpdatedDate;
    @JsonIgnore
    private Long parentId;

    private String taxNo;
    private String documentName;
    private Integer documentTypeId;

}
