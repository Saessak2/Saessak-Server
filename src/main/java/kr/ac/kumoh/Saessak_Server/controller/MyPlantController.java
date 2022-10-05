package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public class MyPlantController {

    @PostMapping
    public void createMyPlant(MyPlant plant){

    }

    public void updateMyPlant(MyPlant plant){

    }

    public void deleteMyPlant(Long id){

    }

    public void updatePlantOrder(MyPlant plant){

    }

    public List<MyPlant> readMyPlantList(Long userId){

        return null;
    }

    public MyPlant readMyPlantOne(Long plantId){

        return null;
    }

    public MyPlant readMyPlantDetail(Long plantId){

        return null;
    }

}
