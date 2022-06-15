package com.esign.service.configuration.service;

import com.esign.service.configuration.dto.TransStatusDto;
import com.esign.service.configuration.entity.TransStatusEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TransStatusRepository;
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

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class TransStatusService {
    private TransStatusRepository transStatusRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setLanguageService(TransStatusRepository transStatusRepository,
                                   ModelMapper modelMapper){
        this.transStatusRepository = transStatusRepository;
        this.modelMapper = modelMapper;
    }

    public TransStatusEntity save(TransStatusDto dto) throws BusinessServiceException {
        log.info("String Saving Data....");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info("Map dto with Language Entity.class.....");
        TransStatusEntity entity = modelMapper.map(dto, TransStatusEntity.class);
        log.info("End Saving Data....");
        return transStatusRepository.save(entity);
    }

    public Integer checkDuplicate(TransStatusDto dto) throws BusinessServiceException {
        log.info("String Check Duplicate Data....");
        try {
            log.info("Get Data from DB....");
            List<TransStatusEntity> rs = transStatusRepository.findAllByStatusCodeAndIsActive(dto.getStatusCode(), true);

            if (rs != null && rs.size() > 0){
                return 1;
            }
            return 0;
        }catch (Exception ex){
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    public TransStatusDto getById(String code) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<TransStatusEntity> byId = transStatusRepository.findByStatusCode(code);
            return byId.isPresent() ? modelMapper.map(byId.get(), TransStatusDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Page<TransStatusDto> findByCri(TransStatusDto dto) throws BusinessServiceException {
        try {
            log.info("Begin LanguageService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<TransStatusEntity> all =
                    transStatusRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<TransStatusDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR LanguageService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End LanguageService.findByCri()...");
        }
    }

    @VisibleForTesting
    protected Specification<TransStatusEntity> getSpecification(TransStatusDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getStatusCode() != null){
                predicates.add(cb.equal(root.get("statusCode"), filter.getStatusCode()));
            }

            if (filter.getStatusName() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("statusName")), "%" + filter.getStatusName().toLowerCase() + "%"));
            }

            if (filter.getIsActive() != null){
                predicates.add(cb.equal(root.get("isActive"), filter.getIsActive()));
            }

            if (filter.getStatusDescription() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("statusDescription")), "%" + filter.getStatusDescription().toLowerCase() + "%"));
            }

            if (filter.getShowSearch() != null){
                predicates.add(cb.equal(root.get("showSearch"), filter.getShowSearch()));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("statusName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("statusDescription")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<TransStatusDto> convertPageEntityToPageDto(Page<TransStatusEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TransStatusDto> map =
                source.map(
                        new Function<TransStatusEntity, TransStatusDto>() {
                            @Override
                            public TransStatusDto apply(TransStatusEntity entity) {
                                TransStatusDto dto = modelMapper.map(entity, TransStatusDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}
