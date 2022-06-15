package com.esign.service.configuration.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "mr_config", schema = "conf")
public class ConfigEntity {

    @Id
    @Column(name = "config_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "config_seq")
    @SequenceGenerator(
            name = "config_seq",
            sequenceName = "config_seq",
            allocationSize = 94)
    private Integer configId;

    @Basic
    @Column(name = "config_module")
    private String configModule;

    @Basic
    @Column(name = "config_name")
    private String configName;

    @Basic
    @Column(name = "config_value")
    private String configValue;

    @Basic
    @Column(name = "config_description")
    private String configDescription;

    @Basic
    @Column(name = "create_date")
    private Date createDate;

    @Basic
    @Column(name = "create_by")
    private String createBy;

    @Basic
    @Column(name = "update_date")
    private Date updateDate;

    @Basic
    @Column(name = "update_by")
    private String updateBy;

    @Basic
    @Column(name = "is_active")
    private Boolean isActive;
}
