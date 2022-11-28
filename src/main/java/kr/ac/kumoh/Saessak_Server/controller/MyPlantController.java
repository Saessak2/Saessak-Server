package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantReqDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantResDto;
import kr.ac.kumoh.Saessak_Server.service.MyPlantService;
import kr.ac.kumoh.Saessak_Server.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/my-plant")
@RequiredArgsConstructor
public class MyPlantController {

    private final MyPlantService myPlantService;
    private final PlanService planService;
    private final WeatherController weatherController;

    @PostMapping
    public ResponseEntity<Long> createMyPlant(
            @RequestBody MyPlantReqDto myPlantReqDto){
        Optional<Long> ret = myPlantService.createMyPlant(
                planService, myPlantReqDto);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<List<MyPlantResDto>> readMyPlantList(
            @PathVariable("user-id") Long userId){
        List<MyPlantResDto> ret =
                myPlantService.readMyPlantList(weatherController, userId);
        return ResponseEntity.ok(ret);
    }

    @GetMapping("/{user-id}/{plant-id}")
    public ResponseEntity<MyPlantResDto> readMyPlantOne(
            @PathVariable("user-id") Long userId, @PathVariable("plant-id") Long plantId){
        Optional<MyPlantResDto> ret;
        if(plantId == 0L)
            ret = myPlantService.readMyFirstPlant(weatherController, userId);
        else
            ret = myPlantService.readMyPlant(weatherController, plantId, userId);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{plant-id}/all")
    public ResponseEntity<Long> updateMyPlant(
            @PathVariable("plant-id") Long id,
            @RequestBody MyPlantReqDto myPlantReqDto){
        Optional<Long> ret = myPlantService.updateMyPlant(id, myPlantReqDto, planService);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/{plant-id}/date")
    public ResponseEntity<Long> updateMyPlantLWD(
            @PathVariable("plant-id") Long id){
        Optional<Long> ret = myPlantService.updateLatestWaterDate(id);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/{plant-id}/active")
    public ResponseEntity<Long> updateMyPlantActive(
            @PathVariable("plant-id") Long id){
        Optional<Long> ret = myPlantService.updateActivation(id);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/{plant-id}")
    public ResponseEntity<MyPlantResDto> deleteMyPlant(@PathVariable("plant-id") Long plantId){
        myPlantService.deleteMyPlant(plantId);
        return ResponseEntity.noContent().build();
    }

}
