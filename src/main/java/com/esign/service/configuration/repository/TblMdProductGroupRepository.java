package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdProductGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblMdProductGroupRepository extends JpaRepository<TblMdProductGroupEntity, Long>, JpaSpecificationExecutor<TblMdProductGroupEntity> {
    List<TblMdProductGroupEntity> findAllByProductGroupCodeAndRecordStatus(String code, String status);
    Optional<TblMdProductGroupEntity> findById(long id);
}
