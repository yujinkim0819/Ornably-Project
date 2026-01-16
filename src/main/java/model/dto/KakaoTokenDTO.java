package model.dto;

public class KakaoTokenDTO {
	//카카오 서버에서 보내준 토큰 정보를 담는 데이터 객체 만들어주기
	//카카오에서 Json으로 주면 자바객체로 변환해서 사용하기 위함 
	public String access_token;//카카오 인증후 발급받는 실제 로그인 인증키(API 호출에 사용)
	private String token_type;//카카오에서 받는 토큰의 종류
	private String refresh_token; // access_token이 만료됬을 때 다시 발급받기 위한 토큰
	private int expires_in;//access_token의 유효시간 -> 시간지나면 갱신필요함
	private int refresh_token_expires_in; //refresh_token의 유효시간 -> access보다 훨씬 길다
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public int getRefresh_token_expires_in() {
		return refresh_token_expires_in;
	}
	public void setRefresh_token_expires_in(int refresh_token_expires_in) {
		this.refresh_token_expires_in = refresh_token_expires_in;
	}
	    
	    
	    
	}
