package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict1Dto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict2Dto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict3Dto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDictDto;
import kr.ac.kumoh.Saessak_Server.service.PlantDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plant-dict")
@RequiredArgsConstructor
public class PlantDictController {

    private final PlantDictService service;

    @GetMapping
    public ResponseEntity<List<PlantDictDto>> readPlantDictList(){
        List<PlantDictDto> ret = service.readAll();
        return  ResponseEntity.ok(ret);
    }

    @GetMapping("/1/{id}")
    public ResponseEntity<PlantDict1Dto> readPlantDict1(@PathVariable("id") Long id){
        Optional<PlantDict1Dto> ret = service.readOneFromDict1(id);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/2/{id}")
    public ResponseEntity<PlantDict2Dto> readPlantDict2(@PathVariable("id") Long id){
        Optional<PlantDict2Dto> ret = service.readOneFromDict2(id);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/3/{id}")
    public ResponseEntity<PlantDict3Dto> readPlantDict3(@PathVariable("id") Long id){
        Optional<PlantDict3Dto> ret = service.readOneFromDict3(id);
        return ret.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
