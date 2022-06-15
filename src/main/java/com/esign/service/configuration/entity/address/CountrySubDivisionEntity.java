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
@Table(name = "addr_country_sub_division", schema = "conf")
public class CountrySubDivisionEntity {

  /*private Integer countrySubDivisionId;
  private Integer countryId;
  private String countrySubDivisionCode;
  private String countrySubDivisionNameTh;
  private String countrySubDivisionNameEn;
  private String status;
  private Integer createBy;
  private Timestamp createDt;
  private Integer updateBy;
  private Timestamp updateDt;*/

  @Id
  @Column(name = "country_sub_division_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_sub_division_seq")
  @SequenceGenerator(
      name = "country_sub_division_seq",
      sequenceName = "country_sub_division_seq",
      allocationSize = 1)
  private Integer countrySubDivisionId;

  @Basic
  @Column(name = "country_id")
  private Integer countryId;

  @Basic
  @Column(name = "country_sub_division_code")
  private String countrySubDivisionCode;

  @Basic
  @Column(name = "country_sub_division_name_th")
  private String countrySubDivisionNameTh;

  @Basic
  @Column(name = "country_sub_division_name_en")
  private String countrySubDivisionNameEn;

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
