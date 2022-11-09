package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;
    private String date;
    private String time;
    private String content;
    private String weather;
    private String cond;
    private String activity1;
    private String activity2;
    private String activity3;
    private String img_url;

    private Long user_id;
    private Long myplant_id;

}
