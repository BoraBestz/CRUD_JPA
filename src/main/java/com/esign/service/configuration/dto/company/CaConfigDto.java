package com.esign.service.configuration.dto.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaConfigDto {
    private Integer caId;
    private String caLibPath;
    private String providerName;
    private String password;
    private Integer slotId;
    private Integer status;
    private Integer createdUser;
    private Timestamp createdDate;
    private Integer lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private BranchDto branchByBranchId;
}
