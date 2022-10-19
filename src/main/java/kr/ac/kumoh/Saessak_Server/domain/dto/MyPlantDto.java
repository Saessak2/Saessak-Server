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
    private int sunCondition;
    private int windCondition;
    private int waterCondition;
    private int waterCycle;
    private String imgUrl;
    private Boolean isDisable;
    private String tempDate;

}
