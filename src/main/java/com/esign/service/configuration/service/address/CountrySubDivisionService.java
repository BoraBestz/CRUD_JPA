package com.esign.service.configuration.service.address;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.address.CitySubDivisionDto;
import com.esign.service.configuration.dto.address.CountrySubDivisionDto;
import com.esign.service.configuration.dto.postal.PostalCodeDto;
import com.esign.service.configuration.entity.address.CitySubDivisionEntity;
import com.esign.service.configuration.entity.address.CountrySubDivisionEntity;
import com.esign.service.configuration.entity.postal.PostalCodeEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.address.CountrySubDivisionRepository;
import com.esign.service.configuration.utils.PageUtils;
import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;

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

@Service
@Slf4j
public class CountrySubDivisionService {
  private EntityManager entityManager;
  private ModelMapper modelMapper;
  private OauthUser oauthUser;
  private CountrySubDivisionRepository countrySubDivisionRepository;

  @Autowired
  public void setCountrySubDivisionService(
      EntityManager entityManager,
      ModelMapper modelMapper,
      OauthUser oauthUser,
      CountrySubDivisionRepository countrySubDivisionRepository) {
    this.entityManager = entityManager;
    this.modelMapper = modelMapper;
    this.oauthUser = oauthUser;
    this.countrySubDivisionRepository = countrySubDivisionRepository;
  }

  public Page<CountrySubDivisionDto> findByCri(CountrySubDivisionDto dto) throws BusinessServiceException {
    try {
      log.info("Begin CountrySubDivisionService.findByCri()...");
      Pageable pageRequest =
          PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
      Page<CountrySubDivisionEntity> all =
          countrySubDivisionRepository.findAll(getSpecification(dto), pageRequest);
      Page<CountrySubDivisionDto> dtos = convertPageEntityToPageDto(all);
      return dtos;
    } catch (Exception ex) {
      log.error("ERROR CountrySubDivisionService.findByCri()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End CountrySubDivisionService.findByCri()...");
    }
  }

  public CountrySubDivisionDto getById(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.getById()...");
      Optional<CountrySubDivisionEntity> byId = countrySubDivisionRepository.findById(id);
      return byId.isPresent() ? modelMapper.map(byId.get(), CountrySubDivisionDto.class) : null;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.getById()...");
    }
  }

  public Integer checkDuplicateCode(CountrySubDivisionDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
      List<CountrySubDivisionEntity> rs =
          countrySubDivisionRepository.findAllByCountryIdAndCountrySubDivisionCodeAndStatus(dto.getCountryId(),dto.getCountrySubDivisionCode(), "A");
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

  public CountrySubDivisionEntity save(CountrySubDivisionDto dto) throws BusinessServiceException {
    log.info("Begin CountrySubDivisionService.save()...");
    if (modelMapper.getConfiguration() != null)
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    CountrySubDivisionEntity entity = modelMapper.map(dto, CountrySubDivisionEntity.class);
    log.info("End CountrySubDivisionService.save()...");
    return countrySubDivisionRepository.save(entity);
  }

  @VisibleForTesting
  protected Specification<CountrySubDivisionEntity> getSpecification(CountrySubDivisionDto filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.getCountryId() != null) {
        predicates.add(cb.equal(root.get("countryId"), filter.getCountryId()));
      }

      if (filter.getCountrySubDivisionId() != null) {
        predicates.add(cb.equal(root.get("countrySubDivisionId"), filter.getCountrySubDivisionId()));
      }

      if (filter.getCountrySubDivisionCode() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("countrySubDivisionCode")), "%" + filter.getCountrySubDivisionCode().toLowerCase() + "%"));
      }

      if (filter.getCountrySubDivisionNameTh() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("countrySubDivisionNameTh")), "%" + filter.getCountrySubDivisionNameTh().toLowerCase() + "%"));
      }

      if (filter.getCountrySubDivisionNameEn() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("countrySubDivisionNameEn")), "%" + filter.getCountrySubDivisionNameEn().toLowerCase() + "%"));
      }

      predicates.add(cb.equal(root.get("status"), filter.getStatus()));

      if (filter.getFullSearch() != null) {
        Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        predicates = new ArrayList<>();
        predicates.add(
            cb.like(
                cb.lower(root.get("countrySubDivisionCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        predicates.add(
            cb.like(
                cb.lower(root.get("countrySubDivisionNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        predicates.add(
            cb.like(
                cb.lower(root.get("countrySubDivisionNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        return cb.and(p1, p2);
      }

      return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    };
  }

  protected Page<CountrySubDivisionDto> convertPageEntityToPageDto(Page<CountrySubDivisionEntity> source) {
    if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
    Page<CountrySubDivisionDto> map =
        source.map(
            new Function<CountrySubDivisionEntity, CountrySubDivisionDto>() {
              @Override
              public CountrySubDivisionDto apply(CountrySubDivisionEntity entity) {
                CountrySubDivisionDto dto = modelMapper.map(entity, CountrySubDivisionDto.class);
                return dto;
              }
            });
    return map;
  }

  public List<CountrySubDivisionDto> findByCountryId(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.findByCityId()...");
      List<CountrySubDivisionEntity> countrySubDivisionEntities = countrySubDivisionRepository.findAllByCountryIdAndStatus(id, 1);

      Type listTpye = new TypeToken<List<CountrySubDivisionDto>>() {}.getType();
      List<CountrySubDivisionDto> map = modelMapper.map(countrySubDivisionEntities, listTpye);

      return map;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.findByCityId()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.findByCityId()...");
    }
  }

  public List<CountrySubDivisionDto> findByCountrySubDivisionId(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.findByProvinceId()...");
      List<CountrySubDivisionEntity> postalCodeEntities = countrySubDivisionRepository.findAllByCountrySubDivisionId(id);

      Type listTpye = new TypeToken<List<CountrySubDivisionDto>>() {}.getType();
      List<CountrySubDivisionDto> map = modelMapper.map(postalCodeEntities, listTpye);

      return map;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.findByProvinceId()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.findByProvinceId()...");
    }
  }

}
