package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.ComBranchEntity;
import java.util.List;
import java.util.Optional;

import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ComBranchRepository
    extends JpaRepository<ComBranchEntity, Long>,
        JpaSpecificationExecutor<ComBranchEntity> {

  List<ComBranchEntity> findAllByCompanyIdAndBranchCodeAndStatus(Long id, String branchCode, int status);
  List<ComBranchEntity> findAllByCompanyIdAndStatus(Long id, int status);
  List<ComBranchEntity> findAllByParentBranchIdAndStatus(long id, Integer status);

}
