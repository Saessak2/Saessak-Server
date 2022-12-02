package kr.ac.kumoh.Saessak_Server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlantDict2Dto {

    private Long id;
    private int contentsNo;
    private String plantName;
    private String growthInfo;
    private String growthSpeed;
    private String winterMinTemp;
    private String character;
    private String lightDemand;
    private String waterCycle;
    private String prpgtMth;
    private String pest;
    private String manageLevel;
    private String batchPlace;
    private String tip;
    private String imgUrl;
    private String imgUrl2;

}
