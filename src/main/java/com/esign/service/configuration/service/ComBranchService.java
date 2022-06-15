package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.ComBranchDto;
import com.esign.service.configuration.dto.address.CitySubDivisionDto;
import com.esign.service.configuration.entity.ComBranchEntity;
import com.esign.service.configuration.entity.address.CitySubDivisionEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.ComBranchRepository;
import com.esign.service.configuration.utils.PageUtils;
import com.google.common.annotations.VisibleForTesting;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ComBranchService {
  private EntityManager entityManager;
  private ModelMapper modelMapper;
  private OauthUser oauthUser;
  private ComBranchRepository comBranchRepository;

  @Autowired
  public void setComBranchService(
      EntityManager entityManager,
      ModelMapper modelMapper,
      OauthUser oauthUser,
      ComBranchRepository comBranchRepository) {
    this.entityManager = entityManager;
    this.modelMapper = modelMapper;
    this.oauthUser = oauthUser;
    this.comBranchRepository = comBranchRepository;
  }

  public Page<ComBranchDto> findByCri(ComBranchDto dto) throws BusinessServiceException {
    try {
      log.info("Begin ComBranchService.findByCri()...");
      Pageable pageRequest =
          PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
      Page<ComBranchEntity> all =
          comBranchRepository.findAll(getSpecification(dto), pageRequest);
      Page<ComBranchDto> dtos = convertPageEntityToPageDto(all);
      return dtos;
    } catch (Exception ex) {
      log.error("ERROR ComBranchService.findByCri()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End ComBranchService.findByCri()...");
    }
  }

  public List<ComBranchDto> listAllByComId(long id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.listAllByComId()...");
      List<ComBranchEntity> allByCompanyIdAndStatus = comBranchRepository
          .findAllByCompanyIdAndStatus(id, 1);

      Type listTpye = new TypeToken<List<ComBranchDto>>() {}.getType();
      List<ComBranchDto> map = modelMapper.map(allByCompanyIdAndStatus, listTpye);
      return map;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.listAllByComId()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.listAllByComId()...");
    }
  }

  public ComBranchDto getById(long id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.getById()...");
      Optional<ComBranchEntity> byId = comBranchRepository.findById(id);
      return byId.isPresent() ? modelMapper.map(byId.get(), ComBranchDto.class) : null;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.getById()...");
    }
  }

  public Integer checkDuplicateCode(ComBranchDto dto) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.checkDuplicateAbbr()...");
      List<ComBranchEntity> rs =
          comBranchRepository.findAllByCompanyIdAndBranchCodeAndStatus(dto.getCompanyId(), dto.getBranchCode(), 1);
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

  public ComBranchEntity save(ComBranchDto dto) throws BusinessServiceException {
    log.info("Begin ComBranchService.save()...");
    if (modelMapper.getConfiguration() != null)
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    ComBranchEntity entity = modelMapper.map(dto, ComBranchEntity.class);
    log.info("End ComBranchService.save()...");
    return comBranchRepository.save(entity);
  }

  @VisibleForTesting
  protected Specification<ComBranchEntity> getSpecification(ComBranchDto filter) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.getBranchId() > 0L) {
        predicates.add(cb.equal(root.get("branchId"), filter.getBranchId()));
      }

      if (filter.getCompanyId() != null) {
        predicates.add(cb.equal(root.get("companyId"), filter.getCompanyId()));
      }

      if (filter.getParentBranchId() != null) {
        predicates.add(cb.equal(root.get("parentBranchId"), filter.getParentBranchId()));
      }

      if (filter.getBranchCode() != null) {
        predicates.add(cb.like(
            cb.lower(root.get("branchCode")), "%" + filter.getBranchCode().toLowerCase() + "%"));
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

      ///Filter Branch
      if(oauthUser.getAllBranch() != null && oauthUser.getAllBranch().size() > 0){
        predicates.add(root.get("branchId").in(oauthUser.getAllBranch()));
      }

      if (filter.getFullSearch() != null) {
        Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        predicates = new ArrayList<>();
        predicates.add(
            cb.like(
                cb.lower(root.get("branchCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
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

  protected Page<ComBranchDto> convertPageEntityToPageDto(Page<ComBranchEntity> source) {
    if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
    Page<ComBranchDto> map =
        source.map(
            new Function<ComBranchEntity, ComBranchDto>() {
              @Override
              public ComBranchDto apply(ComBranchEntity entity) {
                ComBranchDto dto = modelMapper.map(entity, ComBranchDto.class);
                return dto;
              }
            });
    return map;
  }

  public List<ComBranchDto> findByProductId(long id) throws BusinessServiceException {
    try {
      log.info("Begin PostalDistrictService.findByฺBranchId()...");
      List<ComBranchEntity> comBranchEntities = comBranchRepository.findAllByParentBranchIdAndStatus(id, 1);

      Type listTpye = new com.google.common.reflect.TypeToken<List<ComBranchDto>>() {}.getType();
      List<ComBranchDto> map = modelMapper.map(comBranchEntities, listTpye);

      return map;
    } catch (Exception ex) {
      log.error("ERROR PostalDistrictService.findByฺBranchId()...", ex.fillInStackTrace());
      throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    } finally {
      log.info("End PostalDistrictService.findByฺBranchId()...");
    }
  }

}
