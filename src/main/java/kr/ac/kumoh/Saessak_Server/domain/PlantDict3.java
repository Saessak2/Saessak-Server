package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class PlantDict3 {

    @Id
    @GeneratedValue
    private Long id;

    private String plantName;  //식물명
    private String sowingSeason;  //파종시기
    private String hvtSeason;  //수확시기
    private String character;  //특징
    private String cultInfo;  //재배정보
    private String imgUrl;  //이미지 경로

}
