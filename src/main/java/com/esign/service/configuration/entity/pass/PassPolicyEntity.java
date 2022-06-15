package com.esign.service.configuration.entity.pass;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "pass_policy", schema = "", catalog = "")
public class PassPolicyEntity {
    /*private int policyId;
    private String policyName;
    private String policyValueType;
    private String policyValueDefault;
    private String isPredefined;
    private String status;
    private Time createdDate;
    private Integer createdBy;
    private Time updatedDate;
    private Integer updatedBy;
    private Collection<PassEnforcementPolicyEntity> passEnforcementPoliciesByPolicyId;*/

    @Id
    @Column(name = "policy_id")
    private int policyId;

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

    @Basic
    @Column(name = "policy_show_text")
    private String policyShowText;

    @OneToMany(mappedBy = "passPolicyByPolicyId")
    private Collection<PassEnforcementPolicyEntity> passEnforcementPoliciesByPolicyId;
}
