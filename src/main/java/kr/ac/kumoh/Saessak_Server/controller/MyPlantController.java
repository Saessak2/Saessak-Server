package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.ServerConfig;
import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantDto;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import kr.ac.kumoh.Saessak_Server.service.MyPlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/my-plant")
public class MyPlantController {

    private final MyPlantService service = new MyPlantService(
            new MyPlantRepository(ServerConfig.dataSource()));

    @PostMapping()
    public ResponseEntity<Long> createMyPlant(@RequestBody MyPlantDto myPlantDto){
        Long pId = service.createMyPlant(myPlantDto);
        return ResponseEntity.ok(pId);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<List<MyPlantDto>> readMyPlantList(@PathVariable("user-id") Long userId){
        List<MyPlantDto> ret = service.readMyPlantList(userId);
        if(!ret.isEmpty()){
            return ResponseEntity.ok().body(ret);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{user-id}/{plant-id}")
    public ResponseEntity<MyPlantDto> readMyPlantOne(
            @PathVariable("user-id") Long userId, @PathVariable("plant-id") Long plantId){
        Optional<MyPlantDto> ret = service.readMyPlant(plantId);
        return ret.map(myPlant
                -> ResponseEntity.ok().body(myPlant)).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/updateDetail")
    public ResponseEntity<MyPlant> updateMyPlantDetails(@RequestBody MyPlant myPlant){
        myPlant.setLatestWaterDate();
        int ret = service.updateDetails(myPlant);
        if(ret == 1){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/updateDisable")
    public ResponseEntity<MyPlant> updateMyPlantDisabled(@RequestBody MyPlant myPlant){
        int ret = service.updateDisable(myPlant);
        if(ret == 1){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }  //need test //need fix -> param: Long plantId

    //need watering update method

    @DeleteMapping("/{plantId}")
    public ResponseEntity<MyPlant> deleteMyPlant(@PathVariable Long plantId){
        int ret = service.delete(plantId);
        if(ret == 1){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
