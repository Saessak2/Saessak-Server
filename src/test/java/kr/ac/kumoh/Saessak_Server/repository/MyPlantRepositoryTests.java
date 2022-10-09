package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.ServerConfig;
import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MyPlantRepositoryTests {

    @Autowired
    private DataSource dataSource = ServerConfig.dataSource();
    MyPlantRepository repository = new MyPlantRepository(dataSource);

    @Test
    public void 생성(){
        //given
        MyPlant myPlant = new MyPlant(3L, "labyrinth", "testSpe", 1, 2, 3,
        LocalDate.of(2022,10,9), 7, "no-img", false);

        //when
        repository.persist(myPlant);

        //then
    }

    @Test
    public void 조회1(){
        //given
        Long pid = 2L;

        //when
        Optional<MyPlant> tmpPlant = repository.findById(pid);

        //then
        if(tmpPlant.isPresent()){
            System.out.println(tmpPlant);
        }

    }

    @Test
    public void 조회2(){
        //given
        Long uid = 3L;

        //when
        List<MyPlant> tmpPlant = repository.findByUserId(uid);

        //then
        if(!tmpPlant.isEmpty()){
            System.out.println(tmpPlant);
        }

    }


    @Test
    public void update(){
        //given
        MyPlant prevPlant = new MyPlant();
        prevPlant.setNickname("labyrinth");

        MyPlant nextPlant = new MyPlant();
        nextPlant.setNickname("Queen");

        //when
        repository.merge(prevPlant, nextPlant);

        //then
    }

    @Test
    public void delete(){  //delete 미구현
        //given

        //when

        //then
    }

}
