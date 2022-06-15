package com.esign.service.configuration.service;


import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdBuyerTaxTypeDto;
import com.esign.service.configuration.entity.TblMdBuyerTaxTypeEnitity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdBuyerTaxTypeRepository;
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
import java.util.*;
import java.util.function.Function;


@Service
@Slf4j

public class TblMdBuyerTaxTypeService  {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdBuyerTaxTypeRepository tblMdBuyerTaxTypeRepository;

    @Autowired
    public void setTblMdBuyerTaxTypeService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdBuyerTaxTypeRepository tblMdBuyerTaxTypeRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdBuyerTaxTypeRepository = tblMdBuyerTaxTypeRepository;
    }

    public TblMdBuyerTaxTypeDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdBuyerTaxTypeService.getById()...");
            Optional<TblMdBuyerTaxTypeEnitity> byId = tblMdBuyerTaxTypeRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdBuyerTaxTypeDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TblMdBuyerTaxTypeService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdBuyerTaxTypeService.getById()...");
        }
    }

    public TblMdBuyerTaxTypeEnitity save(TblMdBuyerTaxTypeDto dto) throws BusinessServiceException {
        log.info("Begin TblMdBuyerTaxTypeService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdBuyerTaxTypeEnitity entity = modelMapper.map(dto, TblMdBuyerTaxTypeEnitity.class);
        log.info("End TblMdBuyerTaxTypeService.save()...");
        return tblMdBuyerTaxTypeRepository.save(entity);
    }

    public Integer checkDuplicateCode(TblMdBuyerTaxTypeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdBuyerTaxTypeService.checkDuplicateAbbr()...");
            List<TblMdBuyerTaxTypeEnitity> rs =
                    tblMdBuyerTaxTypeRepository.findAllByBuyerTaxTypeCodeAndRecordStatus(dto.getBuyerTaxTypeCode(), dto.getRecordStatus());
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TblMdBuyerTaxTypeService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdBuyerTaxTypeService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdBuyerTaxTypeEnitity> getSpecification(TblMdBuyerTaxTypeDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getBuyerTaxTypeId() != null) {
                predicates.add(cb.equal(root.get("buyerTaxTypeId"), filter.getBuyerTaxTypeId()));
            }

            if (filter.getBuyerTaxTypeCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("buyerTaxTypeCode")), "%" + filter.getBuyerTaxTypeCode().toLowerCase() + "%"));
            }

            if (filter.getBuyerTaxTypeName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("buyerTaxTypeName")), "%" + filter.getBuyerTaxTypeName().toLowerCase() + "%"));
            }

            if (filter.getRecordStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getRecordStatus().trim()));
            }else {
                predicates.add(cb.equal(root.get("status"), "A"));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("buyerTaxTypeCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("buyerTaxTypeName")), "%" + filter.getFullSearch().toLowerCase() + "%"));

                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<TblMdBuyerTaxTypeDto> findByCri(TblMdBuyerTaxTypeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdBuyerTaxTypeService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdBuyerTaxTypeEnitity> all =
                    tblMdBuyerTaxTypeRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdBuyerTaxTypeDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdBuyerTaxTypeService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdBuyerTaxTypeService.findByCri()...");
        }
    }

    protected Page<TblMdBuyerTaxTypeDto> convertPageEntityToPageDto(Page<TblMdBuyerTaxTypeEnitity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdBuyerTaxTypeDto> map =
                source.map(
                        new Function<TblMdBuyerTaxTypeEnitity, TblMdBuyerTaxTypeDto>() {
                            @Override
                            public TblMdBuyerTaxTypeDto apply(TblMdBuyerTaxTypeEnitity entity) {
                                TblMdBuyerTaxTypeDto dto = modelMapper.map(entity, TblMdBuyerTaxTypeDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}
