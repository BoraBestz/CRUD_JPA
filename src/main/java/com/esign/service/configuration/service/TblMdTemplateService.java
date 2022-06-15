package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdSourceDataDto;
import com.esign.service.configuration.dto.TblMdTemplateDto;
import com.esign.service.configuration.entity.TblMdSourceDataEntity;
import com.esign.service.configuration.entity.TblMdTemplateEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdTemplateRepository;
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
public class TblMdTemplateService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdTemplateRepository tblMdTemplateRepository;

    @Autowired
    public void setTblMdTemplateService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdTemplateRepository tblMdTemplateRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdTemplateRepository = tblMdTemplateRepository;
    }

    public Page<TblMdTemplateDto> findByCri(TblMdTemplateDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdTemplateService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdTemplateEntity> all =
                    tblMdTemplateRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdTemplateDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdTemplateService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdTemplateService.findByCri()...");
        }
    }

    public TblMdTemplateDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<TblMdTemplateEntity> byId = tblMdTemplateRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdTemplateDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Integer checkDuplicateCode(TblMdTemplateDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
            List<TblMdTemplateEntity> rs =
                    tblMdTemplateRepository.findAllByTemplateCodeAndStatus(dto.getTemplateCode(),"A");
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

    public TblMdTemplateEntity save(TblMdTemplateDto dto) throws BusinessServiceException {
        log.info("Begin TblMdTemplateService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdTemplateEntity entity = modelMapper.map(dto, TblMdTemplateEntity.class);
        log.info("End TblMdTemplateService.save()...");
        return tblMdTemplateRepository.save(entity);
    }

    @VisibleForTesting
    protected Specification<TblMdTemplateEntity> getSpecification(TblMdTemplateDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getTemplateId() != null) {
                predicates.add(cb.equal(root.get("templateId"), filter.getTemplateId()));
            }

            if (filter.getTemplateCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("templateCode")), "%" + filter.getTemplateCode().toLowerCase() + "%"));
            }
            if (filter.getTemplateName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("templateName")), "%" + filter.getTemplateName().toLowerCase() + "%"));
            }
            if (filter.getIsDefault() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("isDefault")), "%" + filter.getIsDefault().toLowerCase() + "%"));
            }
            if (filter.getDocumentTypeId() != null) {
                predicates.add(cb.equal(root.get("documentTypeId"), filter.getDocumentTypeId()));
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
                                cb.lower(root.get("templateCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("templateName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("isDefault")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("documentTypeId")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<TblMdTemplateDto> convertPageEntityToPageDto(Page<TblMdTemplateEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdTemplateDto> map =
                source.map(
                        new Function<TblMdTemplateEntity, TblMdTemplateDto>() {
                            @Override
                            public TblMdTemplateDto apply(TblMdTemplateEntity entity) {
                                TblMdTemplateDto dto = modelMapper.map(entity, TblMdTemplateDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public List<TblMdTemplateDto> findByDocumentTypeId(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.findByDocumentTypeId()...");
            List<TblMdTemplateEntity> tblMdTemplateEntities = tblMdTemplateRepository.findAllByDocumentTypeIdAndStatus(id, "A");

            Type listTpye = new TypeToken<List<TblMdTemplateDto>>() {}.getType();
            List<TblMdTemplateDto> map = modelMapper.map(tblMdTemplateEntities, listTpye);

            return map;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.findByDocumentTypeId()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.findByDocumentTypeId()...");
        }
    }

}
