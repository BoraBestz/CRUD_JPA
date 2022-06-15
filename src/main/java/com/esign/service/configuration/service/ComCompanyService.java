package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.ComCompanyDto;
import com.esign.service.configuration.dto.address.CitySubDivisionDto;
import com.esign.service.configuration.entity.ComCompanyEntity;
import com.esign.service.configuration.entity.address.CitySubDivisionEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.ComBranchRepository;
import com.esign.service.configuration.repository.ComCompanyRepository;
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
import lombok.SneakyThrows;
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
public class ComCompanyService {
  private EntityManager entityManager;
  private ModelMapper modelMapper;
  private OauthUser oauthUser;
  private ComCompanyRepository companyRepository;
  private ComBranchService comBranchService;

  @Autowired
  public void setComCompanyService(
      EntityManager entityManager,
      ModelMapper modelMapper,
      OauthUser oauthUser,
      ComCompanyRepository companyRepository,
      ComBranchService comBranchService) {
    this.entityManager = entityManager;
    this.modelMapper = modelMapper;
    this.oauthUser = oauthUser;
    this.companyRepository = companyRepository;
    this.comBranchService = comBranchService;
  }

  public Page<ComCompanyDto> findByCri(ComCompanyDto dto) throws BusinessServiceException {
    try {
      log.info("Begin ComCompanyService.findByCri()...");
      Pageable pageRequest =
          PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
      Page<ComCompanyEntity> all =
          companyRepository.findAll(getSpecification(dto), pageRequest);
      if("true".equalsIgnoreCase(dto.getShowBranch())){
        Page<ComCompanyDto> dtos = convertPageEntityToPageDtoWithBranch(all);
        return dtos;
      }else{
        Page<ComCompanyDto> dtos = convertPageEntityToPageDto(all);
        return dtos;
      }
    } catch (Exception ex) {
      log.error("ERROR ComCompanyService.findByCri()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End ComCompanyService.findByCri()...");
    }
  }

  public ComCompanyDto getById(long id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.getById()...");
      Optional<ComCompanyEntity> byId = companyRepository.findById(id);
      return byId.isPresent() ? modelMapper.map(byId.get(), ComCompanyDto.class) : null;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.getById()...");
    }
  }

  public Integer checkDuplicateCode(ComCompanyDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
      List<ComCompanyEntity> rs =
          companyRepository.findAllByNameAndStatus(dto.getName(), 1);
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

  public ComCompanyEntity save(ComCompanyDto dto) throws BusinessServiceException {
    log.info("Begin ComCompanyService.save()...");
    if (modelMapper.getConfiguration() != null)
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    ComCompanyEntity entity = modelMapper.map(dto, ComCompanyEntity.class);
    log.info("End ComCompanyService.save()...");
    return companyRepository.save(entity);
  }

  @VisibleForTesting
  protected Specification<ComCompanyEntity> getSpecification(ComCompanyDto filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.getCompanyId() > 0L) {
        predicates.add(cb.equal(root.get("companyId"), filter.getCompanyId()));
      }

      if (filter.getParentCompanyId() != null) {
        predicates.add(cb.equal(root.get("parentCompanyId"), filter.getParentCompanyId()));
      }

      if (filter.getGlobalId() != null) {
        predicates.add(cb.equal(root.get("globalId"), filter.getGlobalId()));
      }

      if (filter.getName() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
      }

      if (filter.getTaxId() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("taxId")), "%" + filter.getTaxId().toLowerCase() + "%"));
      }

      if (filter.getStatus() != null) {
        predicates.add(cb.equal(root.get("status"), filter.getStatus()));
      }else {
        predicates.add(cb.equal(root.get("status"), 1));
      }

      if (filter.getFullSearch() != null) {
        Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        predicates = new ArrayList<>();
        predicates.add(
            cb.like(
                cb.lower(root.get("name")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        predicates.add(
            cb.like(
                cb.lower(root.get("taxId")), "%" + filter.getFullSearch().toLowerCase() + "%"));
        Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        return cb.and(p1, p2);
      }

      return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    };
  }

  protected Page<ComCompanyDto> convertPageEntityToPageDtoWithBranch(Page<ComCompanyEntity> source) {
    if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
    Page<ComCompanyDto> map =
        source.map(
            new Function<ComCompanyEntity, ComCompanyDto>() {
              @SneakyThrows
              @Override
              public ComCompanyDto apply(ComCompanyEntity entity) {
                ComCompanyDto dto = modelMapper.map(entity, ComCompanyDto.class);
                dto.setBranch(comBranchService.listAllByComId(dto.getCompanyId()));
                return dto;
              }
            });
    return map;
  }

  protected Page<ComCompanyDto> convertPageEntityToPageDto(Page<ComCompanyEntity> source) {
    if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
    Page<ComCompanyDto> map =
        source.map(
            new Function<ComCompanyEntity, ComCompanyDto>() {
              @Override
              public ComCompanyDto apply(ComCompanyEntity entity) {
                ComCompanyDto dto = modelMapper.map(entity, ComCompanyDto.class);
                return dto;
              }
            });
    return map;
  }

  public List<ComCompanyDto> findByComCompanyId(int id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.findByCityId()...");
      List<ComCompanyEntity> citySubDivisionEntities = companyRepository.findAllByCityIdAndStatus(id, 1);

      Type listTpye = new TypeToken<List<ComCompanyDto>>() {}.getType();
      List<ComCompanyDto> map = modelMapper.map(citySubDivisionEntities, listTpye);

      return map;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.findByCityId()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.findByCityId()...");
    }
  }

}
