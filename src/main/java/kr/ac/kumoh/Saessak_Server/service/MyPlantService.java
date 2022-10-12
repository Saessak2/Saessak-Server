package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantDto;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyPlantService {

    private final MyPlantRepository repository;

    public MyPlantService(MyPlantRepository repository){
        this.repository = repository;
    }

    public Long createMyPlant(MyPlantDto myPlantDto){
        MyPlant myPlant = new MyPlant(myPlantDto);
        return repository.persist(myPlant);
    }

    public List<MyPlantDto> readMyPlantList(Long userId){
        List<MyPlantDto> retList = new ArrayList<>();
        List<MyPlant> tmpList = repository.findByUserId(userId);
        for(int i = 0; i < tmpList.size(); i++){
//            retList.add(new ModelMapper().map(new MyPlantDto(), MyPlantDto.class));
            retList.add(new MyPlantDto(tmpList.get(i)));
        }
        return retList;
    }

    public Optional<MyPlantDto> readMyPlant(Long plantId){
        return Optional.of(new MyPlantDto(repository.findById(plantId).get()));
//        return Optional.of(repository.findById(plantId).get());
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
