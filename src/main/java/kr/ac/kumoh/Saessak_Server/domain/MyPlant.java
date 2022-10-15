package kr.ac.kumoh.Saessak_Server.domain;

import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@DynamicUpdate
@Table(name = "myplant")
public class MyPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "myPlant_id", cascade = CascadeType.ALL)
    private List<Plan> planList = new ArrayList<>();

//    @OneToMany(mappedBy = "myPlant_id", cascade = CascadeType.ALL)
//    private List<Diary> diaryList = new ArrayList<>();

    public MyPlant(MyPlantDto myPlantDto){
        this.id = myPlantDto.getId();
        this.user_id = getUser(myPlantDto.getUser_id());
        this.nickname = myPlantDto.getNickname();
        this.species = myPlantDto.getSpecies();
        this.sunCondition = myPlantDto.getSunCondition();
        this.windCondition= myPlantDto.getWindCondition();
        this.waterCondition = myPlantDto.getWaterCondition();
        this.latestWaterDate = getLocalDateFromStr(myPlantDto.getTempDate());
        this.waterCycle = myPlantDto.getWaterCycle();
        this.imgUrl = myPlantDto.getImgUrl();
        this.isDisable = myPlantDto.isDisable();
    }

    public void setLatestWaterDateWithString(String inDate){
        this.latestWaterDate = getLocalDateFromStr(inDate);
    }

    private User getUser(Long userId){
        return new User(userId);
    }

    private LocalDate getLocalDateFromStr(String inDate){
        String[] tmpArr = inDate.split("-");
        return LocalDate.of(Integer.parseInt(tmpArr[0]),
                Integer.parseInt(tmpArr[1]),
                Integer.parseInt(tmpArr[2]));
    }

}
