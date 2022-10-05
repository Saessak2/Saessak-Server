package kr.ac.kumoh.Saessak_Server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomPlantDict {

    @Id
    @GeneratedValue
    private Long id;

    private String plantName;  //식물이름
    private String plantEngName;  //식물영 명
    private String imgPath;  //이미지 경로
    private String tip;  //팁
    private String waterCycle;  //물주기

}
