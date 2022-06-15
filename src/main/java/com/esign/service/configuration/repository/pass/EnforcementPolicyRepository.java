package com.esign.service.configuration.repository.pass;

import com.esign.service.configuration.entity.pass.EnforcementPolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnforcementPolicyRepository extends JpaRepository<EnforcementPolicyEntity, Integer>, JpaSpecificationExecutor<EnforcementPolicyEntity> {
    List<EnforcementPolicyEntity> findAllByPolicyValueAndStatus(String value, String status);
    List<EnforcementPolicyEntity> findAllByEnforcementIdAndStatus(int id, String status);
}
