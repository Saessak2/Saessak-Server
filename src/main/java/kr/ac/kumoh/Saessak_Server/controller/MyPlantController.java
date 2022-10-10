package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.ServerConfig;
import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import kr.ac.kumoh.Saessak_Server.service.MyPlantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MyPlantController {

    private final MyPlantService service = new MyPlantService(
            new MyPlantRepository(ServerConfig.dataSource()));

//    @PostMapping("myPlant/?user_id={user_id}&nickname={nickname}&" +
//            "species={species}&sunCondition={sunCondition}&windCondition=" +
//            "{windCondition}&waterCondition={waterCondition}&tempDate=" +
//            "{tempDate}&waterCycle={waterCycle}&imgUrl={imgUrl}&isDisable={isDisable}")
//    public void createMyPlant(@PathVariable("user_id") Long user_id,
//                              @PathVariable("nickname") String nickname,
//                              @PathVariable("species") String species,
//                              @PathVariable("sunCondition") int sunCondition,
//                              @PathVariable("windCondition") int windCondition,
//                              @PathVariable("waterCondition") int waterCondition,
//                              @PathVariable("tempDate")Date tmpDate,
//                              @PathVariable("waterCycle") int waterCycle,
//                              @PathVariable("imgUrl") String imgUrl,
//                              @PathVariable("isDisable") boolean isDisable){
//
//        String[] tmpArr = tmpDate.toString().split("-");
//        LocalDate latestWaterDate = LocalDate.of(Integer.parseInt(tmpArr[0]),
//                Integer.parseInt(tmpArr[1]),
//                Integer.parseInt(tmpArr[2]));
//        MyPlant myPlant = new MyPlant(user_id, nickname, species, sunCondition, windCondition,
//                                      waterCondition, latestWaterDate, waterCycle, imgUrl, isDisable);
//        service.createMyPlant(myPlant);
//    }

    @PostMapping("myPlant/create")
    public void createMyPlant(@RequestBody MyPlant myPlant){
        service.createMyPlant(myPlant);
    }  //need test

    @GetMapping("myPlant/readList/{userId}")
    public List<MyPlant> readMyPlantList(@PathVariable("userId") Long userId){
        return service.readMyPlantList(userId);
    }

    @GetMapping("myPlant/readDetail/{plantId}")
    public MyPlant readMyPlantOne(@PathVariable("plantId") Long plantId){
        Optional<MyPlant> ret = service.readMyPlant(plantId);
        return ret.get();  //need Optional Error catch
    }

    @PutMapping("myPlant/updateDetail")
    public void updateMyPlantDetails(@RequestBody MyPlant myPlant){
        service.updateDetails(myPlant);
    }  //need test

    @PutMapping("myPlant/updateDisable")
    public void updateMyPlantDisabled(@RequestBody MyPlant myPlant){
        service.updateDisable(myPlant);
    }  //need test

    @DeleteMapping("myPlant/delete/{plantId}")
    public void deleteMyPlant(@PathVariable("plantId") Long id){
        service.delete(id);
    }

}
