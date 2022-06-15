package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdMailTemplateAttEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblMdMailTemplateAttRepository extends JpaRepository<TblMdMailTemplateAttEntity, Long>, JpaSpecificationExecutor<TblMdMailTemplateAttEntity> {
    List<TblMdMailTemplateAttEntity> findAllByMailTemplateAttCode(String code);
    Optional<TblMdMailTemplateAttEntity> findById(long id);
}
