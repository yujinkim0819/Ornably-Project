package controller.kakaopay;

// 카카오페이 연동에 필요한 설정 정보 관리 : Admin Key, CID 등 
public class KakaoPayConfig {
	// 우리 서버의 기본 주소
	public static final String BASE_URL = "http://localhost:8088";
	
	// 테스트용 상점 코드 : 테스트 CID (1회성 결제)
    public static final String CID = "TC0ONETIME";
	
    // Secret Key (개발용)
	public static final String SECRET_KEY_DEV = "DEVA5D1472716045556DB1D1B3558A3F8B34EC98";
	
	// 결제 준비(Ready) API 주소
    // 결제 요청 정보를 카카오페이에 전달하여
    // TID와 결제 페이지 URL을 발급받기 위한 API
    public static final String READY_URL = "https://kapi.kakaopay.com/v1/payment/ready";
    
    // 결제 성공 시 카카오페이가 리다이렉트하는 우리 서버의 URL
    // 사용자가 결제 완료/실패/취소 시 이동하는 주소
    public static final String SUCCESS_URL = BASE_URL + "/BugSandwichOrnamentMall/kakaoPaySuccess.do";
    public static final String FAIL_URL    = BASE_URL + "/BugSandwichOrnamentMall/kakaoPayFail.do";
    public static final String CANCEL_URL  = BASE_URL + "/BugSandwichOrnamentMall/kakaoPayCancel.do";
}

