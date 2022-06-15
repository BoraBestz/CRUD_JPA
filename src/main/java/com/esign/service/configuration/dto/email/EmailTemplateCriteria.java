package com.esign.service.configuration.dto.email;

import lombok.*;
import org.springframework.data.domain.Sort;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateCriteria {
    private Integer page;
    private Integer perPage;
    private Sort.Direction direction;
    private String sort;

    private Integer emailTemplateId;
    private Integer documentTypeId;
    private Integer companyId;
    private Integer status;
}
