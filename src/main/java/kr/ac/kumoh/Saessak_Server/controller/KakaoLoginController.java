package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.User;
import kr.ac.kumoh.Saessak_Server.domain.dto.MyPageDTO;
import kr.ac.kumoh.Saessak_Server.domain.dto.UserDTO;
import kr.ac.kumoh.Saessak_Server.service.KakaoUserService;
import kr.ac.kumoh.Saessak_Server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class KakaoLoginController {
    private final KakaoUserService kakaoUserService;
    private final UserService userService;

    @GetMapping("kakaoLogin/{code}")
    public @ResponseBody ResponseEntity kakaoCallBack(@PathVariable("code") String code) {
        System.out.println("callbackcode = " + code);
        String accessToken = kakaoUserService.getKakaoAccessToken(code);
        System.out.println(accessToken);

        String userInfo = kakaoUserService.getUserInfo(accessToken);
        System.out.println(userInfo);

        User user = kakaoUserService.createKakaoUser(userInfo);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("kakaoLogin")
    public @ResponseBody ResponseEntity<List<MyPageDTO>> createQuestion(@RequestBody UserDTO userDTO) {
        User user = kakaoUserService.createKakaoLogin(userDTO);
        List<MyPageDTO> list = new ArrayList<>();

        String userName = userService.getUserName(user.getId());
        MyPageDTO myPageDTO = new MyPageDTO();

        myPageDTO.setId(user.getId());
        myPageDTO.setUserName(userName);
        int count = userService.CommentCnt(user.getId());
        myPageDTO.setPlantCount(count);
        list.add(myPageDTO);

        return ResponseEntity.ok(list);
    }

}
