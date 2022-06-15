package com.esign.service.configuration.controller.postal;

import com.esign.service.configuration.client.LoggingService;
import com.esign.service.configuration.client.LoggingServiceClient;
import com.esign.service.configuration.client.LoggingServiceClient.ActivityType;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.postal.PostalProvinceDto;
import com.esign.service.configuration.entity.postal.PostalProvinceEntity;
import com.esign.service.configuration.exception.BusinessServiceException;
import com.esign.service.configuration.service.postal.PostalCodeService;
import com.esign.service.configuration.service.postal.PostalProvinceService;
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
@Api(tags = "Province")
@Slf4j
public class PostalProvinceController extends LoggingService {

  private PostalProvinceService provinceService;
  private MessageByLocaleService message;
  private ModelMapper modelMapper;
  private LoggingServiceClient loggingServiceClient;
  private LoggingService loggingService;
  private OauthUser oauthUser;
  private PostalCodeService postalCodeService;
  private PostalSubDistrictService postalSubDistrictService;

  @Autowired
  public void setPostalProvinceController(
      PostalProvinceService provinceService,
      MessageByLocaleService message, ModelMapper modelMapper,
      LoggingServiceClient loggingServiceClient,
      LoggingService loggingService, OauthUser oauthUser,
      PostalCodeService postalCodeService,
      PostalSubDistrictService postalSubDistrictService) {
    this.provinceService = provinceService;
    this.message = message;
    this.modelMapper = modelMapper;
    this.loggingServiceClient = loggingServiceClient;
    this.loggingService = loggingService;
    this.oauthUser = oauthUser;
    this.postalCodeService = postalCodeService;
    this.postalSubDistrictService = postalSubDistrictService;
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/province")
  @ApiOperation(value = "Search Province", response = PostalProvinceDto.class, responseContainer = "List")
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
      log.info("Start PostalProvinceController.searchByCri()... ");
      PostalProvinceDto dto = modelMapper.map(paramDto, PostalProvinceDto.class);
      Page<PostalProvinceDto> byCri = provinceService.findByCri(dto);

      /// FOR LOG///
      status = HttpStatus.OK.value();
      remark = paramDto.toString();
      /// FOR LOG///

      return HelperUtils.responseSuccess(byCri);
    } catch (BusinessServiceException e) {
      log.error("Error PostalProvinceController.searchByCri()... " + e.getMessage());

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
      log.info("End PostalProvinceController.searchByCri()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTV')")
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/province/{id}")
  @ApiOperation(value = "Get Province By Id", response = PostalProvinceDto.class)
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
      log.info("Start PostalProvinceController.getById()... ");
      PostalProvinceDto dto = provinceService.getById(id);

      if (dto.getId() > 0) {
        dto.setPostalCode(postalCodeService.findByProvinceId(dto.getId()));
        dto.setPostalSubDistrict(postalSubDistrictService.findByProvinceId(dto.getId()));
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
      log.error("Error PostalProvinceController.getById()... " + e.getMessage());

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
      log.info("End PostalProvinceController.getById()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTA')")
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/province")
  @ApiOperation(value = "New Province", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseModel> create(@RequestBody PostalProvinceDto dto) {
    /// FOR LOG///
    int status = 0;
    String remark = "";
    /// FOR LOG///
    try {
      log.info("Start PostalProvinceController.create()... ");

      int count = provinceService.checkDuplicateCode(dto);

      if (count > 0) {
        throw new BusinessServiceException(
            HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
      }
      dto.setCreateDt(new Date());
      dto.setCreateBy(oauthUser.oauthUserId());
      dto.setStatus("A");
      PostalProvinceEntity save = provinceService.save(dto);

      /// FOR LOG///
      status = HttpStatus.OK.value();
      remark = "ID=" + save.getId() + " NAME=" + save.getNameTh();
      /// FOR LOG///

      return HelperUtils.responseSuccess(save.getId());
    } catch (BusinessServiceException e) {
      log.error("Error PostalProvinceController.create()... " + e.getMessage());

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
      log.info("End PostalProvinceController.create()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTE')")
  @PreAuthorize("isAuthenticated()")
  @PutMapping("/province")
  @ApiOperation(value = "Update Province", response = void.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 400, message = "Something went wrong"),
        @ApiResponse(code = 403, message = "Access denied"),
        @ApiResponse(code = 500, message = "Error")
      })
  public ResponseEntity<ResponseModel> update(@RequestBody PostalProvinceDto dto) {
    /// FOR LOG///
    int status = 0;
    String remark = "";
    PostalProvinceDto dtoFromDb = null;
    PostalProvinceDto dtoFromDbCurrentData = null;
    /// FOR LOG///
    try {
      log.info("Start PostalProvinceController.update()... ");
      dtoFromDb = provinceService.getById(dto.getId());
      dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
      dtoFromDb.setUpdateDt(new Date());

      /// FOR LOG///
      /// prepare data
      dtoFromDbCurrentData = dtoFromDb.toBuilder().build(); // clone db obj
      /// FOR LOG///

      if (dtoFromDb.getId() > 0) {
        if (dto.getNameTh() != null
            && !dtoFromDb.getNameTh().equalsIgnoreCase(dto.getNameTh())) {
          int count = provinceService.checkDuplicateCode(dto);
          if (count > 0) {
            throw new BusinessServiceException(
                HttpStatus.CONFLICT, message.getMessage("ERM0002"), "ERM0002");
          }
        }

        // map data from front to db
        modelMapper.map(dto, dtoFromDb);
        provinceService.save(dtoFromDb);

        return HelperUtils.responseSuccess();
      } else {
        throw new BusinessServiceException(
            HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
      }
    } catch (BusinessServiceException e) {
      log.error("Error PostalProvinceController.update()... " + e.getMessage());

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

      log.info("End PostalProvinceController.update()... ");
    }
  }

  //@PreAuthorize("hasAnyAuthorityCustom('ARMTD')")
  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/province/{id}")
  @ApiOperation(value = "Delete Province", response = void.class)
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
      log.info("Start PostalProvinceController.delete()... ");
      PostalProvinceDto dtoFromDb = provinceService.getById(id);
      if (dtoFromDb != null) {
        dtoFromDb.setUpdateBy(oauthUser.oauthUserId());
        dtoFromDb.setUpdateDt(new Date());
        dtoFromDb.setStatus("I");

        /// FOR LOG///
        status = HttpStatus.OK.value();
        remark = "ID=" + id;
        /// FOR LOG///

        provinceService.save(dtoFromDb);
        return HelperUtils.responseSuccess();
      } else {
        throw new BusinessServiceException(
            HttpStatus.NOT_FOUND, message.getMessage("ERM0001"), "ERM0001");
      }
    } catch (BusinessServiceException e) {
      log.error("Error PostalProvinceController.delete()... " + e.getMessage());

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
      log.info("End PostalProvinceController.delete()... ");
    }
  }
}
