package kr.ac.kumoh.Saessak_Server.domain.Notification;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("a")
@Getter @Setter
public class CalendarNoti {

    @Id @GeneratedValue
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    private Notification notification_id;
}
