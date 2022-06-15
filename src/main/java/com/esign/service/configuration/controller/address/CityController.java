package com.esign.service.configuration.controller.address;

import com.esign.service.configuration.client.LoggingService;
import com.esign.service.configuration.client.LoggingServiceClient;
import com.esign.service.configuration.client.LoggingServiceClient.ActivityType;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.address.CityDto;
import com.esign.service.configuration.entity.address.CityEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.service.ComCompanyService;
import com.esign.service.configuration.service.address.CityService;
import com.esign.service.configuration.service.address.CitySubDivisionService;
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
@Api(tags = "City")
@Slf4j
public class CityController extends LoggingService {

  private CityService cityService;
  private MessageByLocaleService message;
  private ModelMapper modelMapper;
  private LoggingServiceClient loggingServiceClient;
  private LoggingService loggingService;
  private OauthUser oauthUser;
  private CitySubDivisionService citySubDivisionService;
  private CountrySubDivisionService countrySubDivisionService;
  private ComCompanyService comCompanyService;

  @Autowired
  public void setCityController(
          CityService cityService,
          MessageByLocaleService message, ModelMapper modelMapper,
          LoggingServiceClient loggingServiceClient,
          LoggingService loggingService, OauthUser oauthUser,
          CitySubDivisionService citySubDivisionService,
          CountrySubDivisionService countrySubDivisionService,
          ComCompanyService comCompanyService) {
    this.cityService = cityService;
    this.message = message;
    this.modelMapper = modelMapper;
    this.loggingServiceClient = loggingServiceClient;
    this.loggingService = loggingService;
    this.oauthUser = oauthUser;
    this.citySubDivisionService = citySubDivisionService;
    this.countrySubDivisionService = countrySubDivisionService;
    this.comCompanyService = comCompanyService;
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/city")
  @ApiOperation(value = "Search City", response = CityDto.class, responseContainer = "List")
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
      @RequestParam(required = false, defaultValue = "cityCode", value = "sort") String sort,
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
      log.info("Start CityController.searchByCri()... ");
      CityDto dto = modelMapper.map(paramDto, CityDto.class);
      Page<CityDto> byCri = cityService.findByCri(dto);

      /// FOR LOG///
      status = HttpStatus.OK.value();
      remark = paramDto.toString();
      /// FOR LOG///

      return HelperUtils.responseSuccess(byCri);
    } catch (BusinessServiceException e) {
      log.error("Error CityController.searchByCri()... " + e.getMessage());

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
      log.info("End CityController.searchByCri()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/city/{id}")
  @ApiOperation(value = "Get City By Id", response = CityDto.class)
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
      log.info("Start CityController.getById()... ");
      CityDto dto = cityService.getById(id);

      if (dto.getCityId() > 0) {

        dto.setCitySubDivision(citySubDivisionService.findByCityId(dto.getCityId()));
        dto.setCountrySubDivision(countrySubDivisionService.findByCountrySubDivisionId(dto.getCityId()));
        dto.setComCompany(comCompanyService.findByComCompanyId(dto.getCityId()));

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
      log.error("Error CityController.getById()... " + e.getMessage());

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
      log.info("End CityController.getById()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTA')")
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/city")
  @ApiOperation(value = "New City", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseModel> create(@RequestBody CityDto dto) {
    /// FOR LOG///
    int status = 0;
    String remark = "";
    /// FOR LOG///
    try {
      log.info("Start CityController.create()... ");

      int count = cityService.checkDuplicateCode(dto);

      if (count > 0) {
        throw new BusinessServiceException(
            HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
      }
      dto.setCreateDt(new Date());
      dto.setCreateBy(oauthUser.oauthUserId());
      dto.setStatus(1);
      CityEntity save = cityService.save(dto);

      /// FOR LOG///
      status = HttpStatus.OK.value();
      remark = "ID=" + save.getCityId() + " NAME=" + save.getCityCode();
      /// FOR LOG///

      return HelperUtils.responseSuccess(save.getCityId());
    } catch (BusinessServiceException e) {
      log.error("Error CityController.create()... " + e.getMessage());

      /// FOR LOG///
      status = e.getHttpStatus().value();
      remark = e.getMessage() + " NAME=" + dto.getCityCode();
      /// FOR LOG///

      return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage());

      /// FOR LOG///
      status = HttpStatus.INTERNAL_SERVER_ERROR.value();
      remark = e.getMessage() + " NAME=" + dto.getCityCode();
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
      log.info("End CityController.create()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTE')")
  @PreAuthorize("isAuthenticated()")
  @PutMapping("/city")
  @ApiOperation(value = "Update City", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseModel> update(@RequestBody CityDto dto) {
    /// FOR LOG///
    int status = 0;
    String remark = "";
    CityDto dtoFromDb = null;
    CityDto dtoFromDbCurrentData = null;
    /// FOR LOG///
    try {
      log.info("Start CityController.update()... ");
      dtoFromDb = cityService.getById(dto.getCityId());
      dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
      dtoFromDb.setUpdateDt(new Date());

      /// FOR LOG///
      /// prepare data
      dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
      /// FOR LOG///

      if (dtoFromDb.getCityId() > 0) {
        if (dto.getCityCode() != null
            && !dtoFromDb.getCityCode().equalsIgnoreCase(dto.getCityCode())) {
          int count = cityService.checkDuplicateCode(dto);
          if (count > 0) {
            throw new BusinessServiceException(
                HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
          }
        }

        // map data from front to db
        modelMapper.map(dto, dtoFromDb);
        cityService.save(dtoFromDb);

        return HelperUtils.responseSuccess();
      } else {
        throw new BusinessServiceException(
            HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
      }
    } catch (BusinessServiceException e) {
      log.error("Error CityController.update()... " + e.getMessage());

      /// FOR LOG///
      status = e.getHttpStatus().value();
      remark = e.getMessage() + " ID=" + dto.getCityId();
      /// FOR LOG///

      return HelperUtils.responseError(e.getHttpStatus(), e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage());

      /// FOR LOG///
      status = HttpStatus.INTERNAL_SERVER_ERROR.value();
      remark = e.getMessage() + " ID=" + dto.getCityId();
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

      log.info("End CityController.update()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTD')")
  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/city/{id}")
  @ApiOperation(value = "Delete City", response = void.class)
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
      log.info("Start CityController.delete()... ");
      CityDto dtoFromDb = cityService.getById(id);
      if (dtoFromDb != null) {
        dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
        dtoFromDb.setUpdateDt(new Date());
        dtoFromDb.setStatus(0);

        /// FOR LOG///
        status = HttpStatus.OK.value();
        remark = "ID=" + id;
        /// FOR LOG///

        cityService.save(dtoFromDb);
        return HelperUtils.responseSuccess();
      } else {
        throw new BusinessServiceException(
            HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
      }
    } catch (BusinessServiceException e) {
      log.error("Error CityController.delete()... " + e.getMessage());

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
      log.info("End CityController.delete()... ");
    }
  }
}
