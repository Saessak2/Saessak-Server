package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantDto;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Optional<MyPlantDto> readMyPlant(Long plantId){
        return Optional.of(new MyPlantDto(repository.findById(plantId).get()));
    }

//    public Optional<Long> updateMyPlant(MyPlantDto myPlantDto){
//        Long ret = null;
//        try{
//            Optional<MyPlant> attribute = repository.findById(myPlantDto.getId());
//            if(attribute.isPresent()){
//                attribute.get().setId(null);
//                ret = repository.save(attribute.get()).getId();
//            }
//        } catch(Exception ignored){ }
//        return Optional.ofNullable(ret);
//    }

    @Transactional
    public Optional<Long> updateMyPlant(MyPlantDto myPlantDto){
        return repository.findById(myPlantDto.getId()).map(target -> {
            if(myPlantDto.getNickname() != null)
                target.setNickname(myPlantDto.getNickname());
            if(myPlantDto.getSpecies() != null)
                target.setSpecies(myPlantDto.getSpecies());
            if(myPlantDto.getSunCondition() != 0)
                target.setSunCondition(myPlantDto.getSunCondition());
            if(myPlantDto.getWindCondition() != 0)
                target.setWindCondition(myPlantDto.getWindCondition());
            if(myPlantDto.getWaterCondition() != 0)
                target.setWaterCondition(myPlantDto.getWaterCondition());
            if(myPlantDto.getWaterCycle() != 0)
                target.setWaterCycle(myPlantDto.getWaterCycle());
            if(myPlantDto.getImgUrl() != null)
                target.setImgUrl(myPlantDto.getImgUrl());
            if(myPlantDto.isDisable() != target.isDisable())
                target.setDisable(myPlantDto.isDisable());// error - always false
            if(myPlantDto.getTempDate() != null)
                target.setLatestWaterDateWithString(myPlantDto.getTempDate());
            return target.getId();
        });
    }

    public void deleteMyPlant(Long plantId){
        repository.deleteById(plantId);
    }

    private List<MyPlantDto> convContentType(List<MyPlant> inList){
        List<MyPlantDto> retList = new ArrayList<>();
        for (MyPlant myPlant : inList) {
            retList.add(new MyPlantDto(myPlant));
        }
        return retList;
    }

}
