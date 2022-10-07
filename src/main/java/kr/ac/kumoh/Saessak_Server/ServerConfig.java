package kr.ac.kumoh.Saessak_Server;

import kr.ac.kumoh.Saessak_Server.repository.MemoryMyPlantRepository;
import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import kr.ac.kumoh.Saessak_Server.service.MyPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

public class ServerConfig {

    private final MemoryMyPlantRepository myPlantRepository;

    public ServerConfig(MemoryMyPlantRepository myPlantRepository){
        this.myPlantRepository = myPlantRepository;
    }

    @Bean
    public MyPlantService myPlantService(){
        return new MyPlantService(myPlantRepository);
    }

}
