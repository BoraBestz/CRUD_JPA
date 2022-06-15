package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.DocumentConfigEntity;
import com.esign.service.configuration.entity.TblMdSourceDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblMdSourceDataRepository
        extends JpaRepository<TblMdSourceDataEntity, Integer>,
        JpaSpecificationExecutor<TblMdSourceDataEntity> {

    List<TblMdSourceDataEntity> findAllByStatus(String status);
    List<TblMdSourceDataEntity> findAllBySourceDataCodeAndStatus(String code, String status);
    List<TblMdSourceDataEntity> findAllBySourceDataCode (String code);
    List<TblMdSourceDataEntity> findAllBySourceGroupIdAndStatus(Integer id, String status);

}

