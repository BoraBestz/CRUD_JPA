package com.esign.service.configuration.repository.email;

import com.esign.service.configuration.entity.email.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate,Integer>,JpaSpecificationExecutor<EmailTemplate> {
    EmailTemplate getByEmailTemplateId(Integer emailTemplateId);

    List<EmailTemplate> getByCompanyIdAndDocumentTypeId(Integer companyId, Integer documentTypeId);
}
