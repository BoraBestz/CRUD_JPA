package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdMailTemplateEntity;
import com.esign.service.configuration.entity.TblMdSmsTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblMdSmsTemplateRepository extends JpaRepository<TblMdSmsTemplateEntity, Long>, JpaSpecificationExecutor<TblMdSmsTemplateEntity> {
    List<TblMdSmsTemplateEntity> findAllBySmsTemplateCode(String code);
    Optional<TblMdSmsTemplateEntity> findById(long id);
    List<TblMdSmsTemplateEntity> findAllByDocumentTypeIdAndRecordStatus(Long id, String status);
}
