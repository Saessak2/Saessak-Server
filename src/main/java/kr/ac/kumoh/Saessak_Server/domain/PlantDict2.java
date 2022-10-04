package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class PlantDict2 {

    @Id
    @GeneratedValue
    private Long id;

    private String plant_name;  //유통명(+식물명)
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
    private String tip;  //팁
    private String imgUrl;  //대표이미지1

}