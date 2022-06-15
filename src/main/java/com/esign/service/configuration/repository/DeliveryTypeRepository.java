package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.DeliveryTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryTypeRepository
        extends JpaRepository<DeliveryTypeEntity, Integer>,
        JpaSpecificationExecutor<DeliveryTypeEntity> {

    List<DeliveryTypeEntity> findAllByStatus(int status);
    List<DeliveryTypeEntity> findAllByDeliveryTypeCodeAndStatus(String code, int status);

}

