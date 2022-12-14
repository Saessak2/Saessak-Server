package kr.ac.kumoh.Saessak_Server.domain;

import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict3Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plantdict3")
public class PlantDict3{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plant_name")
    private String plantName;

    @Column(name = "sowing_season")
    private String sowingSeason;

    @Column(name = "hvt_season")
    private String hvtSeason;

    private String character;

    @Column(name = "cult_info")
    private String cultInfo;

    @Column(name = "img_url")
    private String imgUrl;

    public PlantDict3Dto toDto(){
        return new PlantDict3Dto(id, plantName, sowingSeason,
                hvtSeason, character, cultInfo, imgUrl);
    }

}
