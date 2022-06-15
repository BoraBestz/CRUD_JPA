package com.esign.service.configuration.service.pass;

import com.esign.service.configuration.dto.pass.EnforcementDto;
import com.esign.service.configuration.entity.pass.EnforcementEntity;
import com.esign.service.configuration.entity.pass.PassEnforcementEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class EnforcementService {

    private EnforcementRepository enforcementRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setConfigService(EnforcementRepository enforcementRepository,
                                 ModelMapper modelMapper){
        this.enforcementRepository = enforcementRepository;
        this.modelMapper = modelMapper;
    }

    public EnforcementEntity save(EnforcementDto dto) throws BusinessServiceException {
        log.info("String Saving Data....");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info("Map dto with Config Entity.class.....");
        EnforcementEntity entity = modelMapper.map(dto, EnforcementEntity.class);
        log.info("End Saving Data....");
        return enforcementRepository.save(entity);
    }

    public Integer checkDuplicate(EnforcementDto dto) throws BusinessServiceException {
        log.info("String Check Duplicate Data....");
        try {
            log.info("Get Data from DB....");
            List<EnforcementEntity> rs = enforcementRepository.findAllByEnforceNameAndStatus(dto.getEnforceName(), dto.getStatus());

            if (rs != null && rs.size() > 0){
                return 1;
            }
            return 0;
        }catch (Exception ex){
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    public EnforcementDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<EnforcementEntity> byId = enforcementRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), EnforcementDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Page<EnforcementDto> findByCri(EnforcementDto dto) throws BusinessServiceException {
        try {
            log.info("Begin EnforcementService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<EnforcementEntity> all =
                    enforcementRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<EnforcementDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR EnforcementService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End EnforcementService.findByCri()...");
        }
    }
    @VisibleForTesting
    protected Specification<EnforcementEntity> getSpecification(EnforcementDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getEnforceId() != null){
                predicates.add(cb.equal(root.get("enforceId"), filter.getEnforceId()));
            }

            if (filter.getEnforceName() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("enforceName")), "%" + filter.getEnforceName().toLowerCase() + "%"));
            }

            if (filter.getIsDefault() != null){
                predicates.add(cb.equal(root.get("isDefault"), filter.getIsDefault()));
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
                                cb.lower(root.get("enforceName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("isDefault")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<EnforcementDto> convertPageEntityToPageDto(Page<EnforcementEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<EnforcementDto> map =
                source.map(
                        new Function<EnforcementEntity, EnforcementDto>() {
                            @Override
                            public EnforcementDto apply(EnforcementEntity entity) {
                                EnforcementDto dto = modelMapper.map(entity, EnforcementDto.class);
                                return dto;
                            }
                        });
        return map;
    }

}