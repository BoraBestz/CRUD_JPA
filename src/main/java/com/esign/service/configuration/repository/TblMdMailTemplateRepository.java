package com.esign.service.configuration.repository;


import com.esign.service.configuration.entity.TblMdDocumentGroupInfEntity;
import com.esign.service.configuration.entity.TblMdMailTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblMdMailTemplateRepository extends JpaRepository<TblMdMailTemplateEntity, Integer>, JpaSpecificationExecutor<TblMdMailTemplateEntity> {
    List<TblMdMailTemplateEntity> findAllByMailTemplateCode(String code);
    List<TblMdMailTemplateEntity> findAllByBranchIdAndRecordStatus(Integer id, String status);
    List<TblMdMailTemplateEntity> findAllByCompanyIdAndRecordStatus(Integer id, String status);
    List<TblMdMailTemplateEntity> findAllByDocumentTypeIdAndRecordStatus(Integer id, String status);
}
