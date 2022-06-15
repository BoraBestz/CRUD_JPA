package com.esign.service.configuration.service.postal;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.postal.PostalDistrictDto;
import com.esign.service.configuration.entity.postal.PostalDistrictEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.postal.PostalDistrictRepository;
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
public class PostalDistrictService {
  private EntityManager entityManager;
  private ModelMapper modelMapper;
  private OauthUser oauthUser;
  private PostalDistrictRepository postalDistrictRepository;

  @Autowired
  public void setPostalDistrictService(
      EntityManager entityManager,
      ModelMapper modelMapper,
      OauthUser oauthUser,
      PostalDistrictRepository postalDistrictRepository) {
    this.entityManager = entityManager;
    this.modelMapper = modelMapper;
    this.oauthUser = oauthUser;
    this.postalDistrictRepository = postalDistrictRepository;
  }

  public Page<PostalDistrictDto> findByCri(PostalDistrictDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.findByCri()...");
      Pageable pageRequest =
          PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
      Page<PostalDistrictEntity> all =
          postalDistrictRepository.findAll(getSpecification(dto), pageRequest);
      Page<PostalDistrictDto> dtos = convertPageEntityToPageDto(all);
      return dtos;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.findByCri()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.findByCri()...");
    }
  }

  public PostalDistrictDto getById(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.getById()...");
      Optional<PostalDistrictEntity> byId = postalDistrictRepository.findById(id);
      return byId.isPresent() ? modelMapper.map(byId.get(), PostalDistrictDto.class) : null;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.getById()...");
    }
  }

  public Integer checkDuplicateCode(PostalDistrictDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
      List<PostalDistrictEntity> rs =
          postalDistrictRepository.findAllById(dto.getProvinceId());
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

  public PostalDistrictEntity save(PostalDistrictDto dto) throws BusinessServiceException {
    log.info("Begin PostalDistrictService.save()...");
    if (modelMapper.getConfiguration() != null)
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    PostalDistrictEntity entity = modelMapper.map(dto, PostalDistrictEntity.class);
    log.info("End PostalDistrictService.save()...");
    return postalDistrictRepository.save(entity);
  }

  @VisibleForTesting
  protected Specification<PostalDistrictEntity> getSpecification(PostalDistrictDto filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.getId() != null) {
        predicates.add(cb.equal(root.get("id"), filter.getId()));
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

  protected Page<PostalDistrictDto> convertPageEntityToPageDto(Page<PostalDistrictEntity> source) {
    if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
    Page<PostalDistrictDto> map =
        source.map(
            new Function<PostalDistrictEntity, PostalDistrictDto>() {
              @Override
              public PostalDistrictDto apply(PostalDistrictEntity entity) {
                PostalDistrictDto dto = modelMapper.map(entity, PostalDistrictDto.class);
                return dto;
              }
            });
    return map;
  }
}
