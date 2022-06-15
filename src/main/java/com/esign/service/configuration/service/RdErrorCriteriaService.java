package com.esign.service.configuration.service;

import com.esign.service.configuration.dto.RdErrorCriteriaDto;
import com.esign.service.configuration.entity.RdErrorCriteriaEntity;
import com.esign.service.configuration.entity.RdErrorEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.RdErrorCriteriaRepository;
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
public class RdErrorCriteriaService {

    private RdErrorCriteriaRepository rdErrorCriteriaRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setConfigService(RdErrorCriteriaRepository rdErrorCriteriaRepository,
                                 ModelMapper modelMapper){
        this.rdErrorCriteriaRepository = rdErrorCriteriaRepository;
        this.modelMapper = modelMapper;
    }

    public RdErrorCriteriaEntity save(RdErrorCriteriaDto dto) throws BusinessServiceException {
        log.info("String Saving Data....");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info("Map dto with RdErrorCriteria Entity.class.....");
        RdErrorCriteriaEntity entity = modelMapper.map(dto, RdErrorCriteriaEntity.class);
        log.info("End Saving Data....");
        return rdErrorCriteriaRepository.save(entity);
    }

    public Integer checkDuplicate(RdErrorCriteriaDto dto) throws BusinessServiceException {
        log.info("String Check Duplicate Data....");
        try {
            log.info("Get Data from DB....");
            List<RdErrorEntity> rs = rdErrorCriteriaRepository.findAllById(dto.getId());

            if (rs != null && rs.size() > 0){
                return 1;
            }
            return 0;
        }catch (Exception ex){
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    public RdErrorCriteriaDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<RdErrorCriteriaEntity> byId = rdErrorCriteriaRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), RdErrorCriteriaDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Page<RdErrorCriteriaDto> findByCri(RdErrorCriteriaDto dto) throws BusinessServiceException {
        try {
            log.info("Begin RdErrorCriteriaService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<RdErrorCriteriaEntity> all =
                    rdErrorCriteriaRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<RdErrorCriteriaDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR RdErrorCriteriaService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End RdErrorCriteriaService.findByCri()...");
        }
    }
    @VisibleForTesting
    protected Specification<RdErrorCriteriaEntity> getSpecification(RdErrorCriteriaDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null){
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }

            if (filter.getCriteriaDetail() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("criteriaDetail")), "%" + filter.getCriteriaDetail().toLowerCase() + "%"));
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

    protected Page<RdErrorCriteriaDto> convertPageEntityToPageDto(Page<RdErrorCriteriaEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<RdErrorCriteriaDto> map =
                source.map(
                        new Function<RdErrorCriteriaEntity, RdErrorCriteriaDto>() {
                            @Override
                            public RdErrorCriteriaDto apply(RdErrorCriteriaEntity entity) {
                                RdErrorCriteriaDto dto = modelMapper.map(entity, RdErrorCriteriaDto.class);
                                return dto;
                            }
                        });
        return map;
    }

}
