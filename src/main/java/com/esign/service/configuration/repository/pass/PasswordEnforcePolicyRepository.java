package com.esign.service.configuration.repository.pass;

import com.esign.service.configuration.entity.pass.PassEnforcementPolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordEnforcePolicyRepository extends JpaRepository<PassEnforcementPolicyEntity, Integer> {

    List<PassEnforcementPolicyEntity> findByPassEnforcementByEnforcementId_EnforceIdAndStatusAndPassPolicyByPolicyId_StatusAndPassPolicyByPolicyId_IsPredefined(
        int enforce, String status, String policyStatus, String predefined);
    List<PassEnforcementPolicyEntity> findByPassEnforcementByEnforcementId_EnforceIdAndStatusAndPassPolicyByPolicyId_Status(
        int enforce, String status, String policyStatus);

    //@Query(value = "Select u from UserDataEntity u where u.userStatus = '1'")
    //List<UserDataEntity> getAllByUserStatusOrderById();

}
