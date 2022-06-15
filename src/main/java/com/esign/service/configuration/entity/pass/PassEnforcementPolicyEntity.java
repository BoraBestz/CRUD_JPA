package com.esign.service.configuration.entity.pass;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "pass_enforcement_policy", schema = "conf")
public class PassEnforcementPolicyEntity {
    /*private int epId;
    private String policyValue;
    private String status;
    private Time createdDate;
    private Integer createdBy;
    private Time updatedDate;
    private Integer updatedBy;
    private PassEnforcementEntity passEnforcementByEnforcementId;
    private PassPolicyEntity passPolicyByPolicyId;*/

    @Id
    @Column(name = "ep_id")
    private int epId;

    @Basic
    @Column(name = "policy_value")
    private String policyValue;

    @Basic
    @Column(name = "status")
    private String status;

    @Basic
    @Column(name = "created_date")
    private Date createdDate;

    @Basic
    @Column(name = "created_by")
    private Integer createdBy;

    @Basic
    @Column(name = "updated_date")
    private Date updatedDate;

    @Basic
    @Column(name = "updated_by")
    private Integer updatedBy;

    @ManyToOne
    @JoinColumn(name = "enforcement_id", referencedColumnName = "enforce_id")
    private PassEnforcementEntity passEnforcementByEnforcementId;

    @ManyToOne
    @JoinColumn(name = "policy_id", referencedColumnName = "policy_id")
    private PassPolicyEntity passPolicyByPolicyId;
}
