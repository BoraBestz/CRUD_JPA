package com.esign.service.configuration.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DocumentTemplateDto {

    private Integer templateId;
    private Integer companyId;
    private String doctypeCode;
    private Integer documentTypeId;
    private String docName;
    private Integer defaultFlag;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private String contentText;
    private String content;
}
