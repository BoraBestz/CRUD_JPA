package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends JpaRepository<ConfigEntity, Integer>, JpaSpecificationExecutor<ConfigEntity> {
    List<ConfigEntity> findAllByConfigName(String name);
}
