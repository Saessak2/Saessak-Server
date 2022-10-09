package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.ServerConfig;
import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.sql.DataSource;
import java.time.LocalDate;

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
        repository.save(myPlant);

        //then
//        MyPlant result = repository.findById(myPlant.getId()).get();
//        Assertions.assertEquals(myPlant, result);
    }


}
