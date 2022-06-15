package com.esign.service.configuration.dto.company;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CreateUserDto {
    private Integer userId;
    private String email;
    private String password;
    private Integer isEnabled;
    private String enabledKey;
    private String resetPasswordKey;
    private Integer isAccountNonExpired;
    private Integer isAccountNonLocked;
    private Integer isCredentialsNonExpired;
    private String activated;
    private String activationkey;
    private String activatedToken;
    private Timestamp activatedTokenExpiryDate;
    private String resetToken;
    private Timestamp resetTokenExpiryDate;
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedUser;
    private Timestamp lastUpdatedDate;
    private String firstName;
    private String lastName;
    private String personName;
    private String departmentName;
    private String phoneNumber;
    private List<BranchDto> userBranch;
    private List<RoleDto> userRole;

    private String emailUser;
    private String passwordUser;
    private String confirmPassword;
    private String telephoneUser;
    private String faxUser;
}
