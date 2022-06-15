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
@Table(name = "currency", schema = "conf")
public class CurrencyEntity {

  @Id
  @Column(name = "currency_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_seq")
  @SequenceGenerator(
          name = "currency_seq",
          sequenceName = "currency_seq",
          allocationSize = 1)
  private Integer currencyId;

  @Basic
  @Column(name = "currency_description")
  private String currencyDescription;

  @Basic
  @Column(name = "currency_code")
  private String currencyCode;

  @Basic
  @Column(name = "currency_name_th")
  private String currencyNameTh;

  @Basic
  @Column(name = "currency_name_en")
  private String currencyNameEn;

  @Column(name = "status")
  private Integer status;

  @Column(name = "created_by")
  private Integer createBy;

  @Column(name = "created_date")
  private Date createDt;

  @Column(name = "last_updated_by")
  private Integer updateBy;

  @Column(name = "last_updated_date")
  private Date updateDt;
}
