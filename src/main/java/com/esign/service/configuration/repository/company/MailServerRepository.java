package com.esign.service.configuration.repository.company;

import com.esign.service.configuration.entity.email.MailServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MailServerRepository extends JpaRepository<MailServer, Integer>,
    JpaSpecificationExecutor<MailServer> {

    MailServer getByMailServerId(Integer mailServerId);
}
