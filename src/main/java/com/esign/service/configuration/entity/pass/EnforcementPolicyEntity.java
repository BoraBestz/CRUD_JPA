package com.esign.service.configuration.entity.pass;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "pass_enforcement_policy", schema = "conf")
public class EnforcementPolicyEntity {

    @Id
    @Column(name = "ep_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enforcement_policy_seq")
    @SequenceGenerator(
            name = "enforcement_policy_seq",
            sequenceName = "enforcement_policy_seq",
            allocationSize = 23)
    private Integer epId;

    @Basic
    @Column(name = "enforcement_id")
    //@ManyToOne
    //@JoinColumn(name = "enforcement_id", referencedColumnName = "enforce_id")
    private Integer enforcementId;

    @Basic
    @Column(name = "policy_id")
    //@ManyToOne
    //@JoinColumn(name = "policy_id", referencedColumnName = "policy_id")
    private Integer policyId;

    @Basic
    @Column(name = "policy_value")
    private String policyValue;

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

}
