package com.esign.service.configuration.repository.company;

import com.esign.service.configuration.entity.company.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BranchRepository extends JpaRepository<Branch,Integer>,JpaSpecificationExecutor<Branch> {
    Branch getByBranchId(Integer branchId);

    Branch getByTaxIdAndBranchCode(String taxId, String branchCode);
}
