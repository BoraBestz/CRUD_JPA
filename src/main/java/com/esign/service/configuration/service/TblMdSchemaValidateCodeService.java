package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdSchemaValidateCodeDto;
import com.esign.service.configuration.entity.TblMdSchemaValidateCodeEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdSchemaValidateCodeRepository;
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
public class TblMdSchemaValidateCodeService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdSchemaValidateCodeRepository tblMdSchemaValidateCodeRepository;

    @Autowired
    public void setTblMdSchemaValidateCodeService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdSchemaValidateCodeRepository tblMdSchemaValidateCodeRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdSchemaValidateCodeRepository = tblMdSchemaValidateCodeRepository;
    }

    public TblMdSchemaValidateCodeDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdSchemaValidateCodeControllerService.getById()...");
            Optional<TblMdSchemaValidateCodeEntity> byId = tblMdSchemaValidateCodeRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdSchemaValidateCodeDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TblMdSchemaValidateCodeControllerService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdSchemaValidateCodeControllerService.getById()...");
        }
    }

    public TblMdSchemaValidateCodeEntity save(TblMdSchemaValidateCodeDto dto) throws BusinessServiceException {
        log.info("Begin TblMdSchemaValidateCodeControllerService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdSchemaValidateCodeEntity entity = modelMapper.map(dto, TblMdSchemaValidateCodeEntity.class);
        log.info("End TblMdSchemaValidateCodeControllerService.save()...");
        return tblMdSchemaValidateCodeRepository.save(entity);
    }

    public Integer checkDuplicateCode(TblMdSchemaValidateCodeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdSchemaValidateCodeControllerService.checkDuplicateAbbr()...");
            List<TblMdSchemaValidateCodeEntity> rs =
                    tblMdSchemaValidateCodeRepository.findAllBySchemaConditionName(dto.getSchemaConditionName());
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TblMdSchemaValidateCodeControllerService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdSchemaValidateCodeControllerService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdSchemaValidateCodeEntity> getSpecification(TblMdSchemaValidateCodeDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getSchemaValidateCodeId() > 0L) {
                predicates.add(cb.equal(root.get("schemaValidateCodeId"), filter.getSchemaValidateCodeId()));
            }

            if (filter.getSchemaCodeType() != null) {
                predicates.add(cb.equal(root.get("schemaCodeType"), filter.getSchemaCodeType()));
            }

            if (filter.getSchemaCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("schemaCode")), "%" + filter.getSchemaCode().toLowerCase() + "%"));
            }

            if (filter.getSchemaCodeMassage() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("schemaCodeMassage")), "%" + filter.getSchemaCodeMassage().toLowerCase() + "%"));
            }

            if (filter.getSchemaCodeMassage2() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("schemaCodeMassage2")), "%" + filter.getSchemaCodeMassage2().toLowerCase() + "%"));
            }

            if (filter.getConditionGrorpName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("schemaConditionName")), "%" + filter.getConditionGrorpName().toLowerCase() + "%"));
            }

            if (filter.getConditionName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("conditionName")), "%" + filter.getConditionName().toLowerCase() + "%"));
            }

            if (filter.getSchemaConditionName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("schemaName")), "%" + filter.getConditionName().toLowerCase() + "%"));
            }

            if (filter.getConditionGrorpName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("conditionGrorpName")), "%" + filter.getConditionGrorpName().toLowerCase() + "%"));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("schemaConditionName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("schemaCodeType")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("schemaCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("schemaCodeMassage")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("schemaCodeMassage2")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("conditionName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("schemaName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("conditionGrorpName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<TblMdSchemaValidateCodeDto> findByCri(TblMdSchemaValidateCodeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdSchemaValidateCodeControllerService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdSchemaValidateCodeEntity> all =
                    tblMdSchemaValidateCodeRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdSchemaValidateCodeDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdMailTemplateService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdSchemaValidateCodeControllerService.findByCri()...");
        }
    }

    protected Page<TblMdSchemaValidateCodeDto> convertPageEntityToPageDto(Page<TblMdSchemaValidateCodeEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdSchemaValidateCodeDto> map =
                source.map(
                        new Function<TblMdSchemaValidateCodeEntity, TblMdSchemaValidateCodeDto>() {
                            @Override
                            public TblMdSchemaValidateCodeDto apply(TblMdSchemaValidateCodeEntity entity) {
                                TblMdSchemaValidateCodeDto dto = modelMapper.map(entity, TblMdSchemaValidateCodeDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}
