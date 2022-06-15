package com.esign.service.configuration.service;


import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdDocumentGroupDto;
import com.esign.service.configuration.entity.TblMdDocumentGroupEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdDocumentGroupRepository;
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
public class TblMdDocumentGroupService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdDocumentGroupRepository tblMdDocumentGroupRepository;

    @Autowired
    public void setTblMdDocumentGroupService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdDocumentGroupRepository tblMdDocumentGroupRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdDocumentGroupRepository = tblMdDocumentGroupRepository;
    }

    public TblMdDocumentGroupDto getById(long id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdDocumentGroupService.getById()...");
            Optional<TblMdDocumentGroupEntity> byId = tblMdDocumentGroupRepository.findById((long) id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdDocumentGroupDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TblMdDocumentGroupService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdDocumentGroupService.getById()...");
        }
    }

    public TblMdDocumentGroupEntity save(TblMdDocumentGroupDto dto) throws BusinessServiceException {
        log.info("Begin TblMdDocumentGroupService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdDocumentGroupEntity entity = modelMapper.map(dto, TblMdDocumentGroupEntity.class);
        log.info("End TblMdDocumentGroupService.save()...");
        return tblMdDocumentGroupRepository.save(entity);
    }

    public Integer checkDuplicateCode(TblMdDocumentGroupDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdDocumentGroupService.checkDuplicateAbbr()...");
            List<TblMdDocumentGroupEntity> rs =
                    tblMdDocumentGroupRepository.findAllByDocumentGroupCode(dto.getDocumentGroupCode());
            if (rs != null ) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TblMdDocumentGroupService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdDocumentGroupService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdDocumentGroupEntity> getSpecification(TblMdDocumentGroupDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDocumentGroupId() != null) {
                predicates.add(cb.equal(root.get("documentGroupId"), filter.getDocumentGroupId()));
            }


            if (filter.getDocumentGroupCode() != null) {
                predicates.add(cb.equal(root.get("documentGroupCode"), filter.getDocumentGroupCode()));
            }

            if (filter.getDocumentGroupName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("documentGroupName")), "%" + filter.getDocumentGroupName().toLowerCase() + "%"));
            }

            if (filter.getRecordStatus() != null) {
                predicates.add(cb.equal(root.get("recordStatus"), filter.getRecordStatus().trim()));
            }else {
                predicates.add(cb.equal(root.get("recordStatus"), "A"));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("documentGroupCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("documentGroupName")), "%" + filter.getFullSearch().toLowerCase() + "%"));

                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<TblMdDocumentGroupDto> findByCri(TblMdDocumentGroupDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdDocumentGroupService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdDocumentGroupEntity> all =
                    tblMdDocumentGroupRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdDocumentGroupDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdDocumentGroupService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdDocumentGroupService.findByCri()...");
        }
    }

    protected Page<TblMdDocumentGroupDto> convertPageEntityToPageDto(Page<TblMdDocumentGroupEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdDocumentGroupDto> map =
                source.map(
                        new Function<TblMdDocumentGroupEntity, TblMdDocumentGroupDto>() {
                            @Override
                            public TblMdDocumentGroupDto apply(TblMdDocumentGroupEntity entity) {
                                TblMdDocumentGroupDto dto = modelMapper.map(entity, TblMdDocumentGroupDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}
