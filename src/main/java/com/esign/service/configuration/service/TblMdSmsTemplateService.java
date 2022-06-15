package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdMailTemplateDto;
import com.esign.service.configuration.dto.TblMdSmsTemplateDto;
import com.esign.service.configuration.entity.TblMdMailTemplateEntity;
import com.esign.service.configuration.entity.TblMdSmsTemplateEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdSmsTemplateRepository;
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
public class TblMdSmsTemplateService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdSmsTemplateRepository tblMdSmsTemplateRepository;

    @Autowired
    public void setTblMdSmsTemplateService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdSmsTemplateRepository tblMdSmsTemplateRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdSmsTemplateRepository = tblMdSmsTemplateRepository;
    }

    public TblMdSmsTemplateDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdSmsTemplateService.getById()...");
            Optional<TblMdSmsTemplateEntity> byId = tblMdSmsTemplateRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdSmsTemplateDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TblMdSmsTemplateService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdSmsTemplateService.getById()...");
        }
    }

    public TblMdSmsTemplateEntity save(TblMdSmsTemplateDto dto) throws BusinessServiceException {
        log.info("Begin TblMdSmsTemplateService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdSmsTemplateEntity entity = modelMapper.map(dto, TblMdSmsTemplateEntity.class);
        log.info("End TblMdSmsTemplateService.save()...");
        return tblMdSmsTemplateRepository.save(entity);
    }

    public Integer checkDuplicateCode(TblMdSmsTemplateDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdSmsTemplateService.checkDuplicateAbbr()...");
            List<TblMdSmsTemplateEntity> rs =
                    tblMdSmsTemplateRepository.findAllBySmsTemplateCode(dto.getSmsTemplateCode());

            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TblMdSmsTemplateService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdSmsTemplateService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdSmsTemplateEntity> getSpecification(TblMdSmsTemplateDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getSmsTemplateId() > 0L) {
                predicates.add(cb.equal(root.get("smsTemplateId"), filter.getSmsTemplateId()));
            }

            if (filter.getRecordStatus() != null) {
                predicates.add(cb.equal(root.get("recordStatus"), filter.getRecordStatus().trim()));
            }else {
                predicates.add(cb.equal(root.get("recordStatus"), "A"));
            }

            if (filter.getSmsTemplateCode() != null) {
                predicates.add(cb.equal(root.get("smsTemplateCode"), filter.getSmsTemplateCode()));
            }

            if (filter.getSmsTemplateName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("smsTemplateName")), "%" + filter.getSmsTemplateName().toLowerCase() + "%"));
            }

            if (filter.getSmsTemplateSubject() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("smsTemplateSubject")), "%" + filter.getSmsTemplateSubject().toLowerCase() + "%"));
            }

            if (filter.getSmsTemplateBody() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("smsTemplateBody")), "%" + filter.getSmsTemplateBody().toLowerCase() + "%"));
            }

            if (filter.getBranchId()!= null) {
                predicates.add(cb.equal(root.get("branchId"), filter.getBranchId()));
            }

            if (filter.getCompanyId() != null) {
                predicates.add(cb.equal(root.get("companyId"), filter.getCompanyId()));
            }

            if (filter.getDocumentTypeId() != null) {
                predicates.add(cb.equal(root.get("documentTypeId"), filter.getDocumentTypeId()));
            }


            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("smsTemplateName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("smsTemplateCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("smsTemplateSubject")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("smsTemplateBody")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<TblMdSmsTemplateDto> findByCri(TblMdSmsTemplateDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdSmsTemplateService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdSmsTemplateEntity> all =
                    tblMdSmsTemplateRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdSmsTemplateDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdSmsTemplateService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdSmsTemplateService.findByCri()...");
        }
    }

    protected Page<TblMdSmsTemplateDto> convertPageEntityToPageDto(Page<TblMdSmsTemplateEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdSmsTemplateDto> map =
                source.map(
                        new Function<TblMdSmsTemplateEntity, TblMdSmsTemplateDto>() {
                            @Override
                            public TblMdSmsTemplateDto apply(TblMdSmsTemplateEntity entity) {
                                TblMdSmsTemplateDto dto = modelMapper.map(entity, TblMdSmsTemplateDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public List<TblMdSmsTemplateDto> findByDocumentTypeId (int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdMailTemplateService.getById()...");
            List<TblMdSmsTemplateEntity> tblMdSmsTemplateEntities =  tblMdSmsTemplateRepository.findAllByDocumentTypeIdAndRecordStatus((long) id,"A");

            Type listType = new TypeToken<List<TblMdMailTemplateDto>>() {}.getType();
            List<TblMdSmsTemplateDto> map = modelMapper.map(tblMdSmsTemplateEntities, listType);

            return map;
        } catch (Exception ex) {
            log.error("ERROR TblMdMailTemplateService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdMailTemplateService.getById()...");
        }
    }
}
