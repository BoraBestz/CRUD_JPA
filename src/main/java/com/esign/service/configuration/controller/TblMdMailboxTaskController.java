package com.esign.service.configuration.controller;

import com.esign.service.configuration.client.LoggingService;
import com.esign.service.configuration.client.LoggingServiceClient;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdMailboxTaskDto;
import com.esign.service.configuration.entity.TblMdMailboxTaskEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.service.DocumentTypeService;
import com.esign.service.configuration.service.TblMdMailboxTaskService;
import com.esign.service.configuration.service.TblMdMailTemplateService;
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
@Api(tags = "TbMdMailboxTask")
@Slf4j
public class TblMdMailboxTaskController {
    private TblMdMailboxTaskService tblMdMailboxTaskService;
    private MessageByLocaleService message;
    private ModelMapper modelMapper;
    private LoggingServiceClient loggingServiceClient;
    private LoggingService loggingService;
    private OauthUser oauthUser;
    private DocumentTypeService documentTypeService;
    private TblMdMailTemplateService tblMdMailTemplateService;
    private TblMdSmsTemplateService tblMdSmsTemplateService;

    @Autowired
    public void setTbMdMailboxTaskController(
            TblMdMailboxTaskService tblMdMailboxTaskService,
            MessageByLocaleService message, ModelMapper modelMapper,
            LoggingServiceClient loggingServiceClient,
            LoggingService loggingService, OauthUser oauthUser,
            DocumentTypeService documentTypeService,
            TblMdMailTemplateService tblMdMailTemplateService,
            TblMdSmsTemplateService tblMdSmsTemplateService) {
        this.tblMdMailboxTaskService = tblMdMailboxTaskService;
        this.message = message;
        this.modelMapper = modelMapper;
        this.loggingServiceClient = loggingServiceClient;
        this.loggingService = loggingService;
        this.oauthUser = oauthUser;
        this.documentTypeService = documentTypeService;
        this.tblMdMailTemplateService = tblMdMailTemplateService;
        this.tblMdSmsTemplateService = tblMdSmsTemplateService;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mailbox/task")
    @ApiOperation(value = "Search TbMdMailboxTask", response = TblMdMailboxTaskDto.class, responseContainer = "List")
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
            @RequestParam(required = false, defaultValue = "mailboxTaskCode", value = "sort") String sort,
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
            log.info("Start TbMdMailboxTaskController.searchByCri()... ");
            TblMdMailboxTaskDto dto = modelMapper.map(paramDto, TblMdMailboxTaskDto.class);
            Page<TblMdMailboxTaskDto> byCri = tblMdMailboxTaskService.findByCri(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = paramDto.toString();
            /// FOR LOG///

            return HelperUtils.responseSuccess(byCri);
        } catch (BusinessServiceException e) {
            log.error("Error TbMdMailboxTaskController.searchByCri()... " + e.getMessage());

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
            log.info("End TbMdMailboxTaskController.searchByCri()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mailbox/task/{id}")
    @ApiOperation(value = "Get TbMdMailboxTask By Id", response = TblMdMailboxTaskDto.class)
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
            log.info("Start TbMdMailboxTaskController.getById()... ");
            TblMdMailboxTaskDto dto = tblMdMailboxTaskService.getById(id);

            if (dto.getMailboxTaskId() > 0) {
                dto.setDocumentType(documentTypeService.getById(dto.getDocumentTypeId()));
                dto.setTblMdMailTemplate(tblMdMailTemplateService.getById(dto.getMailTemplateId()));
                dto.setTblMdSmsTemplate(tblMdSmsTemplateService.getById(dto.getSmsTemplateId()));
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
            log.error("Error TbMdMailboxTaskController.getById()... " + e.getMessage());

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
            log.info("End TbMdMailboxTaskController.getById()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/mailbox/task")
    @ApiOperation(value = "New TbMdMailboxTask", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> create(@RequestBody TblMdMailboxTaskDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///
        try {
            log.info("Start TbMdMailboxTaskController.create()... ");

            int count = tblMdMailboxTaskService.checkDuplicateCode(dto);

            if (count > 0) {
                throw new BusinessServiceException(
                        HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
            }
            dto.setCreateDate(new Date());
            dto.setCreatedBy((long) oauthUser.oauthUserId());
            dto.setRecordStatus("A");
            TblMdMailboxTaskEntity save = tblMdMailboxTaskService.save(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = "ID=" + save.getMailboxTaskId() ;
            /// FOR LOG///

            return HelperUtils.responseSuccess(save.getMailTemplateId());
        } catch (BusinessServiceException e) {
            log.error("Error TbMdMailboxTaskController.create()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage()  ;
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() ;
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
            log.info("End TbMdMailboxTaskController.create()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/mailbox/task")
    @ApiOperation(value = "Update TbMdMailboxTask", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> update(@RequestBody TblMdMailboxTaskDto dto) throws BusinessServiceException {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        TblMdMailboxTaskDto dtoFromDb = null;
        TblMdMailboxTaskDto dtoFromDbCurrentData = null;
        /// FOR LOG///
        try {
            log.info("Start TbMdMailboxTaskController.update()... ");
            dtoFromDb = tblMdMailboxTaskService.getById((int) dto.getMailboxTaskId());
            dtoFromDb.setCreateBy(oauthUser.oauthUserId());
            dtoFromDb.setCreateDate(new Date());

            /// FOR LOG///
            /// prepare data
            dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
            /// FOR LOG///

            if (dtoFromDb.getMailboxTaskId() > 0) {
                int count = tblMdMailboxTaskService.checkDuplicateCode(dto);
                if (count > 0) {
                    throw new BusinessServiceException(
                            HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
                }
                // map data from front to db
                modelMapper.map(dto, dtoFromDb);
                tblMdMailboxTaskService.save(dtoFromDb);
                return HelperUtils.responseSuccess();
                }else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error TbMdMailboxTaskController.update()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " ID=" + dto.getMailboxTaskId();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " ID=" + dto.getMailboxTaskId();
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

            log.info("End TbMdMailboxTaskController.update()... ");
        }
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/mailbox/task/{id}")
    @ApiOperation(value = "Delete TbMdMailboxTask", response = void.class)
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
            log.info("Start TbMdMailboxTaskController.delete()... ");
            TblMdMailboxTaskDto dtoFromDb = tblMdMailboxTaskService.getById(id);
            if (dtoFromDb != null) {
                dtoFromDb.setCreateDate(new Date());
                dtoFromDb.setRecordStatus("0");

                /// FOR LOG///
                status = HttpStatus.OK.value();
                remark = "ID=" + id;
                /// FOR LOG///

                tblMdMailboxTaskService.save(dtoFromDb);
                return HelperUtils.responseSuccess();
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error TbMdMailboxTaskController.delete()... " + e.getMessage());

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
            log.info("End TbMdMailboxTaskController.delete()... ");
        }
    }
}
