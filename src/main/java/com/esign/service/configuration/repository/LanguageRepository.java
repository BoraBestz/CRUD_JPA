package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer>, JpaSpecificationExecutor<LanguageEntity> {
        List<LanguageEntity> findAllByLanguageCode(String code);
        }
