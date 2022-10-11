package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.ServerConfig;
import kr.ac.kumoh.Saessak_Server.controller.data;
import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import kr.ac.kumoh.Saessak_Server.service.MyPlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/myPlant")
public class MyPlantController {

    private final MyPlantService service = new MyPlantService(
            new MyPlantRepository(ServerConfig.dataSource()));

    @PostMapping("/create")
    public ResponseEntity<Long> createMyPlant(@RequestBody MyPlant myPlant){
        Long pId = service.createMyPlant(myPlant);
        return ResponseEntity.ok(pId);
    }

    @GetMapping("/readlist/{userId}")
    public ResponseEntity<List<data>> readMyPlantList(@PathVariable("userId") Long userId){
        List<MyPlant> ret = service.readMyPlantList(userId);
        if(!ret.isEmpty()){
            return ResponseEntity.ok().body(convList(ret));
        }
        //ㄴ ㅔ 그래서 그냥 이왕 할거 좀 제대로 해야지 싶어서 썻죠 지금 화면 파란건
        //이유를 저도 모름 ㅠ

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    /*
    goal - json
    data:
        id:~
        uid:~
        ...
        tempDate:~
    data:
        ...

        swift 쪽에서 잘 받을 수 있게
        저런 형태로 넘겨달라고 하던데
        어떻게 하는지 1도 모르겠어서
        제일 멍청한 접근 1 2 3 다해봄
        List<data> 얘를 근데
        저렇게 하는게 맞나 하는 생각이 드는게요
        아니 맞나요 ?
        저걸 저렇게 하는게 ?
        ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
        넴
        map 해봣고
        array 해봣음
        ㅋㅋㅋㅋ

    */

    @GetMapping("/readDetail/{plantId}")
    public ResponseEntity<MyPlant> readMyPlantOne(@PathVariable("plantId") Long plantId){
        Optional<MyPlant> ret = service.readMyPlant(plantId);
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

    @DeleteMapping("/delete/{plantId}")
    public ResponseEntity<MyPlant> deleteMyPlant(@PathVariable Long plantId){
        int ret = service.delete(plantId);
        if(ret == 1){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private List<data> convList(List<MyPlant> pList){
        List<data> dList = new ArrayList<>();
        for (MyPlant myPlant : pList) {
            dList.add(new data(myPlant));
        }
        return dList;
    }

}
