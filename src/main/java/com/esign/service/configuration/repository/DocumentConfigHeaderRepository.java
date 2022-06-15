package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.DocumentConfigHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentConfigHeaderRepository
        extends JpaRepository<DocumentConfigHeaderEntity, Integer>,
        JpaSpecificationExecutor<DocumentConfigHeaderEntity> {

    List<DocumentConfigHeaderEntity> findAllByStatus(String status);
    List<DocumentConfigHeaderEntity> findAllByDocumentConfigHeaderCodeAndStatus(String name, String status);

}
