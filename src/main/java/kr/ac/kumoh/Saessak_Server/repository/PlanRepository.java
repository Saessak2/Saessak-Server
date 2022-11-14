package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query(value = "SELECT p FROM Plan p WHERE p.myPlant.id = (:my_plant_id)" +
            "AND YEAR(p.date) = :in_year AND MONTH(p.date)= :in_month")
    List<Plan> findPlansByMyPlantAndMonth(
            @Param("id") Long myPlantId, @Param("in_year") int year, @Param("in_month") int month);

    @Query(value = "SELECT p FROM Plan p WHERE p.user.id = (:user_id)" +
            "AND YEAR(p.date) = :in_year AND MONTH(p.date)= :in_month")
    List<Plan> findPlansByUserAndMonth(
            @Param("user_id") Long userId, @Param("in_year") int year, @Param("in_month") int month);

    @Query("SELECT p FROM Plan p WHERE p.date = :in_date AND p.user.id = :user_id")
    List<Plan> findPlansByUserAndDay(@Param("in_date") String inDate, @Param("user_id") Long userId);

}
