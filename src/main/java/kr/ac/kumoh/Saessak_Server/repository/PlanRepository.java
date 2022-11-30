package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query(value = "SELECT p FROM Plan p WHERE p.myPlant.id = :plantId " +
            "AND YEAR(p.date) = :inYear AND MONTH(p.date)= :inMonth")
    List<Plan> findPlansByMyPlantAndMonth(
            @Param("plantId") Long plantId, @Param("inYear") int year,
            @Param("inMonth") int month);

    @Query(value = "SELECT p FROM Plan p WHERE p.user.id = :userId " +
            "AND YEAR(p.date) = :inYear AND MONTH(p.date)= :inMonth")
    List<Plan> findPlansByUserAndMonth(
            @Param("userId") Long userId, @Param("inYear") int year,
            @Param("inMonth") int month);

    @Query("SELECT p FROM Plan p WHERE p.user.id = :userId AND p.date = :inDate")
    List<Plan> findPlansByUserAndDay(@Param("userId") Long userId,
                                     @Param("inDate") LocalDate inDate);

    @Query(value = "SELECT p FROM Plan p WHERE p.myPlant.id = :plantId " +
            "AND p.date > :inDate AND p.planType = :planType")
    List<Plan> findPlansAfterInDate(
            @Param("plantId") Long plantId, @Param("inDate") LocalDate date,
            @Param("planType") String planType);

    @Query(value = "SELECT p FROM Plan p WHERE p.myPlant.id = :plantId " +
            "AND p.date = :inDate AND p.planType = :planType")
    List<Plan> findPlanByDateAndType(@Param("plantId") Long plantId,
            @Param("inDate") LocalDate inDate, @Param("planType") String planType);

    @Query(value = "SELECT p FROM Plan p WHERE p.planType = :planType " +
            "AND p.myPlant.id = :plantId AND p.isDone = :isDone " +
            "ORDER BY p.id DESC")
    Optional<Plan> findOldPlan(@Param("planType") String planType,
            @Param("plantId") Long plantId, @Param("isDone") boolean isDone);

}
