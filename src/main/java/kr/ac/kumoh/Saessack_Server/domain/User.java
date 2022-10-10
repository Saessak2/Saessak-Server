package kr.ac.kumoh.Saessack_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="USER")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;

    private String name;


}
