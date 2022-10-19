package kr.ac.kumoh.Saessak_Server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
