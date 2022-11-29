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
    public void setIconAndRecStr(String icon, String recommendStr){
        this.icon = icon;
        this.recommendStr = recommendStr;
    }

}
