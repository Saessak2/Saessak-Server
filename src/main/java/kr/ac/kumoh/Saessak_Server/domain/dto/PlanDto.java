package kr.ac.kumoh.Saessak_Server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlanDto {

    private Long id;
    private Long userId;
    private String planType;
    private Long myPlantId;
    private Boolean isDone;
    private String tempDate;

}
