package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;
    private String create_date;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

}

