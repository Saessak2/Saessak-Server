package kr.ac.kumoh.Saessak_Server.domain;

import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDict2Dto;
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
@Table(name = "plantdict2")
public class PlantDict2{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents_no")
    private int contentsNo;

    @Column(name = "plant_name")
    private String plantName;

    @Column(name = "growth_info")
    private String growthInfo;

    @Column(name = "growth_speed")
    private String growthSpeed;

    @Column(name = "winter_min_temp")
    private String winterMinTemp;

    private String character;

    @Column(name = "light_demand")
    private String lightDemand;

    @Column(name = "watercycle")
    private String waterCycle;

    @Column(name = "prpgt_mth")
    private String prpgtMth;

    private String pest;

    @Column(name = "manage_level")
    private String manageLevel;

    @Column(name = "batch_place")
    private String batchPlace;

    private String tip;

    @Column(name = "img_url_1")
    private String imgUrl;

    @Column(name = "img_url_2")
    private String imgUrl2;

    public PlantDict2Dto toDto(){
        return new PlantDict2Dto(id, contentsNo, plantName, growthInfo,
                growthSpeed, winterMinTemp, character, lightDemand,
                waterCycle, prpgtMth, pest, manageLevel, batchPlace,
                tip, imgUrl, imgUrl2);
    }

}
