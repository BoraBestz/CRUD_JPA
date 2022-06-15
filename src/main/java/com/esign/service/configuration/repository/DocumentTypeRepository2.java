package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.DocumentTypeEntity;
import com.esign.service.configuration.entity.email.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeRepository2
        extends JpaRepository<DocumentTypeEntity, Integer>,
        JpaSpecificationExecutor<DocumentTypeEntity> {

    List<DocumentTypeEntity> findAllByStatus(int status);
    DocumentTypeEntity getByDocumentTypeId(int id);
    List<DocumentTypeEntity> findAllByDocumentTypeCodeAndStatus(String code, int status);
    List<DocumentTypeEntity> findAllByDocumentTypeCode (String code);

    DocumentType getByDocumentTypeId(Integer documentTypeId);
}