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
@Table(name = "tbl_md_source_data", schema = "conf")

public class TblMdSourceDataEntity {
  @Id
  @Column(name = "source_data_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_md_source_data_seq")
  @SequenceGenerator(
        name = "tbl_md_source_data_seq",
        sequenceName = "tbl_md_source_data_seq",
        allocationSize = 1)
  private Integer sourceDataId;

  @Basic
  @Column(name = "source_data_code")
  private String sourceDataCode;
  @Basic
  @Column(name = "source_data_name")
  private String sourceDataName;
  @Basic
  @Column(name = "record_status")
  private String status;
  @Basic
  @Column(name = "company_id")
  private Integer companyId;
  @Basic
  @Column(name = "source_group_id")
  private Integer sourceGroupId;

  @Column(name = "created_user")
  private Integer createBy;
  @Column(name = "created_date")
  private Date createDt;
  @Column(name = "last_updated_by")
  private Integer updateBy;
  @Column(name = "last_updated_date")
  private Date updateDt;



}
