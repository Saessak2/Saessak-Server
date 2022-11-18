package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantReqDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantResDto;
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

    public Optional<Long> createMyPlant(MyPlantReqDto myPlantReqDto){
        Long ret = null;
        try {
            ret = repository.save(new MyPlant(myPlantReqDto)).getId();
        } catch(Exception ignored){ }
        return Optional.ofNullable(ret);
    }

    public List<MyPlantResDto> readMyPlantList(Long userId){
        return convContentType(repository.findByUserId(userId));
    }

    public Optional<MyPlantResDto> readMyFirstPlant(Long userId){
        MyPlantResDto ret = null;
        List<MyPlant> data = repository.findMyPlantByUserIdAndActive(userId, true);
        if(!data.isEmpty())
            ret = data.get(0).toDto();
        return Optional.ofNullable(ret);
    }

    public Optional<MyPlantResDto> readMyPlant(Long id){
        MyPlantResDto ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent())
            ret = data.get().toDto();
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateMyPlant(Long id, MyPlantReqDto myPlantReqDto){
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()) {
            MyPlant myPlant = data.get();
            myPlant.update(myPlantReqDto);
            ret = repository.save(myPlant).getId();
        }
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateLatestWaterDate(Long id){
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()){
            MyPlant myPlant = data.get();
            myPlant.updateLatestWaterDate();
            ret = repository.save(myPlant).getId();
        }
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateActivation(Long id){
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()){
            MyPlant myPlant = data.get();
            myPlant.updateIsActive();
            ret = repository.save(myPlant).getId();
        }
        return Optional.ofNullable(ret);
    }

    public void deleteMyPlant(Long id){
        repository.deleteById(id);
    }

    private List<MyPlantResDto> convContentType(List<MyPlant> inList){
        List<MyPlantResDto> retList = new ArrayList<>();
        for (MyPlant myPlant : inList)
            retList.add(myPlant.toDto());

        return retList;
    }

    private void setWeatherRecommendation(String icon, String stmt){
        //TODO:Using WeatherController(Service), fill icon and stmt
        //need to set default data(ex. sunny, stmt1)
    }

}
