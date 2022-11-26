package kr.ac.kumoh.Saessak_Server.service;

import kr.ac.kumoh.Saessak_Server.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;

    //날씨(아이콘) 자르기
    public String icon(String content) {
        return weatherRepository.icon(content);
    }


    //날씨 온도 + 멘트
    public String comments(String content) {
        return weatherRepository.comments(content);
    }


}
