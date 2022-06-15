package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.DocumentConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentConfigRepository
        extends JpaRepository<DocumentConfigEntity, Integer>,
        JpaSpecificationExecutor<DocumentConfigEntity> {

    List<DocumentConfigEntity> findAllByStatus(String status);
    List<DocumentConfigEntity> findAllByNameAndStatus(String name, String status);
    List<DocumentConfigEntity> findAllByDocumentConfigHeaderIdAndStatus(Integer id, String status);


}
