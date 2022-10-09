//package kr.ac.kumoh.Saessak_Server.domain.Notification;
//
//import kr.ac.kumoh.Saessak_Server.domain.User;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "dtype")
//@Getter @Setter
//public abstract class Notification {
//
//    @Id @GeneratedValue
//    private Long id;
//
//    private LocalDateTime datetime;
//    private boolean check;
//    private String message;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    private User user_id;
//
//    @OneToMany(mappedBy = "notification_id", cascade = CascadeType.ALL)
//    private List<CalendarNoti> calendarNotiList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "notification_id", cascade = CascadeType.ALL)
//    private List<CommentNoti> commentNotiList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "notification_id", cascade = CascadeType.ALL)
//    private List<PlantMgmtNoti> plantMgmtNotiList = new ArrayList<>();
//}
//
