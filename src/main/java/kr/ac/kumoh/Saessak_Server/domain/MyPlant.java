package kr.ac.kumoh.Saessak_Server.domain;

import kr.ac.kumoh.Saessak_Server.Utility;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantResDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantReqDto;
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

    @Column(name = "active")
    private boolean isActive;

    @Column(name = "planted_region")
    private String plantedRegion;

    private String icon;

    @Column(name = "rec_str")
    private String recStr;

    public MyPlant(Long id){
        this.id = id;
    }

    public MyPlant(MyPlantReqDto myPlantReqDto){
        this.id = myPlantReqDto.getId();
        this.user = new User(myPlantReqDto.getUserId());
        this.nickname = myPlantReqDto.getNickname();
        this.species = myPlantReqDto.getSpecies();
        this.sunCondition = myPlantReqDto.getSunCondition();
        this.windCondition= myPlantReqDto.getWindCondition();
        this.waterCondition = myPlantReqDto.getWaterCondition();
        this.latestWaterDate = Utility.getLocalDateFromStr(myPlantReqDto.getTempDate());
        this.waterCycle = myPlantReqDto.getWaterCycle();
        this.imgUrl = myPlantReqDto.getImgUrl();
        this.isActive = myPlantReqDto.getIsActive();
        this.plantedRegion = myPlantReqDto.getPlantedRegion();
    }

    public MyPlantResDto toDto(){
        return new MyPlantResDto(id, user.getId(), nickname, species,
                sunCondition, windCondition, waterCondition, waterCycle, imgUrl,
                isActive, latestWaterDate.toString().replace('-', '.'),
                plantedRegion, icon, recStr);
    }

    public void update(MyPlantReqDto myPlantReqDto){
        if(myPlantReqDto.getNickname() != null)
            this.nickname = myPlantReqDto.getNickname();

        if(myPlantReqDto.getSpecies() != null)
            this.species = myPlantReqDto.getSpecies();

        if(myPlantReqDto.getSunCondition() != 0)
            this.sunCondition = myPlantReqDto.getSunCondition();

        if(myPlantReqDto.getWindCondition() != 0)
            this.windCondition = myPlantReqDto.getWindCondition();

        if(myPlantReqDto.getWaterCondition() != 0)
            this.waterCondition = myPlantReqDto.getWaterCondition();

        if(myPlantReqDto.getWaterCycle() != 0)
            this.waterCycle = myPlantReqDto.getWaterCycle();

        if(myPlantReqDto.getImgUrl() != null)
            this.imgUrl = myPlantReqDto.getImgUrl();

        if(myPlantReqDto.getIsActive() != null)
            this.isActive = myPlantReqDto.getIsActive();

        if(myPlantReqDto.getTempDate() != null)
            this.latestWaterDate = Utility.getLocalDateFromStr(myPlantReqDto.getTempDate());

        if(myPlantReqDto.getPlantedRegion() != null)
            this.plantedRegion = myPlantReqDto.getPlantedRegion();
    }

}
