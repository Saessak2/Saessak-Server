package kr.ac.kumoh.Saessak_Server.domain;

import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "myplant")
public class MyPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "myplant_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String nickname;
    private String species;

    @Column(name = "sun_condition")
    private float sunCondition;

    @Column(name = "wind_condition")
    private float windCondition;

    @Column(name = "water_condition")
    private float waterCondition;

    @Column(name = "latest_water_date")
    private LocalDate latestWaterDate;

    @Column(name = "water_cycle")
    private int waterCycle;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "disable")
    private boolean isDisable;

    public MyPlant(Long id){
        this.id = id;
    }

    public MyPlant(MyPlantDto myPlantDto){
        this.id = myPlantDto.getId();
        this.user = new User(myPlantDto.getUserId());
        this.nickname = myPlantDto.getNickname();
        this.species = myPlantDto.getSpecies();
        this.sunCondition = myPlantDto.getSunCondition();
        this.windCondition= myPlantDto.getWindCondition();
        this.waterCondition = myPlantDto.getWaterCondition();
        this.latestWaterDate = EntityUtility.getLocalDateFromStr(myPlantDto.getTempDate());
        this.waterCycle = myPlantDto.getWaterCycle();
        this.imgUrl = myPlantDto.getImgUrl();
        this.isDisable = myPlantDto.getIsDisable();
    }

    public void update(MyPlantDto myPlantDto){
        if(myPlantDto.getNickname() != null)
            this.nickname = myPlantDto.getNickname();

        if(myPlantDto.getSpecies() != null || !myPlantDto.getSpecies().equals(""))
            this.species = myPlantDto.getSpecies();

        if(myPlantDto.getSunCondition() != 0)
            this.sunCondition = myPlantDto.getSunCondition();

        if(myPlantDto.getWindCondition() != 0)
           this.windCondition = myPlantDto.getWindCondition();

        if(myPlantDto.getWaterCondition() != 0)
            this.waterCondition = myPlantDto.getWaterCondition();

        if(myPlantDto.getWaterCycle() != 0)
            this.waterCycle = myPlantDto.getWaterCycle();

        if(myPlantDto.getImgUrl() != null || !myPlantDto.getImgUrl().equals("")
            || myPlantDto.getImgUrl().startsWith("http"))
            this.imgUrl = myPlantDto.getImgUrl();

        if(myPlantDto.getIsDisable() != null)
            this.isDisable = myPlantDto.getIsDisable();

        if(myPlantDto.getTempDate() != null || !myPlantDto.getTempDate().equals(""))
            this.latestWaterDate = EntityUtility.getLocalDateFromStr(myPlantDto.getTempDate());
    }

    public void updateAbilityOnly(){
        isDisable = !isDisable;
    }

    public void updateLatestWaterDateOnly(){
        this.latestWaterDate = LocalDate.now();
    }

    public MyPlantDto toDto(){
        return new MyPlantDto(id, user.getId(), nickname, species,
                sunCondition, windCondition, waterCondition, waterCycle, imgUrl,
                isDisable, latestWaterDate.toString().replace('-', '.'));
    }


}
