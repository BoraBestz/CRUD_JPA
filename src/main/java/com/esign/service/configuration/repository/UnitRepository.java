package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository
        extends JpaRepository<UnitEntity, Integer>,
        JpaSpecificationExecutor<UnitEntity> {

    List<UnitEntity> findAllByStatus(int status);
    List<UnitEntity> findAllByUnitCodeAndStatus(String code, int status);

}

