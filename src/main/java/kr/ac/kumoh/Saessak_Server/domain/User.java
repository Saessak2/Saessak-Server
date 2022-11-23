package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<MyPlant> myPlantList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Diary> diaryList = new ArrayList<>();

}
