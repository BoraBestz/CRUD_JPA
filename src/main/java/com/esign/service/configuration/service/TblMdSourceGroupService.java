package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdSourceGroupDto;
import com.esign.service.configuration.entity.TblMdSourceGroupEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdSourceGroupRepository;
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
public class TblMdSourceGroupService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdSourceGroupRepository tblMdSourceGroupRepository;

    @Autowired
    public void setTblMdSourceGroupService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdSourceGroupRepository tblMdSourceGroupRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdSourceGroupRepository = tblMdSourceGroupRepository;
    }

    public Page<TblMdSourceGroupDto> findByCri(TblMdSourceGroupDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdSourceGroupService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdSourceGroupEntity> all =
                    tblMdSourceGroupRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdSourceGroupDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdSourceGroupService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdSourceGroupService.findByCri()...");
        }
    }

    public TblMdSourceGroupDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<TblMdSourceGroupEntity> byId = tblMdSourceGroupRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdSourceGroupDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Integer checkDuplicateCode(TblMdSourceGroupDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
            List<TblMdSourceGroupEntity> rs =
                    tblMdSourceGroupRepository.findAllBySourceGroupCodeAndStatus(dto.getSourceGroupCode(),"A");
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

    public TblMdSourceGroupEntity save(TblMdSourceGroupDto dto) throws BusinessServiceException {
        log.info("Begin TblMdSourceGroupService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdSourceGroupEntity entity = modelMapper.map(dto, TblMdSourceGroupEntity.class);
        log.info("End TblMdSourceGroupService.save()...");
        return tblMdSourceGroupRepository.save(entity);
    }

    @VisibleForTesting
    protected Specification<TblMdSourceGroupEntity> getSpecification(TblMdSourceGroupDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getSourceGroupId() != null) {
                predicates.add(cb.equal(root.get("sourceGroupId"), filter.getSourceGroupId()));
            }

            if (filter.getSourceGroupCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("sourceGroupCode")), "%" + filter.getSourceGroupCode().toLowerCase() + "%"));
            }
            if (filter.getSourceGroupName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("sourceGroupName")), "%" + filter.getSourceGroupName().toLowerCase() + "%"));
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
                                cb.lower(root.get("sourceGroupCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("sourceGroupName")), "%" + filter.getFullSearch().toLowerCase() + "%"));

                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<TblMdSourceGroupDto> convertPageEntityToPageDto(Page<TblMdSourceGroupEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdSourceGroupDto> map =
                source.map(
                        new Function<TblMdSourceGroupEntity, TblMdSourceGroupDto>() {
                            @Override
                            public TblMdSourceGroupDto apply(TblMdSourceGroupEntity entity) {
                                TblMdSourceGroupDto dto = modelMapper.map(entity, TblMdSourceGroupDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}
