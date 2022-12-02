package kr.ac.kumoh.Saessak_Server.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentDTO {

    private Long id;
    private String content;
    private String dateTime;
    private Long question_id;
    private Long user_id;

}
