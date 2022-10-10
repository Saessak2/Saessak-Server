package kr.ac.kumoh.Saessack_Server;

import kr.ac.kumoh.Saessack_Server.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.*;

@SpringBootApplication
public class SaessackServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaessackServerApplication.class, args);

	}

}
