package kr.ac.kumoh.Saessak_Server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlantDict3Dto {

    private Long id;
    private String plantName;
    private String sowingSeason;
    private String hvtSeason;
    private String character;
    private String cultInfo;
    private String imgUrl;

}
