package kr.ac.kumoh.Saessak_Server.domain.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class QuestionDTO {

    private Long id;
    private String content;
    private String category;
    private Long user_id;
    private String dateTime;
    private String userName;
    private int commentCnt;
    private boolean img;

}
