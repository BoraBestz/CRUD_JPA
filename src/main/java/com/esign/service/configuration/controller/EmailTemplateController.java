package com.esign.service.configuration.controller;

import com.esign.service.configuration.client.DmsServiceClient;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.Document;
import com.esign.service.configuration.dto.DocumentTemplateDto;
import com.esign.service.configuration.dto.JsonResponse;
import com.esign.service.configuration.dto.email.EmailTemplateCriteria;
import com.esign.service.configuration.dto.email.EmailTemplateDto;
import com.esign.service.configuration.entity.email.EmailTemplateAttachment;
import com.esign.service.configuration.entity.email.EmailTemplate;
import com.esign.service.configuration.service.company.CompanyService;
import com.esign.service.configuration.service.email.EmailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v/1/email/template")
@PreAuthorize("isAuthenticated()")
public class EmailTemplateController {

    @Autowired
    protected EmailTemplateService emailTemplateService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    protected DmsServiceClient dmsService;
    
    @Autowired
    OauthUser oauthUser;

    @GetMapping("/{emailTemplateId}")
    public EmailTemplate getMailService(@PathVariable Integer emailTemplateId){
        return emailTemplateService.getEmailTemplate(emailTemplateId);
    }

    @GetMapping("/search")
    public Page<EmailTemplate> findAllEmailTemplate(
            @RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", value = "per_page") Integer perPage,
            @RequestParam(required = false, defaultValue = "ASC", value = "order") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "emailTemplateId", value = "sort") String sort,
            @RequestParam(required = false, value = "emailTemplateId") Integer emailTemplateId,
            @RequestParam(required = false, value = "companyId") Integer companyId,
            @RequestParam(required = false, value = "documentTypeId") Integer documentTypeId,
            @RequestParam(required = false, value = "status") Integer status) {
        EmailTemplateCriteria searchCriteria = EmailTemplateCriteria.builder()
            .page(page)
            .perPage(perPage)
            .direction(direction)
            .sort(sort)
            .emailTemplateId(emailTemplateId)
            .companyId(companyId)
            .documentTypeId(documentTypeId)
            .status(status)
            .build();
        return emailTemplateService.findAllEmailTemplateBySpecification(searchCriteria);
    }

    @PostMapping("/search")
    public Page<EmailTemplate> findAllEmailTemplate(@RequestBody EmailTemplateCriteria searchCriteria) {
        return emailTemplateService.findAllEmailTemplateBySpecification(searchCriteria);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveEmailTemplate(@RequestBody EmailTemplateDto emailTemplateDto, UriComponentsBuilder ucBuilder) {
        EmailTemplate emailTemplate = emailTemplateService.getEmailTemplate(emailTemplateDto.getEmailTemplateId());
        //DocumentType documentType = emailTemplateService.getByDocumentTypeId(emailTemplateDto.getDocumentTypeId());
        if(null != emailTemplate){
            BeanUtils.copyProperties(emailTemplateDto, emailTemplate,"newEmailTemplateAttachments","emailTemplateAttachmentsByEmailTemplateId");
            emailTemplate.setLastUpdatedBy(oauthUser.oauthUserId());
            emailTemplate.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
        }else{
            emailTemplate = new EmailTemplate();
            BeanUtils.copyProperties(emailTemplateDto, emailTemplate,"newEmailTemplateAttachments","emailTemplateAttachmentsByEmailTemplateId");
            emailTemplate.setCreatedUser(oauthUser.oauthUserId());
            emailTemplate.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        }
        emailTemplate = emailTemplateService.saveEmailTemplate(emailTemplate);
        if(emailTemplateDto.getNewEmailTemplateAttachments() != null){
            EmailTemplate finalEmailTemplate = emailTemplate;
            emailTemplateDto.getNewEmailTemplateAttachments().stream().map(document -> {
                return dmsService.sendToDMS("", "", "".getBytes());
            }).collect(Collectors.toList()).stream().map(document -> {
                EmailTemplateAttachment emailTemplateAttachment = new EmailTemplateAttachment();
                emailTemplateAttachment.setEmailTemplateByEmailTemplateId(finalEmailTemplate);
                //emailTemplateAttachment.setDmsId(document.getId());
                emailTemplateAttachment.setStatus(1);
                emailTemplateAttachment.setCreatedUser(oauthUser.oauthUserId());
                emailTemplateAttachment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                return emailTemplateService.saveEmailTemplateAttachment(emailTemplateAttachment);
            }).collect(Collectors.toList());
        }
        if(emailTemplateDto.getEmailTemplateAttachmentsByEmailTemplateId() != null){
            emailTemplateDto.getEmailTemplateAttachmentsByEmailTemplateId().stream().filter(emailTemplateAttachmentDto -> emailTemplateAttachmentDto.getStatus().equals(0)).map(emailTemplateAttachmentDto -> {
                EmailTemplateAttachment emailTemplateAttachment = emailTemplateService.getByEmailTemplateAttachmentId(emailTemplateAttachmentDto.getEmailTemplateAttachmentId());
                emailTemplateAttachment.setStatus(0);
                emailTemplateAttachment.setLastUpdatedBy(oauthUser.oauthUserId());
                emailTemplateAttachment.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
                return emailTemplateService.saveEmailTemplateAttachment(emailTemplateAttachment);
            });
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/v/1/email/template/{emailTemplateId}").buildAndExpand(emailTemplate.getEmailTemplateId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/upload/Attachment/base64")
    public ResponseEntity<Document> saveEmailTemplateAttachmentBase64(@RequestParam(name = "emailTemplateAttachment",required = false) MultipartFile emailTemplateAttachment) throws IOException {
        Document document = Document.builder()
                .filename(emailTemplateAttachment.getOriginalFilename())
                .contentType(emailTemplateAttachment.getContentType())
                .data(Base64.getEncoder().encodeToString(emailTemplateAttachment.getBytes())).build();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(document,headers, HttpStatus.OK);
    }

    @GetMapping("/download/{dmsId}")
    public ResponseEntity<Resource> getAttachment(@PathVariable Long dmsId) throws UnsupportedEncodingException {
        Document document = new Document();//dmsService.download(dmsId,oAuth2AuthenticationDetails);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + URLEncoder.encode (document.getFilename(), "utf-8").replace("+", " ") + "\"")
                .body(new InputStreamResource(new ByteArrayInputStream(Base64.getDecoder().decode(document.getData()))));
    }

    @PostMapping("/delete")
    public JsonResponse deleteXMLtemplate(@RequestBody DocumentTemplateDto docTemplateDto){
        JsonResponse repose = new JsonResponse();
        try {
            emailTemplateService.deleteEmailTemplate(docTemplateDto.getTemplateId());
            repose.setRespStatus("1");
            repose.setRespMessage("Success");
        }catch(Exception ex) {
            repose.setRespStatus("-1");
            repose.setRespMessage(ex.getMessage());
        }
        return repose;
    }

}
