package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.CurrencyDto;


import com.esign.service.configuration.entity.CurrencyEntity;


import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.CurrencyRepository;


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
public class CurrencyService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private CurrencyRepository currencyRepository;

    @Autowired
    public void setCurrencyService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            CurrencyRepository currencyRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.currencyRepository = currencyRepository;
    }

    public Page<CurrencyDto> findByCri(CurrencyDto dto) throws BusinessServiceException {
        try {
            log.info("Begin CurrencyService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<CurrencyEntity> all =
                    currencyRepository.findAll(getSpecification(dto), pageRequest);
            Page<CurrencyDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR CurrencyService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End CurrencyService.findByCri()...");
        }
    }

    public CurrencyDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<CurrencyEntity> byId = currencyRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), CurrencyDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Integer checkDuplicateCode(CurrencyDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
            List<CurrencyEntity> rs =
                    currencyRepository.findAllByCurrencyCodeAndStatus(dto.getCurrencyCode(),1);
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

    public CurrencyEntity save(CurrencyDto dto) throws BusinessServiceException {
        log.info("Begin CurrencyService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CurrencyEntity entity = modelMapper.map(dto, CurrencyEntity.class);
        log.info("End CurrencyService.save()...");
        return currencyRepository.save(entity);
    }

    @VisibleForTesting
    protected Specification<CurrencyEntity> getSpecification(CurrencyDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCurrencyId() != null) {
                predicates.add(cb.equal(root.get("currencyId"), filter.getCurrencyId()));
            }

            if (filter.getCurrencyCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("currencyDescription")), "%" + filter.getCurrencyCode().toLowerCase() + "%"));
            }

            if (filter.getCurrencyCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("currencyCode")), "%" + filter.getCurrencyCode().toLowerCase() + "%"));
            }

            if (filter.getCurrencyNameTh() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("currencyNameTh")), "%" + filter.getCurrencyNameTh().toLowerCase() + "%"));
            }

            if (filter.getCurrencyNameEn() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("currencyNameEn")), "%" + filter.getCurrencyNameEn().toLowerCase() + "%"));
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
                                cb.lower(root.get("currencyDescription")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("currencyCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("currencyNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("currencyNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<CurrencyDto> convertPageEntityToPageDto(Page<CurrencyEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<CurrencyDto> map =
                source.map(
                        new Function<CurrencyEntity, CurrencyDto>() {
                            @Override
                            public CurrencyDto apply(CurrencyEntity entity) {
                                CurrencyDto dto = modelMapper.map(entity, CurrencyDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}