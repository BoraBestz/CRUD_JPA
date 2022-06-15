package com.esign.service.configuration.controller;

import com.esign.service.configuration.client.LoggingService;
import com.esign.service.configuration.client.LoggingServiceClient;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.RdErrorCriteriaDto;
import com.esign.service.configuration.entity.RdErrorCriteriaEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.service.RdErrorCriteriaService;
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

import java.util.Map;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/api")
@Api(tags = "RdErrorCriteria")
@Slf4j
public class RdErrorCriteriaController {

    private RdErrorCriteriaService rdErrorCriteriaService;
    private MessageByLocaleService message;
    private OauthUser oauthUser;
    private LoggingServiceClient loggingServiceClient;
    private LoggingService loggingService;
    private ModelMapper modelMapper;

    @Autowired
    public void setLanguageController(RdErrorCriteriaService rdErrorCriteriaService,
                                      MessageByLocaleService message,
                                      OauthUser oauthUser,
                                      LoggingServiceClient loggingServiceClient,
                                      LoggingService loggingService,
                                      ModelMapper modelMapper){
        this.rdErrorCriteriaService = rdErrorCriteriaService;
        this.message = message;
        this.oauthUser = oauthUser;
        this.loggingServiceClient =loggingServiceClient;
        this.loggingService = loggingService;
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/rd/error/criteria")
    @ApiOperation(value = "Search rdErrorCriteria", response = RdErrorCriteriaDto.class, responseContainer = "List")
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
            @RequestParam(required = false, defaultValue = "criteriaDetail", value = "sort") String sort,
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
            log.info("Start RdErrorCriteriaController.searchByCri()... ");
            RdErrorCriteriaDto dto = modelMapper.map(paramDto, RdErrorCriteriaDto.class);
            Page<RdErrorCriteriaDto> byCri = rdErrorCriteriaService.findByCri(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = paramDto.toString();
            /// FOR LOG///

            return HelperUtils.responseSuccess(byCri);
        } catch (BusinessServiceException e) {
            log.error("Error RdErrorCriteriaController.searchByCri()... " + e.getMessage());

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
                    loggingService.getClientIp(),
                    loggingService.getTracer(),
                    loggingService.getMethodName(new Object() {}),
                    loggingService.getToken(),
                    "XXX",
                    status);
            // ***END ACTIVITY LOG***//
            log.info("End RdErrorCriteriaController.searchByCri()... ");
        }
    }

    //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/rd/error/criteria/{id}")
    @ApiOperation(value = "Get RdErrorCriteria By Id", response = RdErrorCriteriaDto.class)
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
            log.info("Start RdErrorCriteriaController.getById()... ");
            RdErrorCriteriaDto dto = rdErrorCriteriaService.getById(id);

            if (dto.getId() > 0) {
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
            log.error("Error RdErrorCriteriaController.getById()... " + e.getMessage());

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
                    loggingService.getApiOperationValue(new Object() {}),
                    loggingService.getClientIp(),
                    loggingService.getTracer(),
                    loggingService.getMethodName(new Object() {}),
                    loggingService.getToken(),
                    "XXX",
                    status);
            // ***END ACTIVITY LOG***//
            log.info("End RdErrorCriteriaController.getById()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/rd/error/criteria")
    @ApiOperation(value = "New RdError", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> addConfig(@RequestBody RdErrorCriteriaDto dto){
        //FOR LOG//
        int status = 0;
        String remark = "";
        //FOR LOG//
        try {
            int count = rdErrorCriteriaService.checkDuplicate(dto);
            if (count > 0) {
                throw new BusinessServiceException(
                        HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
            }
            dto.setCriteriaDetail(dto.getCriteriaDetail());
            RdErrorCriteriaEntity save = rdErrorCriteriaService.save(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = "ID=" + save.getId();
            /// FOR LOG///

            return HelperUtils.responseSuccess(save.getId());
        } catch (BusinessServiceException e) {
            log.error("Error RdErrorCriteriaController.create()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            //remark = e.getMessage() + " NAME=" + dto.getConfigName();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        }catch (Exception e){
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            //remark = e.getMessage() + " NAME=" + dto.getConfigName();
            /// FOR LOG///
            return HelperUtils.responseErrorInternalServerError(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
        }finally {
            // ***START ACTIVITY LOG***//
            loggingServiceClient.activityLog(
                    LoggingServiceClient.ActivityType.CREATE,
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
            log.info("End RdErrorCriteriaController.create()... ");
        }
    }

    //@PreAuthorize("hasAnyAuthorityCustom('ARMTE')")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/rd/error/criteria")
    @ApiOperation(value = "Update RdErrorCriteria", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> update(@RequestBody RdErrorCriteriaDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        RdErrorCriteriaDto dtoFromDb = null;
        RdErrorCriteriaDto dtoFromDbCurrentData = null;
        /// FOR LOG///
        try {
            log.info("Start RdErrorCriteriaController.update()... ");
            dtoFromDb = rdErrorCriteriaService.getById(dto.getId());
            dtoFromDb.setCriteriaDetail(dto.getCriteriaDetail());

            /// FOR LOG///
            /// prepare data
            dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
            /// FOR LOG///

            /*if (dtoFromDb.getId() > 0) {
                    int count = rdErrorCriteriaService.checkDuplicate(dto);
                    if (count > 0) {
                        throw new BusinessServiceException(
                                HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
                    }

                // map data from front to db
                modelMapper.map(dto, dtoFromDb);
                rdErrorCriteriaService.save(dtoFromDb);

                return HelperUtils.responseSuccess();
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }*/
            modelMapper.map(dto, dtoFromDb);
            rdErrorCriteriaService.save(dtoFromDb);
            return HelperUtils.responseSuccess();
        } catch (BusinessServiceException e) {
            log.error("Error RdErrorCriteriaController.update()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " ID=" + dto.getId();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " ID=" + dto.getId();
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

            log.info("End RdErrorCriteriaController.update()... ");
        }
    }

    //@PreAuthorize("hasAnyAuthorityCustom('ARMTD')")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/rd/error/criteria/{id}")
    @ApiOperation(value = "Delete RdErrorCriteria", response = void.class)
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
            log.info("Start RdErrorCriteriaController.delete()... ");
            RdErrorCriteriaDto dtoFromDb = rdErrorCriteriaService.getById(id);
            if (dtoFromDb != null) {
                //dtoFromDb.setUpdateBy(dtoFromDb.getUpdateBy());

                /// FOR LOG///
                status = HttpStatus.OK.value();
                remark = "ID=" + id;
                /// FOR LOG///

                rdErrorCriteriaService.save(dtoFromDb);
                return HelperUtils.responseSuccess();
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error RdErrorCriteriaController.delete()... " + e.getMessage());

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
            log.info("End RdErrorCriteriaController.delete()... ");
        }
    }

}
