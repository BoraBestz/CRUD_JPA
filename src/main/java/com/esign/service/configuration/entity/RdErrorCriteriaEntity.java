package com.esign.service.configuration.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mr_rd_errorcriteria", schema = "conf")
public class RdErrorCriteriaEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rd_errorcriteria_seq")
    @SequenceGenerator(
            name = "rd_errorcriteria_seq",
            sequenceName = "rd_errorcriteria_seq",
            allocationSize = 16)
    private Integer id;

    @Basic
    @Column(name = "criteria_detail")
    private String criteriaDetail;

}
