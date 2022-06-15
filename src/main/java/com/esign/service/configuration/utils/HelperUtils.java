package com.esign.service.configuration.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.function.Function;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HelperUtils {
  public static final String SUCCESS = "success";
  public static final String FAILURE = "failure";
  public static final Function<BigDecimal, BigDecimal> rounding =
      (BigDecimal e) -> e.setScale(0, RoundingMode.UP);
  public static final Function<BigDecimal, Long> roundingToLong =
      (BigDecimal e) -> e.setScale(0, RoundingMode.UP).longValue();

  private HelperUtils() {
    throw new IllegalStateException("HelperUtils class");
  }

  public static ResponseEntity<ResponseModel> responseSuccess() {
    ResponseModel response = new ResponseModel("", "", SUCCESS, HttpStatus.OK.value());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  public static ResponseEntity<ResponseModel> responseSuccess(Object data) {
    ResponseModel response = new ResponseModel(data, "", SUCCESS, HttpStatus.OK.value());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  public static ResponseEntity<ResponseModel> responseSuccess(HttpStatus httpStatus, Object data) {
    ResponseModel response = new ResponseModel(data, "", SUCCESS, httpStatus.value());
    return new ResponseEntity<>(response, httpStatus);
  }

  public static ResponseEntity<ResponseModel> responseSuccessDescription(
      Object data, String statusDescription) {
    ResponseModel response =
        new ResponseModel(data, statusDescription, SUCCESS, HttpStatus.OK.value());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  public static ResponseEntity<ResponseModel> responseErrorInternalServerError(
      String errorCode, String statusDescription) {
    ResponseModel response =
        new ResponseModel(
            errorCode, statusDescription, FAILURE, HttpStatus.INTERNAL_SERVER_ERROR.value());
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public static ResponseEntity<ResponseModel> responseErrorBadRequest(
      String errorCode, String statusDescription) {
    ResponseModel response =
        new ResponseModel(errorCode, statusDescription, FAILURE, HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  public static ResponseEntity<ResponseModel> responseError(
      HttpStatus httpStatus, String errorCode, String statusDescription) {
    ResponseModel response =
        new ResponseModel(errorCode, statusDescription, FAILURE, httpStatus.value());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  public static SimpleDateFormat dateFormat() {
    return new SimpleDateFormat("yyyy-MM-dd");
  }
}
