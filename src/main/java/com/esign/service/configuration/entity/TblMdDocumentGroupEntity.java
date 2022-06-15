package com.esign.service.configuration.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tbl_md_document_group", schema = "conf", catalog = "etax")
public class TblMdDocumentGroupEntity {


    @Id
    @Column(name = "document_group_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_group_seq")
    @SequenceGenerator(
            name = "document_group_seq",
            sequenceName = "document_group_seq",
            allocationSize = 1)
    private Integer documentGroupId;

    @Basic
    @Column(name = "record_status")
    private String recordStatus;

    @Basic
    @Column(name = "document_group_code")
    private String documentGroupCode;

    @Basic
    @Column(name = "document_group_name")
    private String documentGroupName;
}
