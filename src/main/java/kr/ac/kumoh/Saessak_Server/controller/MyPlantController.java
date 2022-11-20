package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantReqDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantResDto;
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

    //TODO: 물 주기 계획 자동 생성
    @PostMapping()
    public ResponseEntity<Long> createMyPlant(@RequestBody MyPlantReqDto myPlantReqDto){
        Optional<Long> ret = service.createMyPlant(myPlantReqDto);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<List<MyPlantResDto>> readMyPlantList(
            @PathVariable("user-id") Long userId){
        List<MyPlantResDto> ret = service.readMyPlantList(userId);
        if(!ret.isEmpty())
            return ResponseEntity.ok(ret);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{user-id}/{plant-id}")
    public ResponseEntity<MyPlantResDto> readMyPlantOne(
            @PathVariable("user-id") Long userId, @PathVariable("plant-id") Long plantId){
        Optional<MyPlantResDto> ret;
        if(plantId == 0L)
            ret = service.readMyFirstPlant(userId);
        else
            ret = service.readMyPlant(plantId, userId);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //TODO: 물 주기 계획 자동 변경
    @PutMapping("/{plant-id}/all")
    public ResponseEntity<Long> updateMyPlant(
            @PathVariable("plant-id") Long id,
            @RequestBody MyPlantReqDto myPlantReqDto){
        Optional<Long> ret = service.updateMyPlant(id, myPlantReqDto);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/{plant-id}/date")
    public ResponseEntity<Long> updateMyPlantLWD(
            @PathVariable("plant-id") Long id){
        Optional<Long> ret = service.updateLatestWaterDate(id);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/{plant-id}/active")
    public ResponseEntity<Long> updateMyPlantActive(
            @PathVariable("plant-id") Long id){
        Optional<Long> ret = service.updateActivation(id);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/{plant-id}")
    public ResponseEntity<MyPlantResDto> deleteMyPlant(@PathVariable("plant-id") Long plantId){
        service.deleteMyPlant(plantId);
        return ResponseEntity.noContent().build();
    }

}
