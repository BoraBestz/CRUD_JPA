package com.esign.service.configuration.entity.pass;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "pass_policy", schema = "conf")
public class PolicyEntity {

    @Id
    @Column(name = "policy_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "policy_seq")
    @SequenceGenerator(
            name = "policy_seq",
            sequenceName = "policy_seq",
            allocationSize = 25)
    private Integer policyId;

    @Basic
    @Column(name = "policy_name")
    private String policyName;

    @Basic
    @Column(name = "policy_value_type")
    private String policyValueType;

    @Basic
    @Column(name = "policy_value_default")
    private String policyValueDefault;

    @Basic
    @Column(name = "is_predefined")
    private String isPredefined;

    @Basic
    @Column(name = "status")
    private String status;

    @Basic
    @Column(name = "created_date")
    private Date createDate;

    @Basic
    @Column(name = "created_by")
    private Integer createBy;

    @Basic
    @Column(name = "updated_date")
    private Date updateDate;

    @Basic
    @Column(name = "updated_by")
    private Integer updateBy;

    @Basic
    @Column(name = "policy_show_text")
    private String policyShowText;

}
