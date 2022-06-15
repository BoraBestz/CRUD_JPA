package com.esign.service.configuration.dto.pass;

import lombok.Data;

import java.util.Date;

@Data
public class PasswordPolicyDto {
    private int policyId;
    private String policyName;
    private String policyValueType;
    private String policyValue;
    private Date updatedDate;
    private Integer updatedBy;
}
