package com.esign.service.configuration.service.email;

import com.esign.service.configuration.dto.email.EmailTemplateCriteria;
import com.esign.service.configuration.entity.DocumentTypeEntity;
import com.esign.service.configuration.entity.email.*;
import com.esign.service.configuration.repository.DocumentTypeRepository2;
import com.esign.service.configuration.repository.email.EmailTemplateAttachmentRepository;
import com.esign.service.configuration.repository.email.EmailTemplateRepository;
import com.esign.service.configuration.utils.PageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailTemplateService {
    @Autowired
    protected EmailTemplateRepository emailTemplateRepository;

    @Autowired
    protected EmailTemplateAttachmentRepository emailTemplateAttachmentRepository;

    @Autowired
    protected DocumentTypeRepository2 documentTypeRepository;

    private Specification<EmailTemplate> getEmailTemplateSpecification(EmailTemplateCriteria filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getEmailTemplateId() != null) {
                predicates.add(cb.equal(root.get("emailTemplateId"), filter.getEmailTemplateId()));
            }
            if (filter.getCompanyId() != null) {
                predicates.add(cb.equal(root.get("companyId"), filter.getCompanyId()));
            }
            if (filter.getDocumentTypeId() != null) {
                predicates.add(cb.equal(root.get("documentTypeId"), filter.getDocumentTypeId()));
            }
            if (filter.getStatus()!= null ) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<EmailTemplate> findAllEmailTemplateBySpecification(EmailTemplateCriteria searchCriteria) {
        Pageable pageRequest = PageUtils.getPageable(searchCriteria.getPage(), searchCriteria.getPerPage());
        return emailTemplateRepository.findAll(getEmailTemplateSpecification(searchCriteria), pageRequest);
    }

    public EmailTemplate getEmailTemplate(Integer emailTemplateId){
        return emailTemplateRepository.getByEmailTemplateId(emailTemplateId);
    }

    public EmailTemplate getEmailTemplate(Integer companyId, Integer docTypeId){
        List<EmailTemplate> templates = emailTemplateRepository.getByCompanyIdAndDocumentTypeId(companyId, docTypeId);
        if (templates != null && templates.size() > 0) {
            return templates.get(0);
        }
        return null;
    }

    public EmailTemplate saveEmailTemplate(EmailTemplate emailTemplate){
        return emailTemplateRepository.save(emailTemplate);
    }

    public EmailTemplateAttachment saveEmailTemplateAttachment(
        EmailTemplateAttachment emailTemplateAttachment){
        return emailTemplateAttachmentRepository.save(emailTemplateAttachment);
    }

    public EmailTemplateAttachment getByEmailTemplateAttachmentId(Long emailTemplateAttachmentId){
        return emailTemplateAttachmentRepository.getByEmailTemplateAttachmentId(emailTemplateAttachmentId);
    }

    public DocumentType getByDocumentTypeId(Integer documentTypeId){
        return documentTypeRepository.getByDocumentTypeId(documentTypeId);
    }

    public void deleteEmailTemplate(Integer emailTemplateId) {
        emailTemplateRepository.deleteById(emailTemplateId);
    }
}
