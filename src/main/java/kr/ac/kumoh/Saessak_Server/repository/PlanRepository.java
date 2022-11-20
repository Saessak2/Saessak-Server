package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    Optional<Plan> findById(Long id);

    @Query(value = "SELECT p FROM Plan p WHERE p.myPlant.id = (:myPlant_id)" +
            "AND YEAR(p.date) = :in_year AND MONTH(p.date)= :in_month")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    List<Plan> findPlansByMyPlantAndMonth(
            @Param("myPlant_id") Long myPlantId, @Param("in_year") int year, @Param("in_month") int month);

    @Query(value = "SELECT p FROM Plan p WHERE p.user.id = (:user_id)" +
            "AND YEAR(p.date) = :in_year AND MONTH(p.date)= :in_month")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    List<Plan> findPlansByUserAndMonth(
            @Param("user_id") Long userId, @Param("in_year") int year, @Param("in_month") int month);

    @Query("SELECT p FROM Plan p WHERE p.user.id = :user_id AND p.date = :in_date")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    List<Plan> findPlansByUserAndDay(@Param("user_id") Long userId, @Param("in_date") LocalDate inDate);

}
