package controller.account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.AccountDAO;
import model.dao.KakaoUserDAO;
import model.dto.AccountDTO;
import model.dto.KakaoTokenDTO;
import model.dto.KakaoUserDTO;



public class KakaoSocialLoginAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		//카카오 로그인 인증 후 redirectUri로 전달된 code 값을 이용해
		//AccessToken 발급 → 사용자 정보 요청 → 세션 저장 → 메인페이지로 이동
		System.out.println("[로그] KakaoSocialLoginAction 실행됨");
		ActionForward forward = new ActionForward();

		try {
			// 카카오에서 전달된 code 확인
			String code = request.getParameter("code");
			System.out.println("[로그] 수신된 코드 값 : " + code);

			// 만약 code가 없다면 로그인 과정에서 문제가 생긴 것이므로 다시 로그인 페이지로 이동
			if(code == null || code.isEmpty()) {
				System.out.println("[로그] 인증 코드(code)가 존재하지 않음 → 로그인 실패 처리");
				forward.setPath("login.jsp"); // 로그인 페이지로 이동
				forward.setRedirect(true); // sendRedirect 방식
				return forward;
			}


			//[2] 엑세스 토큰 요청을 위한 준비 민감한 정보가 있으니 포스트로 보낸다
			String tokenUrl = "https://kauth.kakao.com/oauth/token";
			URL url;
			url = new URL(tokenUrl);
			// url 객체생성 = 카카오 서버와 연결할 준비
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//카카오 서버랑 연결하는 통로 만들기
			//HttpURLConnection은 "HTTP 프로토콜"을 이용해 서버와 통신하는 통로
			conn.setRequestMethod("POST"); // 데이터 전송방식
			conn.setDoOutput(true);//데이터를 담아보낼수 있도록 설정 (post 방식 필수 옵션)
			//false면 데이터 전송 불가능 → POST 방식에서는 반드시 true

			//중요: 카카오가 요구하는 요청 데이터 형식 지정
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

			//카카오한테 필수로 전달해야 하는 파라미터(매개변수,인자)로는 이것들이 필요하다
			Properties props = new Properties();
			// 클래스 패스에서 설정 파일 로드
			InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
			props.load(is);

			// 카카오에서 발급받은 REST API Key
			String clientId = props.getProperty("KAKAO_LOGIN_API_KEY");
			
			String params = "grant_type=authorization_code" + //grant_type = 인증방식
					"&client_id="+ clientId +  // 내가 카카오에서 발급받은 REST API키
					"&redirect_uri=http://localhost:8088/BugSandwichOrnamentMall/KakaoSocialLogin.do" + //내가 카카오에 등록한 Redirect URI주소
					"&code=" + code; // 방금 카카오가 준 인증코드 (Access Token 받기 위한 핵심 값)

			System.out.println("[로그] TOKEN REQUEST CLIENT ID = " + clientId);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			//포스트방식이라 아웃풋스트림을 통해 파라미터에 전송해야된다
			bw.write(params); // 위에서 만든 요청 파라미터들을 실제로 서버에 보냄
			bw.flush(); //버퍼에 담기 내용을 비우고 즉시 전송한다

			//카카오가 응답을 보내주기때문에 읽을 준비를 해야한다 = 인풋 스트림

			BufferedReader br;
			if (conn.getResponseCode() == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			String line;// 한줄씩 읽어올때 임시로 담아줄 변수
			StringBuilder result = new StringBuilder();
			//한줄씩 읽어온 서버응답을 붙여서 하나의 문자열로 만들기 위한 저장소
			//수정, 추가가 효율적이기 때문에 StringBuilder 사용

			//카카오는 응답을 json 형태로 준다 줄단위로 읽어서 변수에 저장해야한다
			while((line = br.readLine())!=null) {
				//읽어온 내용을 리절트에 추가
				result.append(line);
			}

			//사용한 스트림 닫기
			br.close();
			bw.close();

			//최종 응답 확인 (Access Token, refresh_token 등이 포함된 JSON 형태)
			System.out.println("[로그] 카카오 응답 결과 : " + result.toString());
			// 카카오가 보내준 json 형식의 응답(Access Token이 들어있다)
			// 다음 단계에서 이 값을 가지고 사용자정보를 요청함

			Gson gson = new Gson(); //gson 객체만들기
			KakaoTokenDTO tokenData = gson.fromJson(result.toString(),KakaoTokenDTO.class);
			//fromJson은 매개변수를 두개 받는데 하나는 제이슨 문자열이고 하나는 제이슨을 어떤 타입으로 바꿀지다
			//따라서 카카오에서 받아온 json을 문자열로 받아와서 KakaoTokenDTO에 있는 객체로 바꿔라 임
			//.class는 왜 붙냐 => 자바에서 클래스 자체를 가리키는 객체(클래스 타입의 정보)를 의미함
			//즉 클래스 설계 자체를 가져오는것

			String accessToken = tokenData.access_token; //카카오가 준 토큰 꺼내오기
			System.out.println("[로그] Access Token = " + accessToken);

			if(accessToken == null || accessToken.isEmpty()) {//토큰이 없다면
				System.out.println("[로그] Access Token 없음 → 재로그인 필요");

				request.setAttribute("msg", "로그인 인증이 만료되었습니다. 다시 로그인하세요.");
				request.setAttribute("location", "login.do");

				forward.setPath("message.jsp");//어디로 보낼지
				forward.setRedirect(false);//어떻게 갈지
				return forward;
			}

			//[3] access token으로 사용자 정보요청하기
			// 사용자 정보를 가져올수 있는 API URL
			// 이주소로 요청하면 현재로그인 한 사용자 정보를 json 형태로 받을수 있다
			URL url2 = new URL("https://kapi.kakao.com/v2/user/me");

			HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
			//자바에서 HTTP 요청을 보낼수 있도록 연결통로 만들기

			//사용자 정보 요청방식
			conn2.setRequestMethod("GET");
			//이미 Access Token으로 카카오에 인증이 된 상태에서 사용자의 정보를 조회하는것이기 때문에 겟을 쓴다

			//카카오는 Access Token 있어야 사용자 데이터를 줄수 있다 
			//인가(authorization) = 권한 부여
			//접근 제어: 사용자 역할에 따라 접근 가능한 범위와 수행할 수 있는 작업을 제한하여
			//시스템의 무단 접근을 막는다 
			conn2.setRequestProperty("Authorization", "Bearer " + accessToken);
			//"Bearer " =카카오에서 받아오는 제이슨의 타입

			//서버에서 응답을 받기위한 입력 스트립준비
			// 카카오에서 보내준 제이슨을 줄단위로 읽는다
			BufferedReader br2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
			String line2; // 한줄씩 읽어올때 임시로 담아줄 변수
			StringBuilder userInfo = new StringBuilder();
			//한줄씩 읽어온 서버응답을 붙여서 하나의 문자열로 만들기 위한 저장소
			while ((line2 = br2.readLine()) != null) {// 줄단위로 읽어서 변수에 저장해야한다
				//읽어온 내용을 리절트에 추가
				userInfo.append(line2);
			}
			// 사용한 스트림 닫아주기
			br2.close();
			//확인용 로그 출력
			System.out.println("[로그] 카카오 사용자 정보 = " + userInfo.toString());

			// json 문자열을 DTO로 변환하기
			//JSON 형식의 문자열(String)을 → JsonObject로 변환하는 역할
			//JSON 구조로 분석할 수 있도록 파싱(Parsing) 하는것임
			//JsonParser = 제이슨 텍스트를 제이슨데이터(오브젝트)로 바꿔주는 변환기
			JsonObject jsonObj = JsonParser.parseString(userInfo.toString()).getAsJsonObject();

			//카카오에서 준 유저 고유 아이디(절대 변하지 않음)
			//제이슨안에서 id라는 값을 찾아서 가져온 값을 문자열 형태로 변환하고 카카오아이디에 저장한다
			String kakaoId = jsonObj.get("id").getAsString();

			//프로퍼티스 내부의 유저네임 가져오기
			String nickname = jsonObj.getAsJsonObject("properties").get("nickname").getAsString();

			//이메일과 프로필 이미지는 있을수도 있고 없을수도 있어서 조건식으로 만듬
			String email = null; // 이메일이 없다면
			if(jsonObj.getAsJsonObject("kakao_account").has("email")) {
				//카카오제이슨 객체의 카카오 어카운트객체에서 이메일이라는 키가 있다
				//이메일 키가 있는사람은 카카오에 이메일 제공동의한사람임
				email=jsonObj.getAsJsonObject("kakao_account").get("email").getAsString();
				//이메일 키가 있다면 이메일을 제이슨 객체에서 꺼내서 문자열로 저장해
			}

			String profileImage = null;//프로필 사진이 없다면
			if(jsonObj.getAsJsonObject("properties").has("profile_image")) {
				//카카오제이슨의 내부제이슨인 프로퍼티스에서 프로필이미지라는 키가 있다면 
				profileImage = jsonObj.getAsJsonObject("properties").get("profile_image").getAsString();
				//프로필 이미지를 가져와서 제이슨 객체에서 꺼내서 문자열로 저장해
			}
			//꺼낸 json데이터를 DTO 객체에 저장하기
			KakaoUserDTO kakaouserDTO = new KakaoUserDTO();
			kakaouserDTO.setKakaoId(kakaoId);
			kakaouserDTO.setNickname(nickname);
			kakaouserDTO.setEmail(email);
			kakaouserDTO.setProfileImage(profileImage);

			//DB에 저장여부확인하기 (회원가입인지 기존회원인지 구분)
			KakaoUserDAO kakaouserDAO = new KakaoUserDAO();

			//뉴 티티오 파서  카카오 아이디 해야함
			//디비에 저장된 회원인지 체크
			KakaoUserDTO kakaoUserCheckDTO = new KakaoUserDTO();
			kakaoUserCheckDTO.setKakaoId(kakaoId);
			kakaoUserCheckDTO.setCondition("KAKAOLOGINCHECK");

			boolean isAccount = kakaouserDAO.insert(kakaoUserCheckDTO);

			if(!isAccount) { //디비에 등록이 안되있다면 -> 회원가입
				System.out.println("[로그] 신규 카카오 회원 -> DB 저장");
				//저장할 회원정보
				KakaoUserDTO kakaoUserInsertDTO = new KakaoUserDTO();
				kakaoUserInsertDTO.setKakaoId(kakaoId);
				kakaoUserInsertDTO.setNickname(nickname);
				kakaoUserInsertDTO.setEmail(email);
				kakaoUserInsertDTO.setProfileImage(profileImage);
				kakaoUserInsertDTO.setCondition("KAKAOLOGININSERT");

				
				// 추가로 이메일과 아이디 중복체크는 모르는 거고
				// KAKAO_어쩌고 이름이 이미 먹혀 있을 때
				// 비밀번호는 KAKAO 고정이지만 딱히 떠오르는 문제점은 크게 없고
				// 전화번호는 NULL이 들어가고
				// ** 무조건 하나는 있어야할 배송지 등록이 되지 않는 문제가 존재 **
				// -> 결제 로직에서 배송지가 없으면 등록을 하게 만드는 로직을 만들던가 카카오 로그인 시 생성해줘야하는 문제가 있음
//				 		아무래도 결제시 이벤트 처리 해주는게 좋을거같단 생각이 있음
				// 로직은 잘 돌아가서 리다이랙트 URL만 설정 바꿔주면 좋을것같음
				
				
				kakaouserDAO.insert(kakaoUserInsertDTO);// DB에 저장
			}else{
				System.out.println("[로그] 기존회원 -> 로그인만 진행");
			}

			//카카오 id 기반으로 Account 조회하기
			AccountDAO accountDAO = new AccountDAO();
			AccountDTO accountDTO = new AccountDTO();
			//회원DTO에서 컨디션과 아이디 가져오기
			accountDTO.setCondition("SELECT_ACCOUNT_PK_BY_ACCOUNT_ID");//DAO에서 사용할 컨디션
			accountDTO.setAccountId("KAKAO_" + kakaoId);//ACCOUNT 테이블에 저장된 accountId 형식과 동일하게 맞춤
			//DB에 해당하는 카카오 회원 정보 조회
			AccountDTO accountData = accountDAO.selectOne(accountDTO);
			System.out.println("[로그] kakao 로그인 정보 조회 완료 : " + accountDTO);
			
			//로그인 유저 정보를 세션에 저장하기(로그인 유지)
			HttpSession session = request.getSession();
			//회원PK 가져와서 세션에 로그인정보저장
			session.setAttribute("accountPk",accountData.getAccountPk());
			System.out.println("[로그] kakao 로그인 정보 저장 완료 : " + accountData.getAccountPk());
			
			//로그인 완료후 페이지 이동
			request.setAttribute("message", "환영합니다, " +  nickname + "님");
			request.setAttribute("location", "mainPage.do"); //메시지 확인 후 이동할 페이지
			
			forward.setPath("message.jsp"); // 로그인 성공 후 가는 곳
			forward.setRedirect(false);//포워드방식으로 보냄
			return forward;

		} catch (Exception e) {// 네트워크 요청에서 오류가 날 수 있기 때문에 반드시 처리해야 함

			System.out.println("[로그] 카카오 로그인 도중 오류 발생 : " + e.getMessage());

			request.setAttribute("msg", "카카오 로그인 오류! 다시 시도해주세요.");
			request.setAttribute("location", "login.do");
			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}

	}
}

