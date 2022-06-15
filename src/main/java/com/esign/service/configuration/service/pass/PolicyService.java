package com.esign.service.configuration.service.pass;

import com.esign.service.configuration.dto.pass.PolicyDto;
import com.esign.service.configuration.entity.pass.PolicyEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.pass.PolicyRepository;
import com.esign.service.configuration.utils.PageUtils;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class PolicyService {

    private PolicyRepository policyRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setConfigService(PolicyRepository policyRepository,
                                 ModelMapper modelMapper){
        this.policyRepository = policyRepository;
        this.modelMapper = modelMapper;
    }

    public PolicyEntity save(PolicyDto dto) throws BusinessServiceException {
        log.info("String Saving Data....");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info("Map dto with Config Entity.class.....");
        PolicyEntity entity = modelMapper.map(dto, PolicyEntity.class);
        log.info("End Saving Data....");
        return policyRepository.save(entity);
    }

    public Integer checkDuplicate(PolicyDto dto) throws BusinessServiceException {
        log.info("String Check Duplicate Data....");
        try {
            log.info("Get Data from DB....");
            List<PolicyEntity> rs = policyRepository.findAllByPolicyNameAndStatus(dto.getPolicyName(), dto.getStatus());

            if (rs != null && rs.size() > 0){
                return 1;
            }
            return 0;
        }catch (Exception ex){
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    public PolicyDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<PolicyEntity> byId = policyRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), PolicyDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Page<PolicyDto> findByCri(PolicyDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PolicyService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<PolicyEntity> all =
                    policyRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<PolicyDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR PolicyService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PolicyService.findByCri()...");
        }
    }
    @VisibleForTesting
    protected Specification<PolicyEntity> getSpecification(PolicyDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getPolicyId() != null){
                predicates.add(cb.equal(root.get("policyId"), filter.getPolicyId()));
            }

            if (filter.getPolicyName() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("policyName")), "%" + filter.getPolicyName().toLowerCase() + "%"));
            }

            if (filter.getPolicyValueType() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("policyValueType")), "%" + filter.getPolicyValueType().toLowerCase() + "%"));
            }

            if (filter.getPolicyValueDefault() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("policyValueDefault")), "%" + filter.getPolicyValueDefault().toLowerCase() + "%"));
            }

            if (filter.getIsPredefined() != null) {
                predicates.add(cb.equal(root.get("isPredefined"), filter.getIsPredefined()));
            }

            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus().trim()));
            }else {
                predicates.add(cb.equal(root.get("status"), "A"));
            }

            if (filter.getCreateBy() != null) {
                predicates.add(cb.equal(root.get("createBy"), filter.getCreateBy()));
            }

            if (filter.getUpdateBy() != null) {
                predicates.add(cb.equal(root.get("updateBy"), filter.getUpdateBy()));
            }

            if (filter.getPolicyShowText() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("policyShowText")), "%" + filter.getPolicyShowText().toLowerCase() + "%"));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("policyName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("policyShowText")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<PolicyDto> convertPageEntityToPageDto(Page<PolicyEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<PolicyDto> map =
                source.map(
                        new Function<PolicyEntity, PolicyDto>() {
                            @Override
                            public PolicyDto apply(PolicyEntity entity) {
                                PolicyDto dto = modelMapper.map(entity, PolicyDto.class);
                                return dto;
                            }
                        });
        return map;
    }

}
