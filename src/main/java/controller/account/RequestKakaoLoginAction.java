package controller.account;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;

public class RequestKakaoLoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		Properties props = new Properties();
		// 클래스 패스에서 설정 파일 로드
		InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
		try {
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 카카오에서 발급받은 REST API Key
		String REST_API_KEY = props.getProperty("KAKAO_LOGIN_API_KEY");
		
		String REDIRECT_URI = "http://localhost:8088/BugSandwichOrnamentMall/KakaoSocialLogin.do";//카카오에서 코드받을 리다이렉트 경로 
		
		String kakaoAuthUrl =  "https://kauth.kakao.com/oauth/authorize" + // 카카오에서 제공하는 OAuth url 
        "?response_type=code" + //카카오에서 발급받은 코드 
        "&client_id=" + REST_API_KEY +
        "&redirect_uri=" + REDIRECT_URI;
		
		ActionForward forward = new ActionForward();
		forward.setPath(kakaoAuthUrl);
		forward.setRedirect(true);
		return forward;
	}

}
