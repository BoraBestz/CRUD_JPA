package com.esign.service.configuration.dto.company;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EMailServerDto {
    private Integer mailServerId;
    private String incomingHostname;
    private String incomingEmail;
    private String incomingPort;
    private String incomingUsername;
    private String incomingPassword;
    private String outgoingHostname;
    private String outgoingEmail;
    private String outgoingPort;
    private String outgoingUsername;
    private String outgoingPassword;
    private Integer branchId;
    private Integer incomingMailServiceId;
    private Integer outgoingMailServiceId;
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;
}
