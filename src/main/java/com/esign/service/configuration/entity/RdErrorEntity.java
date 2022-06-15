package com.esign.service.configuration.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "mr_rd_error", schema = "conf")
public class RdErrorEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rd_error_seq")
    @SequenceGenerator(
            name = "rd_error_seq",
            sequenceName = "rd_error_seq",
            allocationSize = 1)
    private Integer id;

    @Basic
    @Column(name = "criteria_id")
    private Integer criteriaId;

}
