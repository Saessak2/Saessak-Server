package kr.ac.kumoh.Saessak_Server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlantDict3Dto {

    public Long id;
    private String plantName;  //식물명
    private String sowingSeason;  //파종시기
    private String hvtSeason;  //수확시기
    private String character;  //특징
    private String cultInfo;  //재배정보
    private String imgUrl;  //이미지 경로

}
