package com.esign.service.configuration.repository.email;

import com.esign.service.configuration.entity.email.EmailTemplateAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateAttachmentRepository extends JpaRepository<EmailTemplateAttachment, Integer>, JpaSpecificationExecutor<EmailTemplateAttachment> {
    EmailTemplateAttachment getByEmailTemplateAttachmentId(Long emailTemplateAttachmentId);
}
