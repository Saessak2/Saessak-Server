package kr.ac.kumoh.Saessak_Server.domain;

import kr.ac.kumoh.Saessak_Server.domain.dto.PlanDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plan")
public class Plan {

    @Id
    @GeneratedValue
    @Column(name = "plan_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate date;

    @Column(name = "plan_type")
    private String planType;

    @ManyToOne
    @JoinColumn(name = "myPlant_id")
    private MyPlant myPlant;

    @Column(name = "done")
    private boolean isDone;

    public Plan(PlanDto planDto){
        this.id = planDto.getId();
        this.user = new User(planDto.getUserId());
        this.date = EntityUtility.getLocalDateFromStr(planDto.getTempDate());
        this.planType = planDto.getPlanType();
        this.myPlant = new MyPlant(planDto.getMyPlantId());
        this.isDone = planDto.getIsDone();
    }

    public void update(PlanDto planDto){
        if(planDto.getTempDate() != null || !planDto.getTempDate().equals(""))
            this.date = EntityUtility.getLocalDateFromStr(planDto.getTempDate());

        if(planDto.getPlanType() != null || !planDto.getPlanType().equals(""))
            this.planType = planDto.getPlanType();

        if(planDto.getIsDone() != null)
            this.isDone = planDto.getIsDone();
    }

    public PlanDto toDto(){
        return new PlanDto(id, user.getId(), planType, myPlant.getId(),
                isDone, date.toString().replace('-', '.'));
    }

}
