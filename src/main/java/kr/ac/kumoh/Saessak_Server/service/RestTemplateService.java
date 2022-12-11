package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.AutoComment;
import kr.ac.kumoh.Saessak_Server.domain.dto.AutoCommentDTO;
import kr.ac.kumoh.Saessak_Server.domain.dto.AutoQuestionDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateService {

    public AutoCommentDTO[] get(AutoQuestionDTO autoQuestionDTO) {

        // To use this method, insert model server url before "/qa"
        String url = "https:///qa/comment/auto/";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AutoQuestionDTO> entity = new HttpEntity<AutoQuestionDTO>(autoQuestionDTO, headers);
        ResponseEntity<AutoCommentDTO[]> responseEntity = restTemplate.postForEntity(url, entity, AutoCommentDTO[].class);

        AutoCommentDTO[] autoCommentDTO = responseEntity.getBody();

        return autoCommentDTO;
    }

}
