package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantDto;
import kr.ac.kumoh.Saessak_Server.service.MyPlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/my-plant")
@RequiredArgsConstructor
public class MyPlantController {

    private final MyPlantService service;

    @PostMapping()
    public ResponseEntity<Long> createMyPlant(@RequestBody MyPlantDto myPlantDto){
        Optional<Long> ret = service.createMyPlant(myPlantDto);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
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
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping()
    public ResponseEntity<Long> updateMyPlant(@RequestBody MyPlantDto myPlantDto){
        Optional<Long> ret = service.updateMyPlant(myPlantDto);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

//    @PutMapping("/updateDetail")
//    public ResponseEntity<MyPlant> updateMyPlantDetails(@RequestBody MyPlant myPlant){
//        myPlant.setLatestWaterDate();
//        int ret = service.updateDetails(myPlant);
//        if(ret == 1){
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
//
//    @PutMapping("/updateDisable")
//    public ResponseEntity<MyPlant> updateMyPlantDisabled(@RequestBody MyPlant myPlant){
//        int ret = service.updateDisable(myPlant);
//        if(ret == 1){
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }  //need test //need fix -> param: Long plantId
//
//    //need watering update method

    @DeleteMapping("/{plantId}")
    public ResponseEntity<MyPlant> deleteMyPlant(@PathVariable Long plantId){
        service.deleteMyPlant(plantId);
        return ResponseEntity.noContent().build();
    }

}
