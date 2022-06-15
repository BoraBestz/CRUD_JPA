package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdDocumentValidateCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblMdDocumentValidateCodeRepository extends JpaRepository<TblMdDocumentValidateCodeEntity, Long>, JpaSpecificationExecutor<TblMdDocumentValidateCodeEntity> {
    List<TblMdDocumentValidateCodeEntity> findAllByConditionDocumentCode(String name);
    Optional<TblMdDocumentValidateCodeEntity> findById(long id);
}
