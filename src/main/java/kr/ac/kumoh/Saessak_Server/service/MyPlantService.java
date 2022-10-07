package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.repository.MemoryMyPlantRepository;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MyPlantService {

    private final MemoryMyPlantRepository repository;

    @Autowired
    public MyPlantService(MemoryMyPlantRepository repository){
        this.repository = repository;
    }

    @Transactional
    public void create(MyPlant myPlant){
        repository.save(myPlant);
    }

    @Transactional(readOnly = true)
    public Optional<MyPlant> read(Long plantId){
        return repository.findById(plantId);
    }

    @Transactional
    public void update(MyPlant myPlant){
        repository.save(myPlant);
    }

    @Transactional
    public void delete(Long plantId){
        repository.deleteById(plantId);
    }

    @Transactional(readOnly = true)
    public List<MyPlant> getMyPlantList(Long user_id){
        return repository.findByuser_id(user_id);
    }

}
