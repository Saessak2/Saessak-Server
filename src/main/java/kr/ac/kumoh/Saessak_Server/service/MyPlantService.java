package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyPlantService {

    private final MyPlantRepository repository;

    public MyPlantService(MyPlantRepository repository){
        this.repository = repository;
    }

    public void createMyPlant(MyPlant myPlant){
        repository.persist(myPlant);
    }

    public List<MyPlant> readMyPlantList(Long userId){
        return repository.findByUserId(userId);
    }

    public Optional<MyPlant> readMyPlant(Long plantId){
        return repository.findById(plantId);
    }

    public void updateDisable(MyPlant myPlant){
        repository.merge(myPlant, true);
    }

    public void updateDetails(MyPlant myPlant){
        repository.merge(myPlant, false);
    }

    public void delete(Long plantId){
        repository.delete(plantId);
    }

}
