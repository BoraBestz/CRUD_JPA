package com.esign.service.configuration.service.address;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.address.CountryDto;
import com.esign.service.configuration.entity.address.CountryEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.address.CountryRepository;
import com.esign.service.configuration.utils.PageUtils;
import com.google.common.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CountryService {
  private EntityManager entityManager;
  private ModelMapper modelMapper;
  private OauthUser oauthUser;
  private CountryRepository countryRepository;

  @Autowired
  public void setCountryService(
      EntityManager entityManager,
      ModelMapper modelMapper,
      OauthUser oauthUser,
      CountryRepository countryRepository) {
    this.entityManager = entityManager;
    this.modelMapper = modelMapper;
    this.oauthUser = oauthUser;
    this.countryRepository = countryRepository;
  }

  public Page<CountryDto> findByCri(CountryDto dto) throws BusinessServiceException {
    try {
      log.info("Begin CountryService.findByCri()...");
      Pageable pageRequest =
          PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
      Page<CountryEntity> all =
          countryRepository.findAll(getSpecification(dto), pageRequest);
      Page<CountryDto> dtos = convertPageEntityToPageDto(all);
      return dtos;
    } catch (Exception ex) {
      log.error("ERROR CountryService.findByCri()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End CountryService.findByCri()...");
    }
  }

  public CountryDto getById(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.getById()...");
      Optional<CountryEntity> byId = countryRepository.findById(id);
      return byId.isPresent() ? modelMapper.map(byId.get(), CountryDto.class) : null;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.getById()...");
    }
  }

  public Integer checkDuplicateCode(CountryDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
      List<CountryEntity> rs =
          countryRepository.findAllByCountryCodeAndStatus(dto.getCountryCode(), "A");
      if (rs != null && rs.size() > 0) {
        return 1;
      }
      return 0;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.checkDuplicateAbbr()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.checkDuplicateAbbr()...");
    }
  }

  public CountryEntity save(CountryDto dto) throws BusinessServiceException {
    log.info("Begin CountryService.save()...");
    if (modelMapper.getConfiguration() != null)
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    CountryEntity entity = modelMapper.map(dto, CountryEntity.class);
    log.info("End CountryService.save()...");
    return countryRepository.save(entity);
  }

  @VisibleForTesting
  protected Specification<CountryEntity> getSpecification(CountryDto filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.getCountryId() != null) {
        predicates.add(cb.equal(root.get("countryId"), filter.getCountryId()));
      }

      if (filter.getCountryCode() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("countryCode")), "%" + filter.getCountryCode().toLowerCase() + "%"));
      }

      if (filter.getCountryNameTh() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("countryNameTh")), "%" + filter.getCountryNameTh().toLowerCase() + "%"));
      }

      if (filter.getCountryNameEn() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("countryNameEn")), "%" + filter.getCountryNameEn().toLowerCase() + "%"));
      }

      predicates.add(cb.equal(root.get("status"), filter.getStatus()));

      if (filter.getFullSearch() != null) {
        Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        predicates = new ArrayList<>();
        predicates.add(
            cb.like(
                cb.lower(root.get("countryCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        predicates.add(
            cb.like(
                cb.lower(root.get("countryNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        predicates.add(
            cb.like(
                cb.lower(root.get("countryNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        return cb.and(p1, p2);
      }

      return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    };
  }

  protected Page<CountryDto> convertPageEntityToPageDto(Page<CountryEntity> source) {
    if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
    Page<CountryDto> map =
        source.map(
            new Function<CountryEntity, CountryDto>() {
              @Override
              public CountryDto apply(CountryEntity entity) {
                CountryDto dto = modelMapper.map(entity, CountryDto.class);
                return dto;
              }
            });
    return map;
  }
}
