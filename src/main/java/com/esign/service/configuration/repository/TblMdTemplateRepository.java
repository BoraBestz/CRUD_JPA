package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblMdTemplateRepository
        extends JpaRepository<TblMdTemplateEntity, Integer>,
        JpaSpecificationExecutor<TblMdTemplateEntity> {

    List<TblMdTemplateEntity> findAllByStatus(String status);
    List<TblMdTemplateEntity> findAllByTemplateCodeAndStatus(String code, String status);
    List<TblMdTemplateEntity> findAllByTemplateCode (String code);
    List<TblMdTemplateEntity> findAllByDocumentTypeIdAndStatus(Integer id, String status);


}
