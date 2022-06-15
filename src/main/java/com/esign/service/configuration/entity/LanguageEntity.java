package com.esign.service.configuration.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "language", schema = "conf")
public class LanguageEntity {

    @Id
    @Column(name = "language_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "language_seq")
    @SequenceGenerator(
            name = "language_seq",
            sequenceName = "language_seq",
            allocationSize = 1)
    private Integer languageId;

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
    private Integer lastUpdateBy;

    @Basic
    @Column(name = "last_updated_date")
    private Date lastUpdateDate;

    @Basic
    @Column(name = "language_code")
    private String languageCode;

    @Basic
    @Column(name = "language_name_th")
    private String languageNameTh;

    @Basic
    @Column(name = "language_name_en")
    private String languageNameEn;

    @Basic
    @Column(name = "language_description")
    private String languageDescription;
}
