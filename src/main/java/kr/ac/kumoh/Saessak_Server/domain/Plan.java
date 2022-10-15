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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    private LocalDate date;

    @Column(name = "plan_type")
    private boolean planType;

    @ManyToOne
    @JoinColumn(name = "myPlant_id")
    private MyPlant myPlant_id;

    @Column(name = "done")
    private boolean isDone;

}
