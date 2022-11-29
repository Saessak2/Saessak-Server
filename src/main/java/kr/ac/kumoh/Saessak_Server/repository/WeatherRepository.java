package kr.ac.kumoh.Saessak_Server.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
