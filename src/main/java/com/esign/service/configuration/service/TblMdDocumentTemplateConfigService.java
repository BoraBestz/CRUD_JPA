package com.esign.service.configuration.service;


import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdDocumentTemplateConfigDto;
import com.esign.service.configuration.entity.TblMdDocumentTemplateConfigEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdDocumentTemplateConfigRepository;
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
public class TblMdDocumentTemplateConfigService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdDocumentTemplateConfigRepository tblMdDocumentTemplateConfigRepository;

    @Autowired
    public void setTblMdDocumentTemplateConfigService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdDocumentTemplateConfigRepository tblMdDocumentTemplateConfigRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdDocumentTemplateConfigRepository = tblMdDocumentTemplateConfigRepository;
    }

    public TblMdDocumentTemplateConfigDto getById(long id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdDocumentTemplateConfigService.getById()...");
            Optional<TblMdDocumentTemplateConfigEntity> byId = tblMdDocumentTemplateConfigRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdDocumentTemplateConfigDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TblMdDocumentTemplateConfigService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdDocumentTemplateConfigService.getById()...");
        }
    }

    public TblMdDocumentTemplateConfigEntity save(TblMdDocumentTemplateConfigDto dto) throws BusinessServiceException {
        log.info("Begin TblMdDocumentTemplateConfigService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdDocumentTemplateConfigEntity entity = modelMapper.map(dto, TblMdDocumentTemplateConfigEntity.class);
        log.info("End TblMdDocumentTemplateConfigService.save()...");
        return tblMdDocumentTemplateConfigRepository.save(entity);
    }

    public Integer checkDuplicateCode(TblMdDocumentTemplateConfigDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdDocumentTemplateConfigService.checkDuplicateAbbr()...");
            List<TblMdDocumentTemplateConfigEntity> rs =
                    tblMdDocumentTemplateConfigRepository.findAllByDocumentTemplateCodeAndStatus(dto.getDocumentTemplateCode(), 1);
            if (rs != null ) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TblMdDocumentTemplateConfigService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdDocumentTemplateConfigService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdDocumentTemplateConfigEntity> getSpecification(TblMdDocumentTemplateConfigDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDocumentTemplateCode() != null) {
                predicates.add(cb.equal(root.get("documentTemplateCode"), filter.getDocumentTemplateCode()));
            }

            if (filter.getDocumentTemplateName() != null) {
                predicates.add(cb.equal(root.get("documentTemplateName"), filter.getDocumentTemplateName()));
            }

            if (filter.getCompanyId() > 0) {
                predicates.add(cb.equal(root.get("companyId"), filter.getCompanyId()));
            }

            if (filter.getDocumentTypeId() > 0) {
                predicates.add(cb.equal(root.get("documentTypeId"), filter.getDocumentTypeId()));
            }

            if (filter.getProductGroupId() != null && filter.getProductGroupId() > 0) {
                predicates.add(cb.equal(root.get("productGroupId"), filter.getProductGroupId()));
            }

            if (filter.getImportTemplateCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("importTemplateCode")), "%" + filter.getImportTemplateCode().toLowerCase() + "%"));
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
                                cb.lower(root.get("documentTemplateCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("documentTemplateName")), "%" + filter.getFullSearch().toLowerCase() + "%"));

                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<TblMdDocumentTemplateConfigDto> findByCri(TblMdDocumentTemplateConfigDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdDocumentTemplateConfigService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdDocumentTemplateConfigEntity> all =
                    tblMdDocumentTemplateConfigRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdDocumentTemplateConfigDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdDocumentTemplateConfigService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdDocumentTemplateConfigService.findByCri()...");
        }
    }

    protected Page<TblMdDocumentTemplateConfigDto> convertPageEntityToPageDto(Page<TblMdDocumentTemplateConfigEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdDocumentTemplateConfigDto> map =
                source.map(
                        new Function<TblMdDocumentTemplateConfigEntity, TblMdDocumentTemplateConfigDto>() {
                            @Override
                            public TblMdDocumentTemplateConfigDto apply(TblMdDocumentTemplateConfigEntity entity) {
                                TblMdDocumentTemplateConfigDto dto = modelMapper.map(entity, TblMdDocumentTemplateConfigDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}
