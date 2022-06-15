package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdPdfPasswordDto;
import com.esign.service.configuration.entity.TblMdPdfPasswordEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdPdfPasswordRepository;
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
public class TblMdPdfPasswordService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdPdfPasswordRepository tblMdPdfPasswordRepository;

    @Autowired
    public void setTblMdPdfPasswordService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdPdfPasswordRepository tblMdPdfPasswordRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdPdfPasswordRepository = tblMdPdfPasswordRepository;
    }

    public TblMdPdfPasswordDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdPdfPasswordService.getById()...");
            Optional<TblMdPdfPasswordEntity> byId = tblMdPdfPasswordRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdPdfPasswordDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TblMdPdfPasswordService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdPdfPasswordService.getById()...");
        }
    }

    public TblMdPdfPasswordEntity save(TblMdPdfPasswordDto dto) throws BusinessServiceException {
        log.info("Begin TblMdPdfPasswordService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdPdfPasswordEntity entity = modelMapper.map(dto, TblMdPdfPasswordEntity.class);
        log.info("End TblMdPdfPasswordService.save()...");
        return tblMdPdfPasswordRepository.save(entity);
    }

    public Integer checkDuplicateCode(TblMdPdfPasswordDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdPdfPasswordService.checkDuplicateAbbr()...");
            List<TblMdPdfPasswordEntity> rs =
                    tblMdPdfPasswordRepository.findAllByPdfPasswordCode(dto.getPdfPasswordCode());
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TblMdPdfPasswordService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdPdfPasswordService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdPdfPasswordEntity> getSpecification(TblMdPdfPasswordDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getPdfPasswordId() > 0L) {
                predicates.add(cb.equal(root.get("pdfPasswordId"), filter.getPdfPasswordId()));
            }

            if (filter.getRecordStatus() != null) {
                predicates.add(cb.equal(root.get("recordStatus"), filter.getRecordStatus().trim()));
            }else {
                predicates.add(cb.equal(root.get("recordStatus"), "A"));
            }

            if (filter.getPdfPasswordCode() != null) {
                predicates.add(cb.equal(root.get("pdfPasswordCode"), filter.getPdfPasswordCode()));
            }

            if (filter.getPdfPasswordName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("pdfPasswordName")), "%" + filter.getPdfPasswordName().toLowerCase() + "%"));
            }

            if (filter.getPdfPasswordField() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("pdfPasswordField")), "%" + filter.getPdfPasswordField().toLowerCase() + "%"));
            }

            if (filter.getDefaultFlag() != null) {
                predicates.add(cb.equal(root.get("defaultFlag"), filter.getDefaultFlag()));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("pdfPasswordName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("pdfPasswordCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("pdfPasswordField")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("defaultFlag")), "%" + filter.getFullSearch().toLowerCase() + "%"));

                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<TblMdPdfPasswordDto> findByCri(TblMdPdfPasswordDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdPdfPasswordService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdPdfPasswordEntity> all =
                    tblMdPdfPasswordRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdPdfPasswordDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdMailTemplateAttService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdPdfPasswordService.findByCri()...");
        }
    }

    protected Page<TblMdPdfPasswordDto> convertPageEntityToPageDto(Page<TblMdPdfPasswordEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdPdfPasswordDto> map =
                source.map(
                        new Function<TblMdPdfPasswordEntity, TblMdPdfPasswordDto>() {
                            @Override
                            public TblMdPdfPasswordDto apply(TblMdPdfPasswordEntity entity) {
                                TblMdPdfPasswordDto dto = modelMapper.map(entity, TblMdPdfPasswordDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}
