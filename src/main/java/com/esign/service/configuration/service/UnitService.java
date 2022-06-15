package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.ComBranchDto;
import com.esign.service.configuration.dto.UnitDto;


import com.esign.service.configuration.entity.ComBranchEntity;
import com.esign.service.configuration.entity.UnitEntity;


import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.UnitRepository;


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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class UnitService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private UnitRepository unitRepository;

    @Autowired
    public void setUnitService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            UnitRepository unitRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.unitRepository = unitRepository;
    }

    public Page<UnitDto> findByCri(UnitDto dto) throws BusinessServiceException {
        try {
            log.info("Begin UnitService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<UnitEntity> all =
                    unitRepository.findAll(getSpecification(dto), pageRequest);
            Page<UnitDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR UnitService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End UnitService.findByCri()...");
        }
    }

    public UnitDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<UnitEntity> byId = unitRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), UnitDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Integer checkDuplicateCode(UnitDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
            List<UnitEntity> rs =
                    unitRepository.findAllByUnitCodeAndStatus(dto.getUnitCode(),1);
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

    public UnitEntity save(UnitDto dto) throws BusinessServiceException {
        log.info("Begin UnitService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UnitEntity entity = modelMapper.map(dto, UnitEntity.class);
        log.info("End UnitService.save()...");
        return unitRepository.save(entity);
    }

    @VisibleForTesting
    protected Specification<UnitEntity> getSpecification(UnitDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getUnitId() != null) {
                predicates.add(cb.equal(root.get("unitId"), filter.getUnitId()));
            }

            if (filter.getUnitCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("unitDescription")), "%" + filter.getUnitCode().toLowerCase() + "%"));
            }

            if (filter.getUnitCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("unitCode")), "%" + filter.getUnitCode().toLowerCase() + "%"));
            }

            if (filter.getUnitNameTh() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("unitNameTh")), "%" + filter.getUnitNameTh().toLowerCase() + "%"));
            }

            if (filter.getUnitNameEn() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("unitNameEn")), "%" + filter.getUnitNameEn().toLowerCase() + "%"));
            }

            if (filter.getConvertionFactor() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("convertionFactor")), "%" + filter.getConvertionFactor().toLowerCase() + "%"));
            }

            if (filter.getSymbol() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("symbol")), "%" + filter.getSymbol().toLowerCase() + "%"));
            }

            if (filter.getCreatedUser() != null) {
                predicates.add(cb.equal(root.get("createdUser"), filter.getCreatedUser()));
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
                                cb.lower(root.get("unitDescription")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("unitCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("unitNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("unitNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("convertionFactor")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("symbol")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("createdUser")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<UnitDto> convertPageEntityToPageDto(Page<UnitEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<UnitDto> map =
                source.map(
                        new Function<UnitEntity, UnitDto>() {
                            @Override
                            public UnitDto apply(UnitEntity entity) {
                                UnitDto dto = modelMapper.map(entity, UnitDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public List<UnitDto> findByUnitCode(String code) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.findByUnitCode()...");
            List<UnitEntity> unitEntities = unitRepository.findAllByUnitCodeAndStatus(code, 1);

            Type listTpye = new com.google.common.reflect.TypeToken<List<UnitDto>>() {}.getType();
            List<UnitDto> map = modelMapper.map(unitEntities, listTpye);

            return map;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.findByUnitCode()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.findByUnitCode()...");
        }
    }
}