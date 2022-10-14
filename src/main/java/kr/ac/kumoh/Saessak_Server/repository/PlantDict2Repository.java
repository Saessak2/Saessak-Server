package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.PlantDict2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantDict2Repository extends JpaRepository<PlantDict2, Long>{

    @Query(value = "SELECT pd2 FROM PlantDict2 pd2 WHERE pd2.id = :id")
    Optional<PlantDict2> findByIdFrom2(@Param("id") Long plantId);

}
