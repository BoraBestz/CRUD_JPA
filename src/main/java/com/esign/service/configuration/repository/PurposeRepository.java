package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.PurposeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurposeRepository extends JpaRepository<PurposeEntity, Integer>, JpaSpecificationExecutor<PurposeEntity> {
    List<PurposeEntity> findAllByPurposeCodeAndStatus(String name, int status);
    List<PurposeEntity> findAllByStatus(int status);
}
