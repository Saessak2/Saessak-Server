package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyPlantRepository extends JpaRepository<MyPlant, Long> {

    @Query(value = "SELECT p FROM MyPlant p WHERE p.user.id = :userId " +
            "ORDER BY p.listOrder")
    List<MyPlant> findByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT p FROM MyPlant p WHERE p.user.id = :userId " +
            "AND p.isActive = :isActive ORDER BY p.listOrder")
    List<MyPlant> findMyPlantByUserIdAndActive(
            @Param("userId") Long userId, @Param("isActive") boolean isActive);

    @Query(value = "SELECT MAX(p.id) FROM MyPlant p")
    Long findLastId();

    @Query(value = "SELECT p FROM MyPlant p WHERE p.id = :id")
    MyPlant findOne(@Param("id") Long id);

}
