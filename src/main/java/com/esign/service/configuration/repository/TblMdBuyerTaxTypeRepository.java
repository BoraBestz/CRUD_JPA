package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdBuyerTaxTypeEnitity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblMdBuyerTaxTypeRepository extends JpaRepository<TblMdBuyerTaxTypeEnitity, Integer>, JpaSpecificationExecutor<TblMdBuyerTaxTypeEnitity> {
    List<TblMdBuyerTaxTypeEnitity> findAllByBuyerTaxTypeCodeAndRecordStatus(String name, String status);
}
