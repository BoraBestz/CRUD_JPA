package com.esign.service.configuration.controller;

import com.esign.service.configuration.client.LoggingService;
import com.esign.service.configuration.client.LoggingServiceClient;
import com.esign.service.configuration.client.LoggingServiceClient.ActivityType;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.TermTypeDto;
import com.esign.service.configuration.entity.TermTypeEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.service.TermTypeService;
import com.esign.service.configuration.utils.HelperUtils;
import com.esign.service.configuration.utils.MessageByLocaleService;
import com.esign.service.configuration.utils.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Date;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/api")
@Api(tags = "TermType")
@Slf4j
public class TermTypeController extends LoggingService {

    private TermTypeService termTypeService;
    private MessageByLocaleService message;
    private ModelMapper modelMapper;
    private LoggingServiceClient loggingServiceClient;
    private LoggingService loggingService;
    private OauthUser oauthUser;

    @Autowired
    public void setTermTypeController(
            TermTypeService termTypeService,
            MessageByLocaleService message, ModelMapper modelMapper,
            LoggingServiceClient loggingServiceClient,
            LoggingService loggingService, OauthUser oauthUser) {
        this.termTypeService = termTypeService;
        this.message = message;
        this.modelMapper = modelMapper;
        this.loggingServiceClient = loggingServiceClient;
        this.loggingService = loggingService;
        this.oauthUser = oauthUser;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/term/type/{id}")
    @ApiOperation(value = "Get TermType By Id", response = TermTypeDto.class)
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
            log.info("Start TermTypeController.getById()... ");
            TermTypeDto dto = termTypeService.getById(id);

            if (dto.getTermTypeId() > 0) {
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
            log.error("Error TermTypeController.getById()... " + e.getMessage());

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
                    ActivityType.VIEW,
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
            log.info("End TermTypeController.getById()... ");
        }
    }

    //@PreAuthorize("hasAnyAuthorityCustom('ARMTA')")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/term/type")
    @ApiOperation(value = "New TermType", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> create(@RequestBody TermTypeDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        /// FOR LOG///
        try {
            log.info("Start TermTypeController.create()... ");

            int count = termTypeService.checkDuplicateCode(dto);

            if (count > 0) {
                throw new BusinessServiceException(
                        HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
            }
            dto.setCreateDt(new Date());
            dto.setCreateBy(oauthUser.oauthUserId());
            dto.setStatus(1);
            TermTypeEntity save = termTypeService.save(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = "ID=" + save.getTermTypeId() + " NAME=" + save.getTermTypeCode();
            /// FOR LOG///

            return HelperUtils.responseSuccess(save.getTermTypeId());
        } catch (BusinessServiceException e) {
            log.error("Error TermTypeController.create()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " NAME=" + dto.getTermTypeCode();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " NAME=" + dto.getTermTypeCode();
            /// FOR LOG///

            return HelperUtils.responseErrorInternalServerError(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
        } finally {
            // ***START ACTIVITY LOG***//
            loggingServiceClient.activityLog(
                    ActivityType.CREATE,
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
            log.info("End TermTypeController.create()... ");
        }
    }
    //@PreAuthorize("hasAnyAuthorityCustom('ARMTE')")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/term/type")
    @ApiOperation(value = "Update TermType", response = void.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 500, message = "Error")
            })
    public ResponseEntity<ResponseModel> update(@RequestBody TermTypeDto dto) {
        /// FOR LOG///
        int status = 0;
        String remark = "";
        TermTypeDto dtoFromDb = null;
        TermTypeDto dtoFromDbCurrentData = null;
        /// FOR LOG///
        try {
            log.info("Start TermTypeController.update()... ");
            dtoFromDb = termTypeService.getById(dto.getTermTypeId());
            dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
            dtoFromDb.setUpdateDt(new Date());

            /// FOR LOG///
            /// prepare data
            dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
            /// FOR LOG///

            if (dtoFromDb.getTermTypeId() > 0) {
                if (dto.getTermTypeCode() != null
                        && !dtoFromDb.getTermTypeCode().equalsIgnoreCase(dto.getTermTypeCode())) {
                    int count = termTypeService.checkDuplicateCode(dto);
                    if (count > 0) {
                        throw new BusinessServiceException(
                                HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
                    }
                }

                // map data from front to db
                modelMapper.map(dto, dtoFromDb);
                dtoFromDb.setStatus(1);
                termTypeService.save(dtoFromDb);

                return HelperUtils.responseSuccess();
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error TermTypeController.update()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage() + " ID=" + dto.getTermTypeId();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());

            /// FOR LOG///
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            remark = e.getMessage() + " ID=" + dto.getTermTypeId();
            /// FOR LOG///

            return HelperUtils.responseErrorInternalServerError(
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
        } finally {

            // ***START ACTIVITY LOG***//
            loggingServiceClient.activityLog(
                    ActivityType.UPDATE,
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

            log.info("End TermTypeController.update()... ");
        }
    }

    //@PreAuthorize("hasAnyAuthorityCustom('ARMTD')")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/term/type/{id}")
    @ApiOperation(value = "Delete TermType", response = void.class)
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
            log.info("Start TermTypeController.delete()... ");
            TermTypeDto dtoFromDb = termTypeService.getById(id);
            if (dtoFromDb != null) {
                dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
                dtoFromDb.setUpdateDt(new Date());
                dtoFromDb.setStatus(0);

                /// FOR LOG///
                status = HttpStatus.OK.value();
                remark = "ID=" + id;
                /// FOR LOG///

                termTypeService.save(dtoFromDb);
                return HelperUtils.responseSuccess();
            } else {
                throw new BusinessServiceException(
                        HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
            }
        } catch (BusinessServiceException e) {
            log.error("Error TermTypeController.delete()... " + e.getMessage());

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
                    ActivityType.DELETE,
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
            log.info("End TermTypeController.delete()... ");
        }
    }

    //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/term/type")
    @ApiOperation(value = "Search TermType", response = TermTypeDto.class, responseContainer = "List")
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
            @RequestParam(required = false, defaultValue = "termTypeCode", value = "sort") String sort,
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
            log.info("Start TermTypeController.searchByCri()... ");
            TermTypeDto dto = modelMapper.map(paramDto, TermTypeDto.class);
            Page<TermTypeDto> byCri = termTypeService.findByCri(dto);

            /// FOR LOG///
            status = HttpStatus.OK.value();
            remark = paramDto.toString();
            /// FOR LOG///

            return HelperUtils.responseSuccess(byCri);
        } catch (BusinessServiceException e) {
            log.error("Error TermTypeController.searchByCri()... " + e.getMessage());

            /// FOR LOG///
            status = e.getHttpStatus().value();
            remark = e.getMessage();
            /// FOR LOG///

            return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
        } finally {
            // ***START ACTIVITY LOG***//
            loggingServiceClient.activityLog(
                    ActivityType.SEARCH,
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
            log.info("End TermTypeController.searchByCri()... ");
        }
    }

}
