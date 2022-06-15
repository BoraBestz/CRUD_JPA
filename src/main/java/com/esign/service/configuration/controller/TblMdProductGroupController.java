package com.esign.service.configuration.controller;

import com.esign.service.configuration.client.LoggingService;
import com.esign.service.configuration.client.LoggingServiceClient;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdProductDto;
import com.esign.service.configuration.dto.TblMdProductGroupDto;
import com.esign.service.configuration.entity.TblMdProductEntity;
import com.esign.service.configuration.entity.TblMdProductGroupEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.repository.TblMdProductRepository;
import com.esign.service.configuration.service.TblMdProductGroupService;
import com.esign.service.configuration.service.TblMdProductService;
import com.esign.service.configuration.utils.HelperUtils;
import com.esign.service.configuration.utils.MessageByLocaleService;
import com.esign.service.configuration.utils.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/api")
@Api(tags = "TblMdProductGroup")
@Slf4j
public class TblMdProductGroupController {
    private TblMdProductGroupService tblMdProductGroupService;
    private MessageByLocaleService message;
    private ModelMapper modelMapper;
    private LoggingServiceClient loggingServiceClient;
    private LoggingService loggingService;
    private OauthUser oauthUser;
    private TblMdProductService tblMdProductService;
    private TblMdProductRepository tblMdProductRepository;

