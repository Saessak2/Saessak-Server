package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyPlantRepository extends JpaRepository<MyPlant, Long> {

    //extension
    //save() for create(plant), update(plant), update~Order(plant)
    //deleteById() for delete(plantId)
    //findById() for findById(plantId): MyPlant

    //custom method
    List<MyPlant> findByuser_id(Long user_id);  //readMyPlantList

}
