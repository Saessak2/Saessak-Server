//package kr.ac.kumoh.Saessak_Server.domain;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.sql.Time;
//import java.time.LocalDate;
//
//@Entity
//@Getter
//@Setter
//public class Diary {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    private User user_id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    private MyPlant myPlant_id;
//
//    private LocalDate date;
//    private Time time;
//    private String content;
//    private String weather;
//    private String condition;
//    private String activity1;
//    private String activity2;
//    private String activity3;
//    private String img_url;
//
//}
