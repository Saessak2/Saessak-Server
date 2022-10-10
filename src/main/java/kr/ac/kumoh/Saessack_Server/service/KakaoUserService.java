package kr.ac.kumoh.Saessack_Server.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kr.ac.kumoh.Saessack_Server.domain.KakaoUser;
import kr.ac.kumoh.Saessack_Server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class KakaoUserService {

    private final UserRepository userRepository;
    private final String REQ_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final String GRANT_TYPE = "authorization_code";
    private final String CLIENT_ID = "50b725ea03d0368b00c6345db6da213d";
    private final String REDIRECT_URI = "http://localhost:8000/auth/kakao/callback";
    private final String REQ_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    public String getKakaoAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";

        try {
            URL url = new URL(REQ_TOKEN_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=" + GRANT_TYPE);
            sb.append("&client_id=" + CLIENT_ID);
            sb.append("&redirect_uri=" + REDIRECT_URI);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + accessToken);
            System.out.println("refresh_token : " + refreshToken);

            br.close();
            bw.close();

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return accessToken;
    }

    public String getUserInfo(String accessToken) {
        String userInfo = "";

        try {
            URL url = new URL(REQ_USER_INFO_URL + "?access_token=" + accessToken);

            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String line;
            while((line = bf.readLine()) != null) {
                userInfo += line;
            }
            bf.close();

            return userInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public void createKakaoUser(String userInfo) {
        JsonObject jsonObject = JsonParser.parseString(userInfo).getAsJsonObject();
        Long id = jsonObject.get("id").getAsLong();
        String name = jsonObject.get("properties").getAsJsonObject().get("nickname").getAsString();

        // 등록된 사용자인지 확인
        if (isRegisteredUser(id)) {
            KakaoUser user = new KakaoUser();
            user.setKakaoId(id);
            user.setName(name);
            userRepository.createUser(user);
        }
    }

    private boolean isRegisteredUser(Long kakaoId) {
        return userRepository.selectKakaoUser(kakaoId);
    }
}
