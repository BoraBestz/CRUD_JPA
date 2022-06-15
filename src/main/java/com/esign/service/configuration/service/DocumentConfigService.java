package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.DocumentConfigDto;
import com.esign.service.configuration.entity.DocumentConfigEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.DocumentConfigRepository;
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
public class DocumentConfigService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private DocumentConfigRepository documentConfigRepository;

    @Autowired
    public void setDocumentConfigService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            DocumentConfigRepository documentConfigRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.documentConfigRepository = documentConfigRepository;
    }

    public Page<DocumentConfigDto> findByCri(DocumentConfigDto dto) throws BusinessServiceException {
        try {
            log.info("Begin DocumentConfigService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<DocumentConfigEntity> all =
                    documentConfigRepository.findAll(getSpecification(dto), pageRequest);
            Page<DocumentConfigDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR DocumentConfigService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End DocumentConfigService.findByCri()...");
        }
    }

    public DocumentConfigDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<DocumentConfigEntity> byId = documentConfigRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), DocumentConfigDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Integer checkDuplicateCode(DocumentConfigDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
            List<DocumentConfigEntity> rs =
                    documentConfigRepository.findAllByNameAndStatus(dto.getName(),"A");
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

    public DocumentConfigEntity save(DocumentConfigDto dto) throws BusinessServiceException {
        log.info("Begin DocumentConfigService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        DocumentConfigEntity entity = modelMapper.map(dto, DocumentConfigEntity.class);
        log.info("End DocumentConfigService.save()...");
        return documentConfigRepository.save(entity);
    }

    @VisibleForTesting
    protected Specification<DocumentConfigEntity> getSpecification(DocumentConfigDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDocumentConfigId() != null) {
                predicates.add(cb.equal(root.get("documentConfigId"), filter.getDocumentConfigId()));
            }

            if (filter.getName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }
            if (filter.getMessageItemName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("messageItemName")), "%" + filter.getMessageItemName().toLowerCase() + "%"));
            }
            if (filter.getPackageName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("packageName")), "%" + filter.getPackageName().toLowerCase() + "%"));
            }
            if (filter.getType() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("type")), "%" + filter.getType().toLowerCase() + "%"));
            }
            if (filter.getFilter() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("filter")), "%" + filter.getFilter().toLowerCase() + "%"));
            }
            if (filter.getAttribute() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("attribute")), "%" + filter.getAttribute().toLowerCase() + "%"));
            }
            if (filter.getDocumentConfigHeaderId() != null) {
                predicates.add(cb.equal(root.get("documentConfigHeaderId"), filter.getDocumentConfigHeaderId()));
            }
            if (filter.getDocTemplateId() != null) {
                predicates.add(cb.equal(root.get("docTemplateId"), filter.getDocTemplateId()));
            }
            if (filter.getGroupData() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("groupData")), "%" + filter.getGroupData().toLowerCase() + "%"));
            }
            if (filter.getExcelColumnIndex() != null) {
                predicates.add(cb.equal(root.get("excelColumnIndex"), filter.getExcelColumnIndex()));
            }
            if (filter.getIsDocumentCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("isDocumentCode")), "%" + filter.getIsDocumentCode().toLowerCase() + "%"));
            }
            if (filter.getDataFormat() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("dataFormat")), "%" + filter.getDataFormat().toLowerCase() + "%"));
            }
            if (filter.getDataLength() != null) {
                predicates.add(cb.equal(root.get("dataLength"), filter.getDataLength()));
            }
            if (filter.getTableValidate() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("tableValidate")), "%" + filter.getTableValidate().toLowerCase() + "%"));
            }
            if (filter.getColumnValidate() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("columnValidate")), "%" + filter.getColumnValidate().toLowerCase() + "%"));
            }
            if (filter.getTemplateCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("templateCode")), "%" + filter.getTemplateCode().toLowerCase() + "%"));
            }
            if (filter.getIsEncrypt() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("isEncrypt")), "%" + filter.getIsEncrypt().toLowerCase() + "%"));
            }
            if (filter.getIsShow() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("isShow")), "%" + filter.getIsShow().toLowerCase() + "%"));
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
                                cb.lower(root.get("name")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("messageItemName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("packageName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("type")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("filter")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("attribute")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("groupData")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("isDocumentCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("dataFormat")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("tableValidate")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("columnValidate")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("templateCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("isEncrypt")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("isShow")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<DocumentConfigDto> convertPageEntityToPageDto(Page<DocumentConfigEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<DocumentConfigDto> map =
                source.map(
                        new Function<DocumentConfigEntity, DocumentConfigDto>() {
                            @Override
                            public DocumentConfigDto apply(DocumentConfigEntity entity) {
                                DocumentConfigDto dto = modelMapper.map(entity, DocumentConfigDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public List<DocumentConfigDto> findByDocumentConfigHeaderId(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.findByDocumentConfigHeaderId()...");
            List<DocumentConfigEntity> documentConfigEntities = documentConfigRepository.findAllByDocumentConfigHeaderIdAndStatus(id, "A");

            Type listTpye = new TypeToken<List<DocumentConfigDto>>() {}.getType();
            List<DocumentConfigDto> map = modelMapper.map(documentConfigEntities, listTpye);

            return map;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.findByDocumentConfigHeaderId()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.findByDocumentConfigHeaderId()...");
        }
    }
}
