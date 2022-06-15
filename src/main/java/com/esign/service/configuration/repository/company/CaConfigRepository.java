package com.esign.service.configuration.repository.company;

import com.esign.service.configuration.entity.company.CaConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CaConfigRepository extends JpaRepository<CaConfig,Integer>,JpaSpecificationExecutor<CaConfig> {
    CaConfig getByCaId(Integer caId);
}
