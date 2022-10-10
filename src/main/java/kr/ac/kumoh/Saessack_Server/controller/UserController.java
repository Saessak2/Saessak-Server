package kr.ac.kumoh.Saessack_Server.controller;

import kr.ac.kumoh.Saessack_Server.domain.User;
import kr.ac.kumoh.Saessack_Server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseBody
    @GetMapping("/user")
    public User getUserInfo(@RequestParam Long id) {
        System.out.println("id = " + id);
        User user = userService.selectUserById(id);
        return user;
    }
}
