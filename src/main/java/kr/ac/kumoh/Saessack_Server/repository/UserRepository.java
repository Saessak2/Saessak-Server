package kr.ac.kumoh.Saessack_Server.repository;

import kr.ac.kumoh.Saessack_Server.domain.KakaoUser;
import kr.ac.kumoh.Saessack_Server.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void createUser(User user) {
        em.persist(user);
    }

    public User selectUser(Long id) {
        User user = em.find(User.class, id);
        user.setId(id);
        return user;
    }

    public boolean selectKakaoUser(Long kakaoId) {
        if (em.find(KakaoUser.class, kakaoId) != null) {
            return true;
        }
        return false;
    }
}
