package com.esign.service.configuration.repository.pass;

import com.esign.service.configuration.entity.pass.PasswordHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassHistoryRepository extends JpaRepository<PasswordHistoryEntity, Integer>, JpaSpecificationExecutor<PasswordHistoryEntity> {
    List<PasswordHistoryEntity> findAllByPasswordHistoryAndStatus(String password, String status);
}
