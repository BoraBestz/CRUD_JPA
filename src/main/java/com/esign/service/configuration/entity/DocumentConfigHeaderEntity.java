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
@Table(name = "document_config_header", schema = "conf")

public class DocumentConfigHeaderEntity {
  @Id
  @Column(name = "document_config_header_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_type_seq")
  @SequenceGenerator(
          name = "delivery_type_seq",
          sequenceName = "delivery_type_seq",
          allocationSize = 1)
  private Integer documentConfigHeaderId;

  @Basic
  @Column(name = "document_config_header_code")
  private String documentConfigHeaderCode;
  @Basic
  @Column(name = "document_config_header_name")
  private String documentConfigHeaderName;
  @Basic
  @Column(name = "company_code")
  private String companyCode;
  @Basic
  @Column(name = "is_validate")
  private String isValidate;

  @Basic
  @Column(name = "record_status")
  private String status;

  @Column(name = "created_by")
  private Integer createBy;

  @Column(name = "created_date")
  private Date createDt;

  @Column(name = "updated_by")
  private Integer updateBy;

  @Column(name = "updated_date")
  private Date updateDt;

  @Basic
  @Column(name = "is_sel")
  private String isSel;

}
