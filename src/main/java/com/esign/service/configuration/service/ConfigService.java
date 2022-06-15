package com.esign.service.configuration.service;

import com.esign.service.configuration.dto.ConfigDto;
import com.esign.service.configuration.entity.ConfigEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.ConfigRepository;
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
public class ConfigService {

    private ConfigRepository configRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setConfigService(ConfigRepository configRepository,
                                   ModelMapper modelMapper){
        this.configRepository = configRepository;
        this.modelMapper = modelMapper;
    }

    public ConfigEntity save(ConfigDto dto) throws BusinessServiceException {
        log.info("String Saving Data....");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info("Map dto with Config Entity.class.....");
        ConfigEntity entity = modelMapper.map(dto, ConfigEntity.class);
        log.info("End Saving Data....");
        return configRepository.save(entity);
    }

    public Integer checkDuplicate(ConfigDto dto) throws BusinessServiceException {
        log.info("String Check Duplicate Data....");
        try {
            log.info("Get Data from DB....");
            List<ConfigEntity> rs = configRepository.findAllByConfigName(dto.getConfigName());

            if (rs != null && rs.size() > 0){
                return 1;
            }
            return 0;
        }catch (Exception ex){
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    public ConfigDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<ConfigEntity> byId = configRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), ConfigDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Page<ConfigDto> findByCri(ConfigDto dto) throws BusinessServiceException {
        try {
            log.info("Begin LanguageService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<ConfigEntity> all =
                    configRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<ConfigDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR LanguageService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End LanguageService.findByCri()...");
        }
    }
    @VisibleForTesting
    protected Specification<ConfigEntity> getSpecification(ConfigDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getConfigId() != null){
                predicates.add(cb.equal(root.get("configId"), filter.getConfigId()));
            }

            if (filter.getConfigModule() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("configModule")), "%" + filter.getConfigModule().toLowerCase() + "%"));
            }

            if (filter.getConfigName() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("configName")), "%" + filter.getConfigName().toLowerCase() + "%"));
            }

            if (filter.getConfigValue() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("configValue")), "%" + filter.getConfigValue().toLowerCase() + "%"));
            }

            if (filter.getConfigDescription() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("configDescription")), "%" + filter.getConfigDescription().toLowerCase() + "%"));
            }

            if (filter.getCreateBy() != null) {
                predicates.add(cb.equal(root.get("createBy"), filter.getCreateBy()));
            }

            if (filter.getFullSearch() != null) {
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
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<ConfigDto> convertPageEntityToPageDto(Page<ConfigEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<ConfigDto> map =
                source.map(
                        new Function<ConfigEntity, ConfigDto>() {
                            @Override
                            public ConfigDto apply(ConfigEntity entity) {
                                ConfigDto dto = modelMapper.map(entity, ConfigDto.class);
                                return dto;
                            }
                        });
        return map;
    }
}
