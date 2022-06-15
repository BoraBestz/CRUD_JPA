package com.esign.service.configuration.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "tbl_md_source_group", schema = "conf")

public class TblMdSourceGroupEntity {
  @Id
  @Column(name = "source_group_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_md_source_group_seq")
  @SequenceGenerator(
          name = "tbl_md_source_group_seq",
          sequenceName = "tbl_md_source_group_seq",
          allocationSize = 1)
  private Integer sourceGroupId;

  @Basic
  @Column(name = "source_group_code")
  private String sourceGroupCode;
  @Basic
  @Column(name = "source_group_name")
  private String sourceGroupName;
  @Basic
  @Column(name = "record_status")
  private String status;

  @Column(name = "created_user")
  private Integer createBy;
  @Column(name = "created_date")
  private Date createDt;
  @Column(name = "last_updated_by")
  private Integer updateBy;
  @Column(name = "last_updated_date")
  private Date updateDt;

}
