package com.esign.service.configuration.dto.company;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EMailServerCriteria {
    private Integer page;
    private Integer perPage;
    private Sort.Direction direction;
    private String sort;

    private Integer mailServerId;
    private Integer branchId;
    private String branchName;
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
    private Integer incomingMailServiceId;
    private Integer outgoingMailServiceId;
    private Integer status;
}
