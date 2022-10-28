package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantDto;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
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

    public Optional<Long> createMyPlant(MyPlantDto myPlantDto){
        Long ret = null;
        try {
            ret = repository.save(new MyPlant(myPlantDto)).getId();
        } catch(Exception ignored){ }
        return Optional.ofNullable(ret);
    }

    public List<MyPlantDto> readMyPlantList(Long userId){
        return convContentType(repository.findByUserId(userId));
    }

    public Optional<MyPlantDto> readMyFirstPlant(Long userId){
        List<MyPlantDto> myPlantDtoList = readMyPlantList(userId);
        return myPlantDtoList.stream()
                .filter(dto -> !dto.getIsDisable())
                .findAny();
    }

    public Optional<MyPlantDto> readMyPlant(Long plantId){
        MyPlantDto ret = null;
        Optional<MyPlant> data = repository.findById(plantId);
        if(data.isPresent())
            ret = data.get().toDto();
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateMyPlant(Long id, MyPlantDto myPlantDto){
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()) {
            MyPlant myPlant = data.get();
            myPlant.update(myPlantDto);
            ret = repository.save(myPlant).getId();
        }
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateLatestWaterDate(Long id){
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()){
            MyPlant myPlant = data.get();
            myPlant.updateLatestWaterDateOnly();
            ret = repository.save(myPlant).getId();
        }
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateAbility(Long id){
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()){
            MyPlant myPlant = data.get();
            myPlant.updateAbilityOnly();
            ret = repository.save(myPlant).getId();
        }
        return Optional.ofNullable(ret);
    }

    public void deleteMyPlant(Long plantId){
        repository.deleteById(plantId);
    }

    private List<MyPlantDto> convContentType(List<MyPlant> inList){
        List<MyPlantDto> retList = new ArrayList<>();
        for (MyPlant myPlant : inList) {
            retList.add(myPlant.toDto());
        }
        return retList;
    }

}
