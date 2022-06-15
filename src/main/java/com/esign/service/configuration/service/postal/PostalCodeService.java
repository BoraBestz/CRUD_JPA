package com.esign.service.configuration.service.postal;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.address.CitySubDivisionDto;
import com.esign.service.configuration.dto.postal.PostalCodeDto;
import com.esign.service.configuration.dto.postal.PostalSubDistrictDto;
import com.esign.service.configuration.entity.address.CitySubDivisionEntity;
import com.esign.service.configuration.entity.postal.PostalCodeEntity;
import com.esign.service.configuration.entity.postal.PostalSubDistrictEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.postal.PostalCodeRepository;
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
public class PostalCodeService {
  private EntityManager entityManager;
  private ModelMapper modelMapper;
  private OauthUser oauthUser;
  private PostalCodeRepository postalCodeRepository;

  @Autowired
  public void setPostalCodeService(
      EntityManager entityManager,
      ModelMapper modelMapper,
      OauthUser oauthUser,
      PostalCodeRepository postalCodeRepository) {
    this.entityManager = entityManager;
    this.modelMapper = modelMapper;
    this.oauthUser = oauthUser;
    this.postalCodeRepository = postalCodeRepository;
  }

  public Page<PostalCodeDto> findByCri(PostalCodeDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalCodeService.findByCri()...");
      Pageable pageRequest =
          PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
      Page<PostalCodeEntity> all =
          postalCodeRepository.findAll(getSpecification(dto), pageRequest);
      Page<PostalCodeDto> dtos = convertPageEntityToPageDto(all);
      return dtos;
    } catch (Exception ex) {
      log.error("ERROR PostalCodeService.findByCri()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalCodeService.findByCri()...");
    }
  }

  public PostalCodeDto getById(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.getById()...");
      Optional<PostalCodeEntity> byId = postalCodeRepository.findById(id);
      return byId.isPresent() ? modelMapper.map(byId.get(), PostalCodeDto.class) : null;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.getById()...");
    }
  }

  public Integer checkDuplicateCode(PostalCodeDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
      List<PostalCodeEntity> rs =
          postalCodeRepository.findAllBySubDistrictIdAndPostalCode(dto.getSubDistrictId() ,dto.getPostalCode());
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

  public PostalCodeEntity save(PostalCodeDto dto) throws BusinessServiceException {
    log.info("Begin PostalCodeService.save()...");
    if (modelMapper.getConfiguration() != null)
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    PostalCodeEntity entity = modelMapper.map(dto, PostalCodeEntity.class);
    log.info("End PostalCodeService.save()...");
    return postalCodeRepository.save(entity);
  }

  @VisibleForTesting
  protected Specification<PostalCodeEntity> getSpecification(PostalCodeDto filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.getId() != null) {
        predicates.add(cb.equal(root.get("id"), filter.getId()));
      }

      if (filter.getSubDistrictId() != null) {
        predicates.add(cb.equal(root.get("subDistrictId"), filter.getSubDistrictId()));
      }

      if (filter.getDistrictId() != null) {
        predicates.add(cb.equal(root.get("districtId"), filter.getDistrictId()));
      }

      if (filter.getProvinceId() != null) {
        predicates.add(cb.equal(root.get("provinceId"), filter.getProvinceId()));
      }

      if (filter.getPostalCode() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("postalCode")), "%" + filter.getPostalCode().toLowerCase() + "%"));
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
                cb.lower(root.get("postalCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        return cb.and(p1, p2);
      }

      return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    };
  }

  protected Page<PostalCodeDto> convertPageEntityToPageDto(Page<PostalCodeEntity> source) {
    if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
    Page<PostalCodeDto> map =
        source.map(
            new Function<PostalCodeEntity, PostalCodeDto>() {
              @Override
              public PostalCodeDto apply(PostalCodeEntity entity) {
                PostalCodeDto dto = modelMapper.map(entity, PostalCodeDto.class);
                return dto;
              }
            });
    return map;
  }

  public List<PostalCodeDto> findByDistrictId(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.findByCityId()...");
      List<PostalCodeEntity> postalCodeEntities = postalCodeRepository.findAllByDistrictId(id);

      Type listTpye = new TypeToken<List<PostalCodeDto>>() {}.getType();
      List<PostalCodeDto> map = modelMapper.map(postalCodeEntities, listTpye);

      return map;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.findByCityId()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.findByCityId()...");
    }
  }

  public List<PostalCodeDto> findByProvinceId(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.findByProvinceId()...");
      List<PostalCodeEntity> postalCodeEntities = postalCodeRepository.findAllByProvinceId(id);

      Type listTpye = new TypeToken<List<PostalCodeDto>>() {}.getType();
      List<PostalCodeDto> map = modelMapper.map(postalCodeEntities, listTpye);

      return map;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.findByProvinceId()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.findByProvinceId()...");
    }
  }

  public List<PostalCodeDto> findBySubDistrictId(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.findBySubDistrictId()...");
      List<PostalCodeEntity> postalCodeEntities = postalCodeRepository.findAllBySubDistrictId(id);

      Type listTpye = new TypeToken<List<PostalCodeDto>>() {}.getType();
      List<PostalCodeDto> map = modelMapper.map(postalCodeEntities, listTpye);

      return map;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.findBySubDistrictId()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.findBySubDistrictId()...");
    }
  }

}
