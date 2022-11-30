package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    private String content;
    private String create_date;
    private String category;
    private int answer_count;

    @Column(name = "user_name")
    private String user_name;

    @OneToMany(mappedBy = "question_id", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "question_id", cascade = CascadeType.ALL)
    private List<AutoComment> autoCommentList = new ArrayList<>();

    @Embedded
    private Image image;

}

