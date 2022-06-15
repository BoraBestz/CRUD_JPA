package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdProductEntity;
import com.esign.service.configuration.entity.TblMdProductGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblMdProductRepository extends JpaRepository<TblMdProductEntity, Long>, JpaSpecificationExecutor<TblMdProductEntity> {
    List<TblMdProductEntity> findAllByProductCodeAndRecordStatus(String code,String status);
    Optional<TblMdProductEntity> findById(long id);
    List<TblMdProductEntity> findByProductGroupId(Long id);


}
