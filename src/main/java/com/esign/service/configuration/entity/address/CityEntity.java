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
@Table(name = "addr_city", schema = "conf")
public class CityEntity {

  /*private Integer cityId;
  private Integer countrySubDivisionId;
  private String cityCode;
  private String cityNameTh;
  private String cityNameEn;
  private String status;
  private Integer createBy;
  private Timestamp createDt;
  private Integer updateBy;
  private Timestamp updateDt;*/

  @Id
  @Column(name = "city_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
  @SequenceGenerator(
      name = "city_seq",
      sequenceName = "city_seq",
      allocationSize = 1)
  private Integer cityId;

  @Basic
  @Column(name = "country_sub_division_id")
  private Integer countrySubDivisionId;

  @Basic
  @Column(name = "city_code")
  private String cityCode;

  @Basic
  @Column(name = "city_name_th")
  private String cityNameTh;

  @Basic
  @Column(name = "city_name_en")
  private String cityNameEn;

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
