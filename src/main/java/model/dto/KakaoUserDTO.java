package model.dto;

public class KakaoUserDTO {
	
	//카카오로 로그인 한 사용자 정보 저장용 DTO
	private String kakaoId; //카카오 아이디
	private String email; //이메일
	private String nickname;// 이름 = 닉네임
	private String profileImage; // 프로필 사진
	private String condition;
	
	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getKakaoId() {
		return kakaoId;
	}
	public void setKakaoId(String kakaoId) {
		this.kakaoId = kakaoId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	

}
