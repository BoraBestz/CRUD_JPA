package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdDocumentTemplateConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblMdDocumentTemplateConfigRepository extends JpaRepository<TblMdDocumentTemplateConfigEntity, Long>, JpaSpecificationExecutor<TblMdDocumentTemplateConfigEntity> {
    List<TblMdDocumentTemplateConfigEntity> findAllByDocumentTemplateCodeAndStatus(String code, int status);
}
