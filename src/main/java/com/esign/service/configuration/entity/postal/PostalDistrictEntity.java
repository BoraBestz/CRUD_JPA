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
@Table(name = "postal_district", schema = "conf")
public class PostalDistrictEntity {

  /*private Short id;
  private Integer provinceId;
  private Integer geoId;
  private Integer code;
  private String nameTh;
  private String status;
  private Integer createBy;
  private Date createDt;
  private Integer updateBy;
  private Date updateDt;*/

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postal_district_seq")
  @SequenceGenerator(
      name = "postal_district_seq",
      sequenceName = "postal_district_seq",
      allocationSize = 1)
  private Integer id;

  @Column(name = "province_id")
  private Integer provinceId;

  @Column(name = "geo_id")
  private Integer geoId;

  @Column(name = "code")
  private Integer code;

  @Column(name = "name_th")
  private String nameTh;

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
