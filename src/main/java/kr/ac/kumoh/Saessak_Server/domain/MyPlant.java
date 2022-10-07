package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class MyPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, insertable = false, updatable = false)
    private Long id;

    private String nickname;
    private String species;
    private int sun_condition;
    private int wind_condition;
    private int water_condition;
    private LocalDate latest_water_date;
    private int water_cycle;
    private String img_url;
    private boolean disable;
//    private int order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

//    @OneToMany(mappedBy = "myPlant_id", cascade = CascadeType.ALL)
//    private List<Plan> planList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "myPlant_id", cascade = CascadeType.ALL)
//    private List<Plan> diaryList = new ArrayList<>();

    public MyPlant(String nickname, String species, int sun_condition,
                   int wind_condition, int water_condition, LocalDate latest_water_date,
                   int water_cycle, String img_url, boolean disable){
        this.nickname = nickname;
        this.species = species;
        this.sun_condition = sun_condition;
        this.wind_condition = wind_condition;
        this.water_condition = water_condition;
        this.latest_water_date = latest_water_date;
        this.water_cycle = water_cycle;
        this.img_url = img_url;
        this.disable = disable;
    }

    public MyPlant() {

    }

    public Long getUser(){
        return user_id.getId();
    }

}
