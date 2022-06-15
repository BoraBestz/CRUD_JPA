//package com.esign.service.configuration.entity;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Data
//@Entity
//@Table(name = "product", schema = "conf")
//public class ProductEntity {
//
//    @Id
//    @Column(name = "product_id")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
//    @SequenceGenerator(
//            name = "product_seq",
//            sequenceName = "product_seq",
//            allocationSize = 86)
//    private Integer productId;
//
//    @Basic
//    @Column(name = "branch_id")
//    private Long branchId;
//
//    @Basic
//    @Column(name = "status")
//    private Integer status;
//
//    @Basic
//    @Column(name = "created_by")
//    private Integer createdBy;
//
//    @Basic
//    @Column(name = "created_date")
//    private Date createdDate;
//
//    @Basic
//    @Column(name = "last_updated_by")
//    private Integer lastUpdateBy;
//
//    @Basic
//    @Column(name = "last_updated_date")
//    private Date lastUpdateDate;
//
//    @Basic
//    @Column(name = "class_id")
//    private Integer classId;
//
//    @Basic
//    @Column(name = "class_code")
//    private String classCode;
//
//    @Basic
//    @Column(name = "class_name")
//    private String className;
//
//    @Basic
//    @Column(name = "unit_id")
//    private Integer unitId;
//
//    @Basic
//    @Column(name = "unit_code")
//    private String unitCode;
//
//    @Basic
//    @Column(name = "unit_name")
//    private String unitName;
//
//    @Basic
//    @Column(name = "product_code")
//    private String productCode;
//
//    @Basic
//    @Column(name = "product_name_th")
//    private String productNameTh;
//
//    @Basic
//    @Column(name = "product_name_en")
//    private String productNameEn;
//
//    @Basic
//    @Column(name = "product_description")
//    private String productDescription;
//
//    @Basic
//    @Column(name = "created_user")
//    private Integer createUser;
//
//}
