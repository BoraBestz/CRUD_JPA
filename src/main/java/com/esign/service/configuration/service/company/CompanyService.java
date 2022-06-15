package com.esign.service.configuration.service.company;

import com.esign.service.configuration.dto.company.CompanyCriteria;
import com.esign.service.configuration.entity.company.Company;
import com.esign.service.configuration.entity.company.CompanyLogo;
import com.esign.service.configuration.repository.company.CompanyLogoRepository;
import com.esign.service.configuration.repository.company.CompanyRepository;
import com.esign.service.configuration.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompanyService {

    @Autowired
    protected CompanyRepository companyRepository;

    @Autowired
    protected CompanyLogoRepository companyLogoRepository;

    private Specification<Company> getCompanySpecification(CompanyCriteria filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            /*if (filter.getCompanyId() != null) {
                predicates.add(cb.equal(root.get("companyId"), filter.getCompanyId()));
            }*/
            if (filter.getGlobalId() != null) {
                predicates.add(cb.equal(root.get("globalId"), filter.getGlobalId()));
            }
            if (filter.getName() != null) {
                predicates.add(cb.like(cb.lower(root.get("name")),"%" + filter.getName().toLowerCase() + "%"));
            }
            if (filter.getTaxId() != null) {
                predicates.add(cb.equal(root.get("taxId"), filter.getTaxId()));
            }
            if (filter.getTaxSchemeId() != null) {
                predicates.add(cb.equal(root.get("taxSchemeId"), filter.getTaxSchemeId()));
            }
            if (filter.getPostcodeCode() != null) {
                predicates.add(cb.equal(root.get("postcodeCode"), filter.getPostcodeCode()));
            }
            if (filter.getParentCompanyId() != null) {
                predicates.add(cb.equal(root.get("parentCompanyId"), filter.getParentCompanyId()));
            }

            if (filter.getBranchId() != null || filter.getBranchName() != null) {
                Join mapBranchByCompanyId = root.join("mapBranchByCompanyId",JoinType.INNER);
                if(filter.getBranchId() != null){
                    predicates.add(cb.equal(mapBranchByCompanyId.get("branchId"), filter.getBranchId()));
                }
                if(filter.getBranchName() != null){
                    predicates.add(cb.like(cb.lower(mapBranchByCompanyId.get("name")),"%" + filter.getBranchName().toLowerCase() + "%"));
                }
            }

            if(filter.getCompanyIds() != null && filter.getCompanyIds().size() > 0){
                CriteriaBuilder.In branchIdIn = cb.in(root.get("companyId"));
                filter.getCompanyIds().forEach(branchIdIn::value);
                //Arrays.stream(filter.getBranchIds()).forEach(branchIdIn::value);
                predicates.add(branchIdIn);
            }

            if(filter.getSearch() != null) {
                predicates.add(
                        cb.or(
                                cb.like(root.get("taxId"), "%" + filter.getSearch() + "%"),
                                cb.like(root.get("name"), "%" + filter.getSearch() + "%"),
                                cb.like(root.get("taxSchemeId"), "%" + filter.getSearch() + "%"),
                                cb.like(root.get("postcodeCode"), "%" + filter.getSearch() + "%")
                        ));
            }
            if (filter.getStatus()!= null ) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<Company> findAllCompany (CompanyCriteria dto) {
        Pageable pageRequest = PageUtils.getPageable(dto.getPage(), dto.getPerPage());
        return companyRepository.findAll(getCompanySpecification(dto), pageRequest);
    }

    public Company saveCompany(Company company){
        /*ModelMapper modelMapper = new ModelMapper();
        PropertyMap<CompanyDto, Company> propertyMap = new PropertyMap<CompanyDto, Company>() {
            protected void configure() {
                try {
                    map().setCompanyLogo(source.getCompanyLogo().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        modelMapper.addMappings(propertyMap);
        Company company = modelMapper.map(companyDto,Company.class);*/
        return this.companyRepository.save(company);
    }

    public Company getCompany(Integer companyId){
        return this.companyRepository.getByCompanyId(companyId);
    }

    public List<Company> getCompanyByParentCompany(Integer[] companyId) {
        return companyRepository.getByParentCompanyIdIn(companyId);
    }

    public CompanyLogo save(CompanyLogo companyLogo) {
        return companyLogoRepository.save(companyLogo);
    }

    public CompanyLogo getByCompanyId(Integer companyId) {
        return companyLogoRepository.getByCompanyId(companyId);
    }

    public CompanyLogo getFirstByCompanyIdAndStatusOrderByCreatedDateDesc(Integer companyId,Integer status) {
        return companyLogoRepository.getFirstByCompanyIdAndStatusOrderByCreatedDateDesc(companyId,status);
    }

    public CompanyLogo getByCompanyLogoId(Integer companyLogoId) {
        return companyLogoRepository.getByCompanyLogoId(companyLogoId);
    }

    public Company getCompany(String taxId) {
        return companyRepository.getByTaxId(taxId);
    }
}
