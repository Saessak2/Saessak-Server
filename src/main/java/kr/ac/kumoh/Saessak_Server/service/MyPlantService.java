package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.Utility;
import kr.ac.kumoh.Saessak_Server.controller.WeatherController;
import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantReqDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantResDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.WeatherDTO;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            WeatherController weatherController,
            PlanService planService, MyPlantReqDto myPlantReqDto){
        Long ret = null;
        try {
            MyPlant myPlant = new MyPlant(myPlantReqDto);
            setWeatherRecommendation(weatherController, myPlant);
            myPlant.setListOrder(repository.findLastId().intValue() + 1);
            myPlant = repository.save(myPlant);

            ret = myPlant.getId();
            planService.createPlansForNewPlant(myPlant);
        } catch(Exception ignored){ }
        return Optional.ofNullable(ret);
    }

    @Transactional
    public Optional<Long> checkWeather(
            WeatherController weatherController, Long userId){
        Long ret = null;
        List<MyPlant> data = repository.findByUserId(userId);
        MyPlant myPlant;
        if(!data.isEmpty()){
            for (MyPlant datum : data) {
                myPlant = datum;
                setWeatherRecommendation(weatherController, myPlant);
                ret = repository.save(myPlant).getId();
            }
        }
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

    public Optional<MyPlantResDto> readMyPlant(Long id, Long userId){
        MyPlantResDto ret;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent())
            ret = data.get().toDto();
        else
            return readMyFirstPlant(userId);
        return Optional.of(ret);
    }

    public MyPlant findOne(Long id) {
        return repository.findOne(id);
    }

    public Optional<Long> updateMyPlant(
            Long id, MyPlantReqDto myPlantReqDto, PlanService planService){
        String[] attrs = new String[4];
        Long ret = null;
        Optional<MyPlant> data = repository.findById(id);
        if(data.isPresent()) {
            MyPlant myPlant = data.get();
            attrs[0] = String.valueOf(myPlant.getWaterCycle());
            attrs[1] = myPlant.getLatestWaterDate().toString();

            myPlant.update(myPlantReqDto);
            MyPlant changedMyPlant = repository.save(myPlant);
            attrs[2] = String.valueOf(myPlant.getWaterCycle());
            attrs[3] = changedMyPlant.getLatestWaterDate().toString();

            ret = changedMyPlant.getId();
            checkUpdatedAttrs(myPlant.getId(), planService, attrs);
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

    public Optional<Long> updateMyPlantOrder(Long plantId, int listOrder) {
        Long ret = null;
        Optional<MyPlant> data = repository.findById(plantId);
        if(data.isPresent()) {
            MyPlant myPlant = data.get();
            myPlant.setListOrder(listOrder);
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
            WeatherController weatherController, MyPlant myPlant){
        String city = myPlant.getPlantedRegion();
        WeatherDTO weatherDTO = weatherController
                .readWeatherWithSunCond(city, myPlant.getSunCondition());
        myPlant.setIconAndRecStr(
                Objects.requireNonNull(weatherDTO).getIcon(),
                Objects.requireNonNull(weatherDTO).getComments());
    }

    private void checkUpdatedAttrs(
            Long plantId, PlanService planService, String[] attrs){
        if(!attrs[0].equals(attrs[2])
                || !attrs[1].equals(attrs[3]))
            planService.updateDateOfPlans(
                    plantId, Integer.parseInt(attrs[2]),
                    Utility.getLocalDateFromStr(attrs[3]));
    }

}
