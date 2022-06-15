package com.esign.service.configuration.service;

import com.esign.service.configuration.dto.RdErrorDto;
import com.esign.service.configuration.entity.RdErrorEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.RdErrorRepository;
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
public class RdErrorService {

    private RdErrorRepository rdErrorRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setConfigService(RdErrorRepository rdErrorRepository,
                                 ModelMapper modelMapper){
        this.rdErrorRepository = rdErrorRepository;
        this.modelMapper = modelMapper;
    }

    public RdErrorEntity save(RdErrorDto dto) throws BusinessServiceException {
        log.info("String Saving Data....");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info("Map dto with RdError Entity.class.....");
        RdErrorEntity entity = modelMapper.map(dto, RdErrorEntity.class);
        log.info("End Saving Data....");
        return rdErrorRepository.save(entity);
    }

    public Integer checkDuplicate(RdErrorDto dto) throws BusinessServiceException {
        log.info("String Check Duplicate Data....");
        try {
            log.info("Get Data from DB....");
            List<RdErrorEntity> rs = rdErrorRepository.findAllById(dto.getId());

            if (rs != null && rs.size() > 0){
                return 1;
            }
            return 0;
        }catch (Exception ex){
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    public RdErrorDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<RdErrorEntity> byId = rdErrorRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), RdErrorDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Page<RdErrorDto> findByCri(RdErrorDto dto) throws BusinessServiceException {
        try {
            log.info("Begin RdErrorService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<RdErrorEntity> all =
                    rdErrorRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<RdErrorDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR RdErrorService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End RdErrorService.findByCri()...");
        }
    }
    @VisibleForTesting
    protected Specification<RdErrorEntity> getSpecification(RdErrorDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null){
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }

            if (filter.getCriteriaId() != null) {
                predicates.add(cb.equal(root.get("criteriaId"), filter.getCriteriaId()));
            }

            /*if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("configModel")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("configName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }*/

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<RdErrorDto> convertPageEntityToPageDto(Page<RdErrorEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<RdErrorDto> map =
                source.map(
                        new Function<RdErrorEntity, RdErrorDto>() {
                            @Override
                            public RdErrorDto apply(RdErrorEntity entity) {
                                RdErrorDto dto = modelMapper.map(entity, RdErrorDto.class);
                                return dto;
                            }
                        });
        return map;
    }

}
