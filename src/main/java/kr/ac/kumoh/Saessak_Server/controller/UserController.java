package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.User;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPageDTO;
import kr.ac.kumoh.Saessak_Server.domain.dto.UserDTO;
import kr.ac.kumoh.Saessak_Server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public @ResponseBody ResponseEntity getUserInfo(@PathVariable("id") Long id) {
        User user = userService.findOne(id);
        UserDTO userDTO = new UserDTO();

        System.out.println("id = " + id);
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());

        return ResponseEntity.ok(userDTO);
    }

    //마이페이지 조회
    @GetMapping("/myPage/{id}")
    public @ResponseBody ResponseEntity readMyPage(@PathVariable("id") Long id) {
        String userName = userService.getUserName(id);
        MyPageDTO myPageDTO = new MyPageDTO();

        myPageDTO.setId(id);
        myPageDTO.setUserName(userName);

        int count = userService.CommentCnt(id);
        myPageDTO.setPlantCount(count);

        return ResponseEntity.ok(myPageDTO);
    }


}
