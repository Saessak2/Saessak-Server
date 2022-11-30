package kr.ac.kumoh.Saessak_Server.controller;

import kr.ac.kumoh.Saessak_Server.domain.dto.WeatherDTO;
import kr.ac.kumoh.Saessak_Server.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherDTO readWeatherWithSunCond(String city, float sunCond){
        WeatherDTO weatherDTO = setWeatherDateWithSunCond(setCityName(city, 1), sunCond);

        if(weatherDTO.getIcon().isEmpty() && weatherDTO.getComments().isEmpty())
            weatherDTO = setWeatherDateWithSunCond(setCityName(city, 2), sunCond);

        if(weatherDTO.getIcon().isEmpty() && weatherDTO.getComments().isEmpty())
            weatherDTO = setWeatherDateWithSunCond(setCityName(city, 3), sunCond);

        return weatherDTO;
    }

    // 도시(영어) 받아서 날씨 넘겨주기
    @GetMapping("weather/{city}")
    public @ResponseBody
    ResponseEntity readWeather(@PathVariable("city") String city) {

        WeatherDTO weatherDTO = setWeatherDate(setCityName(city, 1));

        if(weatherDTO.getIcon().isEmpty() && weatherDTO.getComments().isEmpty())
            weatherDTO = setWeatherDate(setCityName(city, 2));

        if(weatherDTO.getIcon().isEmpty() && weatherDTO.getComments().isEmpty())
            weatherDTO = setWeatherDate(setCityName(city, 3));

        if(weatherDTO.getIcon().isEmpty() && weatherDTO.getComments().isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(weatherDTO);
    }

    //식물일기 -> 날씨 넘겨주기
    @GetMapping("diaryWeather/{city}")
    public @ResponseBody ResponseEntity readDiaryWeather(@PathVariable("city") String city) {

        WeatherDTO weatherDTO = new WeatherDTO();

        try {
            URI uri = new URI("https://api.openweathermap.org/data/2.5/weather?q=" + city
                    + "&appid=652a889361e23bc999e15881c7659057");
            uri = new URIBuilder(uri)
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .build();

            HttpResponse httpResponse = httpClient.execute(new HttpGet(uri));
            HttpEntity entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity);
//            System.out.println("content = " + content);

            weatherDTO.setIcon(weatherService.icon(content));

        } catch (Exception ignored) {

        }
        return ResponseEntity.ok(weatherDTO);
    }

    private WeatherDTO setWeatherDateWithSunCond(String city, float sunCond) {
        WeatherDTO weatherDTO = new WeatherDTO();

        try {
            URI uri = new URI("https://api.openweathermap.org/data/2.5/weather?q=" + city
                    + "&appid=652a889361e23bc999e15881c7659057");
            uri = new URIBuilder(uri)
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .build();

            HttpResponse httpResponse = httpClient.execute(new HttpGet(uri));
            HttpEntity entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity);

            weatherDTO.setIcon(weatherService.icon(content));
            weatherDTO.setComments(weatherService.comments(content, sunCond));

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
        return weatherDTO;
    }

        private WeatherDTO setWeatherDate(String city){
        WeatherDTO weatherDTO = new WeatherDTO();

        try {
            URI uri = new URI("https://api.openweathermap.org/data/2.5/weather?q=" + city
                    + "&appid=652a889361e23bc999e15881c7659057");
            uri = new URIBuilder(uri)
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .build();

            HttpResponse httpResponse = httpClient.execute(new HttpGet(uri));
            HttpEntity entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity);
//            System.out.println("content = " + content);

            weatherDTO.setIcon(weatherService.icon(content));
            weatherDTO.setComments(weatherService.comments(content));

        } catch (Exception ignored) {

        }
        return weatherDTO;
    }

    private String setCityName(String city, int parseLevel){
        if(!city.contains("-") && !city.contains(","))
            return city;

        int subStrPoint;
        switch (parseLevel){
            case 1:
                subStrPoint = city.indexOf("-");
                return city.substring(0, subStrPoint);
            case 2:
                subStrPoint = city.indexOf(",");
                return city.substring(0, subStrPoint);
            case 3:
                subStrPoint = city.indexOf(",");
                return city.substring(subStrPoint + 1);
        }
        return city;
    }

}
