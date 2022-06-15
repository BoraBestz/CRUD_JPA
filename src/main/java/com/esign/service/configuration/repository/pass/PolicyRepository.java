package com.esign.service.configuration.repository.pass;

import com.esign.service.configuration.entity.pass.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<PolicyEntity, Integer>, JpaSpecificationExecutor<PolicyEntity> {
    List<PolicyEntity> findAllByPolicyNameAndStatus(String name, String status);
}
