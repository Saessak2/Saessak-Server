package kr.ac.kumoh.Saessak_Server.domain;

import kr.ac.kumoh.Saessak_Server.Utility;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlanReqDto;
import kr.ac.kumoh.Saessak_Server.domain.dto.PlanResDto;
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

    public Plan(PlanReqDto planReqDto){
        this.id = planReqDto.getId();
        this.user = new User(planReqDto.getUserId());
        this.date = Utility.getLocalDateFromStr(planReqDto.getDate());
        this.planType = planReqDto.getPlanType();
        this.myPlant = new MyPlant(planReqDto.getMyplant_id());
        this.isDone = planReqDto.getDone();
    }

    public PlanResDto toDto(){
        return new PlanResDto(id, planType, myPlant.getId(),
                myPlant.getNickname(), isDone, date.toString().replace('-', '.'),
                String.valueOf(isDone));
    }

    public void update(PlanReqDto planReqDto){
        if(planReqDto.getDate() != null)
            this.date = Utility.getLocalDateFromStr(planReqDto.getDate());

        if(planReqDto.getPlanType() != null)
            this.planType = planReqDto.getPlanType();

        if(planReqDto.getDone() != null)
            this.isDone = planReqDto.getDone();
    }

}
