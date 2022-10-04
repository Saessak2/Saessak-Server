package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class PlantSpecies {

    @Id
    @GeneratedValue
    private Long id;

    private String imgPath;  //이미지 경로
    private String plantEngName;  //식물영문명
    private int plantDictRef;  //식물정보출처
    private String plantName;  //식물명

}
