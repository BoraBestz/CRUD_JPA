package com.esign.service.configuration.utils;

import com.esign.service.configuration.dto.company.BranchDto;
import com.esign.service.configuration.dto.company.CompanyDto;
import com.esign.service.configuration.dto.company.CompanyLogoDto;
import com.esign.service.configuration.dto.company.EMailServerDto;
import com.esign.service.configuration.entity.company.Branch;
import com.esign.service.configuration.entity.company.Company;
import com.esign.service.configuration.entity.company.CompanyLogo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MapperUtils {

    public static final String VAT_INCLUDE = "1";
    public static final String VAT_EXCLUDE = "2";
    public static final String LOG_WAY_MANUAL = "M";
    public static final String LOG_WAY_AUTO = "A";
    public static final String LOG_STATE_SUCCESS = "S";
    public static final String LOG_STATE_ERROR = "E";
    public static final String LOG_LEVEL_INFO = "I";
    public static final String LOG_LEVEL_ERROR = "E";

    public static final String TRN_PROCESSING = "Processing";
    public static final String TRN_TRANSFER_PENDING = "TR-101";
    public static final String TRN_TRANSFER_PROCESSING = "TR-102";
    public static final String TRN_TRANSFER_SUCCESS = "TR-103";

    public static BranchDto getBranchDto(Branch branch){
        BranchDto branchDto = new BranchDto();
        BeanUtils.copyProperties(branch,branchDto);
        //branchDto.setMailServersByBranchId(getMailServerDto(branch.getMailServersByBranchId()));
        return branchDto;
    }

    private static List<BranchDto> getBranchDtoList(Collection<Branch> branches){
        return null!=branches ? branches.stream().map(branch -> {
            return getBranchDto(branch);
        }).collect(Collectors.toList()):null;
    }

    private static EMailServerDto getMailServerDto(EMailServerDto mailServer){
        EMailServerDto mailServerDto = new EMailServerDto();
        BeanUtils.copyProperties(mailServer,mailServerDto);
        return mailServerDto;
    }

    private static Collection<EMailServerDto> getMailServerDto(Collection<EMailServerDto> mailServers){
        return null!=mailServers ? mailServers.stream().map(mailServer -> {
            return getMailServerDto(mailServer);
        }).collect(Collectors.toList()):null;
    }

    public static CompanyDto getCompanyDto(Company company){
        CompanyDto companyDto = new CompanyDto();
        BranchDto branchDto = new BranchDto();
        BeanUtils.copyProperties(company,companyDto,"mapBranchByCompanyId");
        companyDto.setBranchesByCompanyId(getBranchDtoList(company.getBranchesByCompanyId()));
        companyDto.setCompanyLogosByCompanyId(getCompanyLogoDto(company.getCompanyLogosByCompanyIdEntity()));
        return companyDto;
    }

    private static CompanyLogoDto getCompanyLogoDto(CompanyLogo companyLogo){
        CompanyLogoDto companyLogoDto = new CompanyLogoDto();
        BeanUtils.copyProperties(companyLogo,companyLogoDto);
        return companyLogoDto;
    }

    private static Collection<CompanyLogoDto> getCompanyLogoDto(Collection<CompanyLogo> companyLogos){
        return null!= companyLogos ? companyLogos.stream().map(branch -> {
            return getCompanyLogoDto(branch);
        }).collect(Collectors.toList()) : null;
    }

    public static Page<CompanyDto> findCompanyDtoPage(Page<Company> companyPage)  {
        List<CompanyDto> companyDtoList = companyPage.stream().map(company -> {
            return getCompanyDto(company);
        }).collect(Collectors.toList());
        return new PageImpl(companyDtoList,companyPage.getPageable(),companyPage.getTotalElements());
    }
}
