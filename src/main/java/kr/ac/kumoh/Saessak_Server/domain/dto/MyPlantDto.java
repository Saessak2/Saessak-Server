package kr.ac.kumoh.Saessak_Server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPlantDto {

    private Long id;
    private Long userId;
    private String nickname;
    private String species;
    private float sunCondition;
    private float windCondition;
    private float waterCondition;
    private int waterCycle;
    private String imgUrl;
    private Boolean isDisable;
    private String tempDate;

}
