package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.DeliveryTypeDto;


import com.esign.service.configuration.entity.DeliveryTypeEntity;


import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.DeliveryTypeRepository;


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
public class DeliveryTypeService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private DeliveryTypeRepository deliveryTypeRepository;

    @Autowired
    public void setDeliveryTypeService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            DeliveryTypeRepository deliveryTypeRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.deliveryTypeRepository = deliveryTypeRepository;
    }

    public Page<DeliveryTypeDto> findByCri(DeliveryTypeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin DeliveryTypeService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<DeliveryTypeEntity> all =
                    deliveryTypeRepository.findAll(getSpecification(dto), pageRequest);
            Page<DeliveryTypeDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR DeliveryTypeService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End DeliveryTypeService.findByCri()...");
        }
    }

    public DeliveryTypeDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<DeliveryTypeEntity> byId = deliveryTypeRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), DeliveryTypeDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Integer checkDuplicateCode(DeliveryTypeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
            List<DeliveryTypeEntity> rs =
                    deliveryTypeRepository.findAllByDeliveryTypeCodeAndStatus(dto.getDeliveryTypeCode(),1);
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

    public DeliveryTypeEntity save(DeliveryTypeDto dto) throws BusinessServiceException {
        log.info("Begin DeliveryTypeService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        DeliveryTypeEntity entity = modelMapper.map(dto, DeliveryTypeEntity.class);
        log.info("End DeliveryTypeService.save()...");
        return deliveryTypeRepository.save(entity);
    }

    @VisibleForTesting
    protected Specification<DeliveryTypeEntity> getSpecification(DeliveryTypeDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDeliveryTypeId() != null) {
                predicates.add(cb.equal(root.get("deliveryTypeId"), filter.getDeliveryTypeId()));
            }

            if (filter.getDeliveryTypeCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("deliveryTypeDescription")), "%" + filter.getDeliveryTypeCode().toLowerCase() + "%"));
            }

            if (filter.getDeliveryTypeCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("deliveryTypeCode")), "%" + filter.getDeliveryTypeCode().toLowerCase() + "%"));
            }

            if (filter.getDeliveryTypeNameTh() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("deliveryTypeNameTh")), "%" + filter.getDeliveryTypeNameTh().toLowerCase() + "%"));
            }

            if (filter.getDeliveryTypeNameEn() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("deliveryTypeNameEn")), "%" + filter.getDeliveryTypeNameEn().toLowerCase() + "%"));
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
                                cb.lower(root.get("deliveryTypeDescription")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("deliveryTypeCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("deliveryTypeNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("deliveryTypeNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<DeliveryTypeDto> convertPageEntityToPageDto(Page<DeliveryTypeEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<DeliveryTypeDto> map =
                source.map(
                        new Function<DeliveryTypeEntity, DeliveryTypeDto>() {
                            @Override
                            public DeliveryTypeDto apply(DeliveryTypeEntity entity) {
                                DeliveryTypeDto dto = modelMapper.map(entity, DeliveryTypeDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}