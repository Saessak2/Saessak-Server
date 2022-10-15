package kr.ac.kumoh.Saessak_Server.domain.dto;


import kr.ac.kumoh.Saessak_Server.domain.MyPlant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPlantDto {

    private Long id;
    private Long user_id;
    private String nickname;
    private String species;
    private int sunCondition;
    private int windCondition;
    private int waterCondition;
    private int waterCycle;
    private String imgUrl;
    private boolean isDisable;
    private String tempDate;

    public MyPlantDto(MyPlant myPlant){
        id = myPlant.getId();
        user_id = myPlant.getUser_id().getId();
        nickname = myPlant.getNickname();
        species = myPlant.getSpecies();
        sunCondition = myPlant.getSunCondition();
        windCondition = myPlant.getWindCondition();
        waterCondition = myPlant.getWaterCondition();
        waterCycle = myPlant.getWaterCycle();
        imgUrl = myPlant.getImgUrl();
        isDisable = myPlant.isDisable();
        tempDate = myPlant.getLatestWaterDate().toString();
    }

}
