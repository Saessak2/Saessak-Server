package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "myplant")
public class MyPlant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "myplant_id")
    private Long id;

    private String nickname;
    private String species;
    private float sun_condition;
    private float wind_condition;
    private float water_condition;
    private String latest_water_date;
    private int water_cycle;
    private String img_url;

    private boolean disable;

    private Long user_id;

    @OneToMany
    @JoinColumn(name = "myplant_id")
    private List<Diary> diaryList = new ArrayList<>();
}
