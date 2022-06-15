package com.esign.service.configuration.repository.address;

import com.esign.service.configuration.entity.address.CityEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository
    extends JpaRepository<CityEntity, Integer>,
        JpaSpecificationExecutor<CityEntity> {

  List<CityEntity> findAllByStatus(String status);
  List<CityEntity> findAllByCountrySubDivisionIdAndCityCodeAndStatus(Integer id, String code, String status);

  /*@Query(
      value =
          "SELECT MIN(spld.job_start) jobStart, MAX(spld.job_finish) as jobFinish, COUNT(spld.sign_process_log_detail_id) as total, \n"
              + "COUNT(CASE WHEN spld.job_status = 'S' then 1 ELSE NULL END) as signSuccess, \n"
              + "COUNT(CASE WHEN spld.job_status = 'F' then 1 ELSE NULL END) as signFail \n"
              + "FROM sign_process_log_detail spld \n"
              + "WHERE spld.sign_process_log_id = :logId",
      nativeQuery = true)
  List<Object[]> findDetailForSummary(@Param("logId") int logId);


  @Query(value ="SELECT * FROM sign_process_log_detail WHERE status = 'A' AND create_by = 'ESTMP-BATCH' "
      + "AND create_dt >= CURRENT_DATE - interval '1 hour' "
      + "AND remark LIKE :ref",nativeQuery = true)
  List<SignProcessLogDetailEntity> findLogByRef(@Param("ref") String ref);*/
}
