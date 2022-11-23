package kr.ac.kumoh.Saessak_Server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlantDict1Dto {

    private Long id;
    private int contentsNo;
    private String plantEngName;
    private String plantName;
    private int growthHg;
    private int growthAra;
    private String manageLevel;
    private String winterMinTemp;
    private String humidity;
    private String fertilizer;
    private String soilInfo;
    private String waterCycleSpring;
    private String waterCycleSummer;
    private String waterCycleAutumn;
    private String waterCycleWinter;
    private String speclManageInfo;
    private String prpgtMth;
    private String lightDemand;
    private String pest;
    private String imgUrl;

}

