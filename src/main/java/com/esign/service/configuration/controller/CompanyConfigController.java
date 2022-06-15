package com.esign.service.configuration.controller;

import com.esign.service.configuration.client.DmsServiceClient;
import com.esign.service.configuration.config.OauthUser;
import com.esign.service.configuration.dto.Document;
import com.esign.service.configuration.dto.company.*;
import com.esign.service.configuration.entity.email.MailServer;
import com.esign.service.configuration.entity.company.*;
import com.esign.service.configuration.service.company.*;
import com.esign.service.configuration.utils.MapperUtils;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


@Transactional
@RestController
@RequestMapping("api/v/1/company")
@PreAuthorize("isAuthenticated()")
public class CompanyConfigController {

    @Autowired
    OauthUser oauthUser;
    
    @Autowired
    protected CompanyService companyService;

    @Autowired
    protected DmsServiceClient dmsService;

    @Autowired
    protected BranchService branchService;

    @Autowired
    protected MailServerService mailServerService;

    @Autowired
    protected CaConfigService caConfigService;

    private Company saveCompany(CompanyDto companyDto){
        Company company = companyService.getCompany(companyDto.getCompanyId());
        if(null != company){
            BeanUtils.copyProperties(companyDto, company,new String[]{"branchesByCompanyId","mapBranchByCompanyId",
                "companiesByCompanyId","companyLogosByCompanyId",
                "caConfigsByCompanyId","mailServersByCompanyId"});
            company.setLastUpdatedBy(oauthUser.oauthUserId());
            company.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));

            if(null!=companyDto.getMailServersByCompanyId()){
                Company finalCompany2 = company;
                Collection<MailServer> mailServersByCompanyId = companyDto.getMailServersByCompanyId().stream().map(mailServerDto -> {
                    MailServer mailServer = mailServerService.getMailServer(mailServerDto.getMailServerId());
                    if(null != mailServer){
                        BeanUtils.copyProperties(mailServerDto, mailServer);
                        mailServer.setCompanyByCompanyId(finalCompany2);
                        mailServer.setLastUpdatedBy(oauthUser.oauthUserId());
                        mailServer.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
                    }else{
                        mailServer = new MailServer();
                        BeanUtils.copyProperties(mailServerDto, mailServer);
                        mailServer.setStatus(1);
                        mailServer.setCompanyByCompanyId(finalCompany2);
                        mailServer.setCreatedUser(oauthUser.oauthUserId());
                        mailServer.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                        mailServer = mailServerService.saveMailServer(mailServer);
                    }
                    return mailServer;
                }).collect(Collectors.toList());
                company.setMailServersByCompanyId(mailServersByCompanyId);
            }
            if(null != companyDto.getCaConfigsByCompanyId()){
                Company finalCompany3 = company;
                Collection<CaConfig> caConfigsByCompanyId = companyDto.getCaConfigsByCompanyId().stream().map(caConfigDto -> {
                    CaConfig caConfig = caConfigService.getCaConfigByCaId(caConfigDto.getCaId());
                    if(null != caConfig){
                        BeanUtils.copyProperties(caConfigDto, caConfig);
                        caConfig.setCompanyByCompanyId(finalCompany3);
                        caConfig.setCreatedUser(oauthUser.oauthUserId());
                        caConfig.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                    }else{
                        caConfig = new CaConfig();
                        BeanUtils.copyProperties(caConfigDto, caConfig);
                        caConfig.setStatus(1);
                        caConfig.setCompanyByCompanyId(finalCompany3);
                        caConfig.setCreatedUser(oauthUser.oauthUserId());
                        caConfig.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                        caConfig = caConfigService.save(caConfig);
                    }
                    return caConfig;
                }).collect(Collectors.toList());
                company.setCaConfigsByCompanyId(caConfigsByCompanyId);
            }

