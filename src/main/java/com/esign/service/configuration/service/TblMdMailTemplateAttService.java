package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdMailTemplateAttDto;
import com.esign.service.configuration.entity.TblMdMailTemplateAttEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdMailTemplateAttRepository;
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
public class TblMdMailTemplateAttService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdMailTemplateAttRepository tblMdMailTemplateAttRepository;

    @Autowired
    public void setTblMdMailTemplateAttService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdMailTemplateAttRepository tblMdMailTemplateAttRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdMailTemplateAttRepository = tblMdMailTemplateAttRepository;
    }

    public TblMdMailTemplateAttDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdMailTemplateAttService.getById()...");
            Optional<TblMdMailTemplateAttEntity> byId = tblMdMailTemplateAttRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdMailTemplateAttDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TblMdMailTemplateAttService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdMailTemplateAttService.getById()...");
        }
    }

    public TblMdMailTemplateAttEntity save(TblMdMailTemplateAttDto dto) throws BusinessServiceException {
        log.info("Begin TblMdMailTemplateAttService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdMailTemplateAttEntity entity = modelMapper.map(dto, TblMdMailTemplateAttEntity.class);
        log.info("End TblMdMailTemplateAttService.save()...");
        return tblMdMailTemplateAttRepository.save(entity);
    }

    public Integer checkDuplicateCode(TblMdMailTemplateAttDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdMailTemplateAttService.checkDuplicateAbbr()...");
            List<TblMdMailTemplateAttEntity> rs =
                    tblMdMailTemplateAttRepository.findAllByMailTemplateAttCode(dto.getMailTemplateAttCode());
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TblMdMailTemplateAttService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdMailTemplateAttService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdMailTemplateAttEntity> getSpecification(TblMdMailTemplateAttDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getMailTemplateId() > 0L) {
                predicates.add(cb.equal(root.get("mailTemplateId"), filter.getMailTemplateId()));
            }

            if (filter.getMailTemplateAttCode() != null) {
                predicates.add(cb.equal(root.get("mailTemplateAttCode"), filter.getMailTemplateAttCode()));
            }

            if (filter.getMailTemplateAttName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("mailTemplateAttName")), "%" + filter.getMailTemplateAttName().toLowerCase() + "%"));
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
                                cb.lower(root.get("mailTemplateAttCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("mailTemplateAttName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<TblMdMailTemplateAttDto> findByCri(TblMdMailTemplateAttDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdMailTemplateAttService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdMailTemplateAttEntity> all =
                    tblMdMailTemplateAttRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdMailTemplateAttDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdMailTemplateAttService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdMailTemplateAttService.findByCri()...");
        }
    }

    protected Page<TblMdMailTemplateAttDto> convertPageEntityToPageDto(Page<TblMdMailTemplateAttEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdMailTemplateAttDto> map =
                source.map(
                        new Function<TblMdMailTemplateAttEntity, TblMdMailTemplateAttDto>() {
                            @Override
                            public TblMdMailTemplateAttDto apply(TblMdMailTemplateAttEntity entity) {
                                TblMdMailTemplateAttDto dto = modelMapper.map(entity, TblMdMailTemplateAttDto.class);
                                return dto;
                            }
                        });
        return map;
    }


}
