package com.esign.service.configuration.controller;

import com.esign.service.configuration.client.LoggingService;
import com.esign.service.configuration.client.LoggingServiceClient;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdSmsTemplateDto;
import com.esign.service.configuration.entity.TblMdSmsTemplateEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.service.DocumentTypeService;
import com.esign.service.configuration.service.TblMdMailboxTaskService;
import com.esign.service.configuration.service.TblMdSmsTemplateService;
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
import java.util.Map;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/api")
@Api(tags = "TblMdProduct")
@Slf4j
public class TblMdSmsTemplateController {
    private TblMdSmsTemplateService tblMdSmsTemplateService;
    private MessageByLocaleService message;
    private ModelMapper modelMapper;
    private LoggingServiceClient loggingServiceClient;
    private LoggingService loggingService;
    private OauthUser oauthUser;
    private DocumentTypeService documentTypeService;
    private TblMdMailboxTaskService tblMdMailboxTaskService;

    @Autowired
    public void setTblMdSmsTemplateController(
            TblMdSmsTemplateService tblMdSmsTemplateService,
            MessageByLocaleService message, ModelMapper modelMapper,
            LoggingServiceClient loggingServiceClient,
            LoggingService loggingService, OauthUser oauthUser,
            DocumentTypeService documentTypeService,
            TblMdMailboxTaskService tblMdMailboxTaskService) {
        this.tblMdSmsTemplateService = tblMdSmsTemplateService;
        this.message = message;
        this.modelMapper = modelMapper;
        this.loggingServiceClient = loggingServiceClient;
        this.loggingService = loggingService;
        this.oauthUser = oauthUser;
        this.documentTypeService = documentTypeService;
        this.tblMdMailboxTaskService = tblMdMailboxTaskService;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/smstemplate")
    @ApiOperation(value = "Search TblMdSmsTemplate", response = TblMdSmsTemplateDto.class, responseContainer = "List")
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
            @RequestParam(required = false, defaultValue = "smsTemplateCode", value = "sort") String sort,
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
            log.info("Start TblMdSmsTemplateController.searchByCri()... ");
            TblMdSmsTemplateDto dto = modelMapper.map(paramDto, TblMdSmsTemplateDto.class);
            Page<TblMdSmsTemplateDto> byCri = tblMdSmsTemplateService.findByCri(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = paramDto.toString();
            /// FOR LOG///

            return HelperUtils.responseSuccess(byCri);
        } catch (BusinessServiceException e) {
            log.error("Error TblMdSmsTemplateController.searchByCri()... " + e.getMessage());

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
            log.info("End TblMdSmsTemplateController.searchByCri()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/smstemplate/{id}")
    @ApiOperation(value = "Get TblMdSmsTemplate By Id", response = TblMdSmsTemplateDto.class)
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
            log.info("Start TblMdSmsTemplateController.getById()... ");
            TblMdSmsTemplateDto dto = tblMdSmsTemplateService.getById(id);

            if (dto.getSmsTemplateId() > 0) {
                dto.setDocumentType(documentTypeService.getById(dto.getDocumentTypeId()));
                dto.setTblMdMailboxTask(tblMdMailboxTaskService.findBySmsTemplateId((int) dto.getSmsTemplateId()));
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
            log.error("Error TblMdSmsTemplateController.getById()... " + e.getMessage());

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
            log.info("End TblMdSmsTemplateController.getById()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/smstemplate")
    @ApiOperation(value = "New TblMdSmsTemplate", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> create(@RequestBody TblMdSmsTemplateDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///
        try {
            log.info("Start TblMdSmsTemplateController.create()... ");

            int count = tblMdSmsTemplateService.checkDuplicateCode(dto);

            if (count > 0) {
                throw new BusinessServiceException(
                        HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
            }
            dto.setCreateDate(new Date());
            dto.setCreateBy(oauthUser.oauthUserId());
            dto.setRecordStatus("A");
            TblMdSmsTemplateEntity save = tblMdSmsTemplateService.save(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = "ID=" + save.getSmsTemplateId() + " NAME=" + save.getSmsTemplateName();
            /// FOR LOG///

            return HelperUtils.responseSuccess(save.getSmsTemplateId());
        } catch (BusinessServiceException e) {
            log.error("Error TblMdSmsTemplateController.create()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " NAME=" + dto.getSmsTemplateName();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " NAME=" + dto.getSmsTemplateName();
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
            log.info("End TblMdSmsTemplateController.create()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/smstemplate")
    @ApiOperation(value = "Update TblMdSmsTemplate", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> update(@RequestBody TblMdSmsTemplateDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        TblMdSmsTemplateDto dtoFromDb = null;
        TblMdSmsTemplateDto dtoFromDbCurrentData = null;
        /// FOR LOG///
        try {
            log.info("Start TblMdSmsTemplateController.update()... ");
            dtoFromDb = tblMdSmsTemplateService.getById((int) dto.getSmsTemplateId());
            dtoFromDb.setCreateBy( oauthUser.oauthUserId());
            dtoFromDb.setCreateDate(new Date());

            /// FOR LOG///
            /// prepare data
            dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
            /// FOR LOG///

            if (dtoFromDb.getSmsTemplateId() > 0) {
                if (dto.getSmsTemplateCode() != null
                        && !dtoFromDb.getSmsTemplateCode().equalsIgnoreCase(dto.getSmsTemplateCode())) {
                    int count = tblMdSmsTemplateService.checkDuplicateCode(dto);
                    if (count > 0) {
                        throw new BusinessServiceException(
                                HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
                    }
                }

                // map data from front to db
                modelMapper.map(dto, dtoFromDb);
                tblMdSmsTemplateService.save(dtoFromDb);

                return HelperUtils.responseSuccess();
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error TblMdSmsTemplateController.update()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " ID=" + dto.getSmsTemplateId();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " ID=" + dto.getSmsTemplateId();
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

            log.info("End TblMdSmsTemplateController.update()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/smstemplate/{id}")
    @ApiOperation(value = "Delete TblMdSmsTemplate", response = void.class)
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
            log.info("Start TblMdSmsTemplateController.delete()... ");
            TblMdSmsTemplateDto dtoFromDb = tblMdSmsTemplateService.getById(id);
            if (dtoFromDb != null) {
                dtoFromDb.setCreateBy((int) oauthUser.oauthUserId());
                dtoFromDb.setLastUpdatedDate(new Date());
                dtoFromDb.setRecordStatus("I");

                /// FOR LOG///
                status = HttpStatus.OK.value();
                remark = "ID=" + id;
                /// FOR LOG///

                tblMdSmsTemplateService.save(dtoFromDb);
                return HelperUtils.responseSuccess();
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error TblMdSmsTemplateController.delete()... " + e.getMessage());

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
            log.info("End TblMdSmsTemplateController.delete()... ");
        }
    }
}
