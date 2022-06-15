package com.esign.service.configuration.dto.email;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EmailTemplateAttachmentDto {
    private Long emailTemplateAttachmentId;
    private Integer emailTemplateId;
    private Long dmsId;
    private Integer status;
    private Long createdUser;
    private Timestamp createdDate;
    private Long lastUpdatedBy;
    private Timestamp lastUpdatedDate;
}
