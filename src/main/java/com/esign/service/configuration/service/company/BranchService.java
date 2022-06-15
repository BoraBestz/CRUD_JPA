package com.esign.service.configuration.service.company;

import com.esign.service.configuration.dto.company.BranchCriteria;
import com.esign.service.configuration.entity.company.Branch;
import com.esign.service.configuration.repository.company.BranchRepository;
import com.esign.service.configuration.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
public class BranchService {

    @Autowired
    protected BranchRepository branchRepository;

    private Specification<Branch> getBranchSpecification(BranchCriteria filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter.getBranchId() != null) {
                predicates.add(cb.equal(root.get("branchId"), filter.getBranchId()));
            }
            if(filter.getBranchIds() != null && filter.getBranchIds().length > 0){
                CriteriaBuilder.In buyerIdIn = cb.in(root.get("branchId"));
                Arrays.stream(filter.getBranchIds()).forEach(buyerIdIn::value);
                predicates.add(buyerIdIn);
            }
            if (filter.getCompanyId() != null) {
                predicates.add(cb.equal(root.get("companyId"), filter.getCompanyId()));
            }
            if (filter.getGlobalId() != null) {
                predicates.add(cb.equal(root.get("globalId"), filter.getGlobalId()));
            }
            if (filter.getName() != null) {
                predicates.add(cb.like(cb.lower(root.get("name")),"%" + filter.getName().toLowerCase() + "%"));
            }
            if (filter.getTaxId() != null) {
                predicates.add(cb.equal(root.get("taxId"), filter.getTaxId()));
            }
            if (filter.getPostcodeCode() != null) {
                predicates.add(cb.equal(root.get("postcodeCode"), filter.getPostcodeCode()));
            }
            if (filter.getCityCode() != null) {
                predicates.add(cb.equal(root.get("cityCode"), filter.getCityCode()));
            }
            if (filter.getCitySubDivisionCode() != null) {
                predicates.add(cb.equal(root.get("citySubDivisionCode"), filter.getCitySubDivisionCode()));
            }
            if (filter.getCountryCode()!= null ) {
                predicates.add(cb.equal(root.get("countryCode"), filter.getCountryCode()));
            }
            if (filter.getCountrySchemeId()!= null ) {
                predicates.add(cb.equal(root.get("countrySchemeId"), filter.getCountrySchemeId()));
            }
            if (filter.getCountrySubDivisionCode()!= null ) {
                predicates.add(cb.equal(root.get("countrySubDivisionCode"), filter.getCountrySubDivisionCode()));
            }
            if (filter.getStatus()!= null ) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<Branch> branchConfigSearch(BranchCriteria searchCriteria) {
        Pageable pageRequest = PageUtils.getPageable(searchCriteria.getPage(), searchCriteria.getPerPage());
        return branchRepository.findAll(getBranchSpecification(searchCriteria), pageRequest);
    }

    public Branch getBranch(String taxId, String branchCode) {
        return branchRepository.getByTaxIdAndBranchCode(taxId, branchCode);
    }

    public Branch getByBranchId(Integer branchId){
        return branchRepository.getByBranchId(branchId);
    }

    public Branch branchConfigSave(Branch branch){
        return branchRepository.save(branch);
    }

    public List<Branch> getBranchByCriteria(BranchCriteria searchCriteria){
        return branchRepository.findAll(getBranchSpecification(searchCriteria));
    }
}
