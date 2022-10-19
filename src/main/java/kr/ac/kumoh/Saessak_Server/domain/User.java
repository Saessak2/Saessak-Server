package kr.ac.kumoh.Saessak_Server.domain;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "setting_id")
    private Long settingId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MyPlant> myPlantList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Plan> planList = new ArrayList<>();

    public User(Long userId){
        this.id = userId;
    }

}
