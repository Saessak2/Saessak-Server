package kr.ac.kumoh.Saessak_Server.domain;

//import kr.ac.kumoh.Saessak_Server.domain.Notification.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_name")
    private String userName;

//    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
//    private List<Question> questionList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
//    private List<Comment> commentList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
//    private List<Notification> notificationList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
//    private List<Plan> planList = new ArrayList<>();

    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
    private List<MyPlant> myPlantList = new ArrayList<>();

    public User(Long id){
        this.id = id;
    }

}
