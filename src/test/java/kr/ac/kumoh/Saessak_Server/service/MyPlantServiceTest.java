//package kr.ac.kumoh.Saessak_Server.service;
//
//import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
//import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.time.LocalDate;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class MyPlantServiceTest {
//
//    MyPlantRepository repository = new MyPlantRepository();
//    MyPlantService myPlantService = new MyPlantService(repository);
//
//    @Test
//    void 생성(){
//        //given
//        MyPlant myPlant = new MyPlant(3L, "labyrinth", "testSpe", 1, 2, 3,
//                LocalDate.of(2022,10,9), 7, "no-img", false);
//
//        //when
//        myPlantService.create(myPlant);
//
//        //then
//
//    }
//
//}
