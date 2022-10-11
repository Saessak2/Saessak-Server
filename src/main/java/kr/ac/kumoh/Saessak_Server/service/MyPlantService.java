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

    public Long createMyPlant(MyPlant myPlant){
        return repository.persist(myPlant);
    }

    public List<MyPlant> readMyPlantList(Long userId){
        return repository.findByUserId(userId);
    }

    public Optional<MyPlant> readMyPlant(Long plantId){
        return Optional.of(repository.findById(plantId).get());
    }

    public int updateDisable(MyPlant myPlant){
        return repository.merge(myPlant, true);
    }

    public int updateDetails(MyPlant myPlant){
        return repository.merge(myPlant, false);
    }

    public int delete(Long plantId){
        return repository.delete(plantId);
    }

}
