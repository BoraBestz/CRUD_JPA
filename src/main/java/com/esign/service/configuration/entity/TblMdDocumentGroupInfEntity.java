package com.esign.service.configuration.entity;

import lombok.Data;

import javax.persistence.*;
@Entity
@Data
@Table(name = "tbl_md_document_group_inf", schema = "conf", catalog = "etax")
public class TblMdDocumentGroupInfEntity {

    @Id
    @Column(name = "document_group_inf")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_group_inf_seq")
    @SequenceGenerator(
            name = "document_group_inf_seq",
            sequenceName = "document_group_inf_seq",
            allocationSize = 1)
    private Integer documentGroupInf;


    @Basic
    @Column(name = "document_group_id")
    private Integer documentGroupId;

    @Basic
    @Column(name = "document_group_inf_code")
    private String documentGroupInfCode;


    @Basic
    @Column(name = "document_group_inf_name")
    private String documentGroupInfName;

    @Basic
    @Column(name = "record_status")
    private String recordStatus;
}
