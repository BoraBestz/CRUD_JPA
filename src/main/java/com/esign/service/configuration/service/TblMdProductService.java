package com.esign.service.configuration.service;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdProductDto;
import com.esign.service.configuration.dto.TblMdProductGroupDto;
import com.esign.service.configuration.entity.TblMdProductEntity;
import com.esign.service.configuration.entity.TblMdProductGroupEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdProductRepository;
import com.esign.service.configuration.utils.PageUtils;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class TblMdProductService {
    private EntityManager entityManager;
    private ModelMapper modelMapper;
    private OauthUser oauthUser;
    private TblMdProductRepository tblMdProductRepository;

    @Autowired
    public void setTblMdProductService(
            EntityManager entityManager,
            ModelMapper modelMapper,
            OauthUser oauthUser,
            TblMdProductRepository tblMdProductRepository) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.oauthUser = oauthUser;
        this.tblMdProductRepository = tblMdProductRepository;
    }

    public TblMdProductDto getById(int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdProductService.getById()...");
            Optional<TblMdProductEntity> byId = tblMdProductRepository.findById(id);
            return byId.isPresent() ? modelMapper.map(byId.get(), TblMdProductDto.class) : null;
        } catch (Exception ex) {
            log.error("ERROR TblMdProductService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdProductService.getById()...");
        }
    }

    public TblMdProductEntity save(TblMdProductDto dto) throws BusinessServiceException {
        log.info("Begin TblMdProductService.save()...");
        if (modelMapper.getConfiguration() != null)
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TblMdProductEntity entity = modelMapper.map(dto, TblMdProductEntity.class);
        log.info("End TblMdProductService.save()...");
        return tblMdProductRepository.save(entity);
    }

    public List<TblMdProductDto> getByProductGroupId(int id) throws BusinessServiceException {
        try {
            log.info("Begin TblMdProductGroupService.getById()...");
            List<TblMdProductEntity> byProductGroupId = tblMdProductRepository.findByProductGroupId(Long.valueOf(id));
            List<TblMdProductDto> list = new ArrayList<>();
            for (TblMdProductEntity item : byProductGroupId) {
                list.add(modelMapper.map(item, TblMdProductDto.class));
            }
            return list;
        } catch (Exception ex) {
            log.error("ERROR TblMdProductGroupService.getById()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdProductGroupService.getById()...");
        }
    }

    public Integer checkDuplicateCode(TblMdProductDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdProductService.checkDuplicateAbbr()...");
            List<TblMdProductEntity> rs =
                    tblMdProductRepository.findAllByProductCodeAndRecordStatus(dto.getProductCode(),"A");
            if (rs != null && rs.size() > 0) {
                return 1;
            }
            return 0;
        } catch (Exception ex) {
            log.error("ERROR TblMdProductService.checkDuplicateAbbr()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdProductService.checkDuplicateAbbr()...");
        }
    }

    @VisibleForTesting
    protected Specification<TblMdProductEntity> getSpecification(TblMdProductDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getProductId() > 0L) {
                predicates.add(cb.equal(root.get("productId"), filter.getProductId()));
            }

            if (filter.getRecordStatus() != null) {
                predicates.add(cb.equal(root.get("recordStatus"), filter.getRecordStatus().trim()));
            }else {
                predicates.add(cb.equal(root.get("recordStatus"), "A"));
            }

            if (filter.getProductCode() != null) {
                predicates.add(cb.equal(root.get("productCode"), filter.getProductCode()));
            }

            if (filter.getProductNameTh() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("productNameTh")), "%" + filter.getProductNameTh().toLowerCase() + "%"));
            }

            if (filter.getProductNameEn() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("productNameEn")), "%" + filter.getProductNameEn().toLowerCase() + "%"));
            }

            if (filter.getProductDescription() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("productDescription")), "%" + filter.getProductDescription().toLowerCase() + "%"));
            }

            if (filter.getProductLevel() != null) {
                predicates.add(cb.equal(root.get("productLevel"), filter.getProductLevel()));
            }

            if (filter.getProductParent() != null) {
                predicates.add(cb.equal(root.get("productParent"), filter.getProductParent()));
            }

            if (filter.getProductGroupId() != null) {
                predicates.add(cb.equal(root.get("productGroupId"), filter.getProductGroupId()));
            }

            if (filter.getUnitId() != null) {
                predicates.add(cb.equal(root.get("unitId"), filter.getUnitId()));
            }

            if (filter.getUnspscCode() != null) {
                predicates.add(cb.like(
                        cb.lower(root.get("unspecCode")), "%" + filter.getUnspscCode().toLowerCase() + "%"));
            }

            if (filter.getFullSearch() != null) {
                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                predicates = new ArrayList<>();
                predicates.add(
                        cb.like(
                                cb.lower(root.get("productNameEn")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("productCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("productNameTh")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("productDescription")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("productLevel")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("productParent")), "%" + filter.getFullSearch().toLowerCase() + "%"));
                predicates.add(
                        cb.like(
                                cb.lower(root.get("unspecCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));

                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                return cb.and(p1, p2);
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<TblMdProductDto> findByCri(TblMdProductDto dto) throws BusinessServiceException {
        try {
            log.info("Begin TblMdPdfPasswordService.findByCri()...");
            Pageable pageRequest =
                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
            Page<TblMdProductEntity> all =
                    tblMdProductRepository.findAll(getSpecification(dto), pageRequest);
            Page<TblMdProductDto> dtos = convertPageEntityToPageDto(all);
            return dtos;
        } catch (Exception ex) {
            log.error("ERROR TblMdProductService.findByCri()...", ex.fillInStackTrace());
            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } finally {
            log.info("End TblMdProductService.findByCri()...");
        }
    }

    protected Page<TblMdProductDto> convertPageEntityToPageDto(Page<TblMdProductEntity> source) {
        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
        Page<TblMdProductDto> map =
                source.map(
                        new Function<TblMdProductEntity, TblMdProductDto>() {
                            @Override
                            public TblMdProductDto apply(TblMdProductEntity entity) {
                                TblMdProductDto dto = modelMapper.map(entity, TblMdProductDto.class);
                                return dto;
                            }
                        });
        return map;
    }

    public void getChild (TblMdProductDto dto){
        log.info("Start TblMdProductService.getChild()...");
        //Map<String, Object> map = new HashMap<String, Object>();
        List<TblMdProductDto> child = new ArrayList<>();
        try {
            log.info("Start Getting Data from Database...");
            List<TblMdProductEntity> entities = tblMdProductRepository.findAll();
            log.info("Getting Data from Database Success...");
            for (TblMdProductEntity data : entities){
                if (data.getProductParent() != null){
                    if (dto.getProductId() == data.getProductParent()){
                        log.info("Start Map TblMdProductEntity to Dto ...");
                        TblMdProductDto childDto = modelMapper.map(data, TblMdProductDto.class);
                        log.info("Map TblMdProductEntity to Dto Success...");

                        if (data.getRecordStatus() != null){
                            if (data.getRecordStatus().equals("A")){
                                log.info("Start add child...");
                                child.add(childDto);
                                log.info("Add child Success...");
                                getChild(childDto);
                            }
                        }

                        /*log.info("Start map Object with String...");
                        dto.getTblMdProductDtoList().add(childDto);
                        log.info("Map Object with String Success...");*/
                    }
                }
            }
            dto.setTblMdProductDtoList(child);
        }catch (Exception ex){
            log.error("ERROR TblMdProductService.getChild()...", ex.fillInStackTrace());
        }finally {
            log.info("End TblMdProductService.getChild()..." + dto.getProductId());
        }
    }

    public void getChildren(TblMdProductGroupDto dto){
        List<TblMdProductDto> child = new ArrayList<>();
        try {
            log.info("Start Getting Data from Database...");
            List<TblMdProductEntity> entities = tblMdProductRepository.findAll();
            log.info("Getting Data from Database Success...");
            for (TblMdProductEntity data : entities){
                if (data.getProductGroupId() != null){
                    if (dto.getProductGroupId() == data.getProductGroupId()){
                        log.info("Start Map TblMdProductGroupEntity to Dto ...");
                        TblMdProductDto childDto = modelMapper.map(data, TblMdProductDto.class);
                        log.info("Map TblMdProductGroupEntity to Dto Success...");

                        if (data.getRecordStatus() != null){
                            if (data.getRecordStatus().equals("A")){
                                log.info("Start add child...");
                                child.add(childDto);
                                log.info("Add child Success...");
                                getChild(childDto);
                            }
                        }
                    }
                }
            }
            dto.setTblMdProductDtoList(child);
        }catch (Exception ex){
            log.error("ERROR TblMdProductService.getChild()...", ex.fillInStackTrace());
        }finally {
            log.info("End TblMdProductService.getChild()..." + dto.getProductGroupId());
        }
    }

}
