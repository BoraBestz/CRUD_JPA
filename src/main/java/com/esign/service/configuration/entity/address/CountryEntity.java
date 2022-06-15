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
@Table(name = "addr_country", schema = "conf")
public class CountryEntity {

  /*private Integer countryId;
  private String countryCode;
  private String countryNameTh;
  private String countryNameEn;
  private String status;
  private Integer createBy;
  private Timestamp createDt;
  private Integer updateBy;
  private Timestamp updateDt;*/

  @Id
  @Column(name = "country_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_seq")
  @SequenceGenerator(
      name = "country_seq",
      sequenceName = "country_seq",
      allocationSize = 1)
  private Integer countryId;

  @Basic
  @Column(name = "country_code")
  private String countryCode;

  @Basic
  @Column(name = "country_name_th")
  private String countryNameTh;

  @Basic
  @Column(name = "country_name_en")
  private String countryNameEn;

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
