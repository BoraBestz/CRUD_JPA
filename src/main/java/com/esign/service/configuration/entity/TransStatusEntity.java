package com.esign.service.configuration.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mr_trans_status", schema = "conf")
public class TransStatusEntity {

    @Id
    @Column(name = "rd_status_code")
    /*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trans_status_seq")
    @SequenceGenerator(
            name = "trans_status_seq",
            sequenceName = "trans_status_seq",
            allocationSize = 1)*/
    private String statusCode;

    @Basic
    @Column(name = "rd_status_name")
    private String statusName;

    @Basic
    @Column(name = "is_active")
    private Boolean isActive;

    @Basic
    @Column(name = "rd_status_description")
    private String statusDescription;

    @Basic
    @Column(name = "is_show_search")
    private Boolean showSearch;

}
