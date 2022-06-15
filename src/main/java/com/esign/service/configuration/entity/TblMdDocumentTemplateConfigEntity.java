package com.esign.service.configuration.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@Table(name = "tbl_md_document_template_config", schema = "conf", catalog = "etax")
public class TblMdDocumentTemplateConfigEntity {
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

    @Id
    @Column(name = "document_template_config_id")
    public long getDocumentTemplateConfigId() {
        return documentTemplateConfigId;
    }

    public void setDocumentTemplateConfigId(long documentTemplateConfigId) {
        this.documentTemplateConfigId = documentTemplateConfigId;
    }

    @Basic
    @Column(name = "document_template_code")
    public String getDocumentTemplateCode() {
        return documentTemplateCode;
    }

    public void setDocumentTemplateCode(String documentTemplateCode) {
        this.documentTemplateCode = documentTemplateCode;
    }

    @Basic
    @Column(name = "document_template_name")
    public String getDocumentTemplateName() {
        return documentTemplateName;
    }

    public void setDocumentTemplateName(String documentTemplateName) {
        this.documentTemplateName = documentTemplateName;
    }

    @Basic
    @Column(name = "company_id")
    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "document_type_id")
    public long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    @Basic
    @Column(name = "source_data_id")
    public Integer getSourceDataId() {
        return sourceDataId;
    }

    public void setSourceDataId(Integer sourceDataId) {
        this.sourceDataId = sourceDataId;
    }

    @Basic
    @Column(name = "product_group_id")
    public Integer getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(Integer productGroupId) {
        this.productGroupId = productGroupId;
    }

    @Basic
    @Column(name = "import_template_code")
    public String getImportTemplateCode() {
        return importTemplateCode;
    }

    public void setImportTemplateCode(String importTemplateCode) {
        this.importTemplateCode = importTemplateCode;
    }

    @Basic
    @Column(name = "import_template_name")
    public String getImportTemplateName() {
        return importTemplateName;
    }

    public void setImportTemplateName(String importTemplateName) {
        this.importTemplateName = importTemplateName;
    }

    @Basic
    @Column(name = "total_column")
    public Short getTotalColumn() {
        return totalColumn;
    }

    public void setTotalColumn(Short totalColumn) {
        this.totalColumn = totalColumn;
    }

    @Basic
    @Column(name = "start_read_rec")
    public Short getStartReadRec() {
        return startReadRec;
    }

    public void setStartReadRec(Short startReadRec) {
        this.startReadRec = startReadRec;
    }

    @Basic
    @Column(name = "is_gen_pdf")
    public String getIsGenPdf() {
        return isGenPdf;
    }

    public void setIsGenPdf(String isGenPdf) {
        this.isGenPdf = isGenPdf;
    }

    @Basic
    @Column(name = "is_sign_doc")
    public String getIsSignDoc() {
        return isSignDoc;
    }

    public void setIsSignDoc(String isSignDoc) {
        this.isSignDoc = isSignDoc;
    }

    @Basic
    @Column(name = "is_gen_xml")
    public String getIsGenXml() {
        return isGenXml;
    }

    public void setIsGenXml(String isGenXml) {
        this.isGenXml = isGenXml;
    }

    @Basic
    @Column(name = "is_portal")
    public String getIsPortal() {
        return isPortal;
    }

    public void setIsPortal(String isPortal) {
        this.isPortal = isPortal;
    }

    @Basic
    @Column(name = "is_deliver_sent_email")
    public String getIsDeliverSentEmail() {
        return isDeliverSentEmail;
    }

    public void setIsDeliverSentEmail(String isDeliverSentEmail) {
        this.isDeliverSentEmail = isDeliverSentEmail;
    }

    @Basic
    @Column(name = "is_deliver_sent_sms")
    public String getIsDeliverSentSms() {
        return isDeliverSentSms;
    }

    public void setIsDeliverSentSms(String isDeliverSentSms) {
        this.isDeliverSentSms = isDeliverSentSms;
    }

    @Basic
    @Column(name = "is_notify_sent_line")
    public String getIsNotifySentLine() {
        return isNotifySentLine;
    }

    public void setIsNotifySentLine(String isNotifySentLine) {
        this.isNotifySentLine = isNotifySentLine;
    }

    @Basic
    @Column(name = "notify_line_template_code")
    public String getNotifyLineTemplateCode() {
        return notifyLineTemplateCode;
    }

    public void setNotifyLineTemplateCode(String notifyLineTemplateCode) {
        this.notifyLineTemplateCode = notifyLineTemplateCode;
    }

    @Basic
    @Column(name = "is_dms")
    public String getIsDms() {
        return isDms;
    }

    public void setIsDms(String isDms) {
        this.isDms = isDms;
    }

    @Basic
    @Column(name = "dms_file_extension")
    public String getDmsFileExtension() {
        return dmsFileExtension;
    }

    public void setDmsFileExtension(String dmsFileExtension) {
        this.dmsFileExtension = dmsFileExtension;
    }

    @Basic
    @Column(name = "dms_file_expireday")
    public String getDmsFileExpireday() {
        return dmsFileExpireday;
    }

    public void setDmsFileExpireday(String dmsFileExpireday) {
        this.dmsFileExpireday = dmsFileExpireday;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "created_user")
    public Long getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Long createdUser) {
        this.createdUser = createdUser;
    }

    @Basic
    @Column(name = "created_date")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "last_updated_by")
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Basic
    @Column(name = "last_updated_date")
    public Timestamp getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Basic
    @Column(name = "pdf_template_id")
    public Long getPdfTemplateId() {
        return pdfTemplateId;
    }

    public void setPdfTemplateId(Long pdfTemplateId) {
        this.pdfTemplateId = pdfTemplateId;
    }

    @Basic
    @Column(name = "mail_template_id")
    public Long getMailTemplateId() {
        return mailTemplateId;
    }

    public void setMailTemplateId(Long mailTemplateId) {
        this.mailTemplateId = mailTemplateId;
    }

    @Basic
    @Column(name = "xml_template_id")
    public Long getXmlTemplateId() {
        return xmlTemplateId;
    }

    public void setXmlTemplateId(Long xmlTemplateId) {
        this.xmlTemplateId = xmlTemplateId;
    }

    @Basic
    @Column(name = "sms_template_id")
    public Long getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(Long smsTemplateId) {
        this.smsTemplateId = smsTemplateId;
    }

    @Basic
    @Column(name = "locale")
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Basic
    @Column(name = "form_id")
    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    @Basic
    @Column(name = "is_pdf_a3_sign")
    public String getIsPdfA3Sign() {
        return isPdfA3Sign;
    }

    public void setIsPdfA3Sign(String isPdfA3Sign) {
        this.isPdfA3Sign = isPdfA3Sign;
    }

    @Basic
    @Column(name = "is_pdf_a4")
    public String getIsPdfA4() {
        return isPdfA4;
    }

    public void setIsPdfA4(String isPdfA4) {
        this.isPdfA4 = isPdfA4;
    }

    @Basic
    @Column(name = "is_pdf_a4_sign")
    public String getIsPdfA4Sign() {
        return isPdfA4Sign;
    }

    public void setIsPdfA4Sign(String isPdfA4Sign) {
        this.isPdfA4Sign = isPdfA4Sign;
    }
}
