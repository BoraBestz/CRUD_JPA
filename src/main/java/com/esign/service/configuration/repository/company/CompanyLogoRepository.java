package com.esign.service.configuration.repository.company;

import com.esign.service.configuration.entity.company.CompanyLogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyLogoRepository extends JpaRepository<CompanyLogo, Integer>, JpaSpecificationExecutor<CompanyLogo> {
    CompanyLogo getByCompanyId(Integer companyId);
    CompanyLogo getFirstByCompanyIdAndStatusOrderByCreatedDateDesc(Integer companyId,Integer status);
    CompanyLogo getByCompanyLogoId(Integer companyLogoId);
}
