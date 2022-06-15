package com.esign.service.configuration.entity.postal;

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
@Table(name = "postal_code", schema = "conf")
public class PostalCodeEntity {

  /*private Short id;
  private Integer provinceId;
  private Integer districtId;
  private Integer subDistrictId;
  private Integer postalCode;
  private String status;
  private Integer createBy;
  private Date createDt;
  private Integer updateBy;
  private Date updateDt;*/

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postal_code_seq")
  @SequenceGenerator(
      name = "postal_code_seq",
      sequenceName = "postal_code_seq",
      allocationSize = 1)
  private Integer id;

  @Column(name = "province_id")
  private Integer provinceId;

  @Column(name = "district_id")
  private Integer districtId;

  @Column(name = "sub_district_id")
  private Integer subDistrictId;

  @Column(name = "postal_code")
  private String postalCode;

  /*@Column(name = "status")
  private String status;

  @Column(name = "create_by")
  private Integer createBy;

  @Column(name = "create_dt")
  private Date createDt;

  @Column(name = "update_by")
  private Integer updateBy;

  @Column(name = "update_dt")
  private Date updateDt;*/
}
