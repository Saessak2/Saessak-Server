package kr.ac.kumoh.Saessak_Server.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plantdict1")
public class PlantDict1{

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "contents_no")
    private int contentsNo;

    @Column(name = "plant_eng_name")
    private String plantEngName;

    @Column(name = "plant_name")
    private String plantName;

    @Column(name = "growth_hg")
    private int growthHg;

    @Column(name = "growth_ara")
    private int growthAra;

    @Column(name = "manage_level")
    private String manageLevel;

    @Column(name = "winter_min_temp")
    private String winterMinTemp;

    private String humidity;
    private String fertilizer;

    @Column(name = "soil_info")
    private String soilInfo;

    @Column(name = "watercycle_spring")
    private String waterCycleSpring;

    @Column(name = "watercycle_summer")
    private String waterCycleSummer;

    @Column(name = "watercycle_autumn")
    private String waterCycleAutumn;

    @Column(name = "watercycle_winter")
    private String waterCycleWinter;

    @Column(name = "specl_manage")
    private String speclManageInfo;

    @Column(name = "prpgt_mth")
    private String prpgtMth;

    @Column(name = "light_demand")
    private String lightDemand;

    private String pest;

    @Column(name = "img_url")
    private String imgUrl;

}
