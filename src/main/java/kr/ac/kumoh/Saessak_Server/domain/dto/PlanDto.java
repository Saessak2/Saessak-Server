package kr.ac.kumoh.Saessak_Server.domain.dto;

import kr.ac.kumoh.Saessak_Server.domain.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlanDto {

    private Long id;
    private Long user_id;
    private String tempDate;
    private boolean planType;
    private Long myPlant_id;
    private boolean isDone;

    public PlanDto(Plan plan){
        this.id = plan.getId();
        this.user_id = plan.getUser_id().getId();
        this.tempDate = plan.getDate().toString();
        this.planType = plan.isPlanType();
        this.myPlant_id = plan.getMyPlant_id().getId();
        this.isDone = plan.isDone();
    }

}
