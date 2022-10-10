package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "myplant")
public class MyPlant {

    @Id
    @GeneratedValue/*(strategy = GenerationType.IDENTITY)*/
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    private String nickname;
    private String species;

    @Column(name = "sun_condition")
    private int sunCondition;

    @Column(name = "wind_condition")
    private int windCondition;

    @Column(name = "water_condition")
    private int waterCondition;

    @Column(name = "latest_water_date")
    private LocalDate latestWaterDate;

    @Column(name = "water_cycle")
    private int waterCycle;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "disable")
    private boolean isDisable;

//    @OneToMany(mappedBy = "myPlant_id", cascade = CascadeType.ALL)
//    private List<Plan> planList = new ArrayList<>();

//    @OneToMany(mappedBy = "myPlant_id", cascade = CascadeType.ALL)
//    private List<Diary> diaryList = new ArrayList<>();

    public MyPlant(Long pid){
        this.id = pid;
    }

    public MyPlant(Long uid, String nn, String sp, int sunC, int windC, int waterC,
                   LocalDate lwd, int wCycle, String img, boolean isDisable){
        this.user_id = new User(uid);
        this.nickname = nn;
        this.species = sp;
        this.sunCondition = sunC;
        this.windCondition = windC;
        this.waterCondition = waterC;
        this.latestWaterDate = lwd;
        this.waterCycle = wCycle;
        this.imgUrl = img;
        this.isDisable = isDisable;
    }

    public Long getUser_id(){
        return user_id.getId();
    }

    public void setUser_id(Long uid){
        this.user_id = new User(uid);
    }

    public void setLatestWaterDate(Date date){
        String[] tmpDate = date.toString().split("-");
        this.latestWaterDate = LocalDate.of(Integer.parseInt(tmpDate[0]),
                                            Integer.parseInt(tmpDate[1]),
                                            Integer.parseInt(tmpDate[2]));
    }

    public boolean getIsDisable() {
        return isDisable;
    }

}
