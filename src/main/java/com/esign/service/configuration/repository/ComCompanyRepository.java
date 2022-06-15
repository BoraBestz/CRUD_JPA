package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.ComCompanyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ComCompanyRepository
    extends JpaRepository<ComCompanyEntity, Long>,
        JpaSpecificationExecutor<ComCompanyEntity> {

  List<ComCompanyEntity> findAllByNameAndStatus(String name, int status);
  List<ComCompanyEntity> findAllByCityIdAndStatus(int id, int status);
}
