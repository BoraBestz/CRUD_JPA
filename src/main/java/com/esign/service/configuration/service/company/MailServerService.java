package com.esign.service.configuration.service.company;

import com.esign.service.configuration.dto.company.EMailServerCriteria;
import com.esign.service.configuration.entity.email.MailServer;
import com.esign.service.configuration.repository.company.MailServerRepository;
import com.esign.service.configuration.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailServerService {

    @Autowired
    protected MailServerRepository mailServerRepository;

    private Specification<MailServer> getMailServerSpecification(EMailServerCriteria filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getMailServerId() != null) {
                predicates.add(cb.equal(root.get("mailServerId"), filter.getMailServerId()));
            }
            if (filter.getBranchId() != null || filter.getBranchName() != null) {
                Join branchByBranchId = root.join("branchByBranchId",JoinType.INNER);
                if(filter.getBranchId() != null){
                    predicates.add(cb.equal(branchByBranchId.get("branchId"), filter.getBranchId()));
                }
                if(filter.getBranchName() != null){
                    predicates.add(cb.like(cb.lower(branchByBranchId.get("name")),"%" + filter.getBranchName().toLowerCase() + "%"));
                }
            }
            if (filter.getStatus()!= null ) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Page<MailServer> findAllMailServerBySpecification(EMailServerCriteria searchCriteria) {
        Pageable pageRequest = PageUtils.getPageable(searchCriteria.getPage(), searchCriteria.getPerPage());
        return mailServerRepository.findAll(getMailServerSpecification(searchCriteria), pageRequest);
    }

    public MailServer getMailServer(Integer mailServerId){
        return mailServerRepository.getByMailServerId(mailServerId);
    }

    public MailServer saveMailServer(MailServer mailServer){
        return mailServerRepository.save(mailServer);
    }
}
