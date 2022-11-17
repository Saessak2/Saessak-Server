package kr.ac.kumoh.Saessak_Server.domain;

import kr.ac.kumoh.Saessak_Server.Utility;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate date;

    @Column(name = "plan_type")
    private String planType;

    @ManyToOne
    @JoinColumn(name = "my_plant_id_fk1")
    private MyPlant myPlant;

    @Column(name = "done")
    private boolean isDone;

    public Plan(PlanDto planDto){
        this.id = planDto.getId();
        this.user = new User(planDto.getUserId());
        this.date = Utility.getLocalDateFromStr(planDto.getDate());
        this.planType = planDto.getPlanType();
        this.myPlant = new MyPlant(planDto.getMyplant_id());
        this.isDone = planDto.getDone();
    }

    public void update(PlanDto planDto){
        if(planDto.getDate() != null || !planDto.getDate().equals(""))
            this.date = Utility.getLocalDateFromStr(planDto.getDate());

        if(planDto.getPlanType() != null || !planDto.getPlanType().equals(""))
            this.planType = planDto.getPlanType();

        if(planDto.getDone() != null)
            this.isDone = planDto.getDone();
    }

    public PlanDto toDto(){
        return new PlanDto(id, user.getId(), planType, myPlant.getId(),
                myPlant.getNickname(), isDone, date.toString().replace('-', '.'));
    }

}
