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

    //TODO: 물 주기 계획 자동 생성
    @PostMapping()
    public ResponseEntity<Long> createMyPlant(@RequestBody MyPlantDto myPlantDto){
        Optional<Long> ret = service.createMyPlant(myPlantDto);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/{user-id}")
    public ResponseEntity<List<MyPlantDto>> readMyPlantList(@PathVariable("user-id") Long userId){
        List<MyPlantDto> ret = service.readMyPlantList(userId);
        if(!ret.isEmpty())
            return ResponseEntity.ok(ret);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{user-id}/{plant-id}")
    public ResponseEntity<MyPlantDto> readMyPlantOne(
            @PathVariable("user-id") Long userId, @PathVariable("plant-id") Long plantId){
        Optional<MyPlantDto> ret;
        if(plantId == -1L)
            ret = service.readMyFirstPlant(userId);
        else
            ret = service.readMyPlant(plantId);

        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //TODO: 물 주기 계획 자동 변경
    @PutMapping("/{plant-id}/{type}")
    public ResponseEntity<Long> updateMyPlant(@PathVariable("plant-id") Long id,
            @PathVariable("type") String updateType, @RequestBody MyPlantDto myPlantDto){
        Optional<Long> ret = Optional.empty();
        switch (updateType){
            case "all":
                ret = service.updateMyPlant(id, myPlantDto);
                break;
            case "date":
                ret = service.updateLatestWaterDate(id);
                break;
            case "ability":
                ret = service.updateAbility(id);
                break;
        }
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @DeleteMapping("/{plant-id}")
    public ResponseEntity<MyPlantDto> deleteMyPlant(@PathVariable("plant-id") Long plantId){
        service.deleteMyPlant(plantId);
        return ResponseEntity.noContent().build();
    }

}
