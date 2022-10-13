package kr.ac.kumoh.Saessak_Server.domain.dto;

import kr.ac.kumoh.Saessak_Server.domain.PlantDict2;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlantDict2Dto {

    private Long id;
    private String plantName;  //유통명(+식물명)
    private String growthInfo;  //생장형
    private String growthSpeed;  //생장속도
    private String winterMinTemp;  //월동온도
    private String character;  //특성
    private String lightDemand;  //광요구도
    private String waterCycle;  //물주기
    private String prpgtMth;  //번식
    private String pest;  //병충해
    private String manageLevel;  //관리수준
    private String batchPlace;  //배치장소
    private String tip;    //팁
    private String imgUrl;  //대표이미지1

    public PlantDict2Dto(PlantDict2 plantDict2){
        this.id = plantDict2.getId();
        this.plantName = plantDict2.getPlantName();
        this.growthInfo = plantDict2.getGrowthInfo();
        this.growthSpeed = plantDict2.getGrowthSpeed();
        this.winterMinTemp = plantDict2.getWinterMinTemp();
        this.character = plantDict2.getCharacter();
        this.lightDemand = plantDict2.getLightDemand();
        this.prpgtMth = plantDict2.getPrpgtMth();
        this.pest = plantDict2.getPest();
        this.manageLevel = plantDict2.getManageLevel();
        this.batchPlace = plantDict2.getBatchPlace();
        this.tip = plantDict2.getTip();
        this.imgUrl = plantDict2.getImgUrl();
    }
}
