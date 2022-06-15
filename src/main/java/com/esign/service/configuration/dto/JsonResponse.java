package com.esign.service.configuration.dto;

import lombok.*;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponse {

    private String respStatus;
    private String respMessage;
}
