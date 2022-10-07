//package kr.ac.kumoh.Saessak_Server.domain;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//
//@Entity
//@Getter @Setter
//public class Comment {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false, insertable = false, updatable = false)
//    private Long id;
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "id", columnDefinition = "Question", insertable = false, updatable = false)
////    private Question question_id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id", columnDefinition = "User", insertable = false, updatable = false)
//    private User user_id;
//
//    private String content;
//    private LocalDate create_date;
//    private LocalDate update_date;
//}
//
