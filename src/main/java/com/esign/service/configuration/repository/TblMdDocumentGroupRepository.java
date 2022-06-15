package com.esign.service.configuration.repository;

import com.esign.service.configuration.entity.TblMdDocumentGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TblMdDocumentGroupRepository extends JpaRepository<TblMdDocumentGroupEntity, Long>, JpaSpecificationExecutor<TblMdDocumentGroupEntity> {
    List<TblMdDocumentGroupEntity> findAllByDocumentGroupCode(String code);

    Optional<TblMdDocumentGroupEntity> findById(long id);
}
