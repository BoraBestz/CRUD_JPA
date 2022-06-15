package com.esign.service.configuration.service.postal;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.postal.PostalCodeDto;
import com.esign.service.configuration.dto.postal.PostalSubDistrictDto;
import com.esign.service.configuration.entity.postal.PostalCodeEntity;
import com.esign.service.configuration.entity.postal.PostalSubDistrictEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.postal.PostalSubDistrictRepository;
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
public class PostalSubDistrictService {
  private EntityManager entityManager;
  private ModelMapper modelMapper;
  private OauthUser oauthUser;
  private PostalSubDistrictRepository postalSubDistrictRepository;

  @Autowired
  public void setPostalSubDistrictService(
      EntityManager entityManager,
      ModelMapper modelMapper,
      OauthUser oauthUser,
      PostalSubDistrictRepository postalSubDistrictRepository) {
    this.entityManager = entityManager;
    this.modelMapper = modelMapper;
    this.oauthUser = oauthUser;
    this.postalSubDistrictRepository = postalSubDistrictRepository;
  }

  public Page<PostalSubDistrictDto> findByCri(PostalSubDistrictDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalSubDistrictService.findByCri()...");
      Pageable pageRequest =
          PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
      Page<PostalSubDistrictEntity> all =
          postalSubDistrictRepository.findAll(getSpecification(dto), pageRequest);
      Page<PostalSubDistrictDto> dtos = convertPageEntityToPageDto(all);
      return dtos;
    } catch (Exception ex) {
      log.error("ERROR PostalSubDistrictService.findByCri()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalSubDistrictService.findByCri()...");
    }
  }

  public PostalSubDistrictDto getById(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalSubDistrictService.getById()...");
      Optional<PostalSubDistrictEntity> byId = postalSubDistrictRepository.findById(id);
      return byId.isPresent() ? modelMapper.map(byId.get(), PostalSubDistrictDto.class) : null;
    } catch (Exception ex) {
      log.error("ERROR PostalSubDistrictService.getById()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalSubDistrictService.getById()...");
    }
  }

  public Integer checkDuplicateCode(PostalSubDistrictDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalSubDistrictService.checkDuplicateAbbr()...");
      List<PostalSubDistrictEntity> rs =
          postalSubDistrictRepository.findAllByDistrictIdAndNameTh(dto.getDistrictId() ,dto.getNameTh());
      if (rs != null && rs.size() > 0) {
        return 1;
      }
      return 0;
    } catch (Exception ex) {
      log.error("ERROR PostalSubDistrictService.checkDuplicateAbbr()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalSubDistrictService.checkDuplicateAbbr()...");
    }
  }

  public PostalSubDistrictEntity save(PostalSubDistrictDto dto) throws BusinessServiceException {
    log.info("Begin PostalSubDistrictService.save()...");
    if (modelMapper.getConfiguration() != null)
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    PostalSubDistrictEntity entity = modelMapper.map(dto, PostalSubDistrictEntity.class);
    log.info("End PostalSubDistrictService.save()...");
    return postalSubDistrictRepository.save(entity);
  }

  @VisibleForTesting
  protected Specification<PostalSubDistrictEntity> getSpecification(PostalSubDistrictDto filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.getId() != null) {
        predicates.add(cb.equal(root.get("id"), filter.getId()));
      }

      if (filter.getDistrictId() != null) {
        predicates.add(cb.equal(root.get("districtId"), filter.getDistrictId()));
      }

      if (filter.getProvinceId() != null) {
        predicates.add(cb.equal(root.get("provinceId"), filter.getProvinceId()));
      }

      if (filter.getGeoId() != null) {
        predicates.add(cb.equal(root.get("geoId"), filter.getGeoId()));
      }

      if (filter.getCode() != null) {
        predicates.add(cb.equal(root.get("code"), filter.getCode()));
      }

      if (filter.getNameTh() != null) {
        predicates.add(
            cb.like(
                cb.lower(root.get("nameTh")), "%" + filter.getNameTh().toLowerCase() + "%"));
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
                cb.lower(root.get("nameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        return cb.and(p1, p2);
      }

      return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    };
  }

  protected Page<PostalSubDistrictDto> convertPageEntityToPageDto(Page<PostalSubDistrictEntity> source) {
    if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
    Page<PostalSubDistrictDto> map =
        source.map(
            new Function<PostalSubDistrictEntity, PostalSubDistrictDto>() {
              @Override
              public PostalSubDistrictDto apply(PostalSubDistrictEntity entity) {
                PostalSubDistrictDto dto = modelMapper.map(entity, PostalSubDistrictDto.class);
                return dto;
              }
            });
    return map;
  }

  public List<PostalSubDistrictDto> findByProvinceId(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.findByProvinceId()...");
      List<PostalSubDistrictEntity> postalSubDistrictEntities = postalSubDistrictRepository.findAllByProvinceId(id);

      Type listTpye = new TypeToken<List<PostalSubDistrictDto>>() {}.getType();
      List<PostalSubDistrictDto> map = modelMapper.map(postalSubDistrictEntities, listTpye);

      return map;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.findByProvinceId()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.findByProvinceId()...");
    }
  }

  public List<PostalSubDistrictDto> findByDistrictId(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.findByDistrictId()...");
      List<PostalSubDistrictEntity> postalSubDistrictEntities = postalSubDistrictRepository.findAllByDistrictId(id);

      Type listTpye = new TypeToken<List<PostalSubDistrictDto>>() {}.getType();
      List<PostalSubDistrictDto> map = modelMapper.map(postalSubDistrictEntities, listTpye);

      return map;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.findByDistrictId()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.findByDistrictId()...");
    }
  }

}
