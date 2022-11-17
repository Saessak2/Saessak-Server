package kr.ac.kumoh.Saessak_Server.domain;

import kr.ac.kumoh.Saessak_Server.Utility;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPlantPostDto;
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

    @Column(name = "uploaded_img")
    private boolean hasUploadedImg;

    public MyPlant(Long id){
        this.id = id;
    }

    public MyPlant(MyPlantPostDto myPlantGetDto){
        this.id = myPlantGetDto.getId();
        this.user = new User(myPlantGetDto.getUserId());
        this.nickname = myPlantGetDto.getNickname();
        this.species = myPlantGetDto.getSpecies();
        this.sunCondition = myPlantGetDto.getSunCondition();
        this.windCondition= myPlantGetDto.getWindCondition();
        this.waterCondition = myPlantGetDto.getWaterCondition();
        this.latestWaterDate = Utility.getLocalDateFromStr(myPlantGetDto.getTempDate());
        this.waterCycle = myPlantGetDto.getWaterCycle();
        this.imgUrl = myPlantGetDto.getImgUrl();
        this.isActive = myPlantGetDto.getIsActive();
        this.plantedRegion = myPlantGetDto.getPlantedRegion();
        this.hasUploadedImg = myPlantGetDto.getHasUploadedImg();
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

        if(myPlantDto.getImgUrl().startsWith("http") ||
                myPlantDto.getImgUrl() != null || !myPlantDto.getImgUrl().equals(""))
            this.imgUrl = myPlantDto.getImgUrl();

        if(myPlantDto.getIsActive() != null)
            this.isActive = myPlantDto.getIsActive();

        if(myPlantDto.getTempDate() != null || !myPlantDto.getTempDate().equals(""))
            this.latestWaterDate = Utility.getLocalDateFromStr(myPlantDto.getTempDate());

        if(myPlantDto.getPlantedRegion() != null || !myPlantDto.getPlantedRegion().equals(""))
            this.plantedRegion = myPlantDto.getPlantedRegion();

        if(myPlantDto.getHasUploadedImg() != null)
            this.hasUploadedImg = myPlantDto.getHasUploadedImg();

    }

    public void updateAbilityOnly(){
        isActive = !isActive;
    }

    public void updateLatestWaterDateOnly(){
        this.latestWaterDate = LocalDate.now();
    }

    public MyPlantDto toDto(){
        return new MyPlantDto(id, user.getId(), nickname, species, sunCondition,
                windCondition, waterCondition, waterCycle, imgUrl, isActive,
                latestWaterDate.toString().replace('-', '.'),
                plantedRegion, hasUploadedImg);
    }

}
