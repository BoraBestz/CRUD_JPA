package com.esign.service.configuration.controller.postal;

import com.esign.service.configuration.client.LoggingService;
import com.esign.service.configuration.client.LoggingServiceClient;
import com.esign.service.configuration.client.LoggingServiceClient.ActivityType;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.postal.PostalSubDistrictDto;
import com.esign.service.configuration.entity.postal.PostalSubDistrictEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.service.postal.PostalCodeService;
import com.esign.service.configuration.service.postal.PostalSubDistrictService;
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
@Api(tags = "SubDistrict")
@Slf4j
public class PostalSubDistrictController extends LoggingService {

  private PostalSubDistrictService subDistrictService;
  private MessageByLocaleService message;
  private ModelMapper modelMapper;
  private LoggingServiceClient loggingServiceClient;
  private LoggingService loggingService;
  private OauthUser oauthUser;
  private PostalCodeService postalCodeService;

  @Autowired
  public void setPostalSubDistrictController(
      PostalSubDistrictService subDistrictService,
      MessageByLocaleService message, ModelMapper modelMapper,
      LoggingServiceClient loggingServiceClient,
      LoggingService loggingService, OauthUser oauthUser,
      PostalCodeService postalCodeService) {
    this.subDistrictService = subDistrictService;
    this.message = message;
    this.modelMapper = modelMapper;
    this.loggingServiceClient = loggingServiceClient;
    this.loggingService = loggingService;
    this.oauthUser = oauthUser;
    this.postalCodeService = postalCodeService;
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/sub/district")
  @ApiOperation(value = "Search SubDistrict", response = PostalSubDistrictDto.class, responseContainer = "List")
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
      @RequestParam(required = false, defaultValue = "code", value = "sort") String sort,
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
      log.info("Start PostalSubDistrictController.searchByCri()... ");
      PostalSubDistrictDto dto = modelMapper.map(paramDto, PostalSubDistrictDto.class);
      Page<PostalSubDistrictDto> byCri = subDistrictService.findByCri(dto);

      /// FOR LOG///
      status = HttpStatus.OK.value();
      remark = paramDto.toString();
      /// FOR LOG///

      return HelperUtils.responseSuccess(byCri);
    } catch (BusinessServiceException e) {
      log.error("Error PostalSubDistrictController.searchByCri()... " + e.getMessage());

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
      log.info("End PostalSubDistrictController.searchByCri()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/sub/district/{id}")
  @ApiOperation(value = "Get SubDistrict By Id", response = PostalSubDistrictDto.class)
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
      log.info("Start PostalSubDistrictController.getById()... ");
      PostalSubDistrictDto dto = subDistrictService.getById(id);

      if (dto.getId() > 0) {
        dto.setPostalCode(postalCodeService.findBySubDistrictId(dto.getId()));
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
      log.error("Error PostalSubDistrictController.getById()... " + e.getMessage());

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
      log.info("End PostalSubDistrictController.getById()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTA')")
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/sub/district")
  @ApiOperation(value = "New SubDistrict", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseModel> create(@RequestBody PostalSubDistrictDto dto) {
    /// FOR LOG///
    int status = 0;
    String remark = "";
    /// FOR LOG///
    try {
      log.info("Start PostalSubDistrictController.create()... ");

      int count = subDistrictService.checkDuplicateCode(dto);

      if (count > 0) {
        throw new BusinessServiceException(
            HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
      }
      dto.setCreateDt(new Date());
      dto.setCreateBy(oauthUser.oauthUserId());
      dto.setStatus("A");
      PostalSubDistrictEntity save = subDistrictService.save(dto);

      /// FOR LOG///
      status = HttpStatus.OK.value();
      remark = "ID=" + save.getId() + " NAME=" + save.getNameTh();
      /// FOR LOG///

      return HelperUtils.responseSuccess(save.getId());
    } catch (BusinessServiceException e) {
      log.error("Error PostalSubDistrictController.create()... " + e.getMessage());

      /// FOR LOG///
      status = e.getHttpStatus().value();
      remark = e.getMessage() + " NAME=" + dto.getNameTh();
      /// FOR LOG///

      return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage());

      /// FOR LOG///
      status = HttpStatus.INTERNAL_SERVER_ERROR.value();
      remark = e.getMessage() + " NAME=" + dto.getNameTh();
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
      log.info("End PostalSubDistrictController.create()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTE')")
  @PreAuthorize("isAuthenticated()")
  @PutMapping("/sub/district")
  @ApiOperation(value = "Update SubDistrict", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseModel> update(@RequestBody PostalSubDistrictDto dto) {
    /// FOR LOG///
    int status = 0;
    String remark = "";
    PostalSubDistrictDto dtoFromDb = null;
    PostalSubDistrictDto dtoFromDbCurrentData = null;
    /// FOR LOG///
    try {
      log.info("Start PostalSubDistrictController.update()... ");
      dtoFromDb = subDistrictService.getById(dto.getId());
      dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
      dtoFromDb.setUpdateDt(new Date());

      /// FOR LOG///
      /// prepare data
      dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
      /// FOR LOG///

      if (dtoFromDb.getId() > 0) {
        if (dto.getNameTh() != null
            && !dtoFromDb.getNameTh().equalsIgnoreCase(dto.getNameTh())) {
          int count = subDistrictService.checkDuplicateCode(dto);
          if (count > 0) {
            throw new BusinessServiceException(
                HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
          }
        }

        // map data from front to db
        modelMapper.map(dto, dtoFromDb);
        subDistrictService.save(dtoFromDb);

        return HelperUtils.responseSuccess();
      } else {
        throw new BusinessServiceException(
            HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
      }
    } catch (BusinessServiceException e) {
      log.error("Error PostalSubDistrictController.update()... " + e.getMessage());

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

      log.info("End PostalSubDistrictController.update()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTD')")
  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/sub/district/{id}")
  @ApiOperation(value = "Delete SubDistrict", response = void.class)
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
      log.info("Start PostalSubDistrictController.delete()... ");
      PostalSubDistrictDto dtoFromDb = subDistrictService.getById(id);
      if (dtoFromDb != null) {
        dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
        dtoFromDb.setUpdateDt(new Date());
        dtoFromDb.setStatus("I");

        /// FOR LOG///
        status = HttpStatus.OK.value();
        remark = "ID=" + id;
        /// FOR LOG///

        subDistrictService.save(dtoFromDb);
        return HelperUtils.responseSuccess();
      } else {
        throw new BusinessServiceException(
            HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
      }
    } catch (BusinessServiceException e) {
      log.error("Error PostalSubDistrictController.delete()... " + e.getMessage());

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
      log.info("End PostalSubDistrictController.delete()... ");
    }
  }
}
