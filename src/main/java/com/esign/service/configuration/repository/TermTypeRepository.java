package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TermTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermTypeRepository
        extends JpaRepository<TermTypeEntity, Integer>,
        JpaSpecificationExecutor<TermTypeEntity> {

    List<TermTypeEntity> findAllByStatus(int status);
    List<TermTypeEntity> findAllByTermTypeCodeAndStatus(String code, int status);
    List<TermTypeEntity> findAllByTermTypeCode (String code);

}

