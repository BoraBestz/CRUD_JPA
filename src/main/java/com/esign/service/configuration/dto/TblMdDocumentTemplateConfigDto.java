package com.esign.service.configuration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TblMdDocumentTemplateConfigDto {
    @JsonIgnore
    private int page;
    @JsonIgnore private Integer perPage;
    @JsonIgnore private String direction;
    @JsonIgnore private String sort;

    private long documentTemplateConfigId;
    private String documentTemplateCode;
    private String documentTemplateName;
    private long companyId;
    private long documentTypeId;
    private Integer sourceDataId;
    private Integer productGroupId;
    private String importTemplateCode;
    private String importTemplateName;
    private Short totalColumn;
    private Short startReadRec;
    private String isGenPdf;
    private String isSignDoc;
    private String isGenXml;
    private String isPortal;
    private String isDeliverSentEmail;
    private String isDeliverSentSms;
    private String isNotifySentLine;
    private String notifyLineTemplateCode;
    private String isDms;
    private String dmsFileExtension;
    private String dmsFileExpireday;
    private Integer status;
    private Long createdUser;
    private Timestamp createdDate;
    private Long lastUpdatedBy;
    private Timestamp lastUpdatedDate;
    private Long pdfTemplateId;
    private Long mailTemplateId;
    private Long xmlTemplateId;
    private Long smsTemplateId;
    private String locale;
    private String formId;
    private String isPdfA3Sign;
    private String isPdfA4;
    private String isPdfA4Sign;

    @JsonIgnore private String fullSearch;
    @JsonIgnore private Integer createBy;
    @JsonIgnore private Date createDate;
    @JsonIgnore private Integer updateBy;
    @JsonIgnore private Date updateDate;
}
