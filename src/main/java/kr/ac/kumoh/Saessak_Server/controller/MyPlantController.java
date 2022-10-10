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

    @PostMapping("myPlant/create/{myPlant}")
    public void createMyPlant(@PathVariable("myPlant") MyPlant myPlant){
//        myPlant = new MyPlant(3L, "nn", "sp", 1, 2, 3, LocalDate.of(2022, 10, 10), 7,
//                "no", false);
        service.createMyPlant(myPlant);
    }

    @GetMapping("myPlant/read1/{userId}")
    public List<MyPlant> readMyPlantList(@PathVariable("userId") Long userId){
        return service.readMyPlantList(userId);
    }

    @GetMapping("myPlant/read2/{plantId}")
    public MyPlant readMyPlantOne(@PathVariable("plantId") Long plantId){
        Optional<MyPlant> ret = service.readMyPlant(plantId);
        return ret.get();
    }

    @PutMapping("myPlant/update1/{myPlant}")
    public void updateMyPlantDetails(@PathVariable("myPlant") MyPlant myPlant){
        service.updateDetails(myPlant);
    }

    @PutMapping("myPlant/update2/{myPlant}")
    public void updateMyPlantDisabled(@PathVariable("myPlant") MyPlant myPlant){
        service.updateDisable(myPlant);
    }

    @DeleteMapping("myPlant/delete/{id}")
    public void deleteMyPlant(@PathVariable("id") Long id){
        service.delete(id);
    }

}
