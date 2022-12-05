package kr.ac.kumoh.Saessak_Server.domain.dto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AutoCommentDTO {

    private String link;
    private String title; // 지식인 제목
    private String tags; // 태그
    private String answer;



}
