package com.esign.service.configuration.repository.email;

import com.esign.service.configuration.entity.DocumentTypeEntity;
import com.esign.service.configuration.entity.email.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DocumentTypeRepository extends JpaRepository<DocumentType,Integer>,JpaSpecificationExecutor<DocumentType> {
    DocumentType getByDocumentTypeId(Integer documentTypeId);

    DocumentType getByDocumentTypeCode(String typeCode);
}

