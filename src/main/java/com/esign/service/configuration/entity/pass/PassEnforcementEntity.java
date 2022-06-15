package com.esign.service.configuration.entity.pass;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Collection;

@Data
@Entity
@Table(name = "pass_enforcement", schema = "conf")
public class PassEnforcementEntity {
    /*private int enforceId;
    private String enforceName;
    private String isDefault;
    private String status;
    private Time createdDate;
    private Integer createdBy;
    private Time updatedDate;
    private Integer updatedBy;
    private Collection<PassEnforcementPolicyEntity> passEnforcementPoliciesByEnforceId;*/

    @Id
    @Column(name = "enforce_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enforcement_seq")
    @SequenceGenerator(
            name = "enforcement_seq",
            sequenceName = "enforcement_seq",
            allocationSize = 1)
    private int enforceId;

    @Basic
    @Column(name = "enforce_name")
    private String enforceName;

    @Basic
    @Column(name = "is_default")
    private String isDefault;

    @Basic
    @Column(name = "status")
    private String status;

    @Basic
    @Column(name = "created_date")
    private Time createdDate;

    @Basic
    @Column(name = "created_by")
    private Integer createdBy;

    @Basic
    @Column(name = "updated_date")
    private Time updatedDate;

    @Basic
    @Column(name = "updated_by")
    private Integer updatedBy;

    @OneToMany(mappedBy = "passEnforcementByEnforcementId")
    private Collection<PassEnforcementPolicyEntity> passEnforcementPoliciesByEnforceId;
}
