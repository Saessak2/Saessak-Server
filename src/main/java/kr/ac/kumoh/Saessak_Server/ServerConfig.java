package kr.ac.kumoh.Saessak_Server;

import kr.ac.kumoh.Saessak_Server.repository.MyPlantRepository;
import kr.ac.kumoh.Saessak_Server.service.MyPlantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class ServerConfig {

    private final DataSource dataSource;

    public ServerConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    public MyPlantService myPlantService(){
        return new MyPlantService(myPlantRepository());
    }

    @Bean
    public MyPlantRepository myPlantRepository(){
        return new MyPlantRepository(dataSource);
    }

    @Bean
    public static DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("!Spider0826");
        dataSource.setUrl(
                "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC&characterEncoding=UTF-8");

        return dataSource;
    }

}
