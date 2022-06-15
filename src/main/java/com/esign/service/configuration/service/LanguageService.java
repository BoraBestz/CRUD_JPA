package com.esign.service.configuration.service;

import com.esign.service.configuration.dto.LanguageDto;
import com.esign.service.configuration.dto.address.CityDto;
import com.esign.service.configuration.entity.LanguageEntity;
import com.esign.service.configuration.entity.address.CityEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.LanguageRepository;
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
public class LanguageService {

    private LanguageRepository languageRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setLanguageService(LanguageRepository languageRepository,
                                   ModelMapper modelMapper){
        this.languageRepository = languageRepository;
        this.modelMapper = modelMapper;
    }

    public LanguageEntity save(LanguageDto dto) throws BusinessServiceException{
        log.info("String Saving Data....");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info("Map dto with Language Entity.class.....");
        LanguageEntity entity = modelMapper.map(dto, LanguageEntity.class);
        log.info("End Saving Data....");
        return languageRepository.save(entity);
    }

    public Integer checkDuplicate(LanguageDto dto) throws BusinessServiceException {
        log.info("String Check Duplicate Data....");
        try {
            log.info("Get Data from DB....");
            List<LanguageEntity> rs = languageRepository.findAllByLanguageCode(dto.getLanguageCode());

            if (rs != null && rs.size() > 0){
                return 1;
            }
            return 0;
        }catch (Exception ex){
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    public LanguageDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<LanguageEntity> byId = languageRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), LanguageDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Page<LanguageDto> findByCri(LanguageDto dto) throws BusinessServiceException {
        try {
            log.info("Begin LanguageService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<LanguageEntity> all =
                    languageRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<LanguageDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR LanguageService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End LanguageService.findByCri()...");
        }
    }

    @VisibleForTesting
    protected Specification<LanguageEntity> getSpecification(LanguageDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getLanguageId() != null){
                predicates.add(cb.equal(root.get("languageId"), filter.getLanguageId()));
            }

            if (filter.getStatus() != null && filter.getStatus().intValue() > 0) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }else {
                predicates.add(cb.equal(root.get("status"), 1));
            }

            if (filter.getCreateBy() != null){
                predicates.add(cb.equal(root.get("createBy"), filter.getCreateBy()));
            }

            if (filter.getLanguageCode() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("languageCode")), "%" + filter.getLanguageCode().toLowerCase() + "%"));
            }

            if (filter.getLanguageNameTh() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("languageNameTh")), "%" + filter.getLanguageNameTh().toLowerCase() + "%"));
            }

            if (filter.getLanguageNameEn() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("languageNameEn")), "%" + filter.getLanguageNameEn().toLowerCase() + "%"));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("languageCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("languageNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("languageNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<LanguageDto> convertPageEntityToPageDto(Page<LanguageEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<LanguageDto> map =
                source.map(
                        new Function<LanguageEntity, LanguageDto>() {
                            @Override
                            public LanguageDto apply(LanguageEntity entity) {
                                LanguageDto dto = modelMapper.map(entity, LanguageDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}
