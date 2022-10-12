//package kr.ac.kumoh.Saessak_Server.domain.Notification;
//
//import kr.ac.kumoh.Saessak_Server.domain.Notification.Notification;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@DiscriminatorValue("p")
//@Getter @Setter
//public class PlantMgmtNoti {
//
//    @Id @GeneratedValue
//    private Long id;
//
//    private String plantNickName;
//    private String plantImg;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    private Notification notification_id;
//}
