package com.esign.service.configuration.service;


import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdDocumentGroupInfDto;
import com.esign.service.configuration.dto.TblMdMailTemplateDto;
import com.esign.service.configuration.entity.TblMdDocumentGroupInfEntity;
import com.esign.service.configuration.entity.TblMdMailTemplateEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdMailTemplateRepository;
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
public class TblMdMailTemplateService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdMailTemplateRepository tblMdMailTemplateRepository;

    @Autowired
    public void setTblMdMailTemplateService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdMailTemplateRepository tblMdMailTemplateRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdMailTemplateRepository = tblMdMailTemplateRepository;
    }

    public TblMdMailTemplateDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdMailTemplateService.getById()...");
            Optional<TblMdMailTemplateEntity> byId = tblMdMailTemplateRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdMailTemplateDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TblMdMailTemplateService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdMailTemplateService.getById()...");
        }
    }

    public TblMdMailTemplateEntity save(TblMdMailTemplateDto dto) throws BusinessServiceException {
        log.info("Begin TblMdMailTemplateService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdMailTemplateEntity entity = modelMapper.map(dto, TblMdMailTemplateEntity.class);
        log.info("End TblMdMailTemplateService.save()...");
        return tblMdMailTemplateRepository.save(entity);
    }

    public Integer checkDuplicateCode(TblMdMailTemplateDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdMailTemplateService.checkDuplicateAbbr()...");
            List<TblMdMailTemplateEntity> rs =
                    tblMdMailTemplateRepository.findAllByMailTemplateCode(dto.getMailTemplateCode());
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TblMdMailTemplateService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdMailTemplateService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdMailTemplateEntity> getSpecification(TblMdMailTemplateDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getMailTemplateId() > 0L) {
                predicates.add(cb.equal(root.get("mailTemplateId"), filter.getMailTemplateId()));
            }

            if (filter.getRecordStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getRecordStatus().trim()));
            }else {
                predicates.add(cb.equal(root.get("status"), "A"));
            }

            if (filter.getMailTemplateCode() != null) {
                predicates.add(cb.equal(root.get("mailTemplateCode"), filter.getMailTemplateCode()));
            }

            if (filter.getMailTemplateFrom() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("mailTemplateFrom")), "%" + filter.getMailTemplateFrom().toLowerCase() + "%"));
            }

            if (filter.getMailTemplateTo() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("mailTemplateTo")), "%" + filter.getMailTemplateTo().toLowerCase() + "%"));
            }

            if (filter.getMailTemplateCc() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("mailTemplateCc")), "%" + filter.getMailTemplateCc().toLowerCase() + "%"));
            }


            if (filter.getMailTemplateSubject() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("mailTemplateSubject")), "%" + filter.getMailTemplateSubject().toLowerCase() + "%"));
            }

            if (filter.getMailTemplateBody() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("mailTemplateBody")), "%" + filter.getMailTemplateBody().toLowerCase() + "%"));
            }

            if (filter.getMailTemplateBcc() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("mailTemplateBcc")), "%" + filter.getMailTemplateBcc().toLowerCase() + "%"));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("mailTemplateName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("mailTemplateCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("mailTemplateFrom")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("mailTemplateTo")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("mailTemplateCc")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("mailTemplateSubject")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("mailTemplateBody")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("mailTemplateBcc")), "%" + filter.getFullSearch().toLowerCase() + "%"));

                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<TblMdMailTemplateDto> findByCri(TblMdMailTemplateDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdMailTemplateService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdMailTemplateEntity> all =
                    tblMdMailTemplateRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdMailTemplateDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdMailTemplateService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdMailTemplateService.findByCri()...");
        }
    }

    protected Page<TblMdMailTemplateDto> convertPageEntityToPageDto(Page<TblMdMailTemplateEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdMailTemplateDto> map =
                source.map(
                        new Function<TblMdMailTemplateEntity, TblMdMailTemplateDto>() {
                            @Override
                            public TblMdMailTemplateDto apply(TblMdMailTemplateEntity entity) {
                                TblMdMailTemplateDto dto = modelMapper.map(entity, TblMdMailTemplateDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public List<TblMdMailTemplateDto> findByCompanyId (int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdMailTemplateService.getById()...");
            List<TblMdMailTemplateEntity> tblMdMailTemplateEntities =  tblMdMailTemplateRepository.findAllByCompanyIdAndRecordStatus( id,"A");

            Type listType = new TypeToken<List<TblMdMailTemplateDto>>() {}.getType();
            List<TblMdMailTemplateDto> map = modelMapper.map(tblMdMailTemplateEntities, listType);

            return map;
        } catch (Exception ex) {
            log.error("ERROR TblMdMailTemplateService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdMailTemplateService.getById()...");
        }
    }

    public List<TblMdMailTemplateDto> findByDocumentTypeId (int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdMailTemplateService.getById()...");
            List<TblMdMailTemplateEntity> tblMdMailTemplateEntities =  tblMdMailTemplateRepository.findAllByDocumentTypeIdAndRecordStatus( id,"A");

            Type listType = new TypeToken<List<TblMdMailTemplateDto>>() {}.getType();
            List<TblMdMailTemplateDto> map = modelMapper.map(tblMdMailTemplateEntities, listType);

            return map;
        } catch (Exception ex) {
            log.error("ERROR TblMdMailTemplateService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdMailTemplateService.getById()...");
        }
    }

    public List<TblMdMailTemplateDto> findByBranchId (int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdMailTemplateService.getById()...");
            List<TblMdMailTemplateEntity> tblMdMailTemplateEntities =  tblMdMailTemplateRepository.findAllByBranchIdAndRecordStatus( id,"A");

            Type listType = new TypeToken<List<TblMdMailTemplateDto>>() {}.getType();
            List<TblMdMailTemplateDto> map = modelMapper.map(tblMdMailTemplateEntities, listType);

            return map;
        } catch (Exception ex) {
            log.error("ERROR TblMdMailTemplateService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdMailTemplateService.getById()...");
        }
    }
}
