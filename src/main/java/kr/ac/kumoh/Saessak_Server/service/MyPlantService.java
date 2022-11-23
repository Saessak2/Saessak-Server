package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.Utility;
import kr.ac.kumoh.Saessak_Server.controller.WeatherController;
import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantReqDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantResDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.WeatherDTO;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MyPlantService {

    private final MyPlantRepository repository;

    public MyPlantService(MyPlantRepository repository){
        this.repository = repository;
    }

    public Optional<Long> createMyPlant(
            PlanService planService, MyPlantReqDto myPlantReqDto){
        Long ret = null;
        try {
            MyPlant myPlant = repository.save(new MyPlant(myPlantReqDto));
            ret = myPlant.getId();
            planService.createPlans(myPlant);
        } catch(Exception ignored){ }
        return Optional.ofNullable(ret);
    }

    public List<MyPlantResDto> readMyPlantList(Long userId){
        return convContentType(repository.findByUserId(userId));
    }

    public Optional<MyPlantResDto> readMyPlant(
            WeatherController weatherController, Long id, Long userId){
        MyPlantResDto ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent())
            ret = data.get().toDto();
        else {
            List<MyPlant> tempList = repository.findMyPlantByUserIdAndActive(userId, true);
            if(!tempList.isEmpty())
                ret = tempList.get(0).toDto();
        }
        setWeatherRecommendation(weatherController, Objects.requireNonNull(ret));
        return Optional.of(ret);
    }

    public Optional<MyPlantResDto> readMyFirstPlant(
            WeatherController weatherController, Long userId){
        MyPlantResDto ret = null;
        List<MyPlant> data = repository.findMyPlantByUserIdAndActive(userId, true);
        if(!data.isEmpty()){
            ret = data.get(0).toDto();
            setWeatherRecommendation(weatherController, ret);
        }
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateMyPlant(
            Long id, MyPlantReqDto myPlantReqDto, PlanService planService){
        String[] updatedCols = new String[4];
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()) {
            MyPlant myPlant = data.get();
            updatedCols[0] = String.valueOf(myPlant.getWaterCycle());
            updatedCols[1] = myPlant.getLatestWaterDate().toString();

            myPlant.update(myPlantReqDto);
            MyPlant changedMyPlant = repository.save(myPlant);
            updatedCols[2] = String.valueOf(myPlant.getWaterCycle());
            updatedCols[3] = changedMyPlant.getLatestWaterDate().toString();

            ret = changedMyPlant.getId();
            checkUpdateCols(myPlant.getId(), planService, updatedCols);
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

    private void setWeatherRecommendation(
            WeatherController weatherController, MyPlantResDto myPlantResDto){
        String city = myPlantResDto.getPlantedRegion();
        WeatherDTO weatherDTO = (WeatherDTO) weatherController.readWeather(city).getBody();
        myPlantResDto.setWeatherRc(
                Objects.requireNonNull(weatherDTO).getIcon(),
                Objects.requireNonNull(weatherDTO).getComments());
    }

    private void checkUpdateCols(
            Long plantId, PlanService planService, String[] updatedCols){
        if(!updatedCols[0].equals(updatedCols[2])
                || !updatedCols[1].equals(updatedCols[3]))

            planService.updateDateOfPlans(
                    plantId, Integer.parseInt(updatedCols[2]),
                    Utility.getLocalDateFromStr(updatedCols[3]));
    }

}
