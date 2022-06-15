//package com.esign.service.configuration.controller;
//
//import com.esign.service.configuration.client.LoggingService;
//import com.esign.service.configuration.client.LoggingServiceClient;
//import com.esign.service.configuration.config.OauthUser;
//import com.esign.service.configuration.dto.ProductDto;
//import com.esign.service.configuration.dto.pass.EnforcementDto;
//import com.esign.service.configuration.entity.ProductEntity;
//import com.esign.service.configuration.entity.pass.PassEnforcementEntity;
//import com.esign.service.configuration.exception.BusinessServiceException;
//import com.esign.service.configuration.service.ComBranchService;
//import com.esign.service.configuration.service.ProductService;
//import com.esign.service.configuration.service.UnitService;
//import com.esign.service.configuration.service.pass.EnforcementService;
//import com.esign.service.configuration.utils.HelperUtils;
//import com.esign.service.configuration.utils.MessageByLocaleService;
//import com.esign.service.configuration.utils.ResponseModel;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.convention.MatchingStrategies;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Date;
//import java.util.Map;
//
//@PreAuthorize("isAuthenticated()")
//@RestController
//@RequestMapping(path = "/api")
//@Api(tags = "Product")
//@Slf4j
//public class ProductController {
//
//    private ProductService productService;
//    private MessageByLocaleService message;
//    private OauthUser oauthUser;
//    private LoggingServiceClient loggingServiceClient;
//    private LoggingService loggingService;
//    private ModelMapper modelMapper;
//    private ComBranchService comBranchService;
//    private UnitService unitService;
//
//    @Autowired
//    public void setLanguageController(ProductService productService,
//                                      MessageByLocaleService message,
//                                      OauthUser oauthUser,
//                                      LoggingServiceClient loggingServiceClient,
//                                      LoggingService loggingService,
//                                      ModelMapper modelMapper,
//                                      ComBranchService comBranchService,
//                                      UnitService unitService){
//        this.productService = productService;
//        this.message = message;
//        this.oauthUser = oauthUser;
//        this.loggingServiceClient =loggingServiceClient;
//        this.loggingService = loggingService;
//        this.modelMapper = modelMapper;
//        this.comBranchService = comBranchService;
//        this.unitService = unitService;
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//    }
//
//    //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/product")
//    @ApiOperation(value = "Search Product", response = ProductDto.class, responseContainer = "List")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(code = 400, message = "Something went wrong"),
//                    @ApiResponse(code = 403, message = "Access denied"),
//                    @ApiResponse(code = 500, message = "Error")
//            })
//    public ResponseEntity<ResponseModel> searchByCri(
//            @RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
//            @RequestParam(required = false, defaultValue = "10", value = "perPage") Integer perPage,
//            @RequestParam(required = false, defaultValue = "ASC", value = "order")
//                    Sort.Direction direction,
//            @RequestParam(required = false, defaultValue = "productCode", value = "sort") String sort,
//            @RequestParam Map<String, Object> paramDto) {
//        paramDto.put("page", page);
//        paramDto.put("perPage", perPage);
//        paramDto.put("direction", direction);
//        paramDto.put("sort", sort);
//
//        /// FOR LOG///
//        int status = 0;
//        String remark = "";
//        /// FOR LOG///
//
//        try {
//            log.info("Start ProductController.searchByCri()... ");
//            ProductDto dto = modelMapper.map(paramDto, ProductDto.class);
//            Page<ProductDto> byCri = productService.findByCri(dto);
//
//            /// FOR LOG///
//            status = HttpStatus.OK.value();
//            remark = paramDto.toString();
//            /// FOR LOG///
//
//            return HelperUtils.responseSuccess(byCri);
//        } catch (BusinessServiceException e) {
//            log.error("Error ProductController.searchByCri()... " + e.getMessage());
//
//            /// FOR LOG///
//            status = e.getHttpStatus().value();
//            remark = e.getMessage();
//            /// FOR LOG///
//
//            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
//        } finally {
//            // ***START ACTIVITY LOG***//
//            loggingServiceClient.activityLog(
//                    LoggingServiceClient.ActivityType.SEARCH,
//                    null,
//                    null,
//                    remark,
//                    loggingService.getApiOperationValue(new Object() {}),
//                    loggingService.getClientIp(),
//                    loggingService.getTracer(),
//                    loggingService.getMethodName(new Object() {}),
//                    loggingService.getToken(),
//                    "XXX",
//                    status);
//            // ***END ACTIVITY LOG***//
//            log.info("End ProductController.searchByCri()... ");
//        }
//    }
//
//    //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/product/{id}")
//    @ApiOperation(value = "Get Product By Id", response = ProductDto.class)
//    @ApiResponses(
//            value = {
//                    @ApiResponse(code = 400, message = "Something went wrong"),
//                    @ApiResponse(code = 403, message = "Access denied"),
//                    @ApiResponse(code = 500, message = "Error")
//            })
//    public ResponseEntity<ResponseModel> getById(@PathVariable("id") int id) {
//        /// FOR LOG///
//        int status = 0;
//        String remark = "";
//        /// FOR LOG///
//        try {
//            log.info("Start ProductController.getById()... ");
//            ProductDto dto = productService.getById(id);
//
//            if (dto.getProductId() > 0) {
//
//                dto.setComBranch(comBranchService.findByProductId(dto.getProductId()));
//                dto.setUnit(unitService.findByUnitCode(dto.getUnitCode()));
//
//                /// FOR LOG///
//                status = HttpStatus.OK.value();
//                remark = "ID=" + id;
//                /// FOR LOG///
//
//                return HelperUtils.responseSuccess(dto);
//            } else {
//                throw new BusinessServiceException(
//                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), ("ERM0001"));
//            }
//        } catch (BusinessServiceException e) {
//            log.error("Error ProductController.getById()... " + e.getMessage());
//
//            /// FOR LOG///
//            status = e.getHttpStatus().value();
//            remark = e.getMessage() + " ID=" + id;
//            /// FOR LOG///
//
//            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//
//            /// FOR LOG///
//            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
//            remark = e.getMessage() + " ID=" + id;
//            /// FOR LOG///
//
//            return HelperUtils.responseErrorInternalServerError(
//                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
//        } finally {
//            // ***START ACTIVITY LOG***//
//            loggingServiceClient.activityLog(
//                    LoggingServiceClient.ActivityType.VIEW,
//                    null,
//                    null,
//                    remark,
//                    loggingService.getApiOperationValue(new Object() {}),
//                    loggingService.getClientIp(),
//                    loggingService.getTracer(),
//                    loggingService.getMethodName(new Object() {}),
//                    loggingService.getToken(),
//                    "XXX",
//                    status);
//            // ***END ACTIVITY LOG***//
//            log.info("End ProductController.getById()... ");
//        }
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/product")
//    @ApiOperation(value = "New Product", response = void.class)
//    @ApiResponses(
//            value = {
//                    @ApiResponse(code = 400, message = "Something went wrong"),
//                    @ApiResponse(code = 403, message = "Access denied"),
//                    @ApiResponse(code = 500, message = "Error")
//            })
//    public ResponseEntity<ResponseModel> addConfig(@RequestBody ProductDto dto){
//        //FOR LOG//
//        int status = 0;
//        String remark = "";
//        //FOR LOG//
//        try {
//            int count = productService.checkDuplicate(dto);
//            if (count > 0) {
//                throw new BusinessServiceException(
//                        HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
//            }
//            dto.setCreatedDate(new Date());
//            dto.setCreatedBy(dto.getCreatedBy());
//            dto.setStatus(1);
//            ProductEntity save = productService.save(dto);
//
//            /// FOR LOG///
//            status = HttpStatus.OK.value();
//            remark = "ID=" + save.getProductId() + " NAME=" + save.getProductNameTh();
//            /// FOR LOG///
//
//            return HelperUtils.responseSuccess(save.getProductId());
//        } catch (BusinessServiceException e) {
//            log.error("Error ProductController.create()... " + e.getMessage());
//
//            /// FOR LOG///
//            status = e.getHttpStatus().value();
//            remark = e.getMessage() + " NAME=" + dto.getProductNameTh();
//            /// FOR LOG///
//
//            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
//        }catch (Exception e){
//            log.error(e.getMessage());
//
//            /// FOR LOG///
//            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
//            remark = e.getMessage() + " NAME=" + dto.getProductNameTh();
//            /// FOR LOG///
//            return HelperUtils.responseErrorInternalServerError(
//                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
//        }finally {
//            // ***START ACTIVITY LOG***//
//            loggingServiceClient.activityLog(
//                    LoggingServiceClient.ActivityType.CREATE,
//                    null,
//                    null,
//                    remark,
//                    loggingService.getApiOperationValue(new Object() {}),
//                    loggingService.getClientIp(),
//                    loggingService.getTracer(),
//                    loggingService.getMethodName(new Object() {}),
//                    loggingService.getToken(),
//                    "XXX",
//                    status);
//            // ***END ACTIVITY LOG***//
//            log.info("End ProductController.create()... ");
//        }
//    }
//
//    //@PreAuthorize("hasAnyAuthorityCustom('ARMTE')")
//    @PreAuthorize("isAuthenticated()")
//    @PutMapping("/product")
//    @ApiOperation(value = "Update Product", response = void.class)
//    @ApiResponses(
//            value = {
//                    @ApiResponse(code = 400, message = "Something went wrong"),
//                    @ApiResponse(code = 403, message = "Access denied"),
//                    @ApiResponse(code = 500, message = "Error")
//            })
//    public ResponseEntity<ResponseModel> update(@RequestBody ProductDto dto) {
//        /// FOR LOG///
//        int status = 0;
//        String remark = "";
//        ProductDto dtoFromDb = null;
//        ProductDto dtoFromDbCurrentData = null;
//        /// FOR LOG///
//        try {
//            log.info("Start ProductController.update()... ");
//            dtoFromDb = productService.getById(dto.getProductId());
//            dtoFromDb.setLastUpdateBy(dto.getLastUpdateBy());
//            dtoFromDb.setLastUpdateDate(new Date());
//
//            /// FOR LOG///
//            /// prepare data
//            dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
//            /// FOR LOG///
//
//            if (dtoFromDb.getProductId() > 0) {
//                if (dto.getProductNameTh() != null
//                        && !dtoFromDb.getProductNameTh().equalsIgnoreCase(dto.getProductNameTh())) {
//                    int count = productService.checkDuplicate(dto);
//                    if (count > 0) {
//                        throw new BusinessServiceException(
//                                HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
//                    }
//                }
//
//                // map data from front to db
//                modelMapper.map(dto, dtoFromDb);
//                productService.save(dtoFromDb);
//
//                return HelperUtils.responseSuccess();
//            } else {
//                throw new BusinessServiceException(
//                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
//            }
//        } catch (BusinessServiceException e) {
//            log.error("Error ProductController.update()... " + e.getMessage());
//
//            /// FOR LOG///
//            status = e.getHttpStatus().value();
//            remark = e.getMessage() + " ID=" + dto.getProductId();
//            /// FOR LOG///
//
//            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//
//            /// FOR LOG///
//            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
//            remark = e.getMessage() + " ID=" + dto.getProductId();
//            /// FOR LOG///
//
//            return HelperUtils.responseErrorInternalServerError(
//                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
//        } finally {
//
//            // ***START ACTIVITY LOG***//
//            loggingServiceClient.activityLog(
//                    LoggingServiceClient.ActivityType.UPDATE,
//                    dtoFromDbCurrentData,
//                    dtoFromDb,
//                    remark,
//                    loggingService.getApiOperationValue(new Object() {}),
//                    loggingService.getClientIp(),
//                    loggingService.getTracer(),
//                    loggingService.getMethodName(new Object() {}),
//                    loggingService.getToken(),
//                    "XXX",
//                    status);
//            // ***END ACTIVITY LOG***//
//
//            log.info("End ProductController.update()... ");
//        }
//    }
//
//    //@PreAuthorize("hasAnyAuthorityCustom('ARMTD')")
//    @PreAuthorize("isAuthenticated()")
//    @DeleteMapping("/product/{id}")
//    @ApiOperation(value = "Delete Product", response = void.class)
//    @ApiResponses(
//            value = {
//                    @ApiResponse(code = 400, message = "Something went wrong"),
//                    @ApiResponse(code = 403, message = "Access denied"),
//                    @ApiResponse(code = 500, message = "Error")
//            })
//    public ResponseEntity<ResponseModel> delete(@PathVariable("id") int id) {
//        /// FOR LOG///
//        int status = 0;
//        String remark = "";
//        /// FOR LOG///
//        try {
//            log.info("Start ProductController.delete()... ");
//            ProductDto dtoFromDb = productService.getById(id);
//            if (dtoFromDb != null) {
//                dtoFromDb.setLastUpdateBy(dtoFromDb.getLastUpdateBy());
//                dtoFromDb.setLastUpdateDate(new Date());
//                dtoFromDb.setStatus(0);
//
//                /// FOR LOG///
//                status = HttpStatus.OK.value();
//                remark = "ID=" + id;
//                /// FOR LOG///
//
//                productService.save(dtoFromDb);
//                return HelperUtils.responseSuccess();
//            } else {
//                throw new BusinessServiceException(
//                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
//            }
//        } catch (BusinessServiceException e) {
//            log.error("Error ProductController.delete()... " + e.getMessage());
//
//            /// FOR LOG///
//            status = e.getHttpStatus().value();
//            remark = e.getMessage() + " ID=" + id;
//            /// FOR LOG///
//
//            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//
//            /// FOR LOG///
//            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
//            remark = e.getMessage() + " ID=" + id;
//            /// FOR LOG///
//
//            return HelperUtils.responseErrorInternalServerError(
//                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
//        } finally {
//            // ***START ACTIVITY LOG***//
//            loggingServiceClient.activityLog(
//                    LoggingServiceClient.ActivityType.DELETE,
//                    null,
//                    null,
//                    remark,
//                    loggingService.getApiOperationValue(new Object() {}),
//                    loggingService.getClientIp(),
//                    loggingService.getTracer(),
//                    loggingService.getMethodName(new Object() {}),
//                    loggingService.getToken(),
//                    "XXX",
//                    status);
//            // ***END ACTIVITY LOG***//
//            log.info("End ProductController.delete()... ");
//        }
//    }
//
//}
