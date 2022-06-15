package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.RdErrorCriteriaEntity;
import com.esign.service.configuration.entity.RdErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RdErrorCriteriaRepository extends JpaRepository<RdErrorCriteriaEntity, Integer>, JpaSpecificationExecutor<RdErrorCriteriaEntity> {
    List<RdErrorEntity> findAllById(Integer id);
}
