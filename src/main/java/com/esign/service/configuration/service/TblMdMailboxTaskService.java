package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdMailboxTaskDto;
import com.esign.service.configuration.entity.TblMdMailboxTaskEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdMailboxTaskRepository;
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
public class TblMdMailboxTaskService {
    private ModelMapper modelMapper;
    private TblMdMailboxTaskRepository tblMdMailboxTaskRepository;
    private EntityManager entityManager;
    private OauthUser oauthUser;

    @Autowired
    public void setTbMdMailboxTaskService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdMailboxTaskRepository tblMdMailboxTaskRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdMailboxTaskRepository = tblMdMailboxTaskRepository;
    }

    public TblMdMailboxTaskDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin TbMdMailboxTaskControllerService.getById()...");
            Optional<TblMdMailboxTaskEntity> byId = tblMdMailboxTaskRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdMailboxTaskDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TbMdMailboxTaskControllerService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TbMdMailboxTaskControllerService.getById()...");
        }
    }

    public TblMdMailboxTaskEntity save(TblMdMailboxTaskDto dto) throws BusinessServiceException {
        log.info("Begin TbMdMailboxTaskControllerService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdMailboxTaskEntity entity = modelMapper.map(dto, TblMdMailboxTaskEntity.class);
        log.info("End TbMdMailboxTaskControllerService.save()...");
        return tblMdMailboxTaskRepository.save(entity);
    }

    public Integer checkDuplicateCode(TblMdMailboxTaskDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TbMdMailboxTaskControllerService.checkDuplicateAbbr()...");
            List<TblMdMailboxTaskEntity> rs =
                    tblMdMailboxTaskRepository.findAllByMailboxTaskCodeAndRecordStatus(dto.getMailboxTaskCode(), dto.getRecordStatus());
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TbMdMailboxTaskControllerService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TbMdMailboxTaskControllerService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdMailboxTaskEntity> getSpecification(TblMdMailboxTaskDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getMailboxTaskId() != null) {
                predicates.add(cb.equal(root.get("mailboxTaskId"), filter.getMailboxTaskId()));
            }

            if (filter.getMailboxTaskCode() != null) {
                predicates.add(cb.equal(root.get("mailboxTaskCode"), filter.getMailboxTaskCode()));
            }

            if (filter.getDocumentTypeId() != null) {
                predicates.add(cb.equal(root.get("documentTypeId"), filter.getDocumentTypeId()));
            }

            if (filter.getCompanyId() != null) {
                predicates.add(cb.equal(root.get("companyId"), filter.getCompanyId()));
            }

            if (filter.getBranchId() != null) {
                predicates.add(cb.equal(root.get("branchId"), filter.getBranchId()));
            }


            if (filter.getMailTemplateId() != null) {
                predicates.add(cb.equal(root.get("mailTemplateId"), filter.getMailTemplateId()));
            }

            if (filter.getMailLink() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("mailLink")), "%" + filter.getMailLink().toLowerCase() + "%"));
            }

            if (filter.getSmsTemplateId() != null) {
                predicates.add(cb.equal(root.get("smsTemplateId"), filter.getSmsTemplateId()));
            }

            if (filter.getSmsLink() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("smsLink")), "%" + filter.getSmsLink().toLowerCase() + "%"));
            }

            if (filter.getRdTemplateId() != null) {
                predicates.add(cb.equal(root.get("rdTemplateId"), filter.getRdTemplateId()));
            }

            if (filter.getRdLink() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("rdLink")), "%" + filter.getRdLink().toLowerCase() + "%"));
            }

            if (filter.getRdTemplateId() != null) {
                predicates.add(cb.equal(root.get("rdTemplateId"), filter.getRdTemplateId()));
            }

            if (filter.getRecordStatus()!= null) {
                predicates.add(cb.equal(root.get("recordStatus"), filter.getRecordStatus().trim()));
            }else {
                predicates.add(cb.equal(root.get("recordStatus"), "A"));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("mailLink")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("mailboxTaskCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("smsLink")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("rdLink")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<TblMdMailboxTaskDto> findByCri(TblMdMailboxTaskDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TbMdMailboxTaskControllerService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdMailboxTaskEntity> all =
                    tblMdMailboxTaskRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdMailboxTaskDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TbMdMailboxTaskControllerService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TbMdMailboxTaskControllerService.findByCri()...");
        }
    }

    protected Page<TblMdMailboxTaskDto> convertPageEntityToPageDto(Page<TblMdMailboxTaskEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdMailboxTaskDto> map =
                source.map(
                        new Function<TblMdMailboxTaskEntity, TblMdMailboxTaskDto>() {
                            @Override
                            public TblMdMailboxTaskDto apply(TblMdMailboxTaskEntity entity) {
                                TblMdMailboxTaskDto dto = modelMapper.map(entity, TblMdMailboxTaskDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public List<TblMdMailboxTaskDto> findByDocumentTypeId (int id) throws BusinessServiceException {
        try {
            log.info("Begin TbMdMailboxTaskControllerService.getById()...");
            List<TblMdMailboxTaskEntity> tbMdMailboxTaskEntities =  tblMdMailboxTaskRepository.findAllByDocumentTypeIdAndRecordStatus((long) id,"A");

            Type listType = new TypeToken<List<TblMdMailboxTaskDto>>() {}.getType();
            List<TblMdMailboxTaskDto> map = modelMapper.map(tbMdMailboxTaskEntities, listType);

            return map;
        } catch (Exception ex) {
            log.error("ERROR TbMdMailboxTaskControllerService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TbMdMailboxTaskControllerService.getById()...");
        }
    }

    public List<TblMdMailboxTaskDto> findByMailTemplateId (int id) throws BusinessServiceException {
        try {
            log.info("Begin TbMdMailboxTaskControllerService.getById()...");
            List<TblMdMailboxTaskEntity> tbMdMailboxTaskEntities =  tblMdMailboxTaskRepository.findAllByMailTemplateIdAndRecordStatus((long) id,"A");

            Type listType = new TypeToken<List<TblMdMailboxTaskDto>>() {}.getType();
            List<TblMdMailboxTaskDto> map = modelMapper.map(tbMdMailboxTaskEntities, listType);

            return map;
        } catch (Exception ex) {
            log.error("ERROR TbMdMailboxTaskControllerService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TbMdMailboxTaskControllerService.getById()...");
        }
    }

    public List<TblMdMailboxTaskDto> findBySmsTemplateId (int id) throws BusinessServiceException {
        try {
            log.info("Begin TbMdMailboxTaskControllerService.getById()...");
            List<TblMdMailboxTaskEntity> tbMdMailboxTaskEntities =  tblMdMailboxTaskRepository.findAllBySmsTemplateIdAndRecordStatus((long) id,"A");

            Type listType = new TypeToken<List<TblMdMailboxTaskDto>>() {}.getType();
            List<TblMdMailboxTaskDto> map = modelMapper.map(tbMdMailboxTaskEntities, listType);

            return map;
        } catch (Exception ex) {
            log.error("ERROR TbMdMailboxTaskControllerService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TbMdMailboxTaskControllerService.getById()...");
        }
    }


}
