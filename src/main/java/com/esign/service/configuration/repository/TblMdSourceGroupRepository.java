package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdSourceGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblMdSourceGroupRepository
        extends JpaRepository<TblMdSourceGroupEntity, Integer>,
        JpaSpecificationExecutor<TblMdSourceGroupEntity> {

    List<TblMdSourceGroupEntity> findAllByStatus(String status);
    List<TblMdSourceGroupEntity> findAllBySourceGroupCodeAndStatus(String code, String status);
    List<TblMdSourceGroupEntity> findAllBySourceGroupCode (String code);

}

