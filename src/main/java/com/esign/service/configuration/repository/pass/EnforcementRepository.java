package com.esign.service.configuration.repository.pass;

import com.esign.service.configuration.entity.pass.EnforcementEntity;
import com.esign.service.configuration.entity.pass.PassEnforcementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnforcementRepository extends JpaRepository<EnforcementEntity, Integer>, JpaSpecificationExecutor<EnforcementEntity> {
    List<EnforcementEntity> findAllByEnforceNameAndStatus(String name, String status);
}
