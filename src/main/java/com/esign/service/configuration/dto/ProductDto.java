//package com.esign.service.configuration.dto;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import java.util.Date;
//import java.util.List;
//
//@Builder(toBuilder = true)
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class ProductDto {
//
//    @JsonIgnore
//    private int page;
//    @JsonIgnore private Integer perPage;
//    @JsonIgnore private String direction;
//    @JsonIgnore private String sort;
//
//    private Integer productId;
//    private Long branchId;
//    private Integer status;
//    private Integer createdBy;
//    private Date createdDate;
//    private Integer lastUpdateBy;
//    private Date lastUpdateDate;
//    private Integer classId;
//    private String classCode;
//    private String className;
//    private Integer unitId;
//    private String unitCode;
//    private String unitName;
//    private String productCode;
//    private String productNameTh;
//    private String productNameEn;
//    private String productDescription;
//    private Integer createUser;
//
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private List<ComBranchDto> comBranch;
//
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private List<UnitDto> unit;
//
//    @JsonIgnore private String fullSearch;
//
//}
