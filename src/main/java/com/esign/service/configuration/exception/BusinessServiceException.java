package com.esign.service.configuration.exception;

import java.io.Serializable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class BusinessServiceException extends Throwable implements Serializable {

  private static final long serialVersionUID = 1L;

  private final String message;
  private final HttpStatus httpStatus;

  @Nullable
  private final String errorCode;

  public BusinessServiceException(HttpStatus httpStatus, String message) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.errorCode = "";
  }

  public BusinessServiceException(HttpStatus httpStatus, String message, @Nullable String errorCode) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.errorCode = errorCode;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getErrorCode(){
    if(StringUtils.isEmpty(errorCode))
      return "";
    else
      return errorCode;
  }
}
