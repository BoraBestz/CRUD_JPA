package com.esign.service.configuration.controller.address;

import com.esign.service.configuration.client.LoggingService;
import com.esign.service.configuration.client.LoggingServiceClient;
import com.esign.service.configuration.client.LoggingServiceClient.ActivityType;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.address.CountrySubDivisionDto;
import com.esign.service.configuration.entity.address.CountrySubDivisionEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.service.address.CountrySubDivisionService;
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
@Api(tags = "CountrySubDivision")
@Slf4j
public class CountrySubDivisionController extends LoggingService {

  private CountrySubDivisionService countrySubDivisionService;
  private MessageByLocaleService message;
  private ModelMapper modelMapper;
  private LoggingServiceClient loggingServiceClient;
  private LoggingService loggingService;
  private OauthUser oauthUser;

  @Autowired
  public void setCountrySubDivisionController(
      CountrySubDivisionService countrySubDivisionService,
      MessageByLocaleService message, ModelMapper modelMapper,
      LoggingServiceClient loggingServiceClient,
      LoggingService loggingService, OauthUser oauthUser) {
    this.countrySubDivisionService = countrySubDivisionService;
    this.message = message;
    this.modelMapper = modelMapper;
    this.loggingServiceClient = loggingServiceClient;
    this.loggingService = loggingService;
    this.oauthUser = oauthUser;
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/country-sub-division")
  @ApiOperation(value = "Search CountrySubDivision", response = CountrySubDivisionDto.class, responseContainer = "List")
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
      @RequestParam(required = false, defaultValue = "countrySubDivisionCode", value = "sort") String sort,
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
      log.info("Start CountrySubDivisionController.searchByCri()... ");
      CountrySubDivisionDto dto = modelMapper.map(paramDto, CountrySubDivisionDto.class);
      Page<CountrySubDivisionDto> byCri = countrySubDivisionService.findByCri(dto);

      /// FOR LOG///
      status = HttpStatus.OK.value();
      remark = paramDto.toString();
      /// FOR LOG///

      return HelperUtils.responseSuccess(byCri);
    } catch (BusinessServiceException e) {
      log.error("Error CountrySubDivisionController.searchByCri()... " + e.getMessage());

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
      log.info("End CountrySubDivisionController.searchByCri()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/country-sub-division/{id}")
  @ApiOperation(value = "Get CountrySubDivision By Id", response = CountrySubDivisionDto.class)
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
      log.info("Start CountrySubDivisionController.getById()... ");
      CountrySubDivisionDto dto = countrySubDivisionService.getById(id);

      if (dto.getCountrySubDivisionId() > 0) {
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
      log.error("Error CountrySubDivisionController.getById()... " + e.getMessage());

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
      log.info("End CountrySubDivisionController.getById()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTA')")
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/country-sub-division")
  @ApiOperation(value = "New CountrySubDivision", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseModel> create(@RequestBody CountrySubDivisionDto dto) {
    /// FOR LOG///
    int status = 0;
    String remark = "";
    /// FOR LOG///
    try {
      log.info("Start CountrySubDivisionController.create()... ");

      int count = countrySubDivisionService.checkDuplicateCode(dto);

      if (count > 0) {
        throw new BusinessServiceException(
            HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
      }
      dto.setCreateDt(new Date());
      dto.setCreateBy(oauthUser.oauthUserId());
      dto.setStatus(1);
      CountrySubDivisionEntity save = countrySubDivisionService.save(dto);

      /// FOR LOG///
      status = HttpStatus.OK.value();
      remark = "ID=" + save.getCountrySubDivisionId() + " NAME=" + save.getCountrySubDivisionCode();
      /// FOR LOG///

      return HelperUtils.responseSuccess(save.getCountrySubDivisionId());
    } catch (BusinessServiceException e) {
      log.error("Error CountrySubDivisionController.create()... " + e.getMessage());

      /// FOR LOG///
      status = e.getHttpStatus().value();
      remark = e.getMessage() + " NAME=" + dto.getCountrySubDivisionCode();
      /// FOR LOG///

      return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage());

      /// FOR LOG///
      status = HttpStatus.INTERNAL_SERVER_ERROR.value();
      remark = e.getMessage() + " NAME=" + dto.getCountrySubDivisionCode();
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
      log.info("End CountrySubDivisionController.create()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTE')")
  @PreAuthorize("isAuthenticated()")
  @PutMapping("/country-sub-division")
  @ApiOperation(value = "Update CountrySubDivision", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseModel> update(@RequestBody CountrySubDivisionDto dto) {
    /// FOR LOG///
    int status = 0;
    String remark = "";
    CountrySubDivisionDto dtoFromDb = null;
    CountrySubDivisionDto dtoFromDbCurrentData = null;
    /// FOR LOG///
    try {
      log.info("Start CountrySubDivisionController.update()... ");
      dtoFromDb = countrySubDivisionService.getById(dto.getCountrySubDivisionId());
      dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
      dtoFromDb.setUpdateDt(new Date());

      /// FOR LOG///
      /// prepare data
      dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
      /// FOR LOG///

      if (dtoFromDb.getCountrySubDivisionId() > 0) {
        if (dto.getCountrySubDivisionCode() != null
            && !dtoFromDb.getCountrySubDivisionCode().equalsIgnoreCase(dto.getCountrySubDivisionCode())) {
          if(dto.getCountryId() == null || dto.getCountryId() == 0)dto.setCountryId(dtoFromDb.getCountryId());
          int count = countrySubDivisionService.checkDuplicateCode(dto);
          if (count > 0) {
            throw new BusinessServiceException(
                HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
          }
        }

        // map data from front to db
        modelMapper.map(dto, dtoFromDb);
        countrySubDivisionService.save(dtoFromDb);

        return HelperUtils.responseSuccess();
      } else {
        throw new BusinessServiceException(
            HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
      }
    } catch (BusinessServiceException e) {
      log.error("Error CountrySubDivisionController.update()... " + e.getMessage());

      /// FOR LOG///
      status = e.getHttpStatus().value();
      remark = e.getMessage() + " ID=" + dto.getCountrySubDivisionId();
      /// FOR LOG///

      return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage());

      /// FOR LOG///
      status = HttpStatus.INTERNAL_SERVER_ERROR.value();
      remark = e.getMessage() + " ID=" + dto.getCountrySubDivisionId();
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

      log.info("End CountrySubDivisionController.update()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTD')")
  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/country-sub-division/{id}")
  @ApiOperation(value = "Delete CountrySubDivision", response = void.class)
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
      log.info("Start CountrySubDivisionController.delete()... ");
      CountrySubDivisionDto dtoFromDb = countrySubDivisionService.getById(id);
      if (dtoFromDb != null) {
        dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
        dtoFromDb.setUpdateDt(new Date());
        dtoFromDb.setStatus(0);

        /// FOR LOG///
        status = HttpStatus.OK.value();
        remark = "ID=" + id;
        /// FOR LOG///

        countrySubDivisionService.save(dtoFromDb);
        return HelperUtils.responseSuccess();
      } else {
        throw new BusinessServiceException(
            HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
      }
    } catch (BusinessServiceException e) {
      log.error("Error CountrySubDivisionController.delete()... " + e.getMessage());

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
      log.info("End CountrySubDivisionController.delete()... ");
    }
  }
}
