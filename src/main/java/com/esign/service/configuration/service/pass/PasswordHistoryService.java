package com.esign.service.configuration.service.pass;

import com.esign.service.configuration.dto.pass.PasswordHistoryDto;
import com.esign.service.configuration.entity.pass.PasswordHistoryEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.pass.PassHistoryRepository;
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
public class PasswordHistoryService {

    private PassHistoryRepository passHistoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setConfigService(PassHistoryRepository passHistoryRepository,
                                 ModelMapper modelMapper){
        this.passHistoryRepository = passHistoryRepository;
        this.modelMapper = modelMapper;
    }

    public PasswordHistoryEntity save(PasswordHistoryDto dto) throws BusinessServiceException {
        log.info("String Saving Data....");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info("Map dto with Config Entity.class.....");
        PasswordHistoryEntity entity = modelMapper.map(dto, PasswordHistoryEntity.class);
        log.info("End Saving Data....");
        return passHistoryRepository.save(entity);
    }

    public Integer checkDuplicate(PasswordHistoryDto dto) throws BusinessServiceException {
        log.info("String Check Duplicate Data....");
        try {
            log.info("Get Data from DB....");
            List<PasswordHistoryEntity> rs = passHistoryRepository.findAllByPasswordHistoryAndStatus(dto.getPasswordHistory(), dto.getStatus());

            if (rs != null && rs.size() > 0){
                return 1;
            }
            return 0;
        }catch (Exception ex){
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    public PasswordHistoryDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin PostalDistrictService.getById()...");
            Optional<PasswordHistoryEntity> byId = passHistoryRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), PasswordHistoryDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PostalDistrictService.getById()...");
        }
    }

    public Page<PasswordHistoryDto> findByCri(PasswordHistoryDto dto) throws BusinessServiceException {
        try {
            log.info("Begin PasswordHistoryService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            log.info("pageRequest Complete...");
            Page<PasswordHistoryEntity> all =
                    passHistoryRepository.findAll(getSpecification(dto), pageRequest);
            log.info("all Complete...");
            Page<PasswordHistoryDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR PasswordHistoryService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End PasswordHistoryService.findByCri()...");
        }
    }
    @VisibleForTesting
    protected Specification<PasswordHistoryEntity> getSpecification(PasswordHistoryDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getPasswordHistory() != null){
                predicates.add(cb.equal(root.get("passwordHistoryId"), filter.getPasswordHistory()));
            }

            if (filter.getPasswordHistory() != null){
                predicates.add(cb.like(
                        cb.lower(root.get("passwordHistory")), "%" + filter.getPasswordHistory().toLowerCase() + "%"));
            }

            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus().trim()));
            }else {
                predicates.add(cb.equal(root.get("status"), "A"));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("passwordHistory")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("status")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    protected Page<PasswordHistoryDto> convertPageEntityToPageDto(Page<PasswordHistoryEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<PasswordHistoryDto> map =
                source.map(
                        new Function<PasswordHistoryEntity, PasswordHistoryDto>() {
                            @Override
                            public PasswordHistoryDto apply(PasswordHistoryEntity entity) {
                                PasswordHistoryDto dto = modelMapper.map(entity, PasswordHistoryDto.class);
                                return dto;
                            }
                        });
        return map;
    }

}
