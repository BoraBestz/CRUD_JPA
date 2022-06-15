package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TransStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransStatusRepository extends JpaRepository<TransStatusEntity, String>, JpaSpecificationExecutor<TransStatusEntity> {
    List<TransStatusEntity> findAllByStatusCode(String code);

    List<TransStatusEntity> findAllByStatusCodeAndIsActive(String code, boolean status);

    Optional<TransStatusEntity> findByStatusCode(String code);
}