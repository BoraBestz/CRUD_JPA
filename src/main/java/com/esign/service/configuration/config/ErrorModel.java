package com.esign.service.configuration.config;

import io.swagger.annotations.ApiModelProperty;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorModel {

  @ApiModelProperty(notes = "HTTP URL")
  private String url;

  @ApiModelProperty(notes = "HTTP Status Code")
  private int statusCode;

  @ApiModelProperty(notes = "HTTP Reason Phrase")
  private String reasonPhrase;

  @ApiModelProperty(notes = "Message to the user")
  private String message;

  @ApiModelProperty(notes = "Ticket created on IT help desk if applicable", required = false)
  private String helpDeskTicket;

  @ApiModelProperty(
      notes =
          "Debug information (e.g., stack trace), not visible if runtime environment is 'production'",
      required = false)
  private String debugInfo;

  public ErrorModel() {
    // Default Constructor
  }
}
