package kr.ac.kumoh.Saessak_Server.domain.dto;

import kr.ac.kumoh.Saessak_Server.domain.PlantDict1;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlantDict1Dto {

    private Long id;
    private String plantEngName;  //식물영 명
    private String plantName;  //유통명(식물명)
    private String manageLevel;  //관리 수준
    private String winterMinTemp;  //겨울 최저 온도
    private String humidity;  //습도
    private String fertilizer;  //비료 정보
    private String soilInfo;  //토양 정보
    private String waterCycleSpring;  //물주기 봄
    private String waterCycleSummer;  //물주기 여름
    private String waterCycleAutumn;  //물주기 가을
    private String waterCycleWinter;  //물주기 겨울
    private String speclManageInfo;  //특별 관리 정보
    private String prpgtMth;  //번식방법
    private String lightDemand;  //광요구도
    private String pest;  //병충해
    private String imgUrl;  //이미지

    public PlantDict1Dto(PlantDict1 plantDict1){
        this.id = plantDict1.getId();
        this.plantEngName = plantDict1.getPlantEngName();
        this.plantName = plantDict1.getPlantName();
        this.manageLevel = plantDict1.getManageLevel();
        this.winterMinTemp = plantDict1.getWinterMinTemp();
        this.humidity = plantDict1.getHumidity();
        this.fertilizer = plantDict1.getFertilizer();
        this.soilInfo = plantDict1.getSoilInfo();
        this.waterCycleSpring = plantDict1.getWaterCycleSpring();
        this.waterCycleSummer = plantDict1.getWaterCycleSummer();
        this.waterCycleAutumn = plantDict1.getWaterCycleAutumn();
        this.waterCycleWinter = plantDict1.getWaterCycleWinter();
        this.speclManageInfo = plantDict1.getSpeclManageInfo();
        this.prpgtMth = plantDict1.getPrpgtMth();
        this.lightDemand = plantDict1.getLightDemand();
        this.pest = plantDict1.getPest();
        this.imgUrl = plantDict1.getImgUrl();
    }

}
