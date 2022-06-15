package com.esign.service.configuration.entity.pass;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "pass_password_history", schema = "conf")
public class PasswordHistoryEntity {

    @Id
    @Column(name = "password_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_history_seq")
    @SequenceGenerator(
            name = "password_history_seq",
            sequenceName = "password_history_seq",
            allocationSize = 10)
    private Integer passwordHistoryId;

    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @Basic
    @Column(name = "password_history")
    private String passwordHistory;

    @Basic
    @Column(name = "status")
    private String status;

    @Basic
    @Column(name = "created_date")
    private Date createDate;

}
