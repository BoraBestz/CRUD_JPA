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
@Table(name = "tbl_md_template", schema = "conf")

public class TblMdTemplateEntity {
  @Id
  @Column(name = "template_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_md_template_seq")
  @SequenceGenerator(
          name = "tbl_md_template_seq",
          sequenceName = "tbl_md_template_seq",
          allocationSize = 1)
  private Integer templateId;

  @Basic
  @Column(name = "template_code")
  private String templateCode;
  @Basic
  @Column(name = "template_name")
  private String templateName;
  @Basic
  @Column(name = "record_status")
  private String status;
  @Basic
  @Column(name = "is_default")
  private String isDefault;
  @Basic
  @Column(name = "document_type_id")
  private Integer documentTypeId;

}
