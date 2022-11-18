package kr.ac.kumoh.Saessak_Server.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPlantResDto {

    private Long id;
    private Long userId;
    private String nickname;
    private String species;
    private float sunCondition;
    private float windCondition;
    private float waterCondition;
    private int waterCycle;
    private String imgUrl;
    private Boolean isActive;
    private String tempDate;
    private String plantedRegion;
    private String icon;
    private String recommendStr;

    @JsonIgnore
    public MyPlantResDto(Long id, Long userId, String nickname,
                         String species, float sunCondition, float windCondition,
                         float waterCondition, int waterCycle, String imgUrl,
                         Boolean isActive, String tempDate, String plantedRegion){
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.species = species;
        this.sunCondition = sunCondition;
        this.windCondition = windCondition;
        this.waterCondition = waterCondition;
        this.waterCycle = waterCycle;
        this.imgUrl = imgUrl;
        this.isActive = isActive;
        this.tempDate = tempDate;
        this.plantedRegion = plantedRegion;
    }

    @JsonIgnore
    public void setData(){
        icon = "sun";
        recommendStr = "해가 짱짱쩅쨩";
    }

}
