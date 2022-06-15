package com.esign.service.configuration.controller;

import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.company.EMailServerCriteria;
import com.esign.service.configuration.dto.company.EMailServerDto;
import com.esign.service.configuration.entity.email.MailServer;
import com.esign.service.configuration.service.company.MailServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;

@Slf4j
@RestController
@RequestMapping("api/v/1/mail/server")
@PreAuthorize("isAuthenticated()")
public class MailServerConfigController {

    @Autowired
    protected MailServerService mailServerService;

    @Autowired
    OauthUser oauthUser;

    @GetMapping("/{mailServerId}")
    public MailServer getMailServer(@PathVariable Integer mailServerId){
        return mailServerService.getMailServer(mailServerId);
    }

    @GetMapping("/search")
    public Page<MailServer> findAllMailServer(
            @RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", value = "per_page") Integer perPage,
            @RequestParam(required = false, defaultValue = "ASC", value = "order") Sort.Direction direction,
            @RequestParam(required = false, defaultValue = "roleId", value = "sort") String sort,
            @RequestParam(required = false, value = "mailServerId") Integer mailServerId,
            @RequestParam(required = false, value = "branchId") Integer branchId,
            @RequestParam(required = false, value = "branchName") String branchName,
            @RequestParam(required = false, value = "status") Integer status) {
        EMailServerCriteria searchCriteria = EMailServerCriteria.builder().page(page).perPage(perPage)
                .direction(direction).sort(sort).mailServerId(mailServerId).branchId(branchId).branchName(branchName).status(status).build();
        return mailServerService.findAllMailServerBySpecification(searchCriteria);
    }

    @PostMapping("/search")
    public Page<MailServer> findAllMailServer(@RequestBody EMailServerCriteria searchCriteria) {
        return mailServerService.findAllMailServerBySpecification(searchCriteria);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMailService(@RequestBody EMailServerDto mailServerDto, UriComponentsBuilder ucBuilder) {
        MailServer mailServer = mailServerService.getMailServer(mailServerDto.getMailServerId());
        if(null != mailServer){
            BeanUtils.copyProperties(mailServerDto, mailServer);
            mailServer.setLastUpdatedBy(oauthUser.oauthUserId());
            mailServer.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
        }else{
            mailServer = new MailServer();
            BeanUtils.copyProperties(mailServerDto, mailServer);
            mailServer.setCreatedUser(oauthUser.oauthUserId());
            mailServer.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        }
        mailServer = mailServerService.saveMailServer(mailServer);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/v/1/mail/server/{mailServerId}").buildAndExpand(mailServer.getMailServerId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}

