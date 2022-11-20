package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query(value = "SELECT p FROM Plan p WHERE p.myPlant.id = (:myPlant_id)" +
            "AND YEAR(p.date) = :in_year AND MONTH(p.date)= :in_month")
    List<Plan> findPlansByMyPlantAndMonth(
            @Param("myPlant_id") Long myPlantId, @Param("in_year") int year,
            @Param("in_month") int month);

    @Query(value = "SELECT p FROM Plan p WHERE p.user.id = (:user_id)" +
            "AND YEAR(p.date) = :in_year AND MONTH(p.date)= :in_month")
    List<Plan> findPlansByUserAndMonth(
            @Param("user_id") Long userId, @Param("in_year") int year,
            @Param("in_month") int month);

    @Query("SELECT p FROM Plan p WHERE p.user.id = :user_id AND p.date = :in_date")
    List<Plan> findPlansByUserAndDay(@Param("user_id") Long userId,
            @Param("in_date") LocalDate inDate);

    @Query(value = "SELECT p FROM Plan p WHERE p.myPlant.id = :plantId AND p.date > :in_date AND p.planType = :planType")
    List<Plan> findPlansAfterInDate(
            @Param("plantId") Long plantId, @Param("in_date") LocalDate date, @Param("planType") String planType);

    @Query(value = "SELECT p FROM Plan p WHERE p.myPlant.id = :plantId AND p.date = :inDate")
    Plan findPlanByMyPlantAndDate(@Param("plantId") Long plantId, @Param("inDate") LocalDate inDate);

}
