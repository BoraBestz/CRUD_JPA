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
public class BranchCriteria {

    private Integer page;
    private Integer perPage;
    private Sort.Direction direction;
    private String sort;

    private Integer branchId;
    private Integer[] branchIds;
    private Integer companyId;
    private String globalId;
    private String name;
    private String taxId;
    private String taxSchemeId;
    private String postcodeCode;
    private String cityCode;
    private String citySubDivisionCode;
    private String countryCode;
    private String countrySchemeId;
    private Integer countrySubDivisionCode;
    private Integer status;
}
