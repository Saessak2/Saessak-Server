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
    public void save(){
        //given
        MyPlant myPlant = new MyPlant(3L, "labyrinth", "testSpe", 1, 2, 3,
        LocalDate.of(2022,10,9), 7, "no-img", false);

        //when
        repository.persist(myPlant);
    }

    @Test
    public void find_id(){
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
    public void find_uid(){
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
        MyPlant myPlant = new MyPlant();
        myPlant.setNickname("Queen");

        //when
        repository.merge(myPlant, false);
    }

    @Test
    public void delete(){
        //given
        Long pid = 3L;

        //when
        repository.delete(pid);
    }

}
