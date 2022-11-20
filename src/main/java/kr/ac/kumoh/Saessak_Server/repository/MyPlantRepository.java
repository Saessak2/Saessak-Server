package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface MyPlantRepository extends JpaRepository<MyPlant, Long> {

    @Query(value = "SELECT p FROM MyPlant p WHERE p.user.id = :userId")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    List<MyPlant> findByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT p FROM MyPlant p WHERE p.user.id = :userId " +
            "AND p.isActive = :isActive ORDER BY p.id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    List<MyPlant> findMyPlantByUserIdAndActive(
            @Param("userId") Long userId, @Param("isActive") boolean isActive);

}
