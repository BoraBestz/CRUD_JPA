package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.RdErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RdErrorRepository extends JpaRepository<RdErrorEntity, Integer>, JpaSpecificationExecutor<RdErrorEntity> {
    List<RdErrorEntity> findAllById(Integer id);
}
