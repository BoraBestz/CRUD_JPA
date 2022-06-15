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
@Table(name = "delivery_type", schema = "conf")

public class DeliveryTypeEntity {
  @Id
  @Column(name = "delivery_type_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_type_seq")
  @SequenceGenerator(
          name = "delivery_type_seq",
          sequenceName = "delivery_type_seq",
          allocationSize = 1)
  private Integer deliveryTypeId;

  @Basic
  @Column(name = "status")
  private Integer status;

  @Column(name = "created_user")
  private Integer createBy;

  @Column(name = "created_date")
  private Date createDt;

  @Column(name = "last_updated_by")
  private Integer updateBy;

  @Column(name = "last_updated_date")
  private Date updateDt;

  @Basic
  @Column(name = "delivery_type_code")
  private String deliveryTypeCode;

  @Basic
  @Column(name = "delivery_type_name_th")
  private String deliveryTypeNameTh;

  @Basic
  @Column(name = "delivery_type_name_en")
  private String deliveryTypeNameEn;

  @Basic
  @Column(name = "delivery_type_description")
  private String deliveryTypeDescription;
}
