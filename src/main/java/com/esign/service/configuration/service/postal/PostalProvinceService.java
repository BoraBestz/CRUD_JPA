package com.esign.service.configuration.service.postal;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.postal.PostalProvinceDto;
import com.esign.service.configuration.entity.postal.PostalProvinceEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.postal.PostalProvinceRepository;
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
public class PostalProvinceService {
  private EntityManager entityManager;
  private ModelMapper modelMapper;
  private OauthUser oauthUser;
  private PostalProvinceRepository postalProvinceRepository;

  @Autowired
  public void setPostalProvinceService(
      EntityManager entityManager,
      ModelMapper modelMapper,
      OauthUser oauthUser,
      PostalProvinceRepository postalProvinceRepository) {
    this.entityManager = entityManager;
    this.modelMapper = modelMapper;
    this.oauthUser = oauthUser;
    this.postalProvinceRepository = postalProvinceRepository;
  }

  public Page<PostalProvinceDto> findByCri(PostalProvinceDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalProvinceService.findByCri()...");
      Pageable pageRequest =
          PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
      Page<PostalProvinceEntity> all =
          postalProvinceRepository.findAll(getSpecification(dto), pageRequest);
      Page<PostalProvinceDto> dtos = convertPageEntityToPageDto(all);
      return dtos;
    } catch (Exception ex) {
      log.error("ERROR PostalProvinceService.findByCri()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalProvinceService.findByCri()...");
    }
  }

  public PostalProvinceDto getById(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalProvinceService.getById()...");
      Optional<PostalProvinceEntity> byId = postalProvinceRepository.findById(id);
      return byId.isPresent() ? modelMapper.map(byId.get(), PostalProvinceDto.class) : null;
    } catch (Exception ex) {
      log.error("ERROR PostalProvinceService.getById()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalProvinceService.getById()...");
    }
  }

  public Integer checkDuplicateCode(PostalProvinceDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalProvinceService.checkDuplicateAbbr()...");
      List<PostalProvinceEntity> rs =
          postalProvinceRepository.findAllByNameTh(dto.getNameTh());
      if (rs != null && rs.size() > 0) {
        return 1;
      }
      return 0;
    } catch (Exception ex) {
      log.error("ERROR PostalProvinceService.checkDuplicateAbbr()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalProvinceService.checkDuplicateAbbr()...");
    }
  }

  public PostalProvinceEntity save(PostalProvinceDto dto) throws BusinessServiceException {
    log.info("Begin PostalProvinceService.save()...");
    if (modelMapper.getConfiguration() != null)
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    PostalProvinceEntity entity = modelMapper.map(dto, PostalProvinceEntity.class);
    log.info("End PostalProvinceService.save()...");
    return postalProvinceRepository.save(entity);
  }

  @VisibleForTesting
  protected Specification<PostalProvinceEntity> getSpecification(PostalProvinceDto filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.getId() != null) {
        predicates.add(cb.equal(root.get("id"), filter.getId()));
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

  protected Page<PostalProvinceDto> convertPageEntityToPageDto(Page<PostalProvinceEntity> source) {
    if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
    Page<PostalProvinceDto> map =
        source.map(
            new Function<PostalProvinceEntity, PostalProvinceDto>() {
              @Override
              public PostalProvinceDto apply(PostalProvinceEntity entity) {
                PostalProvinceDto dto = modelMapper.map(entity, PostalProvinceDto.class);
                return dto;
              }
            });
    return map;
  }
}
