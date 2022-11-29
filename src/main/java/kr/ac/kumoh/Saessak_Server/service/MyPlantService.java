package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.Utility;
import kr.ac.kumoh.Saessak_Server.controller.WeatherController;
import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantReqDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantResDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.WeatherDTO;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            planService.createPlansForNewPlant(myPlant);
        } catch(Exception ignored){ }
        return Optional.ofNullable(ret);
    }

    public List<MyPlantResDto> readMyPlantList(
            WeatherController weatherController, Long userId){
        return convContentType(
                weatherController, repository.findByUserId(userId));
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

    public Optional<MyPlantResDto> readMyPlant(
            WeatherController weatherController, Long id, Long userId){
        MyPlantResDto ret;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()){
            ret = data.get().toDto();
            setWeatherRecommendation(weatherController, ret);
        }
        else
            return readMyFirstPlant(weatherController, userId);
        return Optional.of(ret);
    }

    public Optional<Long> updateMyPlant(
            Long id, MyPlantReqDto myPlantReqDto, PlanService planService){
        String[] attrArr = new String[4];
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()) {
            MyPlant myPlant = data.get();
            attrArr[0] = String.valueOf(myPlant.getWaterCycle());
            attrArr[1] = myPlant.getLatestWaterDate().toString();

            myPlant.update(myPlantReqDto);
            MyPlant changedMyPlant = repository.save(myPlant);
            attrArr[2] = String.valueOf(myPlant.getWaterCycle());
            attrArr[3] = changedMyPlant.getLatestWaterDate().toString();

            ret = changedMyPlant.getId();
            checkUpdatedAttrs(myPlant.getId(), planService, attrArr);
        }
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateLatestWaterDate(Long id){
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()){
            MyPlant myPlant = data.get();
            myPlant.setLatestWaterDate(LocalDate.now());
            ret = repository.save(myPlant).getId();
        }
        return Optional.ofNullable(ret);
    }

    public Optional<Long> updateActivation(Long id){
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()){
            MyPlant myPlant = data.get();
            myPlant.setActive(!myPlant.isActive());
            ret = repository.save(myPlant).getId();
        }
        return Optional.ofNullable(ret);
    }

    public void deleteMyPlant(Long id){
        repository.deleteById(id);
    }

    private List<MyPlantResDto> convContentType(
            WeatherController weatherController, List<MyPlant> inList){
        List<MyPlantResDto> retList = new ArrayList<>();
        for (MyPlant myPlant : inList) {
//            MyPlantResDto tmpResDto = myPlant.toDto();
//            setWeatherRecommendation(weatherController, tmpResDto);
//            retList.add(tmpResDto);
            retList.add(myPlant.toDto());
        }
        return retList;
    }

    private void setWeatherRecommendation(
            WeatherController weatherController, MyPlantResDto myPlantResDto){
        String city = myPlantResDto.getPlantedRegion();
        WeatherDTO weatherDTO = (WeatherDTO) weatherController.readWeather(city).getBody();
        myPlantResDto.setIconAndRecStr(
                Objects.requireNonNull(weatherDTO).getIcon(),
                Objects.requireNonNull(weatherDTO).getComments());
    }

    private void checkUpdatedAttrs(
            Long plantId, PlanService planService, String[] updatedAttrs){
        if(!updatedAttrs[0].equals(updatedAttrs[2])
                || !updatedAttrs[1].equals(updatedAttrs[3]))
            planService.updateDateOfPlans(
                    plantId, Integer.parseInt(updatedAttrs[2]),
                    Utility.getLocalDateFromStr(updatedAttrs[3]));
    }

}
