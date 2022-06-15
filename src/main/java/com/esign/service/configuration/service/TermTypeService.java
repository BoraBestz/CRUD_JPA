package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TermTypeDto;


import com.esign.service.configuration.entity.TermTypeEntity;


import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TermTypeRepository;


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

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class TermTypeService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TermTypeRepository termTypeRepository;

    @Autowired
    public void setTermTypeService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TermTypeRepository termTypeRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.termTypeRepository = termTypeRepository;
    }

    public Page<TermTypeDto> findByCri(TermTypeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TermTypeService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TermTypeEntity> all =
                    termTypeRepository.findAll(getSpecification(dto), pageRequest);
            Page<TermTypeDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TermTypeService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TermTypeService.findByCri()...");
        }
    }

    public TermTypeDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<TermTypeEntity> byId = termTypeRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TermTypeDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Integer checkDuplicateCode(TermTypeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
            List<TermTypeEntity> rs =
                    termTypeRepository.findAllByTermTypeCodeAndStatus(dto.getTermTypeCode(),1);
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.checkDuplicateAbbr()...");
        }
    }

    public TermTypeEntity save(TermTypeDto dto) throws BusinessServiceException {
        log.info("Begin TermTypeService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TermTypeEntity entity = modelMapper.map(dto, TermTypeEntity.class);
        log.info("End TermTypeService.save()...");
        return termTypeRepository.save(entity);
    }

    @VisibleForTesting
    protected Specification<TermTypeEntity> getSpecification(TermTypeDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getTermTypeId() != null) {
                predicates.add(cb.equal(root.get("termTypeId"), filter.getTermTypeId()));
            }

            if (filter.getTermTypeCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("termTypeDescription")), "%" + filter.getTermTypeCode().toLowerCase() + "%"));
            }

            if (filter.getTermTypeCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("termTypeCode")), "%" + filter.getTermTypeCode().toLowerCase() + "%"));
            }

            if (filter.getTermTypeNameTh() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("termTypeNameTh")), "%" + filter.getTermTypeNameTh().toLowerCase() + "%"));
            }

            if (filter.getTermTypeNameEn() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("termTypeNameEn")), "%" + filter.getTermTypeNameEn().toLowerCase() + "%"));
            }

            if (filter.getStatus() != null && filter.getStatus().intValue() > 0) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }else {
                predicates.add(cb.equal(root.get("status"), 1));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("termTypeDescription")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("termTypeCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("termTypeNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("termTypeNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<TermTypeDto> convertPageEntityToPageDto(Page<TermTypeEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TermTypeDto> map =
                source.map(
                        new Function<TermTypeEntity, TermTypeDto>() {
                            @Override
                            public TermTypeDto apply(TermTypeEntity entity) {
                                TermTypeDto dto = modelMapper.map(entity, TermTypeDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}