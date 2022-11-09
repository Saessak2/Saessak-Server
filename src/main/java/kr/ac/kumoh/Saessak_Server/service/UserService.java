package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.domain.KakaoUser;
import kr.ac.kumoh.Saessak_Server.domain.User;
import kr.ac.kumoh.Saessak_Server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long create(User user) {
        return userRepository.createUser(user);
    }

    @Transactional
    public User createKakao(KakaoUser kakaoUser) {
        return userRepository.createKakao(kakaoUser);
    }

    public List<User> findAll() {
        return  userRepository.findAll();
    }

    public User findOne(Long id) {
        User user = userRepository.findOne(id);
        return user;
    }

    //회원 이름 조회
    public String getUserName(Long id) {
        return userRepository.getUserName(id);
    }

    //카카오 로그인
    public User selectUser(Long kakaoId) {
        return userRepository.selectUser(kakaoId);
    }

}
