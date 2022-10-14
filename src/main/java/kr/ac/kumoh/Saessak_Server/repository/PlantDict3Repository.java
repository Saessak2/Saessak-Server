package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.PlantDict3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantDict3Repository extends JpaRepository<PlantDict3, Long>{

    @Query(value = "SELECT pd3 FROM PlantDict3 pd3 WHERE pd3.id = :id")
    Optional<PlantDict3> findByIdFrom3(@Param("id") Long plantId);
}
