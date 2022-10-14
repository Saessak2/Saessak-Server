package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.PlantDict1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantDict1Repository extends JpaRepository<PlantDict1, Long>{

    @Query(value = "SELECT pd1 FROM PlantDict1 pd1 WHERE pd1.id = :id")
    Optional<PlantDict1> findByIdFrom1(@Param("id") Long plantId);

}
