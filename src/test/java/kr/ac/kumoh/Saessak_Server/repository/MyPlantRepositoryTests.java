package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MyPlantRepositoryTests {

    MemoryMyPlantRepository repository = new MemoryMyPlantRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        //given
        MyPlant myPlant = new MyPlant();
        myPlant.setPlantNickname("labyrinth");

        //when
        repository.save(myPlant);

        MyPlant result = repository.findById(myPlant.getId()).get();
        Assertions.assertEquals(myPlant, result);
    }


}
