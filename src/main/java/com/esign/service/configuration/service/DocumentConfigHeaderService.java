package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.DocumentConfigHeaderDto;
import com.esign.service.configuration.entity.DocumentConfigHeaderEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.DocumentConfigHeaderRepository;
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
public class DocumentConfigHeaderService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private DocumentConfigHeaderRepository documentConfigHeaderRepository;

    @Autowired
    public void setDocumentConfigHeaderService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            DocumentConfigHeaderRepository documentConfigHeaderRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.documentConfigHeaderRepository = documentConfigHeaderRepository;
    }

    public Page<DocumentConfigHeaderDto> findByCri(DocumentConfigHeaderDto dto) throws BusinessServiceException {
        try {
            log.info("Begin DocumentConfigHeaderService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<DocumentConfigHeaderEntity> all =
                    documentConfigHeaderRepository.findAll(getSpecification(dto), pageRequest);
            Page<DocumentConfigHeaderDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR DocumentConfigHeaderService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End DocumentConfigHeaderService.findByCri()...");
        }
    }

    public DocumentConfigHeaderDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<DocumentConfigHeaderEntity> byId = documentConfigHeaderRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), DocumentConfigHeaderDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Integer checkDuplicateCode(DocumentConfigHeaderDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
            List<DocumentConfigHeaderEntity> rs =
                    documentConfigHeaderRepository.findAllByDocumentConfigHeaderCodeAndStatus(dto.getDocumentConfigHeaderCode(),"A");
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

    public DocumentConfigHeaderEntity save(DocumentConfigHeaderDto dto) throws BusinessServiceException {
        log.info("Begin DocumentConfigHeaderService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        DocumentConfigHeaderEntity entity = modelMapper.map(dto, DocumentConfigHeaderEntity.class);
        log.info("End DocumentConfigHeaderService.save()...");
        return documentConfigHeaderRepository.save(entity);
    }

    @VisibleForTesting
    protected Specification<DocumentConfigHeaderEntity> getSpecification(DocumentConfigHeaderDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDocumentConfigHeaderId() != null) {
                predicates.add(cb.equal(root.get("documentConfigHeaderId"), filter.getDocumentConfigHeaderId()));
            }
            if (filter.getDocumentConfigHeaderCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("documentConfigHeaderCode")), "%" + filter.getDocumentConfigHeaderCode().toLowerCase() + "%"));
            }
            if (filter.getDocumentConfigHeaderName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("messageItemName")), "%" + filter.getDocumentConfigHeaderName().toLowerCase() + "%"));
            }
            if (filter.getCompanyCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("companyCode")), "%" + filter.getCompanyCode().toLowerCase() + "%"));
            }
            if (filter.getIsValidate() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("isValidate")), "%" + filter.getIsValidate().toLowerCase() + "%"));
            }
            if (filter.getIsSel() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("isSel")), "%" + filter.getIsSel().toLowerCase() + "%"));
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
                                cb.lower(root.get("documentConfigHeaderCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("messageItemName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("companyCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("isValidate")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("isSel")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<DocumentConfigHeaderDto> convertPageEntityToPageDto(Page<DocumentConfigHeaderEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<DocumentConfigHeaderDto> map =
                source.map(
                        new Function<DocumentConfigHeaderEntity, DocumentConfigHeaderDto>() {
                            @Override
                            public DocumentConfigHeaderDto apply(DocumentConfigHeaderEntity entity) {
                                DocumentConfigHeaderDto dto = modelMapper.map(entity, DocumentConfigHeaderDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}
