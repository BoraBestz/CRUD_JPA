package com.esign.service.configuration.dto.email;

import com.esign.service.configuration.dto.Document;
import lombok.*;

import java.sql.Timestamp;
import java.util.Collection;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateDto {
    private Integer emailTemplateId;
    private Integer documentTypeId;
    private Integer companyId;
    private String mailFrom;
    private String mailTo;
    private String cc;
    private String bcc;
    private String subject;
    private String body;
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private Timestamp effectiveDate;
    private Collection<Document> newEmailTemplateAttachments;
    private Collection<EmailTemplateAttachmentDto> emailTemplateAttachmentsByEmailTemplateId;
}
