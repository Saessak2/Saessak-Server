package kr.ac.kumoh.Saessak_Server.repository;

import kr.ac.kumoh.Saessak_Server.domain.dto.WeatherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WeatherRepository {

    private final EntityManager em;

    //날씨(아이콘) 자르기
    public String icon(String content) {
        int temp3 = content.indexOf("icon") + 7;
        String icon = content.substring(temp3, temp3 + 3);

        return icon;
    }

    //날씨 온도 + 멘트
    public String comments(String content) {
        int temp1 = content.indexOf("main") + 7;
        int temp2 = content.indexOf("temp") + 5;

        String weather = content.substring(temp1, temp1 + 1);
        while(true) {
            temp1++;
            if(content.substring(temp1, temp1 + 1).equals("\"")) {
                break;
            }
            weather += content.substring(temp1, temp1 + 1);
        }

        String comments = comment(weather);

        String temperature = content.substring(temp2, temp2);
        while(true) {
            temp2++;
            if(content.substring(temp2, temp2 + 1).equals(",")) {
                break;
            }
            temperature += content.substring(temp2, temp2 + 1);
        }

        double tem = Double.parseDouble(temperature);
        double tem_result = Math.round(tem - 273.15);
        String str = Double.toString(tem_result);

        return str + "ºC\n" + comments;
    }

    public String comments(String content, float sunCond){
        int temp1 = content.indexOf("main") + 7;
        int temp2 = content.indexOf("temp") + 5;

        StringBuilder weather = new StringBuilder(content.substring(temp1, temp1 + 1));
        while(true) {
            temp1++;
            if(content.charAt(temp1) == '"') {
                break;
            }
            weather.append(content.charAt(temp1));
        }

        String comments = comment(weather.toString(), sunCond);

        StringBuilder temperature = new StringBuilder(content.substring(temp2, temp2));
        while(true) {
            temp2++;
            if(content.charAt(temp2) == ',') {
                break;
            }
            temperature.append(content.charAt(temp2));
        }

        double tem = Double.parseDouble(temperature.toString());
        double tem_result = Math.round(tem - 273.15);
        String str = Double.toString(tem_result);

        return str + "ºC\n" + comments;
    }

    public String comment(String str, float sunCond){
        String comments = "";
        switch(str) {
            case "Clouds":
                comments = "구름이 몽글몽글";
                break;
            case "Drizzle":
                comments = "안개가 자욱해요\n 흙 습도를 점검해보세요";
                break;
            case "Rain":
                comments = "비가 와요\n흙 습도를 점검해보세요";
                break;
            case "Snow":
                comments = "눈이 동글동글";
                break;
            case "Clear":
                if(sunCond >= 3.5)
                    comments = "해가 쨍쨍하니 창가로 옮겨주세요";
                else if(sunCond >= 2.0)
                    comments = "해가 쨍쨍해요";
                else
                    comments = "해가 쨍쨍하니 실내로 옮겨주세요";
                break;
            case "Atmosphere": case "Additional":
                comments = "바람이 슝슝슝";
                break;
            case "Thunderstorm": case "Extreme":
                comments = "기타 등등";
                break;
            default: comments = "까꿍"; break;
        }
        return comments;


    }

    //멘트
    public String comment(String str) {
        String comments;
        switch(str) {
            case "Clouds": case "Drizzle":
                comments = "구름 몽글";
                break;
            case "Rain":
                comments = "비 축축";
                break;
            case "Snow":
                comments = "눈 동글";
                break;
            case "Clear":
                comments = "해 반짝";
                break;
            case "Atmosphere": case "Additional":
                comments = "바람 슝슝";
                break;
            case "Thunderstorm": case "Extreme":
                comments = "기타 등등";
                break;
            default: comments = "까꿍"; break;
        }
        return comments;
    }

}
