package kr.ac.kumoh.Saessak_Server.domain.dto;

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
    private Long idFromEachDict;
    private String imgUrl;

}
