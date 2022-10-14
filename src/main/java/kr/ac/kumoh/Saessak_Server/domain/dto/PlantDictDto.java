package kr.ac.kumoh.Saessak_Server.domain.dto;

import kr.ac.kumoh.Saessak_Server.domain.PlantDict;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlantDictDto {

    private Long id;
    private String plantName;
    private String dType;

    public PlantDictDto(PlantDict plantDict){
        this.id = plantDict.getId();
        this.plantName = plantDict.getPlantName();
        this.dType = plantDict.getDType();
    }

}
