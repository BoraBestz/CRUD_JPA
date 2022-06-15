package com.esign.service.configuration.controller;

import com.esign.service.configuration.client.LoggingService;
import com.esign.service.configuration.client.LoggingServiceClient;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdDocumentTemplateConfigDto;
import com.esign.service.configuration.entity.TblMdDocumentTemplateConfigEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.service.TblMdDocumentTemplateConfigService;
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
@Api(tags = "TblMdDocumentTemplateConfig")
@Slf4j
public class TblMdDocumentTemplateConfigController {
    private TblMdDocumentTemplateConfigService tblMdDocumentTemplateConfigService;
    private MessageByLocaleService message;
    private ModelMapper modelMapper;
    private LoggingServiceClient loggingServiceClient;
    private LoggingService loggingService;
    private OauthUser oauthUser;

    @Autowired
    public void setTblMdDocumentTemplateConfigController(
            TblMdDocumentTemplateConfigService tblMdDocumentTemplateConfigService,
            MessageByLocaleService message, ModelMapper modelMapper,
            LoggingServiceClient loggingServiceClient,
            LoggingService loggingService, OauthUser oauthUser) {
        this.tblMdDocumentTemplateConfigService = tblMdDocumentTemplateConfigService;
        this.message = message;
        this.modelMapper = modelMapper;
        this.loggingServiceClient = loggingServiceClient;
        this.loggingService = loggingService;
        this.oauthUser = oauthUser;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/document/template/config")
    @ApiOperation(value = "Search TblMdDocumentTemplateConfig", response = TblMdDocumentTemplateConfigDto.class, responseContainer = "List")
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
            @RequestParam(required = false, defaultValue = "documentTemplateCode", value = "sort") String sort,
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
            log.info("Start TblMdDocumentTemplateConfigController.searchByCri()... ");
            TblMdDocumentTemplateConfigDto dto = modelMapper.map(paramDto, TblMdDocumentTemplateConfigDto.class);
            Page<TblMdDocumentTemplateConfigDto> byCri = tblMdDocumentTemplateConfigService.findByCri(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = paramDto.toString();
            /// FOR LOG///

            return HelperUtils.responseSuccess(byCri);
        } catch (BusinessServiceException e) {
            log.error("Error TblMdDocumentTemplateConfigController.searchByCri()... " + e.getMessage());

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
            log.info("End TblMdDocumentTemplateConfigController.searchByCri()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/document/template/config/{id}")
    @ApiOperation(value = "Get TblMdDocumentTemplateConfig By Id", response = TblMdDocumentTemplateConfigDto.class)
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
            log.info("Start TblMdDocumentTemplateConfigController.getById()... ");
            TblMdDocumentTemplateConfigDto dto = tblMdDocumentTemplateConfigService.getById(id);

            if (dto.getDocumentTemplateConfigId() > 0) {
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
            log.error("Error TblMdDocumentTemplateConfigController.getById()... " + e.getMessage());

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
            log.info("End TblMdDocumentTemplateConfigController.getById()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/document/template/config")
    @ApiOperation(value = "New TblMdDocumentTemplateConfig", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> create(@RequestBody TblMdDocumentTemplateConfigDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///
        try {
            log.info("Start TblMdDocumentTemplateConfigController.create()... ");

            int count = tblMdDocumentTemplateConfigService.checkDuplicateCode(dto);

            if (count > 0) {
                throw new BusinessServiceException(
                        HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
            }
            dto.setStatus(1);
            TblMdDocumentTemplateConfigEntity save = tblMdDocumentTemplateConfigService.save(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = "ID=" + save.getDocumentTemplateConfigId() + " NAME=" + save.getDocumentTemplateCode();
            /// FOR LOG///

            return HelperUtils.responseSuccess(save.getDocumentTemplateConfigId());
        } catch (BusinessServiceException e) {
            log.error("Error TblMdDocumentTemplateConfigController.create()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " NAME=" + dto.getDocumentTemplateCode();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " NAME=" + dto.getDocumentTemplateCode();
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
            log.info("End TblMdDocumentTemplateConfigController.create()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/document/template/config")
    @ApiOperation(value = "Update TblMdDocumentTemplateConfig", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> update(@RequestBody TblMdDocumentTemplateConfigDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        TblMdDocumentTemplateConfigDto dtoFromDb = null;
        TblMdDocumentTemplateConfigDto dtoFromDbCurrentData = null;
        /// FOR LOG///
        try {
            log.info("Start TblMdDocumentTemplateConfigController.update()... ");
            dtoFromDb = tblMdDocumentTemplateConfigService.getById(dto.getDocumentTemplateConfigId());


            /// FOR LOG///
            /// prepare data
            dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
            /// FOR LOG///

            if (dtoFromDb.getDocumentTemplateConfigId() > 0) {
                if (dto.getDocumentTemplateCode() != null
                        && !dtoFromDb.getDocumentTemplateCode().equals(dto.getDocumentTemplateCode())) {
                    int count = tblMdDocumentTemplateConfigService.checkDuplicateCode(dto);
                    if (count > 0) {
                        throw new BusinessServiceException(
                                HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
                    }
                }

                // map data from front to db
                modelMapper.map(dto, dtoFromDb);
                tblMdDocumentTemplateConfigService.save(dtoFromDb);

                return HelperUtils.responseSuccess();
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error TblMdDocumentTemplateConfigController.update()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " ID=" + dto.getDocumentTemplateConfigId();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " ID=" + dto.getDocumentTemplateConfigId();
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

            log.info("End TblMdDocumentTemplateConfigController.update()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/document/template/config/{id}")
    @ApiOperation(value = "Delete TblMdDocumentTemplateConfig", response = void.class)
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
            log.info("Start TblMdDocumentTemplateConfigController.delete()... ");
            TblMdDocumentTemplateConfigDto dtoFromDb = tblMdDocumentTemplateConfigService.getById(id);
            if (dtoFromDb != null) {
                dtoFromDb.setStatus(0);

                /// FOR LOG///
                status = HttpStatus.OK.value();
                remark = "ID=" + id;
                /// FOR LOG///

                tblMdDocumentTemplateConfigService.save(dtoFromDb);
                return HelperUtils.responseSuccess();
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error TblMdDocumentTemplateConfigController.delete()... " + e.getMessage());

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
            log.info("End TblMdDocumentTemplateConfigController.delete()... ");
        }
    }

}
