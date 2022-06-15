package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository
        extends JpaRepository<CurrencyEntity, Integer>,
        JpaSpecificationExecutor<CurrencyEntity> {

    List<CurrencyEntity> findAllByStatus(int status);
    List<CurrencyEntity> findAllByCurrencyCodeAndStatus(String code, int status);
    List<CurrencyEntity> findAllByCurrencyCode (String code);

}

