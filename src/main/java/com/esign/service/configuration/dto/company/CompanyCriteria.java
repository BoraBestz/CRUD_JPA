package com.esign.service.configuration.dto.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCriteria {
    private Integer page;
    private Integer perPage;
    private Sort.Direction direction;
    private String sort;
    private Integer companyId;
    private String globalId;
    private String name;
    private String taxId;
    private String taxSchemeId;
    private String postcodeCode;
    private Integer branchId;
    private String branchName;
    private String search;
    private Integer status;
    private Integer parentCompanyId;
    private Set<Integer> companyIds;
}
