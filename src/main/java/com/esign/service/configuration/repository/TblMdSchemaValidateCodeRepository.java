package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdSchemaValidateCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblMdSchemaValidateCodeRepository extends JpaRepository<TblMdSchemaValidateCodeEntity, Long>, JpaSpecificationExecutor<TblMdSchemaValidateCodeEntity> {
    List<TblMdSchemaValidateCodeEntity> findAllBySchemaConditionName(String name);
    Optional<TblMdSchemaValidateCodeEntity> findById(long id);
}
