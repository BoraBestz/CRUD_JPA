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

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Entity
@Table(name = "document_config", schema = "conf")
public class DocumentConfigEntity {

  @Id
  @Column(name = "document_config_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_document_config")
  @SequenceGenerator(
          name = "seq_document_config",
          sequenceName = "seq_document_config",
          allocationSize = 1)
  private Integer documentConfigId;

  @Basic
  @Column(name = "name")
  private String name;

  @Basic
  @Column(name = "message_item_name")
  private String messageItemName;

  @Column(name = "record_status")
  private String status;

  @Basic
  @Column(name = "package_name")
  private String packageName;

  @Basic
  @Column(name = "type")
  private String type;

  @Basic
  @Column(name = "filter")
  private String filter;

  @Basic
  @Column(name = "attribute")
  private String attribute;

  @Basic
  @Column(name = "document_config_header_id")
  private Integer documentConfigHeaderId;

  @Column(name = "created_by")
  private Integer createBy;

  @Column(name = "created_date")
  private Date createDt;

  @Column(name = "updated_by")
  private Integer updateBy;

  @Column(name = "updated_date")
  private Date updateDt;

  @Basic
  @Column(name = "doc_template_id")
  private Integer docTemplateId;

  @Basic
  @Column(name = "group_data")
  private String groupData;

  @Basic
  @Column(name = "excel_column_index")
  private Long excelColumnIndex;

  @Basic
  @Column(name = "is_document_code")
  private String isDocumentCode;

  @Basic
  @Column(name = "data_format")
  private String dataFormat;

  @Basic
  @Column(name = "data_length")
  private Long dataLength;

  @Basic
  @Column(name = "table_validate")
  private String tableValidate;

  @Basic
  @Column(name = "column_validate")
  private String columnValidate;

  @Basic
  @Column(name = "template_code")
  private String templateCode;

  @Basic
  @Column(name = "is_encrypt")
  private String isEncrypt;

  @Basic
  @Column(name = "is_show")
  private String isShow;

}

