package com.esign.service.configuration.controller.address;

import com.esign.service.configuration.client.LoggingService;
import com.esign.service.configuration.client.LoggingServiceClient;
import com.esign.service.configuration.client.LoggingServiceClient.ActivityType;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.address.CitySubDivisionDto;
import com.esign.service.configuration.entity.address.CitySubDivisionEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.service.address.CityService;
import com.esign.service.configuration.service.address.CitySubDivisionService;
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
@Api(tags = "CitySubDivision")
@Slf4j
public class CitySubDivisionController extends LoggingService {

  private CitySubDivisionService citySubDivisionService;
  private MessageByLocaleService message;
  private ModelMapper modelMapper;
  private LoggingServiceClient loggingServiceClient;
  private LoggingService loggingService;
  private OauthUser oauthUser;
  private CityService cityService;

  @Autowired
  public void setCitySubDivisionController(
          CitySubDivisionService citySubDivisionService,
          MessageByLocaleService message, ModelMapper modelMapper,
          LoggingServiceClient loggingServiceClient,
          LoggingService loggingService, OauthUser oauthUser,
          CityService cityService) {
    this.citySubDivisionService = citySubDivisionService;
    this.message = message;
    this.modelMapper = modelMapper;
    this.loggingServiceClient = loggingServiceClient;
    this.loggingService = loggingService;
    this.oauthUser = oauthUser;
    this.cityService = cityService;
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/city-sub-division")
  @ApiOperation(value = "Search CitySubDivision", response = CitySubDivisionDto.class, responseContainer = "List")
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
      @RequestParam(required = false, defaultValue = "citySubDivisionCode", value = "sort") String sort,
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
      log.info("Start CitySubDivisionController.searchByCri()... ");
      CitySubDivisionDto dto = modelMapper.map(paramDto, CitySubDivisionDto.class);
      Page<CitySubDivisionDto> byCri = citySubDivisionService.findByCri(dto);

      /// FOR LOG///
      status = HttpStatus.OK.value();
      remark = paramDto.toString();
      /// FOR LOG///

      return HelperUtils.responseSuccess(byCri);
    } catch (BusinessServiceException e) {
      log.error("Error CitySubDivisionController.searchByCri()... " + e.getMessage());

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
      log.info("End CitySubDivisionController.searchByCri()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/city-sub-division/{id}")
  @ApiOperation(value = "Get CitySubDivision By Id", response = CitySubDivisionDto.class)
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
      log.info("Start CitySubDivisionController.getById()... ");
      CitySubDivisionDto dto = citySubDivisionService.getById(id);

      if (dto.getCitySubDivisionId() > 0) {
        dto.setCity(cityService.getById(dto.getCityId()));

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
      log.error("Error CitySubDivisionController.getById()... " + e.getMessage());

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
      log.info("End CitySubDivisionController.getById()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTA')")
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/city-sub-division")
  @ApiOperation(value = "New CitySubDivision", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseModel> create(@RequestBody CitySubDivisionDto dto) {
    /// FOR LOG///
    int status = 0;
    String remark = "";
    /// FOR LOG///
    try {
      log.info("Start CitySubDivisionController.create()... ");

      int count = citySubDivisionService.checkDuplicateCode(dto);

      if (count > 0) {
        throw new BusinessServiceException(
            HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
      }
      dto.setCreateDt(new Date());
      dto.setCreateBy(oauthUser.oauthUserId());
      dto.setStatus(1);
      CitySubDivisionEntity save = citySubDivisionService.save(dto);

      /// FOR LOG///
      status = HttpStatus.OK.value();
      remark = "ID=" + save.getCitySubDivisionId() + " NAME=" + save.getCitySubDivisionCode();
      /// FOR LOG///

      return HelperUtils.responseSuccess(save.getCitySubDivisionId());
    } catch (BusinessServiceException e) {
      log.error("Error CitySubDivisionController.create()... " + e.getMessage());

      /// FOR LOG///
      status = e.getHttpStatus().value();
      remark = e.getMessage() + " NAME=" + dto.getCitySubDivisionCode();
      /// FOR LOG///

      return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage());

      /// FOR LOG///
      status = HttpStatus.INTERNAL_SERVER_ERROR.value();
      remark = e.getMessage() + " NAME=" + dto.getCitySubDivisionCode();
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
      log.info("End CitySubDivisionController.create()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTE')")
  @PreAuthorize("isAuthenticated()")
  @PutMapping("/city-sub-division")
  @ApiOperation(value = "Update CitySubDivision", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseModel> update(@RequestBody CitySubDivisionDto dto) {
    /// FOR LOG///
    int status = 0;
    String remark = "";
    CitySubDivisionDto dtoFromDb = null;
    CitySubDivisionDto dtoFromDbCurrentData = null;
    /// FOR LOG///
    try {
      log.info("Start CitySubDivisionController.update()... ");
      dtoFromDb = citySubDivisionService.getById(dto.getCitySubDivisionId());
      dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
      dtoFromDb.setUpdateDt(new Date());

      /// FOR LOG///
      /// prepare data
      dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
      /// FOR LOG///

      if (dtoFromDb.getCitySubDivisionId() > 0) {
        if (dto.getCitySubDivisionCode() != null
            && !dtoFromDb.getCitySubDivisionCode().equalsIgnoreCase(dto.getCitySubDivisionCode())) {
          if(dto.getCityId() == null || dto.getCityId() == 0)dto.setCityId(dtoFromDb.getCityId());
          int count = citySubDivisionService.checkDuplicateCode(dto);
          if (count > 0) {
            throw new BusinessServiceException(
                HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
          }
        }

        // map data from front to db
        modelMapper.map(dto, dtoFromDb);
        citySubDivisionService.save(dtoFromDb);

        return HelperUtils.responseSuccess();
      } else {
        throw new BusinessServiceException(
            HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
      }
    } catch (BusinessServiceException e) {
      log.error("Error CitySubDivisionController.update()... " + e.getMessage());

      /// FOR LOG///
      status = e.getHttpStatus().value();
      remark = e.getMessage() + " ID=" + dto.getCitySubDivisionId();
      /// FOR LOG///

      return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage());

      /// FOR LOG///
      status = HttpStatus.INTERNAL_SERVER_ERROR.value();
      remark = e.getMessage() + " ID=" + dto.getCitySubDivisionId();
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

      log.info("End CitySubDivisionController.update()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTD')")
  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/city-sub-division/{id}")
  @ApiOperation(value = "Delete CitySubDivision", response = void.class)
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
      log.info("Start CitySubDivisionController.delete()... ");
      CitySubDivisionDto dtoFromDb = citySubDivisionService.getById(id);
      if (dtoFromDb != null) {
        dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
        dtoFromDb.setUpdateDt(new Date());
        dtoFromDb.setStatus(0);

        /// FOR LOG///
        status = HttpStatus.OK.value();
        remark = "ID=" + id;
        /// FOR LOG///

        citySubDivisionService.save(dtoFromDb);
        return HelperUtils.responseSuccess();
      } else {
        throw new BusinessServiceException(
            HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
      }
    } catch (BusinessServiceException e) {
      log.error("Error CitySubDivisionController.delete()... " + e.getMessage());

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
      log.info("End CitySubDivisionController.delete()... ");
    }
  }
}
