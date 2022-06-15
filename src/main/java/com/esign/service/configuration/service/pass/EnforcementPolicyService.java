package com.esign.service.configuration.service.pass;

import com.esign.service.configuration.dto.ComBranchDto;
import com.esign.service.configuration.dto.pass.EnforcementDto;
import com.esign.service.configuration.dto.pass.EnforcementPolicyDto;
import com.esign.service.configuration.entity.ComBranchEntity;
import com.esign.service.configuration.entity.pass.EnforcementEntity;
import com.esign.service.configuration.entity.pass.EnforcementPolicyEntity;
import com.esign.service.configuration.entity.pass.PassEnforcementEntity;
import com.esign.service.configuration.entity.pass.PassEnforcementPolicyEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.pass.EnforcementPolicyRepository;
import com.esign.service.configuration.repository.pass.EnforcementRepository;
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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class EnforcementPolicyService {

    private EnforcementPolicyRepository enforcementPolicyRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setConfigService(EnforcementPolicyRepository enforcementPolicyRepository,
                                 ModelMapper modelMapper){
        this.enforcementPolicyRepository = enforcementPolicyRepository;
        this.modelMapper = modelMapper;
    }

    public EnforcementPolicyEntity save(EnforcementPolicyDto dto) throws BusinessServiceException {
        log.info("String Saving Data....");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info("Map dto with Config Entity.class.....");
        EnforcementPolicyEntity entity = modelMapper.map(dto, EnforcementPolicyEntity.class);
        log.info("End Saving Data....");
        return enforcementPolicyRepository.save(entity);
    }

    public Integer checkDuplicate(EnforcementPolicyDto dto) throws BusinessServiceException {
        log.info("String Check Duplicate Data....");
        try {
            log.info("Get Data from DB....");
            List<EnforcementPolicyEntity> rs = enforcementPolicyRepository.findAllByPolicyValueAndStatus(dto.getPolicyValue(), dto.getStatus());

            if (rs != null && rs.size() > 0){
                return 1;
            }
            return 0;
        }catch (Exception ex){
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    public EnforcementPolicyDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<EnforcementPolicyEntity> byId = enforcementPolicyRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), EnforcementPolicyDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Page<EnforcementPolicyDto> findByCri(EnforcementPolicyDto dto) throws BusinessServiceException {
        try {
            log.info("Begin EnforcementPolicyService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<EnforcementPolicyEntity> all =
                    enforcementPolicyRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<EnforcementPolicyDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR EnforcementService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End EnforcementService.findByCri()...");
        }
    }
    @VisibleForTesting
    protected Specification<EnforcementPolicyEntity> getSpecification(EnforcementPolicyDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getEpId() != null){
                predicates.add(cb.equal(root.get("epId"), filter.getEpId()));
            }

            if (filter.getEnforcementId() != null){
                predicates.add(cb.equal(root.get("enforcementId"), filter.getEnforcementId()));
            }

            if (filter.getPolicyId() != null){
                predicates.add(cb.equal(root.get("policyId"), filter.getPolicyId()));
            }

            if (filter.getPolicyValue() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("policyValue")), "%" + filter.getPolicyValue().toLowerCase() + "%"));
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

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("policyValue")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("status")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<EnforcementPolicyDto> convertPageEntityToPageDto(Page<EnforcementPolicyEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<EnforcementPolicyDto> map =
                source.map(
                        new Function<EnforcementPolicyEntity, EnforcementPolicyDto>() {
                            @Override
                            public EnforcementPolicyDto apply(EnforcementPolicyEntity entity) {
                                EnforcementPolicyDto dto = modelMapper.map(entity, EnforcementPolicyDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public List<EnforcementPolicyDto> findByEnforcementId(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.findByEnforcementId()...");
            List<EnforcementPolicyEntity> enforcementPolicyEntities = enforcementPolicyRepository.findAllByEnforcementIdAndStatus(id, "A");

            Type listTpye = new com.google.common.reflect.TypeToken<List<EnforcementPolicyDto>>() {}.getType();
            List<EnforcementPolicyDto> map = modelMapper.map(enforcementPolicyEntities, listTpye);

            return map;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.findByEnforcementId()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.findByEnforcementId()...");
        }
    }

}
