package kr.ac.kumoh.Saessack_Server.controller;

import kr.ac.kumoh.Saessack_Server.service.KakaoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class KakaoLoginController {
    private final KakaoUserService kakaoUserService;

    @ResponseBody
    @GetMapping("/auth/kakao/callback")
    public void kakaoCallBack(@RequestParam String code) {
        System.out.println("callbackcode = " + code);
        String accessToken = kakaoUserService.getKakaoAccessToken(code);
        System.out.println(accessToken);

        String userInfo = kakaoUserService.getUserInfo(accessToken);
        System.out.println(userInfo);

        kakaoUserService.createKakaoUser(userInfo);
    }

}
