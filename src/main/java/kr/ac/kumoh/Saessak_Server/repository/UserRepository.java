package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.KakaoUser;
import kr.ac.kumoh.Saessak_Server.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Long createUser(User user) {
        em.persist(user);
        return user.getId();
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    //회원 이름 조회
    public String getUserName(Long id) {
        User user = em.find(User.class, id);
        return user.getUserName();
    }

    //카카오 로그인
    public User selectUser(Long kakaoId) {
        List<KakaoUser> kakaoUsers = findByKakaoId(kakaoId);
        Long userId = kakaoUsers.get(0).getId();
        User user = em.find(User.class, userId);

        return user;
    }

    public boolean selectKakaoUser(Long kakaoId) {
        List<KakaoUser> list = findByKakaoId(kakaoId);
        if (!(list.isEmpty())) {
            return true;
        }
        return false;
    }

    public List<KakaoUser> findByKakaoId(Long kakaoId) {
        List<KakaoUser> list = em.createQuery("select k from KakaoUser k where k.kakaoId = :kakaoId", KakaoUser.class)
                .setParameter("kakaoId", kakaoId)
                .getResultList();

        return list;
    }

    public User createKakao(KakaoUser kakaoUser) {
        em.persist(kakaoUser);
        return kakaoUser;
    }

}
