package com.esign.service.configuration.service;


import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.PurposeDto;
import com.esign.service.configuration.entity.PurposeEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.PurposeRepository;
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
public class PurposeService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private PurposeRepository purposeRepository;

    @Autowired
    public void setDeliveryTypeService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            PurposeRepository purposeRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.purposeRepository = purposeRepository;
    }

    public PurposeDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PurposeService.getById()...");
            Optional<PurposeEntity> byId = purposeRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), PurposeDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PurposeService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PurposeService.getById()...");
        }
    }

    public PurposeEntity save(PurposeDto dto) throws BusinessServiceException {
        log.info("Begin PurposeService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PurposeEntity entity = modelMapper.map(dto, PurposeEntity.class);
        log.info("End PurposeService.save()...");
        return purposeRepository.save(entity);
    }

    public Integer checkDuplicateCode(PurposeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PurposeService.checkDuplicateAbbr()...");
            List<PurposeEntity> rs =
                    purposeRepository.findAllByPurposeCodeAndStatus(dto.getPurposeCode(), dto.getStatus());
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR PurposeService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PurposeService.checkDuplicateAbbr()...");
        }
    }

    public Page<PurposeDto> findByCri(PurposeDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PurposeService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<PurposeEntity> all =
                    purposeRepository.findAll(getSpecification(dto), pageRequest);
            Page<PurposeDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR PurposeService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PurposeService.findByCri()...");
        }
    }

    @VisibleForTesting
    protected Specification<PurposeEntity> getSpecification(PurposeDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getPurposeId() != null) {
                predicates.add(cb.equal(root.get("purposeId"), filter.getPurposeId()));
            }

            if (filter.getPurposeCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("purposeCode")), "%" + filter.getPurposeCode().toLowerCase() + "%"));
            }

            if (filter.getPurposeNameTh() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("purposeNameTh")), "%" + filter.getPurposeNameTh().toLowerCase() + "%"));
            }

            if (filter.getPurposeNameEn() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("purposeNameEn")), "%" + filter.getPurposeNameEn().toLowerCase() + "%"));
            }

            if (filter.getPurposeDescription() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("purposeDescription")), "%" + filter.getPurposeDescription().toLowerCase() + "%"));
            }

            if (filter.getDocumentTypecode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("documentTypecode")), "%" + filter.getDocumentTypecode().toLowerCase() + "%"));
            }


            if (filter.getCreatedUser() != null) {
                predicates.add(cb.equal(root.get("createdUser"), filter.getCreatedUser()));
            }

            predicates.add(cb.equal(root.get("status"), filter.getStatus()));

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("purposeCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("purposeNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("purposeNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("purposeDescription")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("documentTypecode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("createdUser")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    protected Page<PurposeDto> convertPageEntityToPageDto(Page<PurposeEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<PurposeDto> map =
                source.map(
                        new Function<PurposeEntity, PurposeDto>() {
                            @Override
                            public PurposeDto apply(PurposeEntity entity) {
                                PurposeDto dto = modelMapper.map(entity, PurposeDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}
