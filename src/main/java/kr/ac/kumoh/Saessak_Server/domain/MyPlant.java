package kr.ac.kumoh.Saessak_Server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "myplant")
public class MyPlant {

    @Id
    @GeneratedValue
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

    @JsonIgnore
    @Column(name = "latest_water_date")
    private LocalDate latestWaterDate;

    @Column(name = "water_cycle")
    private int waterCycle;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "disable")
    private boolean isDisable;

    @Transient
    private String tempDate;

//    @OneToMany(mappedBy = "myPlant_id", cascade = CascadeType.ALL)
//    private List<Plan> planList = new ArrayList<>();

//    @OneToMany(mappedBy = "myPlant_id", cascade = CascadeType.ALL)
//    private List<Diary> diaryList = new ArrayList<>();

    public MyPlant(MyPlantDto myPlantDto){
        this.id = myPlantDto.getId();
        setUser_id(myPlantDto.getUser_id());
        this.nickname = myPlantDto.getNickname();
        this.species = myPlantDto.getSpecies();
        this.sunCondition = myPlantDto.getSunCondition();
        this.windCondition= myPlantDto.getWindCondition();
        this.waterCondition = myPlantDto.getWaterCondition();
        this.waterCycle = myPlantDto.getWaterCycle();
        this.imgUrl = myPlantDto.getImgUrl();
        this.isDisable = myPlantDto.isDisable();
        this.tempDate = myPlantDto.getTempDate();
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

    public MyPlant(Long id, Long uid, String nn, String sp, int sunC, int windC, int waterC,
                   int wCycle, String img, boolean isDisable, String tempDate){
        this.id = id;
        this.user_id = new User(uid);
        this.nickname = nn;
        this.species = sp;
        this.sunCondition = sunC;
        this.windCondition = windC;
        this.waterCondition = waterC;
        this.latestWaterDate = getLDFromStr(tempDate);
        this.waterCycle = wCycle;
        this.imgUrl = img;
        this.isDisable = isDisable;
        this.tempDate = tempDate;
    }

    public User getUser(){
        return user_id;
    }

    public Long getUser_id(){
        return user_id.getId();
    }

    public boolean getIsDisable() {
        return isDisable;
    }

    @JsonIgnore
    public void setUser_id(Long userId){
        this.user_id = new User(userId);
    }

    @JsonIgnore
    public void setLatestWaterDate(){
        latestWaterDate = getLDFromStr(tempDate);
    }

    @JsonIgnore
    private LocalDate getLDFromStr(String inDate){
        String[] tmpDateSplit = inDate.split("-");
        return LocalDate.of(Integer.parseInt(tmpDateSplit[0]),
                Integer.parseInt(tmpDateSplit[1]),
                Integer.parseInt(tmpDateSplit[2]));
    }

    @JsonIgnore
    public Map<String, Object> getMyPlantMap(){
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("user_id", user_id.getId());
        retMap.put("nickname", nickname);
        retMap.put("species", species);
        retMap.put("sun_condition", sunCondition);
        retMap.put("wind_condition", windCondition);
        retMap.put("water_condition", waterCondition);
        retMap.put("latest_water_date", latestWaterDate);
        retMap.put("water_cycle", waterCycle);
        retMap.put("img_url", imgUrl);
        retMap.put("disable", isDisable);

        if(!tempDate.isEmpty()){
            retMap.put("latest_water_date", getLDFromStr(tempDate));
        }
        return retMap;
    }

}
