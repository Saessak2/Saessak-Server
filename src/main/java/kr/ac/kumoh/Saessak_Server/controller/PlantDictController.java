package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict1Dto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict2Dto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict3Dto;
import kr.ac.kumoh.Saessak_Server.service.PlantDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/plant-dict")
@RequiredArgsConstructor
public class PlantDictController {

    private final PlantDictService service;

    @GetMapping("/1/{id}")
    public ResponseEntity<PlantDict1Dto> readPlantDict1(@PathVariable("id") Long id){
        Optional<PlantDict1Dto> ret = service.readOneFrom1(id);
        return ret.map(plantDict1Dto
                -> ResponseEntity.ok().body(plantDict1Dto)).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/2/{id}")
    public ResponseEntity<PlantDict2Dto> readPlantDict2(@PathVariable("id") Long id){
        Optional<PlantDict2Dto> ret = service.readOneFrom2(id);
        return ret.map(plantDict2Dto
                -> ResponseEntity.ok().body(plantDict2Dto)).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/3/{id}")
    public ResponseEntity<PlantDict3Dto> readPlantDict3(@PathVariable("id") Long id){
        Optional<PlantDict3Dto> ret = service.readOneFrom3(id);
        return ret.map(plantDict3Dto
                -> ResponseEntity.ok().body(plantDict3Dto)).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
