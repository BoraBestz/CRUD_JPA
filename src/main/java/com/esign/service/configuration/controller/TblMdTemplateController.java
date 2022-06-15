package com.esign.service.configuration.controller;

import com.esign.service.configuration.client.LoggingService;
import com.esign.service.configuration.client.LoggingServiceClient;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TblMdTemplateDto;
import com.esign.service.configuration.entity.TblMdTemplateEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.service.DocumentTypeService;
import com.esign.service.configuration.service.TblMdTemplateService;
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
@Api(tags = "TblMdTemplate")
@Slf4j
public class TblMdTemplateController extends LoggingService {

    private TblMdTemplateService tblMdTemplateService;
    private MessageByLocaleService message;
    private ModelMapper modelMapper;
    private LoggingServiceClient loggingServiceClient;
    private LoggingService loggingService;
    private OauthUser oauthUser;
    private DocumentTypeService documentTypeService;

    @Autowired
    public void setTblMdTemplateController(
            TblMdTemplateService tblMdTemplateService,
            MessageByLocaleService message, ModelMapper modelMapper,
            LoggingServiceClient loggingServiceClient,
            LoggingService loggingService, OauthUser oauthUser,
            DocumentTypeService documentTypeService) {
        this.tblMdTemplateService = tblMdTemplateService;
        this.message = message;
        this.modelMapper = modelMapper;
        this.loggingServiceClient = loggingServiceClient;
        this.loggingService = loggingService;
        this.oauthUser = oauthUser;
        this.documentTypeService = documentTypeService;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/template/{id}")
    @ApiOperation(value = "Get TblMdTemplate By Id", response = TblMdTemplateDto.class)
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
            log.info("Start TblMdTemplateController.getById()... ");
            TblMdTemplateDto dto = tblMdTemplateService.getById(id);

            if (dto.getTemplateId() > 0) {
                dto.setDocumentTypeDto(documentTypeService.getById(dto.getDocumentTypeId()));

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
            log.error("Error TblMdTemplateController.getById()... " + e.getMessage());

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
                    getApiOperationValue(new Object() {}),
                    getClientIp(),
                    getTracer(),
                    getMethodName(new Object() {}),
                    getToken(),
                    "XXX",
                    status);
            // ***END ACTIVITY LOG***//
            log.info("End TblMdTemplateController.getById()... ");
        }
    }

    //@PreAuthorize("hasAnyAuthorityCustom('ARMTA')")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/template")
    @ApiOperation(value = "New TblMdTemplate", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> create(@RequestBody TblMdTemplateDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///
        try {
            log.info("Start TblMdTemplateController.create()... ");

            int count = tblMdTemplateService.checkDuplicateCode(dto);

            if (count > 0) {
                throw new BusinessServiceException(
                        HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
            }
            dto.setCreateDt(new Date());
            dto.setCreateBy(oauthUser.oauthUserId());
            dto.setStatus("A");
            TblMdTemplateEntity save = tblMdTemplateService.save(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = "ID=" + save.getTemplateId() + " NAME=" + save.getTemplateCode();
            /// FOR LOG///

            return HelperUtils.responseSuccess(save.getTemplateId());
        } catch (BusinessServiceException e) {
            log.error("Error TblMdTemplateController.create()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " NAME=" + dto.getTemplateCode();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " NAME=" + dto.getTemplateCode();
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
                    getApiOperationValue(new Object() {}),
                    getClientIp(),
                    getTracer(),
                    getMethodName(new Object() {}),
                    getToken(),
                    "XXX",
                    status);
            // ***END ACTIVITY LOG***//
            log.info("End TblMdTemplateController.create()... ");
        }
    }
    //@PreAuthorize("hasAnyAuthorityCustom('ARMTE')")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/template")
    @ApiOperation(value = "Update TblMdTemplate", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> update(@RequestBody TblMdTemplateDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        TblMdTemplateDto dtoFromDb = null;
        TblMdTemplateDto dtoFromDbCurrentData = null;
        /// FOR LOG///
        try {
            log.info("Start TblMdTemplateController.update()... ");
            dtoFromDb = tblMdTemplateService.getById(dto.getTemplateId());
            dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
            dtoFromDb.setUpdateDt(new Date());

            /// FOR LOG///
            /// prepare data
            dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
            /// FOR LOG///

            if (dtoFromDb.getTemplateId() > 0) {
                if (dto.getTemplateCode() != null
                        && !dtoFromDb.getTemplateCode().equalsIgnoreCase(dto.getTemplateCode())) {
                    int count = tblMdTemplateService.checkDuplicateCode(dto);
                    if (count > 0) {
                        throw new BusinessServiceException(
                                HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
                    }
                }

                // map data from front to db
                modelMapper.map(dto, dtoFromDb);
                dtoFromDb.setStatus("A");
                tblMdTemplateService.save(dtoFromDb);

                return HelperUtils.responseSuccess();
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error TblMdTemplateController.update()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " ID=" + dto.getTemplateId();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " ID=" + dto.getTemplateId();
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
                    getApiOperationValue(new Object() {}),
                    getClientIp(),
                    getTracer(),
                    getMethodName(new Object() {}),
                    getToken(),
                    "XXX",
                    status);
            // ***END ACTIVITY LOG***//

            log.info("End TblMdTemplateController.update()... ");
        }
    }

    //@PreAuthorize("hasAnyAuthorityCustom('ARMTD')")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/template/{id}")
    @ApiOperation(value = "Delete TblMdTemplate", response = void.class)
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
            log.info("Start TblMdTemplateController.delete()... ");
            TblMdTemplateDto dtoFromDb = tblMdTemplateService.getById(id);
            if (dtoFromDb != null) {
                dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
                dtoFromDb.setUpdateDt(new Date());
                dtoFromDb.setStatus("I");

                /// FOR LOG///
                status = HttpStatus.OK.value();
                remark = "ID=" + id;
                /// FOR LOG///

                tblMdTemplateService.save(dtoFromDb);
                return HelperUtils.responseSuccess();
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error TblMdTemplateController.delete()... " + e.getMessage());

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
                    getApiOperationValue(new Object() {}),
                    getClientIp(),
                    getTracer(),
                    getMethodName(new Object() {}),
                    getToken(),
                    "XXX",
                    status);
            // ***END ACTIVITY LOG***//
            log.info("End TblMdTemplateController.delete()... ");
        }
    }

    //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/template")
    @ApiOperation(value = "Search TblMdTemplate", response = TblMdTemplateDto.class, responseContainer = "List")
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
            @RequestParam(required = false, defaultValue = "templateCode", value = "sort") String sort,
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
            log.info("Start TblMdTemplateController.searchByCri()... ");
            TblMdTemplateDto dto = modelMapper.map(paramDto, TblMdTemplateDto.class);
            Page<TblMdTemplateDto> byCri = tblMdTemplateService.findByCri(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = paramDto.toString();
            /// FOR LOG///

            return HelperUtils.responseSuccess(byCri);
        } catch (BusinessServiceException e) {
            log.error("Error TblMdTemplateController.searchByCri()... " + e.getMessage());

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
                    getApiOperationValue(new Object() {}),
                    getClientIp(),
                    getTracer(),
                    getMethodName(new Object() {}),
                    getToken(),
                    "XXX",
                    status);
            // ***END ACTIVITY LOG***//
            log.info("End TblMdTemplateController.searchByCri()... ");
        }
    }

}
