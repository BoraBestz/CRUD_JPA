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
@Table(name = "document_type", schema = "conf")
public class DocumentTypeEntity {
  @Id
  @Column(name = "document_type_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_type_seq")
  @SequenceGenerator(
          name = "document_type_seq",
          sequenceName = "document_type_seq",
          allocationSize = 1)
  private Integer documentTypeId;

  @Basic
  @Column(name = "document_type_code")
  private String documentTypeCode;

  @Basic
  @Column(name = "document_type_name_th")
  private String documentTypeNameTh;

  @Basic
  @Column(name = "document_type_name_en")
  private String documentTypeNameEn;

  @Basic
  @Column(name = "document_type_description")
  private String documentTypeDescription;

  @Basic
  @Column(name = "status")
  private Integer status;

  @Basic
  @Column(name = "document_group_id")
  private Long documentGroupId;

  @Basic
  @Column(name = "gen_xml")
  private Long genXml;

  @Basic
  @Column(name = "gen_pdf")
  private Long genPdf;

  @Basic
  @Column(name = "sign_doc")
  private Long signDoc;

  @Basic
  @Column(name = "is_etax")
  private String isEtax;

  @Basic
  @Column(name = "record_status")
  private String recordStatus;

  @Basic
  @Column(name = "document_type_code_rd")
  private String documentTypeCodeRd;

  @Basic
  @Column(name = "is_send_rd")
  private String isSendRd;

  @Column(name = "created_user")
  private Integer createBy;

  @Column(name = "created_date")
  private Date createDt;

  @Column(name = "last_updated_by")
  private Integer updateBy;

  @Column(name = "last_updated_date")
  private Date updateDt;

}
