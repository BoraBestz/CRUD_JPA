package com.esign.service.configuration.service.address;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.address.CitySubDivisionDto;
import com.esign.service.configuration.entity.address.CitySubDivisionEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.address.CitySubDivisionRepository;
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
public class CitySubDivisionService {
  private EntityManager entityManager;
  private ModelMapper modelMapper;
  private OauthUser oauthUser;
  private CitySubDivisionRepository citySubDivisionRepository;

  @Autowired
  public void setCitySubDivisionService(
      EntityManager entityManager,
      ModelMapper modelMapper,
      OauthUser oauthUser,
      CitySubDivisionRepository citySubDivisionRepository) {
    this.entityManager = entityManager;
    this.modelMapper = modelMapper;
    this.oauthUser = oauthUser;
    this.citySubDivisionRepository = citySubDivisionRepository;
  }

  public Page<CitySubDivisionDto> findByCri(CitySubDivisionDto dto) throws BusinessServiceException {
    try {
      log.info("Begin CitySubDivisionService.findByCri()...");
      Pageable pageRequest =
          PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
      Page<CitySubDivisionEntity> all =
          citySubDivisionRepository.findAll(getSpecification(dto), pageRequest);
      Page<CitySubDivisionDto> dtos = convertPageEntityToPageDto(all);
      return dtos;
    } catch (Exception ex) {
      log.error("ERROR CitySubDivisionService.findByCri()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End CitySubDivisionService.findByCri()...");
    }
  }

  public CitySubDivisionDto getById(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.getById()...");
      Optional<CitySubDivisionEntity> byId = citySubDivisionRepository.findById(id);
      return byId.isPresent() ? modelMapper.map(byId.get(), CitySubDivisionDto.class) : null;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.getById()...");
    }
  }

  public Integer checkDuplicateCode(CitySubDivisionDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
      List<CitySubDivisionEntity> rs =
          citySubDivisionRepository.findAllByCityIdAndCitySubDivisionCodeAndStatus(dto.getCityId(),dto.getCitySubDivisionCode(), 1);
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

  public CitySubDivisionEntity save(CitySubDivisionDto dto) throws BusinessServiceException {
    log.info("Begin CitySubDivisionService.save()...");
    if (modelMapper.getConfiguration() != null)
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    CitySubDivisionEntity entity = modelMapper.map(dto, CitySubDivisionEntity.class);
    log.info("End CitySubDivisionService.save()...");
    return citySubDivisionRepository.save(entity);
  }

  @VisibleForTesting
  protected Specification<CitySubDivisionEntity> getSpecification(CitySubDivisionDto filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.getCityId() != null) {
        predicates.add(cb.equal(root.get("cityId"), filter.getCityId()));
      }

      if (filter.getCitySubDivisionId() != null) {
        predicates.add(cb.equal(root.get("citySubDivisionId"), filter.getCitySubDivisionId()));
      }

      if (filter.getCitySubDivisionCode() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("citySubDivisionCode")), "%" + filter.getCitySubDivisionCode().toLowerCase() + "%"));
      }

      if (filter.getCitySubDivisionNameTh() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("citySubDivisionNameTh")), "%" + filter.getCitySubDivisionNameTh().toLowerCase() + "%"));
      }

      if (filter.getCitySubDivisionNameEn() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("citySubDivisionNameEn")), "%" + filter.getCitySubDivisionNameEn().toLowerCase() + "%"));
      }

      if (filter.getCitySubDivisionPostCode() != null) {
        predicates.add(cb.equal(root.get("citySubDivisionPostCode"), filter.getCitySubDivisionPostCode()));
      }

      predicates.add(cb.equal(root.get("status"), filter.getStatus()));

      if (filter.getFullSearch() != null) {
        Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        predicates = new ArrayList<>();
        predicates.add(
            cb.like(
                cb.lower(root.get("citySubDivisionCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        predicates.add(
            cb.like(
                cb.lower(root.get("citySubDivisionNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        predicates.add(
            cb.like(
                cb.lower(root.get("citySubDivisionNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        return cb.and(p1, p2);
      }

      return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    };
  }

  protected Page<CitySubDivisionDto> convertPageEntityToPageDto(Page<CitySubDivisionEntity> source) {
    if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
    Page<CitySubDivisionDto> map =
        source.map(
            new Function<CitySubDivisionEntity, CitySubDivisionDto>() {
              @Override
              public CitySubDivisionDto apply(CitySubDivisionEntity entity) {
                CitySubDivisionDto dto = modelMapper.map(entity, CitySubDivisionDto.class);
                return dto;
              }
            });
    return map;
  }

  public List<CitySubDivisionDto> findByCityId(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.findByCityId()...");
      List<CitySubDivisionEntity> citySubDivisionEntities = citySubDivisionRepository.findAllByCityIdAndStatus(id, 1);

      Type listTpye = new TypeToken<List<CitySubDivisionDto>>() {}.getType();
      List<CitySubDivisionDto> map = modelMapper.map(citySubDivisionEntities, listTpye);

      return map;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.findByCityId()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.findByCityId()...");
    }
  }
}
