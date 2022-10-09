//package kr.ac.kumoh.Saessak_Server.domain;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//public class Plan {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
//    private int planType;
//    private LocalDate date;
//    private boolean alarm;
//    private boolean isDone;
//
//}
