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
@Table(name = "term_type", schema = "conf")
public class TermTypeEntity {
  @Id
  @Column(name = "term_type_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "term_type_seq")
  @SequenceGenerator(
          name = "term_type_seq",
          sequenceName = "term_type_seq",
          allocationSize = 1)
  private Integer termTypeId;

  @Basic
  @Column(name = "status")
  private Integer status;
  @Basic
  @Column(name = "term_type_code")
  private String termTypeCode;
  @Basic
  @Column(name = "term_type_name_th")
  private String termTypeNameTh;
  @Basic
  @Column(name = "term_type_name_en")
  private String termTypeNameEn;
  @Basic
  @Column(name = "term_type_description")
  private String termTypeDescription;

  @Column(name = "created_by")
  private Integer createBy;
  @Column(name = "created_date")
  private Date createDt;
  @Column(name = "last_updated_by")
  private Integer updateBy;
  @Column(name = "last_updated_date")
  private Date updateDt;
}
