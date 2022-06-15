package com.esign.service.configuration.dto.postal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostalSubDistrictDto {
  @JsonIgnore
  private int page;
  @JsonIgnore private Integer perPage;
  @JsonIgnore private String direction;
  @JsonIgnore private String sort;

  private Integer id;
  private Integer districtId;
  private Integer provinceId;
  private Integer geoId;
  private Integer code;
  private String nameTh;
  private String status;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<PostalCodeDto> postalCode;

  @JsonIgnore private String fullSearch;
  @JsonIgnore private Integer createBy;
  @JsonIgnore private Date createDt;
  @JsonIgnore private Integer updateBy;
  @JsonIgnore private Date updateDt;


}