    @Autowired
    public void setTblMdProductGroupController(
            TblMdProductGroupService tblMdProductGroupService,
            MessageByLocaleService message, ModelMapper modelMapper,
            LoggingServiceClient loggingServiceClient,
            LoggingService loggingService, OauthUser oauthUser,
            TblMdProductService tblMdProductService,
            TblMdProductRepository tblMdProductRepository) {
        this.tblMdProductGroupService = tblMdProductGroupService;
        this.message = message;
        this.modelMapper = modelMapper;
        this.loggingServiceClient = loggingServiceClient;
        this.loggingService = loggingService;
        this.oauthUser = oauthUser;
        this.tblMdProductService = tblMdProductService;
        this.tblMdProductRepository = tblMdProductRepository;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/product/group")
    @ApiOperation(value = "Search TblMdProductGroup", response = TblMdProductGroupDto.class, responseContainer = "List")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })

    public ResponseEntity<ResponseModel> searchByCri(
            @RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", value = "perPage") Integer perPage,
            @RequestParam(required = false, defaultValue = "ASC", value = "order")
                    Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "productGroupCode", value = "sort") String sort,
            @RequestParam Map<String, Object> paramDto) {
        paramDto.put("page", page);
        paramDto.put("perPage", perPage);
        paramDto.put("direction", direction);
        paramDto.put("sort", sort);

        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///

        try {
            log.info("Start TblMdProductGroupController.searchByCri()... ");
            TblMdProductGroupDto dto = modelMapper.map(paramDto, TblMdProductGroupDto.class);
            List<TblMdProductGroupDto> byCri = tblMdProductGroupService.findByCri(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = paramDto.toString();
            /// FOR LOG///

            return HelperUtils.responseSuccess(byCri);
        } catch (BusinessServiceException e) {
            log.error("Error TblMdProductGroupController.searchByCri()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } finally {
            // ***START ACTIVITY LOG***//
            loggingServiceClient.activityLog(
                    LoggingServiceClient.ActivityType.SEARCH,
                    null,
                    null,
                    remark,
                    loggingService.getApiOperationValue(new Object() {}),
                    loggingService. getClientIp(),
                    loggingService.getTracer(),
                    loggingService.getMethodName(new Object() {}),
                    loggingService.getToken(),
                    "XXX",
                    status);
            // ***END ACTIVITY LOG***//
            log.info("End TblMdProductGroupController.searchByCri()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/product/group/{id}")
    @ApiOperation(value = "Get TblMdProductGroup By Id", response = TblMdProductGroupDto.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> getById(@PathVariable("id") int id) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///
        try {
            log.info("Start TblMdProductGroupController.getById()... ");
            TblMdProductGroupDto dto = tblMdProductGroupService.getById(id);

            if (dto.getProductGroupId() > 0) {
                /// FOR LOG///
                status = HttpStatus.OK.value();
                remark = "ID=" + id;
                /// FOR LOG///

                return HelperUtils.responseSuccess(dto);
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), ("ERM0001"));
            }
        } catch (BusinessServiceException e) {
            log.error("Error TblMdProductGroupController.getById()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " ID=" + id;
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " ID=" + id;
            /// FOR LOG///

            return HelperUtils.responseErrorInternalServerError(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
        } finally {
            // ***START ACTIVITY LOG***//
            loggingServiceClient.activityLog(
                    LoggingServiceClient.ActivityType.VIEW,
                    null,
                    null,
                    remark,
                    loggingService.getApiOperationValue(new Object() {
                    }),
                    loggingService.getClientIp(),
                    loggingService.getTracer(),
                    loggingService.getMethodName(new Object() {
                    }),
                    loggingService.getToken(),
                    "XXX",
                    status);
            // ***END ACTIVITY LOG***//
            log.info("End TblMdProductGroupController.getById()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/product/group")
    @ApiOperation(value = "New TblMdProductGroup", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> create(@RequestBody TblMdProductGroupDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///
        try {
            log.info("Start TblMdProductGroupController.create()... ");

            int count = tblMdProductGroupService.checkDuplicateCode(dto);

            if (count > 0) {
                throw new BusinessServiceException(
                        HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
            }
            dto.setCreateDate(new Date());
            dto.setCreateBy(oauthUser.oauthUserId());
            dto.setRecordStatus("A");
            TblMdProductGroupEntity save = tblMdProductGroupService.save(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = "ID=" + save.getProductGroupId() + " NAME=" + save.getProductGroupName();
            /// FOR LOG///

            return HelperUtils.responseSuccess(save.getProductGroupId());
        } catch (BusinessServiceException e) {
            log.error("Error TblMdProductGroupController.create()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " NAME=" + dto.getProductGroupName();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " NAME=" + dto.getProductGroupName();
            /// FOR LOG///

            return HelperUtils.responseErrorInternalServerError(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
        } finally {
            // ***START ACTIVITY LOG***//
            loggingServiceClient.activityLog(
                    LoggingServiceClient.ActivityType.CREATE,
                    null,
                    null,
                    remark,
                    loggingService.getApiOperationValue(new Object() {
                    }),
                    loggingService.getClientIp(),
                    loggingService.getTracer(),
                    loggingService.getMethodName(new Object() {
                    }),
                    loggingService.getToken(),
                    "XXX",
                    status);
            // ***END ACTIVITY LOG***//
            log.info("End TblMdProductGroupController.create()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/product/group")
    @ApiOperation(value = "Update TblMdProductGroup", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> update(@RequestBody TblMdProductGroupDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        TblMdProductGroupDto dtoFromDb = null;
        TblMdProductGroupDto dtoFromDbCurrentData = null;
        /// FOR LOG///
        try {
            log.info("Start TblMdProductGroupController.update()... ");
            dtoFromDb = tblMdProductGroupService.getById((int) dto.getProductGroupId());
            dtoFromDb.setCreateBy( oauthUser.oauthUserId());
            dtoFromDb.setCreateDate(new Date());

            /// FOR LOG///
            /// prepare data
            dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
            /// FOR LOG///

            if (dtoFromDb.getProductGroupId() > 0) {
                if (dto.getProductGroupCode() != null
                        && !dtoFromDb.getProductGroupCode().equalsIgnoreCase(dto.getProductGroupCode())) {
                    int count = tblMdProductGroupService.checkDuplicateCode(dto);
                    if (count > 0) {
                        throw new BusinessServiceException(
                                HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
                    }
                }

                // map data from front to db
                modelMapper.map(dto, dtoFromDb);
                tblMdProductGroupService.save(dtoFromDb);

                if (dto.getRecordStatus() != null){
                    List<TblMdProductDto> listMdProduct = tblMdProductService.getByProductGroupId((int) dto.getProductGroupId());
                    for (TblMdProductDto item : listMdProduct) {
                        item.setRecordStatus(dto.getRecordStatus());
                        tblMdProductService.save(item);
                    }
                }

                return HelperUtils.responseSuccess();

            }

            else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error TblMdProductGroupController.update()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " ID=" + dto.getProductGroupId();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " ID=" + dto.getProductGroupId();
            /// FOR LOG///

            return HelperUtils.responseErrorInternalServerError(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
        } finally {

            // ***START ACTIVITY LOG***//
            loggingServiceClient.activityLog(
                    LoggingServiceClient.ActivityType.UPDATE,
                    dtoFromDbCurrentData,
                    dtoFromDb,
                    remark,
                    loggingService.getApiOperationValue(new Object() {}),
                    loggingService.getClientIp(),
                    loggingService.getTracer(),
                    loggingService.getMethodName(new Object() {}),
                    loggingService.getToken(),
                    "XXX",
                    status);
            // ***END ACTIVITY LOG***//

            log.info("End TblMdProductGroupController.update()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/product/group/{id}")
    @ApiOperation(value = "Delete TblMdProductGroup", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> delete(@PathVariable("id") int id) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///
        try {
            log.info("Start TblMdProductGroupController.delete()... ");
            TblMdProductGroupDto dtoFromDb = tblMdProductGroupService.getById(id);
            if (dtoFromDb != null) {
                dtoFromDb.setCreateBy(oauthUser.oauthUserId());
                dtoFromDb.setLastUpdatedDate(new Date());
                dtoFromDb.setRecordStatus("I");
                tblMdProductGroupService.save(dtoFromDb);

                List<TblMdProductDto> listMdProduct = tblMdProductService.getByProductGroupId(id);
                for (TblMdProductDto item : listMdProduct) {
                    item.setRecordStatus("I");
                    tblMdProductService.save(item);
                }
                /// FOR LOG///
                status = HttpStatus.OK.value();
                remark = "ID=" + id;
                /// FOR LOG///


                return HelperUtils.responseSuccess();
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error TblMdProductGroupController.delete()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " ID=" + id;
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " ID=" + id;
            /// FOR LOG///

            return HelperUtils.responseErrorInternalServerError(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
        } finally {
            // ***START ACTIVITY LOG***//
            loggingServiceClient.activityLog(
                    LoggingServiceClient.ActivityType.DELETE,
                    null,
                    null,
                    remark,
                    loggingService.getApiOperationValue(new Object() {}),
                    loggingService.getClientIp(),
                    loggingService.getTracer(),
                    loggingService.getMethodName(new Object() {}),
                    loggingService.getToken(),
                    "XXX",
                    status);
            // ***END ACTIVITY LOG***//
            log.info("End TblMdProductGroupController.delete()... ");
        }
    }
}
