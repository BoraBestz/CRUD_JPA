package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdDocumentGroupInfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblMdDocumentGroupInfRepository extends JpaRepository<TblMdDocumentGroupInfEntity, Long>, JpaSpecificationExecutor<TblMdDocumentGroupInfEntity> {
    List<TblMdDocumentGroupInfEntity> findAllByDocumentGroupInfCode(String code);
    List<TblMdDocumentGroupInfEntity> findAllByDocumentGroupIdAndRecordStatus (Integer id,String status);

    Optional<TblMdDocumentGroupInfEntity> findById(long id);
}
