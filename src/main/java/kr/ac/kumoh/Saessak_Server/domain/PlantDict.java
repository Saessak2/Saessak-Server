package kr.ac.kumoh.Saessak_Server.domain;

import kr.ac.kumoh.Saessak_Server.domain.dto.PlantDictDto;
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
@Table(name = "plantdict")
public class PlantDict {

    @Id
    private Long id;

    @Column(name = "plant_name")
    private String plantName;

    @Column(name = "dtype")
    private String dType;

    public PlantDictDto toDto(){
        return new PlantDictDto(id, plantName, dType);
    }

}
