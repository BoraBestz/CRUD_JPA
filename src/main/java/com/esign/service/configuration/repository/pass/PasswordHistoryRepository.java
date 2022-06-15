package com.esign.service.configuration.repository.pass;

import com.esign.service.configuration.entity.pass.PassPasswordHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PassPasswordHistoryEntity, Integer> {
    List<PassPasswordHistoryEntity> getAllByUserIdAndStatusOrderByCreatedDateDesc(int userid,
        String status);
}
