package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMyPlantRepository {

    Long persist(MyPlant myPlant);  //createMyPlant
    int merge(MyPlant myPlant, boolean forDisable);  // updateMyPlant
    int delete(Long myPlantId);  //deleteMyPlant
    List<MyPlant> findByUserId(Long userId);  //readMyPlantList
    Optional<MyPlant> findById(Long plantId);  //readMyPlantOne, readMyPlantDetail

}
