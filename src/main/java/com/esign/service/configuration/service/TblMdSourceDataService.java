package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.DocumentConfigDto;
import com.esign.service.configuration.dto.TblMdSourceDataDto;
import com.esign.service.configuration.entity.DocumentConfigEntity;
import com.esign.service.configuration.entity.TblMdSourceDataEntity;
import com.esign.service.configuration.entity.TblMdSourceGroupEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdSourceDataRepository;
import com.esign.service.configuration.utils.PageUtils;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.reflect.TypeToken;
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
public class TblMdSourceDataService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdSourceDataRepository tblMdSourceDataRepository;

    @Autowired
    public void setTblMdSourceDataService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdSourceDataRepository tblMdSourceDataRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdSourceDataRepository = tblMdSourceDataRepository;
    }

    public Page<TblMdSourceDataDto> findByCri(TblMdSourceDataDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdSourceDataService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdSourceDataEntity> all =
                    tblMdSourceDataRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdSourceDataDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdSourceDataService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdSourceDataService.findByCri()...");
        }
    }

    public TblMdSourceDataDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<TblMdSourceDataEntity> byId = tblMdSourceDataRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdSourceDataDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Integer checkDuplicateCode(TblMdSourceDataDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
            List<TblMdSourceDataEntity> rs =
                    tblMdSourceDataRepository.findAllBySourceDataCodeAndStatus(dto.getSourceDataCode(),"A");
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

    public TblMdSourceDataEntity save(TblMdSourceDataDto dto) throws BusinessServiceException {
        log.info("Begin TblMdSourceDataService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdSourceDataEntity entity = modelMapper.map(dto, TblMdSourceDataEntity.class);
        log.info("End TblMdSourceDataService.save()...");
        return tblMdSourceDataRepository.save(entity);
    }

    @VisibleForTesting
    protected Specification<TblMdSourceDataEntity> getSpecification(TblMdSourceDataDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getSourceDataId() != null) {
                predicates.add(cb.equal(root.get("sourceDataId"), filter.getSourceDataId()));
            }

            if (filter.getSourceDataCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("sourceDataCode")), "%" + filter.getSourceDataCode().toLowerCase() + "%"));
            }
            if (filter.getSourceDataName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("sourceDataName")), "%" + filter.getSourceDataName().toLowerCase() + "%"));
            }
            if (filter.getCompanyId() != null) {
                predicates.add(cb.equal(root.get("companyId"), filter.getCompanyId()));
            }
            if (filter.getSourceGroupId() != null) {
                predicates.add(cb.equal(root.get("sourceGroupId"), filter.getSourceGroupId()));
            }
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus().trim()));
            }else {
                predicates.add(cb.equal(root.get("status"), "A"));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("sourceDataCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("sourceDataName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("companyId")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("sourceGroupId")), "%" + filter.getFullSearch().toLowerCase() + "%"));

                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<TblMdSourceDataDto> convertPageEntityToPageDto(Page<TblMdSourceDataEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdSourceDataDto> map =
                source.map(
                        new Function<TblMdSourceDataEntity, TblMdSourceDataDto>() {
                            @Override
                            public TblMdSourceDataDto apply(TblMdSourceDataEntity entity) {
                                TblMdSourceDataDto dto = modelMapper.map(entity, TblMdSourceDataDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public List<TblMdSourceDataDto> findByTblMdSourceGroupId(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.findByTblMdSourceGroupId()...");
            List<TblMdSourceDataEntity> tblMdSourceDataEntities = tblMdSourceDataRepository.findAllBySourceGroupIdAndStatus(id, "A");

            Type listTpye = new TypeToken<List<TblMdSourceDataDto>>() {}.getType();
            List<TblMdSourceDataDto> map = modelMapper.map(tblMdSourceDataEntities, listTpye);

            return map;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.findByTblMdSourceGroupId()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.findByTblMdSourceGroupId()...");
        }
    }
}
