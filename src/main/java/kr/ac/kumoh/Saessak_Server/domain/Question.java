//package kr.ac.kumoh.Saessak_Server.domain;
//
//import kr.ac.kumoh.Saessak_Server.domain.Notification.CommentNoti;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter @Setter
//public class Question {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false, insertable = false, updatable = false)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "id", columnDefinition = "User")
//    private User user_id;
//
//    private String content;
//    private LocalDate create_date;
//    private LocalDate update_date;
//    private String img_path;
//
//    @Enumerated(EnumType.STRING)
//    private Category category;
//
//    @OneToMany(mappedBy = "question_id", cascade = CascadeType.ALL)
//    private List<Comment> commentList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "question_id", cascade = CascadeType.ALL)
//    private List<CommentNoti> commentNotiList = new ArrayList<>();
//
//}
//
