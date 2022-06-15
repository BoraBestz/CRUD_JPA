package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdPdfPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblMdPdfPasswordRepository extends JpaRepository<TblMdPdfPasswordEntity, Long>, JpaSpecificationExecutor<TblMdPdfPasswordEntity> {
    List<TblMdPdfPasswordEntity> findAllByPdfPasswordCode(String code);
    Optional<TblMdPdfPasswordEntity> findById(long id);
}
