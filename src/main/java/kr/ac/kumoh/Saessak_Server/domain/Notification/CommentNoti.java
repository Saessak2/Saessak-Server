package kr.ac.kumoh.Saessak_Server.domain.Notification;

import kr.ac.kumoh.Saessak_Server.domain.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("m")
@Getter @Setter
public class CommentNoti {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Question question_id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    private Notification notification_id;
}
