package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "auto_comment")
public class AutoComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auto_comment_id")
    private Long id;

    private String title;
    private String link;
    private String tags;
    private String answer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question_id;

}
