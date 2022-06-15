package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdDocumentValidateCodeDto;
import com.esign.service.configuration.entity.TblMdDocumentValidateCodeEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdDocumentValidateCodeRepository;
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
public class TblMdDocumentValidateCodeService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdDocumentValidateCodeRepository tblMdDocumentValidateCodeRepository;

    @Autowired
    public void setTblMdDocumentValidateCodeService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdDocumentValidateCodeRepository tblMdDocumentValidateCodeRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdDocumentValidateCodeRepository = tblMdDocumentValidateCodeRepository;
    }

    public TblMdDocumentValidateCodeDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdDocumentValidateCodeService.getById()...");
            Optional<TblMdDocumentValidateCodeEntity> byId = tblMdDocumentValidateCodeRepository.findById((long) id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdDocumentValidateCodeDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TblMdDocumentValidateCodeService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdDocumentValidateCodeService.getById()...");
        }
    }

    public TblMdDocumentValidateCodeEntity save(TblMdDocumentValidateCodeDto dto) throws BusinessServiceException {
        log.info("Begin TblMdDocumentValidateCodeService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdDocumentValidateCodeEntity entity = modelMapper.map(dto, TblMdDocumentValidateCodeEntity.class);
        log.info("End TblMdDocumentValidateCodeService.save()...");
        return tblMdDocumentValidateCodeRepository.save(entity);
    }

    public Integer checkDuplicateCode(TblMdDocumentValidateCodeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdDocumentValidateCodeService.checkDuplicateAbbr()...");
            List<TblMdDocumentValidateCodeEntity> rs =
                    tblMdDocumentValidateCodeRepository.findAllByConditionDocumentCode(dto.getConditionDocumentCode());
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TblMdDocumentValidateCodeService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdDocumentValidateCodeService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdDocumentValidateCodeEntity> getSpecification(TblMdDocumentValidateCodeDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getDocumentValidateCodeId() > 0L) {
                predicates.add(cb.equal(root.get("documentValidateCodeId"), filter.getDocumentValidateCodeId()));
            }

            if (filter.getConditionDocumentCode() != null) {
                predicates.add(cb.equal(root.get("conditionDocumentCode"), filter.getConditionDocumentCode()));
            }

            if (filter.getCodeType() != null) {
                predicates.add(cb.equal(root.get("codeType"), filter.getCodeType()));
            }

            if (filter.getCodeMassage() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("codeMassage")), "%" + filter.getCodeMassage().toLowerCase() + "%"));
            }

            if (filter.getCodeMassageNameEn() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("codeMassageNameEn")), "%" + filter.getCodeMassageNameEn().toLowerCase() + "%"));
            }

            if (filter.getCodeMassageNameTh() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("codeMassageNameTh")), "%" + filter.getCodeMassageNameTh().toLowerCase() + "%"));
            }

            if (filter.getConditionCode1() != null) {
                predicates.add(cb.equal(root.get("conditionCode1"), filter.getConditionCode1()));
            }

            if (filter.getConditionCode2() != null) {
                predicates.add(cb.equal(root.get("conditionCode2"), filter.getConditionCode2()));
            }


            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("conditionDocumentCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("codeType")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("codeMassageNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("codeMassageNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("conditionCode1")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("conditionCode2")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<TblMdDocumentValidateCodeDto> findByCri(TblMdDocumentValidateCodeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdDocumentValidateCodeService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdDocumentValidateCodeEntity> all =
                    tblMdDocumentValidateCodeRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdDocumentValidateCodeDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdDocumentValidateCodeService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdDocumentValidateCodeService.findByCri()...");
        }
    }

    protected Page<TblMdDocumentValidateCodeDto> convertPageEntityToPageDto(Page<TblMdDocumentValidateCodeEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdDocumentValidateCodeDto> map =
                source.map(
                        new Function<TblMdDocumentValidateCodeEntity, TblMdDocumentValidateCodeDto>() {
                            @Override
                            public TblMdDocumentValidateCodeDto apply(TblMdDocumentValidateCodeEntity entity) {
                                TblMdDocumentValidateCodeDto dto = modelMapper.map(entity, TblMdDocumentValidateCodeDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}
