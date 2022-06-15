package com.esign.service.configuration.repository.company;

import com.esign.service.configuration.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>, JpaSpecificationExecutor<Company> {
    Company getByCompanyId(Integer companyId);

    Company getByTaxId(String taxId);

    List<Company> getByParentCompanyIdIn(Integer[] companyId);
}
