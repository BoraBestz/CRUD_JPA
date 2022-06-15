package com.esign.service.configuration.service;


import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdDocumentGroupInfDto;
import com.esign.service.configuration.dto.TblMdProductGroupDto;
import com.esign.service.configuration.entity.TblMdDocumentGroupInfEntity;
import com.esign.service.configuration.entity.TblMdProductGroupEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdDocumentGroupInfRepository;
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
public class TblMdDocumentGroupInfService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdDocumentGroupInfRepository tblMdDocumentGroupInfRepository;

    @Autowired
    public void setTblMdDocumentGroupInfService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdDocumentGroupInfRepository tblMdDocumentGroupInfRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdDocumentGroupInfRepository = tblMdDocumentGroupInfRepository;
    }

    public TblMdDocumentGroupInfDto getById(long id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdDocumentGroupInfService.getById()...");
            Optional<TblMdDocumentGroupInfEntity> byId = tblMdDocumentGroupInfRepository.findById((long) id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdDocumentGroupInfDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TblMdDocumentGroupInfService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdDocumentGroupInfService.getById()...");
        }
    }

    public TblMdDocumentGroupInfEntity save(TblMdDocumentGroupInfDto dto) throws BusinessServiceException {
        log.info("Begin TblMdDocumentGroupInfService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdDocumentGroupInfEntity entity = modelMapper.map(dto, TblMdDocumentGroupInfEntity.class);
        log.info("End TblMdDocumentGroupInfService.save()...");
        return tblMdDocumentGroupInfRepository.save(entity);
    }

    public Integer checkDuplicateCode(TblMdDocumentGroupInfDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdDocumentGroupInfService.checkDuplicateAbbr()...");
            List<TblMdDocumentGroupInfEntity> rs =
                    tblMdDocumentGroupInfRepository.findAllByDocumentGroupInfCode(dto.getDocumentGroupInfCode());
            if (rs != null ) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TblMdDocumentGroupInfService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdDocumentGroupInfService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdDocumentGroupInfEntity> getSpecification(TblMdDocumentGroupInfDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDocumentGroupInf() != null) {
                predicates.add(cb.equal(root.get("documentGroupInf"), filter.getDocumentGroupInf()));
            }

            if (filter.getDocumentGroupId() != null) {
                predicates.add(cb.equal(root.get("documentGroupId"), filter.getDocumentGroupId()));
            }

            if (filter.getDocumentGroupInfCode() != null) {
                predicates.add(cb.equal(root.get("documentGroupInfCode"), filter.getDocumentGroupInfCode()));
            }

            if (filter.getDocumentGroupInfName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("documentGroupInfName")), "%" + filter.getDocumentGroupInfName().toLowerCase() + "%"));
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
                                cb.lower(root.get("documentGroupInfName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("documentGroupInfCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));

                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<TblMdDocumentGroupInfDto> findByCri(TblMdDocumentGroupInfDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdDocumentGroupInfService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdDocumentGroupInfEntity> all =
                    tblMdDocumentGroupInfRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdDocumentGroupInfDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdDocumentGroupInfService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdDocumentGroupInfService.findByCri()...");
        }
    }

    protected Page<TblMdDocumentGroupInfDto> convertPageEntityToPageDto(Page<TblMdDocumentGroupInfEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdDocumentGroupInfDto> map =
                source.map(
                        new Function<TblMdDocumentGroupInfEntity, TblMdDocumentGroupInfDto>() {
                            @Override
                            public TblMdDocumentGroupInfDto apply(TblMdDocumentGroupInfEntity entity) {
                                TblMdDocumentGroupInfDto dto = modelMapper.map(entity, TblMdDocumentGroupInfDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public List<TblMdDocumentGroupInfDto> findByTblMdDocumentGroupId (int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdProductGroupService.getById()...");
            List<TblMdDocumentGroupInfEntity> tblMdDocumentGroupInfEntities =  tblMdDocumentGroupInfRepository.findAllByDocumentGroupIdAndRecordStatus(id,"A");

            Type listType = new TypeToken<List<TblMdDocumentGroupInfDto>>() {}.getType();
            List<TblMdDocumentGroupInfDto> map = modelMapper.map(tblMdDocumentGroupInfEntities, listType);

            return map;
        } catch (Exception ex) {
            log.error("ERROR TblMdProductGroupService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdProductGroupService.getById()...");
        }
    }
}
