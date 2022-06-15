package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.DocumentTypeDto;


import com.esign.service.configuration.entity.DocumentTypeEntity;


import com.esign.service.configuration.exception.BusinessServiceException;


import com.esign.service.configuration.repository.DocumentTypeRepository2;
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
public class DocumentTypeService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private DocumentTypeRepository2 documentTypeRepository2;

    @Autowired
    public void setDocumentTypeService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            DocumentTypeRepository2 documentTypeRepository2) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.documentTypeRepository2 = documentTypeRepository2;
    }

    public Page<DocumentTypeDto> findByCri(DocumentTypeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin DocumentTypeService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<DocumentTypeEntity> all =
                    documentTypeRepository2.findAll(getSpecification(dto), pageRequest);
            Page<DocumentTypeDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR DocumentTypeService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End DocumentTypeService.findByCri()...");
        }
    }

    public DocumentTypeDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<DocumentTypeEntity> byId = documentTypeRepository2.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), DocumentTypeDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Integer checkDuplicateCode(DocumentTypeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
            List<DocumentTypeEntity> rs =
                    documentTypeRepository2.findAllByStatus(1);
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

    public DocumentTypeEntity save(DocumentTypeDto dto) throws BusinessServiceException {
        log.info("Begin DocumentTypeService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        DocumentTypeEntity entity = modelMapper.map(dto, DocumentTypeEntity.class);
        log.info("End DocumentTypeService.save()...");
        return documentTypeRepository2.save(entity);
    }

    @VisibleForTesting
    protected Specification<DocumentTypeEntity> getSpecification(DocumentTypeDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDocumentTypeId() != null) {
                predicates.add(cb.equal(root.get("documentTypeId"), filter.getDocumentTypeId()));
            }

            if (filter.getDocumentTypeCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("documentTypeDescription")), "%" + filter.getDocumentTypeCode().toLowerCase() + "%"));
            }

            if (filter.getDocumentTypeCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("documentTypeCode")), "%" + filter.getDocumentTypeCode().toLowerCase() + "%"));
            }

            if (filter.getDocumentTypeNameTh() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("documentTypeNameTh")), "%" + filter.getDocumentTypeNameTh().toLowerCase() + "%"));
            }

            if (filter.getDocumentTypeNameEn() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("documentTypeNameEn")), "%" + filter.getDocumentTypeNameEn().toLowerCase() + "%"));
            }

            if (filter.getRecordStatus() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("recordStatus")), "%" + filter.getRecordStatus().toLowerCase() + "%"));
            }

            if (filter.getIsEtax() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("isEtax")), "%" + filter.getIsEtax().toLowerCase() + "%"));
            }

            if (filter.getDocumentTypeCodeRd() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("documentTypeCodeRd")), "%" + filter.getDocumentTypeCodeRd().toLowerCase() + "%"));
            }

            if (filter.getIsSendRd() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("isSendRd")), "%" + filter.getIsSendRd().toLowerCase() + "%"));
            }

            if (filter.getDocumentTypeId() != null) {
                predicates.add(cb.equal(root.get("documentGroupId"), filter.getDocumentTypeId()));
            }
            if (filter.getDocumentTypeId() != null) {
                predicates.add(cb.equal(root.get("genXml"), filter.getDocumentTypeId()));
            }
            if (filter.getDocumentTypeId() != null) {
                predicates.add(cb.equal(root.get("genPdf"), filter.getDocumentTypeId()));
            }
            if (filter.getDocumentTypeId() != null) {
                predicates.add(cb.equal(root.get("signDoc"), filter.getDocumentTypeId()));
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
                                cb.lower(root.get("documentTypeDescription")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("documentTypeCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("documentTypeNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("documentTypeNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("recordStatus")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("isEtax")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("documentTypeCodeRd")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("isSendRd")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<DocumentTypeDto> convertPageEntityToPageDto(Page<DocumentTypeEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<DocumentTypeDto> map =
                source.map(
                        new Function<DocumentTypeEntity, DocumentTypeDto>() {
                            @Override
                            public DocumentTypeDto apply(DocumentTypeEntity entity) {
                                DocumentTypeDto dto = modelMapper.map(entity, DocumentTypeDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}