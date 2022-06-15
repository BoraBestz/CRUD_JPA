package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdMailboxTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblMdMailboxTaskRepository extends JpaRepository<TblMdMailboxTaskEntity, Integer>, JpaSpecificationExecutor<TblMdMailboxTaskEntity> {
    List<TblMdMailboxTaskEntity> findAllByMailboxTaskCodeAndRecordStatus(String code, String status);
    List<TblMdMailboxTaskEntity> findAllByDocumentTypeIdAndRecordStatus(Long id, String status);
    List<TblMdMailboxTaskEntity> findAllByMailTemplateIdAndRecordStatus(Long id, String status);
    List<TblMdMailboxTaskEntity> findAllBySmsTemplateIdAndRecordStatus(Long id, String status);

}
