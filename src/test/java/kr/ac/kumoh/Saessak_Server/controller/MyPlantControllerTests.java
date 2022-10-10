package kr.ac.kumoh.Saessak_Server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MyPlantControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        MyPlant myPlant = new MyPlant(3L, "labyrinth", "testSpe", 1, 2, 3,
        LocalDate.of(2022,10,9), 7, "no-img", false);

        mockMvc.perform(post("/myPlant/create/")
                        .content(objectMapper.writeValueAsString(myPlant))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("ISO-8859-1")
                .accept(MediaType.APPLICATION_JSON))


                .andDo(print())
                .andExpect(content().encoding("ISO-8859-1"))
                .andExpect(status().isCreated());
    }

    @Test
    void read() throws Exception {
        mockMvc.perform(get("/myPlant/readOne/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("ISO-8859-1")
                        .accept(MediaType.APPLICATION_JSON))


                .andDo(print())
                .andExpect(content().encoding("ISO-8859-1"));
    }

    @Test
    void updateAll(){

    }

    @Test
    void updateDis_Col(){

    }
}
