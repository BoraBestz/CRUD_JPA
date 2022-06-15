package com.esign.service.configuration.entity.address;

import java.sql.Timestamp;
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
@Table(name = "addr_city_sub_division", schema = "conf")
public class CitySubDivisionEntity {

  /*private Integer citySubDivisionId;
  private Integer cityId;
  private String citySubDivisionCode;
  private String citySubDivisionNameTh;
  private String citySubDivisionNameEn;
  private Integer citySubDivisionPostCode;
  private String status;
  private Integer createBy;
  private Timestamp createDt;
  private Integer updateBy;
  private Timestamp updateDt;*/

  @Id
  @Column(name = "city_sub_division_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_sub_division_seq")
  @SequenceGenerator(
      name = "city_sub_division_seq",
      sequenceName = "city_sub_division_seq",
      allocationSize = 1)
  private Integer citySubDivisionId;


  @Basic
  @Column(name = "city_id")
  private Integer cityId;

  @Basic
  @Column(name = "city_sub_division_code")
  private String citySubDivisionCode;

  @Basic
  @Column(name = "city_sub_division_name_th")
  private String citySubDivisionNameTh;

  @Basic
  @Column(name = "city_sub_division_name_en")
  private String citySubDivisionNameEn;

  @Basic
  @Column(name = "city_sub_division_post_code")
  private Integer citySubDivisionPostCode;

  @Column(name = "status")
  private int status;

  @Column(name = "created_user")
  private Integer createBy;

  @Column(name = "created_date")
  private Date createDt;

  @Column(name = "last_updated_by")
  private Integer updateBy;

  @Column(name = "last_updated_date")
  private Date updateDt;
}
