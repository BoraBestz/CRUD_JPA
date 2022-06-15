package com.esign.service.configuration.dto.company;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    @ApiModelProperty(name = "roleId", required = false)
    @NotNull
    private Integer roleId;

    @ApiModelProperty(name = "roleName", required = true)
    @NotNull
    private String roleName;

    @ApiModelProperty(name = "roleDescription", required = true)
    @NotNull
    private String roleDescription;

    private Integer roleLevel;

    @ApiModelProperty(name = "status", required = true)
    @NotNull
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;

    @ApiModelProperty(name = "roleDescription", required = true)
    private List<Integer> selectedMenu;
}
