package kr.ac.kumoh.Saessak_Server.domain;

import kr.ac.kumoh.Saessak_Server.domain.Notification.CommentNoti;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Question {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user_id;

    private String content;
    private LocalDate create_date;
    private LocalDate update_date;
    private String img_path;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "question_id", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "notification_id", cascade = CascadeType.ALL)
    private List<CommentNoti> commentNotiList = new ArrayList<>();

    //생성 메서드
//    public static Question createQuestion(User user, Question q) {
//        Question question = new Question();
//
//        question.setUser_id(user);
//        question.setContent(q.getContent());
//        question.setCreate_date(LocalDate.now());
//        question.setUpdate_date(null);
//        question.setImg_path(q.getImg_path());
//        question.setCategory(q.getCategory());
//
//        return question;
//    }

}

