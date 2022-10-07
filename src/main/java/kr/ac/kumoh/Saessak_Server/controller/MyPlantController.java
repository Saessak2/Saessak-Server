package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.service.MyPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MyPlantController {

    private final MyPlantService myPlantService;

    @Autowired
    public MyPlantController(MyPlantService myPlantService){
        this.myPlantService = myPlantService;
    }

    @PostMapping
    public void createMyPlant(MyPlant plant){
        myPlantService.create(plant);
    }

    public void updateMyPlant(MyPlant plant){
        myPlantService.update(plant);
    }

    public void deleteMyPlant(Long plantId){
        myPlantService.delete(plantId);
    }

    public void updatePlantOrder(MyPlant plant){
        myPlantService.update(plant);
    }

    public List<MyPlant> readMyPlantList(Long userId){
        myPlantService.getMyPlantList(userId);
        return null;
    }

    public Optional<MyPlant> readMyPlantOne(Long plantId){
        return myPlantService.read(plantId);
    }

    public Optional<MyPlant> readMyPlantDetail(Long plantId){
        return myPlantService.read(plantId);
    }

}
