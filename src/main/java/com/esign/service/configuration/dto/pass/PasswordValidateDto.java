package com.esign.service.configuration.dto.pass;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordValidateDto {
    int userid;
    String username;
    String password;
    String status;
    String detail;
}
