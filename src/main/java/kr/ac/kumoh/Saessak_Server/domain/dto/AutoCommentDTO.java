package kr.ac.kumoh.Saessak_Server.domain.dto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AutoCommentDTO {

    private String link;
    private String title; // 지식인 제목
    private String similarity; // 유사도
    private String answer;



}
