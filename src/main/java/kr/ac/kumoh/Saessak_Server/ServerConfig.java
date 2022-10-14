package kr.ac.kumoh.Saessak_Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class ServerConfig {

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
