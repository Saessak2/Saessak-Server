package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class PlantSpecies {

    @Id @GeneratedValue
    private Long id;

    private String plant_name;
    private String img_path;
    private int plant_dirt_ref;
    private String plant_eng_name;
}
