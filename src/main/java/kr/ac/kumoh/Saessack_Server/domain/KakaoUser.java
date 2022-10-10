package kr.ac.kumoh.Saessack_Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name="KAKAOUSER")
public class KakaoUser extends User{

    private Long kakaoId;
}
