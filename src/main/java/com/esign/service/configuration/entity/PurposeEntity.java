package com.esign.service.configuration.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purpose", schema = "conf")
@Data
public class PurposeEntity {

    @Id
    @Column(name = "purpose_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purpose_seq")
    @SequenceGenerator(
            name = "purpose_seq",
            sequenceName = "purpose_seq",
            allocationSize = 1)
    private Integer purposeId;

    @Basic
    @Column(name = "status")
    private Integer status;


    @Basic
    @Column(name = "created_by")
    private Integer createBy;


    @Basic
    @Column(name = "created_date")
    private Date createDate;


    @Basic
    @Column(name = "last_updated_by")
    private Long lastUpdatedBy;


    @Basic
    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;


    @Basic
    @Column(name = "purpose_code")
    private String purposeCode;


    @Basic
    @Column(name = "purpose_name_th")
    private String purposeNameTh;


    @Basic
    @Column(name = "purpose_name_en")
    private String purposeNameEn;


    @Basic
    @Column(name = "purpose_description")
    private String purposeDescription;

    @Basic
    @Column(name = "document_typecode")
    private String documentTypecode;


    @Basic
    @Column(name = "created_user")
    private Long createdUser;


    @Basic
    @Column(name = "using_type")
    private String usingType;

}