            if(null != companyDto.getBranchesByCompanyId()){
                Company finalCompany = company;
                Collection<Branch> branchesByCompanyId = companyDto.getBranchesByCompanyId().stream().map(branchDto -> {
                    Branch branch = branchService.getByBranchId(branchDto.getBranchId());
                    if(null != branch){
                        BeanUtils.copyProperties(branchDto, branch);
                        branch.setLastUpdatedBy(oauthUser.oauthUserId());
                        branch.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
                        branch.setCompanyByCompanyId(finalCompany);
                        branch.setCompanyId(finalCompany.getCompanyId());
                    }else{
                        branch = new Branch();
                        BeanUtils.copyProperties(branchDto, branch);
                        branch.setStatus(1);
                        branch.setCompanyId(finalCompany.getCompanyId());
                        branch.setCompanyByCompanyId(finalCompany);
                        branch.setCreatedUser(oauthUser.oauthUserId());
                        branch.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                        branch = branchService.branchConfigSave(branch);
                    }
                    return branch;
                }).collect(Collectors.toList());
                company.setBranchesByCompanyId(branchesByCompanyId);
            }
            Document document = null;
            if(null != companyDto.getCompanyLogoFile()){
                document = new Document();
                document.setFilename(companyDto.getCompanyLogoFile().getFilename());
                document.setTaxNo(companyDto.getTaxId());
                document.setDocumentName("CompanyLogo");
                document.setData(companyDto.getCompanyLogoFile().getData());
                document.setContentType(companyDto.getCompanyLogoFile().getContentType());
                //document = dmsService.saveUploadedFiles(document);
            }
            Document finalDocument = document;
            Company finalCompany1 = company;
            if(companyDto.getCompanyLogosByCompanyId() != null && companyDto.getCompanyLogosByCompanyId().size() > 0){
                Collection<CompanyLogo> companyLogosByCompanyId = companyDto.getCompanyLogosByCompanyId().stream().map(companyLogoDto -> {
                    CompanyLogo companyLogo = companyService.getByCompanyLogoId(companyLogoDto.getCompanyLogoId() );
                    if(null == companyLogo){
                        companyLogo = new CompanyLogo();
                        companyLogo.setCompanyId(finalCompany1.getCompanyId());
                        companyLogo.setCompanyByCompanyId(finalCompany1);
                        if(finalDocument != null){
                            companyLogo.setDmsId(finalDocument.getId());
                        }
                        companyLogo.setStatus(companyDto.getStatus());
                        companyLogo.setCreatedUser(oauthUser.oauthUserId());
                        companyLogo.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                    }else{
                        if(finalDocument != null){
                            companyLogo.setDmsId(finalDocument.getId());
                        }
                        companyLogo.setCompanyByCompanyId(finalCompany1);
                        //companyLogo.setStatus(companyDto.getStatus());
                        companyLogo.setLastUpdatedBy(oauthUser.oauthUserId());
                        companyLogo.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
                    }
                    return companyLogo;
                }).collect(Collectors.toList());
                company.setCompanyLogosByCompanyIdEntity(companyLogosByCompanyId);
            }else if(finalDocument != null){
                CompanyLogo companyLogo = new CompanyLogo();
                companyLogo.setCompanyId(finalCompany1.getCompanyId());
                companyLogo.setCompanyByCompanyId(finalCompany1);
                if(finalDocument != null){
                    companyLogo.setDmsId(finalDocument.getId());
                }                companyLogo.setStatus(companyDto.getStatus());
                companyLogo.setCreatedUser(oauthUser.oauthUserId());
                companyLogo.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                companyLogo = companyService.save(companyLogo);
                //company.setCompanyLogosByCompanyId(Arrays.asList(companyLogo));
            }
            company = companyService.saveCompany(company);
        }else{
            company = new Company();
            BeanUtils.copyProperties(companyDto, company);
            company.setCreatedUser(oauthUser.oauthUserId());
            company.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            company.setStatus(1);
            company.setParentCompanyId(0);
            company = companyService.saveCompany(company);
            Document document = null;
            if(companyDto.getCompanyLogoFile() != null){
                document = new Document();
                document.setFilename(companyDto.getCompanyLogoFile().getFilename());
                document.setTaxNo(companyDto.getTaxId());
                document.setDocumentName("CompanyLogo");
                document.setData(companyDto.getCompanyLogoFile().getData());
                document.setContentType(companyDto.getCompanyLogoFile().getContentType());
                //document.setStatus(companyDto.getStatus());
                //document = dmsService.saveUploadedFiles(document);
                CompanyLogo companyLogo  = new CompanyLogo();
                companyLogo.setCompanyId(company.getCompanyId());
                companyLogo.setDmsId(document.getId());
                companyLogo.setStatus(companyDto.getStatus());
                companyLogo.setCreatedUser(oauthUser.oauthUserId());
                companyLogo.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                companyLogo = companyService.save(companyLogo);
                company.setCompanyLogosByCompanyIdEntity(Arrays.asList(companyLogo));
            }

            if(null!=companyDto.getMailServersByCompanyId()){
                Collection<MailServer> mailServersByCompanyId = new ArrayList<>();
                Company finalCompany4 = company;
                companyDto.getMailServersByCompanyId().forEach(mailServerDto -> {
                    MailServer mailServer = new MailServer();
                    BeanUtils.copyProperties(mailServerDto, mailServer);
                    mailServer.setMailServerId(null);
                    mailServer.setStatus(1);
                    mailServer.setCreatedUser(oauthUser.oauthUserId());
                    mailServer.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                    mailServer.setCompanyByCompanyId(finalCompany4);
                    mailServer = mailServerService.saveMailServer(mailServer);
                    mailServersByCompanyId.add(mailServer);
                });
                company.setMailServersByCompanyId(mailServersByCompanyId);
            }

            if(null != companyDto.getCaConfigsByCompanyId()){
                Collection<CaConfig> caConfigsByCompanyId = new ArrayList<>();
                Company finalCompany5 = company;
                companyDto.getCaConfigsByCompanyId().forEach(caConfigDto -> {
                    CaConfig caConfig = new CaConfig();
                    BeanUtils.copyProperties(caConfigDto, caConfig);
                    caConfig.setStatus(1);
                    caConfig.setCaId(null);
                    caConfig.setCreatedUser(oauthUser.oauthUserId());
                    caConfig.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                    caConfig.setCompanyByCompanyId(finalCompany5);
                    caConfig = caConfigService.save(caConfig);
                    caConfigsByCompanyId.add(caConfig);
                });
                company.setCaConfigsByCompanyId(caConfigsByCompanyId);
            }

            /*** Default branch same as company *****/
            Branch branch = new Branch();
            BeanUtils.copyProperties(company, branch);
            branch.setBranchCode("00000");
            branch.setName("สำนักงานใหญ่");
            branch.setBranchId(null);
            branch.setStatus(1);
            branch.setCreatedUser(oauthUser.oauthUserId());
            branch.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            branch.setCompanyByCompanyId(company);
            branch.setCompanyId(company.getCompanyId());
            branch = branchService.branchConfigSave(branch);
            Collection<Branch> branchesByCompanyId = new ArrayDeque<>();
            branchesByCompanyId.add(branch);
            company.setBranchesByCompanyId(branchesByCompanyId);
        }
        return company;
    }

    @GetMapping("/{companyId}")
    public CompanyDto company(@PathVariable Integer companyId ) {
        return MapperUtils.getCompanyDto(companyService.getCompany(companyId));
    }

    @GetMapping("/config/search")
    public Page<CompanyDto> companyConfigSearch(
        @RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
        @RequestParam(required = false, defaultValue = "10", value = "per_page") Integer perPage,
        @RequestParam(required = false, defaultValue = "ASC", value = "order") Sort.Direction direction,
        @RequestParam(required = false, defaultValue = "companyId", value = "sort") String sort,
        @RequestParam(required = false, value = "companyId") Integer companyId,
        @RequestParam(required = false, value = "branchId") Integer branchId,
        @RequestParam(required = false, value = "branchName") String branchName,
        @RequestParam(required = false, value = "globalId") String globalId,
        @RequestParam(required = false, value = "name") String name,
        @RequestParam(required = false, value = "taxId") String taxId,
        @RequestParam(required = false, value = "search") String search,
        @RequestParam(required = false, value = "taxSchemeId") String taxSchemeId,
        @RequestParam(required = false, value = "postcodeCode") String postcodeCode,
        @RequestParam(required = false, value = "status",defaultValue = "1") Integer status) {

        Set<Integer> companyIs = oauthUser.getAllBranch().stream().map(k ->{
            Branch branch = branchService.getByBranchId(Integer.valueOf(k));
            return branch.getCompanyId();
        }).collect(Collectors.toSet());
        Set<Integer> checkvalues = new HashSet<Integer>();
        checkvalues.addAll(companyIs);
        boolean next = true;
        while(next) {
            checkvalues = getCompanyParent(checkvalues);
            if ( checkvalues == null ) {
                break;
            }
            companyIs.addAll(
                checkvalues.stream().map(v ->{
                    return v;
                }).collect(Collectors.toSet())
            );
        }

        CompanyCriteria searchCriteria = CompanyCriteria.builder().page(page).perPage(perPage)
            .direction(direction).sort(sort).globalId(globalId).name(name).taxId(taxId)
            .branchId(branchId).branchName(branchName)
            .companyIds(companyIs)
            //.parentCompanyId(principal.getCompanyId())
            .taxSchemeId(taxSchemeId).postcodeCode(postcodeCode).status(status).build();

        return MapperUtils.findCompanyDtoPage(companyService.findAllCompany(searchCriteria));
    }

    @PostMapping("/config/search")
    public Page<CompanyDto> companyConfigSearch(@RequestBody CompanyCriteria searchCriteria) {
        return MapperUtils.findCompanyDtoPage(companyService.findAllCompany(searchCriteria));
    }

    @PostMapping("/config/save")
    public ResponseEntity<?> companyConfigSave(@RequestBody CompanyDto companyDto, UriComponentsBuilder ucBuilder) {
        Company company = saveCompany(companyDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/v/1/company/{id}").buildAndExpand(company.getCompanyId()).toUri());
        Map map = new HashMap();
        map.put("companyId",company.getCompanyId());
        return new ResponseEntity<>(map,headers, HttpStatus.CREATED);
    }

    @PostMapping("/config/logo/upload/base64")
    public ResponseEntity<?> saveCompanyLogoBase64(@RequestParam(name = "companyLogoFile",required = false) MultipartFile companyLogoFile) throws IOException {
        Document document = Document.builder()
            .filename(companyLogoFile.getOriginalFilename())
            .contentType(companyLogoFile.getContentType())
            .data(Base64.getEncoder().encodeToString(companyLogoFile.getBytes())).build();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(document,headers, HttpStatus.CREATED);
    }

    @PostMapping("/config/logo/upload")
    public ResponseEntity<?> saveCompanyLogo(@RequestParam(name = "companyLogoFile",required = false) MultipartFile companyLogoFile
        ,@RequestParam(name = "companyLogoId",required = false) Integer companyLogoId
        ,@RequestParam(name = "companyId") Integer companyId
        ,@RequestParam(name = "status",defaultValue = "0") Integer status, UriComponentsBuilder ucBuilder) throws IOException {
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        CompanyLogo companyLogo = companyService.getByCompanyLogoId(companyLogoId);
        Document document = new Document();
        document.setFilename(companyLogoFile.getOriginalFilename());
        //document.setTaxNo(taxNo);
        document.setDocumentName("CompanyLogo");
        document.setData(Base64.getEncoder().encodeToString(companyLogoFile.getBytes()));
        document.setContentType(companyLogoFile.getContentType());
        //document = dmsService.saveUploadedFiles(document,oAuth2AuthenticationDetails);
        if(null == companyLogo ){
            companyLogo = new CompanyLogo();
            companyLogo.setCompanyId(companyId);
            companyLogo.setDmsId(document.getId());
            companyLogo.setStatus(status);
            companyLogo.setCreatedUser(oauthUser.oauthUserId());
            companyLogo.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        }else{
            companyLogo.setDmsId(document.getId());
            companyLogo.setStatus(status);
            companyLogo.setLastUpdatedBy(oauthUser.oauthUserId());
            companyLogo.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
        }
        companyLogo = companyService.save(companyLogo);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/v/1/company/config/logo/download/{companyId}").buildAndExpand(companyLogo.getCompanyId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/config/logo/download/{companyId}")
    public ResponseEntity<Resource> getCompanyLogo(@PathVariable Integer companyId) throws UnsupportedEncodingException {
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        CompanyLogo companyLogo = companyService.getFirstByCompanyIdAndStatusOrderByCreatedDateDesc(companyId,1);
        Document document = new Document();//dmsService.download(companyLogo.getDmsId(),oAuth2AuthenticationDetails);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(document.getContentType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + URLEncoder.encode (document.getFilename(), "utf-8").replace("+", " ") + "\"")
            .body(new InputStreamResource(new ByteArrayInputStream(Base64.getDecoder().decode(document.getData()))));
    }

    @GetMapping("/profile/search")
    public Page<CompanyDto> companyProfileSearch(
        @RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
        @RequestParam(required = false, defaultValue = "10", value = "per_page") Integer perPage,
        @RequestParam(required = false, defaultValue = "ASC", value = "order") Sort.Direction direction,
        @RequestParam(required = false, defaultValue = "companyId", value = "sort") String sort,
        @RequestParam(required = false, value = "companyId") Integer companyId,
        @RequestParam(required = false, value = "branchId") Integer branchId,
        @RequestParam(required = false, value = "branchName") String branchName,
        @RequestParam(required = false, value = "globalId") String globalId,
        @RequestParam(required = false, value = "name") String name,
        @RequestParam(required = false, value = "taxId") String taxId,
        @RequestParam(required = false, value = "taxSchemeId") String taxSchemeId,
        @RequestParam(required = false, value = "postcodeCode") String postcodeCode) {
        CompanyCriteria searchCriteria = CompanyCriteria.builder().page(page).perPage(perPage)
            .direction(direction).sort(sort).companyId(companyId).globalId(globalId).name(name).taxId(taxId)
            .branchId(branchId).branchName(branchName)
            .taxSchemeId(taxSchemeId).postcodeCode(postcodeCode).build();
        return MapperUtils.findCompanyDtoPage(companyService.findAllCompany(searchCriteria));
    }

    @PostMapping("/profile/search")
    public Page<CompanyDto> companyProfileSearch(@RequestBody CompanyCriteria searchCriteria) {
        return MapperUtils.findCompanyDtoPage(companyService.findAllCompany(searchCriteria));
    }

    @PostMapping("/profile/save")
    public ResponseEntity<?> companyProfileSave(@RequestBody CompanyDto companyDto, UriComponentsBuilder ucBuilder) {
        Company company = saveCompany(companyDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/v/1/company/{id}").buildAndExpand(company.getCompanyId()).toUri());
        Map map = new HashMap();
        map.put("companyId",company.getCompanyId());
        return new ResponseEntity<>(map,headers, HttpStatus.CREATED);
    }

    @PostMapping("/profile/logo/upload/base64")
    public ResponseEntity<?> saveCompanyProfileLogoBase64(@RequestParam(name = "companyLogoFile",required = false) MultipartFile companyLogoFile) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(Base64.getEncoder().encodeToString(companyLogoFile.getBytes()),headers, HttpStatus.CREATED);
    }

    @PostMapping("/profile/logo/upload")
    public ResponseEntity<?> saveCompanyProfileLogoo(@RequestParam(name = "companyLogoFile",required = false) MultipartFile companyLogoFile
        ,@RequestParam(name = "companyLogoId",required = false) Integer companyLogoId
        ,@RequestParam(name = "companyId") Integer companyId
        ,@RequestParam(name = "status",defaultValue = "0") Integer status, UriComponentsBuilder ucBuilder) throws IOException {
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        CompanyLogo companyLogo = companyService.getByCompanyLogoId(companyLogoId);
        Document document = new Document();
        document.setFilename(companyLogoFile.getOriginalFilename());
        //document.setTaxNo(taxNo);
        document.setDocumentName("CompanyLogo");
        document.setData(Base64.getEncoder().encodeToString(companyLogoFile.getBytes()));
        document.setContentType(companyLogoFile.getContentType());
        document = new Document();//dmsService.saveUploadedFiles(document,oAuth2AuthenticationDetails);
        if(null == companyLogo ){
            companyLogo = new CompanyLogo();
            companyLogo.setCompanyId(companyId);
            companyLogo.setDmsId(document.getId());
            companyLogo.setStatus(status);
            companyLogo.setCreatedUser(oauthUser.oauthUserId());
            companyLogo.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        }else{
            companyLogo.setDmsId(document.getId());
            companyLogo.setStatus(status);
            companyLogo.setLastUpdatedBy(oauthUser.oauthUserId());
            companyLogo.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
        }
        companyLogo = companyService.save(companyLogo);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/v/1/company/config/logo/download/{companyId}").buildAndExpand(companyLogo.getCompanyId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/profile/logo/download/{companyId}")
    public ResponseEntity<Resource> getCompanyProfileLogo(@PathVariable Integer companyId) throws UnsupportedEncodingException {
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        CompanyLogo companyLogo = companyService.getByCompanyId(companyId);
        Document document = new Document();//dmsService.download(companyLogo.getDmsId(),oAuth2AuthenticationDetails);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(document.getContentType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + URLEncoder.encode (document.getFilename(), "utf-8").replace("+", " ") + "\"")
            .body(new InputStreamResource(new ByteArrayInputStream(Base64.getDecoder().decode(document.getData()))));
    }

    private Set<Integer> getCompanyParent(Set<Integer> companyIds) {
        Integer[] array = companyIds.stream().toArray(n -> new Integer[n]);
        List<Company> companies = companyService.getCompanyByParentCompany(companyIds.toArray(array));
        if ( companies != null && companies.size() > 0 ) {
            return companies.stream().map(k -> {
                return k.getCompanyId();
            }).collect(Collectors.toSet());
        }
        return null;
    }
}
