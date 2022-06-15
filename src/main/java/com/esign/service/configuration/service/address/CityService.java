package com.esign.service.configuration.service.address;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.address.CityDto;
import com.esign.service.configuration.entity.address.CityEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.address.CityRepository;
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
public class CityService {
  private EntityManager entityManager;
  private ModelMapper modelMapper;
  private OauthUser oauthUser;
  private CityRepository cityRepository;

  @Autowired
  public void setCityService(
      EntityManager entityManager,
      ModelMapper modelMapper,
      OauthUser oauthUser,
      CityRepository cityRepository) {
    this.entityManager = entityManager;
    this.modelMapper = modelMapper;
    this.oauthUser = oauthUser;
    this.cityRepository = cityRepository;
  }

  public Page<CityDto> findByCri(CityDto dto) throws BusinessServiceException {
    try {
      log.info("Begin CityService.findByCri()...");
      Pageable pageRequest =
          PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
      Page<CityEntity> all =
          cityRepository.findAll(getSpecification(dto), pageRequest);
      Page<CityDto> dtos = convertPageEntityToPageDto(all);
      return dtos;
    } catch (Exception ex) {
      log.error("ERROR CityService.findByCri()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End CityService.findByCri()...");
    }
  }

  public CityDto getById(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.getById()...");
      Optional<CityEntity> byId = cityRepository.findById(id);
      return byId.isPresent() ? modelMapper.map(byId.get(), CityDto.class) : null;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.getById()...");
    }
  }

  public Integer checkDuplicateCode(CityDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
      List<CityEntity> rs =
          cityRepository.findAllByCountrySubDivisionIdAndCityCodeAndStatus(dto.getCountrySubDivisionId(), dto.getCityCode(), "A");
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

  public CityEntity save(CityDto dto) throws BusinessServiceException {
    log.info("Begin CityService.save()...");
    if (modelMapper.getConfiguration() != null)
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    CityEntity entity = modelMapper.map(dto, CityEntity.class);
    log.info("End CityService.save()...");
    return cityRepository.save(entity);
  }

  @VisibleForTesting
  protected Specification<CityEntity> getSpecification(CityDto filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.getCityId() != null) {
        predicates.add(cb.equal(root.get("cityId"), filter.getCityId()));
      }

      if (filter.getCountrySubDivisionId() != null) {
        predicates.add(cb.equal(root.get("countrySubDivisionId"), filter.getCountrySubDivisionId()));
      }

      if (filter.getCityCode() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("cityCode")), "%" + filter.getCityCode().toLowerCase() + "%"));
      }

      if (filter.getCityNameTh() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("cityNameTh")), "%" + filter.getCityNameTh().toLowerCase() + "%"));
      }

      if (filter.getCityNameEn() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("cityNameEn")), "%" + filter.getCityNameEn().toLowerCase() + "%"));
      }

      predicates.add(cb.equal(root.get("status"), filter.getStatus()));

      if (filter.getFullSearch() != null) {
        Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        predicates = new ArrayList<>();
        predicates.add(
            cb.like(
                cb.lower(root.get("cityCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        predicates.add(
            cb.like(
                cb.lower(root.get("cityNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        predicates.add(
            cb.like(
                cb.lower(root.get("cityNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        return cb.and(p1, p2);
      }

      return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    };
  }

  protected Page<CityDto> convertPageEntityToPageDto(Page<CityEntity> source) {
    if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
    Page<CityDto> map =
        source.map(
            new Function<CityEntity, CityDto>() {
              @Override
              public CityDto apply(CityEntity entity) {
                CityDto dto = modelMapper.map(entity, CityDto.class);
                return dto;
              }
            });
    return map;
  }
}
