package kr.ac.kumoh.Saessak_Server.domain;

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
    private boolean planType;

    @ManyToOne
    @JoinColumn(name = "myplant_id")
    private MyPlant myPlant;

    @Column(name = "done")
    private boolean isDone;

}
