package kr.ac.kumoh.Saessack_Server.service;

import kr.ac.kumoh.Saessack_Server.domain.User;
import kr.ac.kumoh.Saessack_Server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User selectUserById(Long id) {
        return userRepository.selectUser(id);
    }
}
