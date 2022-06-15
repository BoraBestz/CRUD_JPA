package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdProductDto;
import com.esign.service.configuration.dto.TblMdProductGroupDto;
import com.esign.service.configuration.dto.address.CitySubDivisionDto;
import com.esign.service.configuration.entity.TblMdProductGroupEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdProductGroupRepository;
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
public class TblMdProductGroupService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdProductGroupRepository tblMdProductGroupRepository;
    private TblMdProductService tblMdProductService;

    @Autowired
    public void setTblMdProductGroupService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdProductGroupRepository tblMdProductGroupRepository,
            TblMdProductService tblMdProductService) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdProductGroupRepository = tblMdProductGroupRepository;
        this.tblMdProductService = tblMdProductService;
    }


    public TblMdProductGroupDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdProductGroupService.getById()...");
            Optional<TblMdProductGroupEntity> byId = tblMdProductGroupRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdProductGroupDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TblMdProductGroupService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdProductGroupService.getById()...");
        }
    }

    public TblMdProductGroupEntity save(TblMdProductGroupDto dto) throws BusinessServiceException {
        log.info("Begin TblMdProductGroupService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdProductGroupEntity entity = modelMapper.map(dto, TblMdProductGroupEntity.class);
        log.info("End TblMdProductGroupService.save()...");
        return tblMdProductGroupRepository.save(entity);
    }

    public Integer checkDuplicateCode(TblMdProductGroupDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdProductGroupService.checkDuplicateAbbr()...");
            List<TblMdProductGroupEntity> rs =
                    tblMdProductGroupRepository.findAllByProductGroupCodeAndRecordStatus(dto.getProductGroupCode(), "A");
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TblMdProductGroupService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdProductGroupService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdProductGroupEntity> getSpecification(TblMdProductGroupDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getProductGroupId() > 0L) {
                predicates.add(cb.equal(root.get("productGroupId"), filter.getProductGroupId()));
            }

            if (filter.getRecordStatus() != null) {
                predicates.add(cb.equal(root.get("recordStatus"), filter.getRecordStatus().trim()));
            }else {
                predicates.add(cb.equal(root.get("recordStatus"), "A"));
            }

            if (filter.getProductGroupCode() != null) {
                predicates.add(cb.equal(root.get("productGroupCode"), filter.getProductGroupCode()));
            }

            if (filter.getProductGroupName() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("productGroupName")), "%" + filter.getProductGroupName().toLowerCase() + "%"));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("productGroupName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("productGroupCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public List<TblMdProductGroupDto> findByCri(TblMdProductGroupDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdProductGroupService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            List<TblMdProductGroupEntity> all =
                    tblMdProductGroupRepository.findAll(getSpecification(dto));
            Type listTpye = new TypeToken<List<TblMdProductGroupDto>>() {}.getType();
            List<TblMdProductGroupDto> map = modelMapper.map(all, listTpye);
            for (TblMdProductGroupDto data : map){
                tblMdProductService.getChildren(data);
            }
            return map;
        } catch (Exception ex) {
            log.error("ERROR TblMdProductGroupService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdProductGroupService.findByCri()...");
        }
    }

    protected Page<TblMdProductGroupDto> convertPageEntityToPageDto(Page<TblMdProductGroupEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdProductGroupDto> map =
                source.map(
                        new Function<TblMdProductGroupEntity, TblMdProductGroupDto>() {
                            @Override
                            public TblMdProductGroupDto apply(TblMdProductGroupEntity entity) {
                                TblMdProductGroupDto dto = modelMapper.map(entity, TblMdProductGroupDto.class);
                                return dto;
                            }
                        });
        return map;
    }


}
