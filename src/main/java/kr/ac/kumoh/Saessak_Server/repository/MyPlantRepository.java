package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyPlantRepository extends JpaRepository<MyPlant, Long> {

    @Query(value = "SELECT p FROM MyPlant p WHERE p.user.id = :userId")
    List<MyPlant> findByUserId(@Param("userId") Long userId);

    @Query(value = "UPDATE MyPlant p SET p.imgUrl = :path, " +
            "p.hasUploadedImg = :hasIt WHERE p.user.id = :id")
    Optional<MyPlant> updateImgUrlById(@Param("path") String path,
            @Param("hasIt") boolean hasIt, @Param("id") Long id);

}
