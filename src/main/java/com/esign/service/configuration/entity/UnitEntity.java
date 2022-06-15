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
@Table(name = "unit", schema = "conf")
public class UnitEntity {
  @Id
  @Column(name = "unit_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_seq")
  @SequenceGenerator(
          name = "unit_seq",
          sequenceName = "unit_seq",
          allocationSize = 1)
  private Integer unitId;

  @Basic
  @Column(name = "status")
  private Integer status;
  @Basic
  @Column(name = "unit_code")
  private String unitCode;
  @Basic
  @Column(name = "unit_name_th")
  private String unitNameTh;
  @Basic
  @Column(name = "unit_name_en")
  private String unitNameEn;
  @Basic
  @Column(name = "unit_description")
  private String unitDescription;
  @Basic
  @Column(name = "convertion_factor")
  private String convertionFactor;
  @Basic
  @Column(name = "symbol")
  private String symbol;
  @Basic
  @Column(name = "created_user")
  private Long createdUser;

  @Column(name = "created_by")
  private Integer createBy;
  @Column(name = "created_date")
  private Date createDt;
  @Column(name = "last_updated_by")
  private Integer updateBy;
  @Column(name = "last_updated_date")
  private Date updateDt;

}
