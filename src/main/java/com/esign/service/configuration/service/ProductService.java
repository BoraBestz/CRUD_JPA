//package com.esign.service.configuration.service;
//
//import com.esign.service.configuration.dto.ProductDto;
//import com.esign.service.configuration.entity.ProductEntity;
//import com.esign.service.configuration.exception.BusinessServiceException;
//import com.esign.service.configuration.repository.ProductRepository;
//import com.esign.service.configuration.utils.PageUtils;
//import com.google.common.annotations.VisibleForTesting;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.convention.MatchingStrategies;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import javax.persistence.criteria.Predicate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//@Service
//@Slf4j
//public class ProductService {
//
//    private ProductRepository productRepository;
//    private ModelMapper modelMapper;
//
//    @Autowired
//    public void setConfigService(ProductRepository productRepository,
//                                 ModelMapper modelMapper){
//        this.productRepository = productRepository;
//        this.modelMapper = modelMapper;
//    }
//
//    public ProductEntity save(ProductDto dto) throws BusinessServiceException {
//        log.info("String Saving Data....");
//        if (modelMapper.getConfiguration() != null)
//            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        log.info("Map dto with Config Entity.class.....");
//        ProductEntity entity = modelMapper.map(dto, ProductEntity.class);
//        log.info("End Saving Data....");
//        return productRepository.save(entity);
//    }
//
//    public Integer checkDuplicate(ProductDto dto) throws BusinessServiceException {
//        log.info("String Check Duplicate Data....");
//        try {
//            log.info("Get Data from DB....");
//            List<ProductEntity> rs = productRepository.findAllByProductCodeAndProductNameTh(dto.getProductCode(), dto.getProductNameTh());
//
//            if (rs != null && rs.size() > 0){
//                return 1;
//            }
//            return 0;
//        }catch (Exception ex){
//            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
//        }
//    }
//
//    public ProductDto getById(int id) throws BusinessServiceException {
//        try {
//            log.info("Begin PostalDistrictService.getById()...");
//            Optional<ProductEntity> byId = productRepository.findById(id);
//            return byId.isPresent() ? modelMapper.map(byId.get(), ProductDto.class) : null;
//        } catch (Exception ex) {
//            log.error("ERROR PostalDistrictService.getById()...", ex.fillInStackTrace());
//            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
//        } finally {
//            log.info("End PostalDistrictService.getById()...");
//        }
//    }
//
//    public Page<ProductDto> findByCri(ProductDto dto) throws BusinessServiceException {
//        try {
//            log.info("Begin ProductService.findByCri()...");
//            Pageable pageRequest =
//                    PageUtils.getPageable(dto.getPage(), dto.getPerPage(), dto.getSort(), dto.getDirection());
//            log.info("pageRequest Complete...");
//            Page<ProductEntity> all =
//                    productRepository.findAll(getSpecification(dto), pageRequest);
//            log.info("all Complete...");
//            Page<ProductDto> dtos = convertPageEntityToPageDto(all);
//            return dtos;
//        } catch (Exception ex) {
//            log.error("ERROR ProductService.findByCri()...", ex.fillInStackTrace());
//            throw new BusinessServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
//        } finally {
//            log.info("End ProductService.findByCri()...");
//        }
//    }
//    @VisibleForTesting
//    protected Specification<ProductEntity> getSpecification(ProductDto filter) {
//        return (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (filter.getProductId() != null){
//                predicates.add(cb.equal(root.get("productId"), filter.getProductId()));
//            }
//
//            if (filter.getBranchId() != null){
//                predicates.add(cb.equal(root.get("branchId"), filter.getBranchId()));
//            }
//
//            if (filter.getStatus() != null && filter.getStatus().intValue() > 0) {
//                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
//            }else {
//                predicates.add(cb.equal(root.get("status"), 1));
//            }
//
//            if (filter.getCreatedBy() != null){
//                predicates.add(cb.equal(root.get("createBy"), filter.getCreatedBy()));
//            }
//
//            if (filter.getLastUpdateBy() != null) {
//                predicates.add(cb.equal(root.get("lastUpdateBy"), filter.getLastUpdateBy()));
//            }
//
//            if (filter.getClassId() != null) {
//                predicates.add(cb.equal(root.get("classId"), filter.getClassId()));
//            }
//
//            if (filter.getClassCode() != null){
//                predicates.add(cb.like(
//                        cb.lower(root.get("classCode")), "%" + filter.getClassCode().toLowerCase() + "%"));
//            }
//
//            if (filter.getClassName() != null){
//                predicates.add(cb.like(
//                        cb.lower(root.get("className")), "%" + filter.getClassName().toLowerCase() + "%"));
//            }
//
//            if (filter.getUnitId() != null) {
//                predicates.add(cb.equal(root.get("unitId"), filter.getUnitId()));
//            }
//
//            if (filter.getUnitCode() != null){
//                predicates.add(cb.like(
//                        cb.lower(root.get("unitCode")), "%" + filter.getUnitCode().toLowerCase() + "%"));
//            }
//
//            if (filter.getUnitName() != null){
//                predicates.add(cb.like(
//                        cb.lower(root.get("unitName")), "%" + filter.getUnitName().toLowerCase() + "%"));
//            }
//
//            if (filter.getProductCode() != null){
//                predicates.add(cb.like(
//                        cb.lower(root.get("productCode")), "%" + filter.getProductCode().toLowerCase() + "%"));
//            }
//
//            if (filter.getProductNameTh() != null){
//                predicates.add(cb.like(
//                        cb.lower(root.get("productNameTh")), "%" + filter.getProductNameTh().toLowerCase() + "%"));
//            }
//
//            if (filter.getProductNameEn() != null){
//                predicates.add(cb.like(
//                        cb.lower(root.get("productNameEn")), "%" + filter.getProductNameEn().toLowerCase() + "%"));
//            }
//
//            if (filter.getProductDescription() != null){
//                predicates.add(cb.like(
//                        cb.lower(root.get("productDescription")), "%" + filter.getProductDescription().toLowerCase() + "%"));
//            }
//
//            if (filter.getCreateUser() != null) {
//                predicates.add(cb.equal(root.get("createUser"), filter.getCreateUser()));
//            }
//
//            if (filter.getFullSearch() != null) {
//                Predicate p1 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
//                predicates = new ArrayList<>();
//                predicates.add(
//                        cb.like(
//                                cb.lower(root.get("productCode")), "%" + filter.getFullSearch().toLowerCase() + "%"));
//                predicates.add(
//                        cb.like(
//                                cb.lower(root.get("productName")), "%" + filter.getFullSearch().toLowerCase() + "%"));
//                Predicate p2 = cb.or(predicates.toArray(new Predicate[predicates.size()]));
//                return cb.and(p1, p2);
//            }
//
//            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
//        };
//    }
//
//    protected Page<ProductDto> convertPageEntityToPageDto(Page<ProductEntity> source) {
//        if (source == null || source.isEmpty()) return Page.empty(PageUtils.getPageable(1, 10));
//        Page<ProductDto> map =
//                source.map(
//                        new Function<ProductEntity, ProductDto>() {
//                            @Override
//                            public ProductDto apply(ProductEntity entity) {
//                                ProductDto dto = modelMapper.map(entity, ProductDto.class);
//                                return dto;
//                            }
//                        });
//        return map;
//    }
//
//}
