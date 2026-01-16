package controller.common;

import java.util.HashMap;
import java.util.Map;

import controller.account.JoinAction;
import controller.account.LoginAction;
import controller.account.LoginPageAction;
import controller.account.LogoutAction;
import controller.account.MyPageAction;
import controller.account.SignInPageAction;
import controller.account.SignOutAction;
import controller.account.SignOutPageAction;
import controller.account.WishlistPageAction;
import controller.cart.CartPageAction;
import controller.deliveryAddress.DeliveryAddressListPageAction;
import controller.deliveryAddress.RegistDeliveryAddressAction;
import controller.deliveryAddress.RegistDeliveryAddressPageAction;
import controller.kakaopay.KakaoPayCancelAction;
import controller.kakaopay.KakaoPayFailAction;
import controller.kakaopay.KakaoPayReadyAction;
import controller.kakaopay.KakaoPaySuccessAction;
import controller.mainPage.MainPageAction;
import controller.orderHistory.OrderHistoryDetailPageAction;
import controller.orderHistory.OrderHistoryListPageAction;
import controller.ornamentShop.OrnamentDetailPageAction;
import controller.ornamentShop.OrnamentListPageAction;
import controller.payment.PaymentAction;
import controller.payment.PaymentPageAction;
import controller.review.MyReviewListPageAction;
import controller.review.ReviewWriteAction;
import controller.review.ReviewWritePageAction;

public class ActionFactory {
	private Map<String, Action> map;
	
	ActionFactory(){
		map = new HashMap<>();
		
		// 메인 페이지
		this.map.put("/mainPage.do", new MainPageAction());
		
		// 로그인 & 로그아웃 
		this.map.put("/login.do", new LoginAction());
		this.map.put("/loginPage.do", new LoginPageAction());
		this.map.put("/logout.do", new LogoutAction());
		
		// 마이페이지
		this.map.put("/myPage.do", new MyPageAction());
		
		// 장바구니
		this.map.put("/cartPage.do", new CartPageAction());
		
		
		// 오너먼트 목록 & 오너먼트 상세보기 페이지
		this.map.put("/ornamentListPage.do", new OrnamentListPageAction());
		this.map.put("/ornamentDetailPage.do", new OrnamentDetailPageAction());
		
		// 주문 목록 & 주문 상품 상세보기 페이지
		this.map.put("/orderHistoryListPage.do", new OrderHistoryListPageAction());
		this.map.put("/orderHistoryDetailPage.do", new OrderHistoryDetailPageAction());

		// 결제 페이지
		this.map.put("/payment.do", new PaymentAction());
		this.map.put("/paymentPage.do", new PaymentPageAction());
		
		// 카카오결제 페이지
		this.map.put("/kakaoPayReady.do", new KakaoPayReadyAction());
		//this.map.put("/kakaoPayApprove.do", new KakaoPayApproveAction());

		this.map.put("/kakaoPayCancel.do", new KakaoPayCancelAction());
		this.map.put("/kakaoPayFail.do", new KakaoPayFailAction());
		this.map.put("/kakaoPaySuccess.do", new KakaoPaySuccessAction());

		
		
		// 배송지 목록 페이지
		this.map.put("/deliveryAddressListPage.do", new DeliveryAddressListPageAction());
		
		// 배송지 등록 페이지
		this.map.put("/registDiliveryAddress.do", new RegistDeliveryAddressAction());
		this.map.put("/registDiliveryAddressPage.do", new RegistDeliveryAddressPageAction());
		
		// 리뷰 페이지(작성페이지, 수정페이지)
		this.map.put("/reviewWrite.do", new ReviewWriteAction());
		this.map.put("/reviewWritePage.do", new ReviewWritePageAction());
		
		// 나의 리뷰 리스트 페이지
		this.map.put("/myReviewListPage.do", new MyReviewListPageAction());
		
		// 좋아요(찜한) 상품 페이지
		this.map.put("/wishlistPage.do", new WishlistPageAction());
		
		// 회원가입
		this.map.put("/signInPage.do", new SignInPageAction());
		this.map.put("/join.do", new JoinAction());
		this.map.put("/signOut.do", new SignOutAction());
		this.map.put("/signOutPage.do", new SignOutPageAction());
	}
	
	public Action getAction(String command) {
		return this.map.get(command);
	}
}
