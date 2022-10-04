package kr.ac.kumoh.Saessak_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class MyPlant {

    @Id
    @GeneratedValue
    private Long id;

    private String plantNickname;
    private String species;
    private int sunCondition;
    private int windCondition;
    private int waterCondition;
    private LocalDate latestWaterDate;
    private int waterCycle;
    private String imgUrl;
    private boolean isDisable;
    private int order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user_id;

    @OneToMany(mappedBy = "question_id", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "notification_id", cascade = CascadeType.ALL)
    private List<CommentNoti> commentNotiList = new ArrayList<>();

    @OneToMany(mappedBy = "notification_id", cascade = CascadeType.ALL)
    private List<PlantMgmtNoti> plantMgmtNotiList = new ArrayList<>();


}
