package controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.AccountDAO;
import model.dao.AddressDAO;
import model.dao.CartDAO;
import model.dao.OrderDAO;
import model.dao.OrderItemDAO;
import model.dao.ReviewDAO;
import model.dao.WishlistDAO;
import model.dto.AccountDTO;
import model.dto.AddressDTO;
import model.dto.CartDTO;
import model.dto.OrderDTO;
import model.dto.OrderItemDTO;
import model.dto.ReviewDTO;
import model.dto.WishlistDTO;

// 회원 탈퇴 로직
// [받는 데이터]
// 
// [동작]
// 1. 사용자 id->null / accountRole=SIGNOUT
// 2. 사용자PK의 모든 리뷰 삭제
// 3. 사용자PK의 모든 좋아요목록 삭제
// 4(A). 사용자PK의 모든 주문상세내역 삭제
// 4(B). 사용자PK의 모든 주문내역 삭제
// 5. 사용자PK의 모든 장바구니 삭제 
// 6. 사용자PK의 모든 배송지 삭제
public class SignOutAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.account.SignOutAction | [회원탈퇴 시도]");
		
		ActionForward forward = new ActionForward();
		
		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		
		System.out.println("[로그] controller.account.SignOutAction | accountPk:["+accountPk+"]");
		
		if (accountPk == null) {
			System.out.println("[이상] controller.account.SignOutAction | [accountPk가 null인데 회원탈퇴 진행하려함]");
			request.setAttribute("location", "/loginPage.do");
			request.setAttribute("message", "로그인 후 이용해주세요");

			// 사용자에게 알림 후 로그인 페이지로 이동
			forward.setPath("message.jsp");
			forward.setRedirect(false);
			System.out.println("[로그] controller.account.SignOutAction | [회원탈퇴 시도 종료]");
			return forward;
		}
		//////////////////////////////
		// 회원탈퇴 로직 시작
		
		System.out.println("[로그] controller.account.SignOutAction | [ (1/7) ACCOUNT 테이블 회원탈퇴 처리]");
	// 1. 사용자PK의 ID를 NULL로, ROLE을 SIGNOUT로 바꾸기
		AccountDAO accountDAO = new AccountDAO();
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAccountPk(accountPk);
		accountDTO.setAccountRole("SIGNOUT");
		accountDTO.setCondition("UPDATE_SIGN_OUT");
		System.out.println("[로그] controller.account.SignOutAction | [accountDAO.update(accountDTO)] - accountDTO:["+accountDTO+"]");
		if(accountDAO.update(accountDTO)) {
			// 성공적으로 업데이트
			System.out.println("[로그] controller.account.SignOutAction | 사용자 계정 테이블 삭제(업데이트) 성공");
		}
		else {
			// 업데이트 실패
			System.out.println("[로그] controller.account.SignOutAction | 사용자 계정 테이블 삭제(업데이트) 실패");
		}
		
		
		System.out.println("[로그] controller.account.SignOutAction | [ (2/7) accountPk에 대한 REVIEW 테이블 삭제 처리]");
	// 2. 사용자PK의 모든 리뷰 삭제
		ReviewDAO reviewDAO = new ReviewDAO();
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setAccountPk(accountPk);
		reviewDTO.setCondition("DELETE_ALL_REVIEW_BY_ACCOUNT_PK");
		System.out.println("[로그] controller.account.SignOutAction | [reviewDAO.delete(reviewDTO)] - reviewDTO:["+reviewDTO+"]");
		if(reviewDAO.delete(reviewDTO)) {
			System.out.println("[로그] controller.account.SignOutAction | 리뷰 삭제 성공");
		}
		else {
			System.out.println("[로그] controller.account.SignOutAction | 리뷰 삭제 실패");
		}
		
		
		System.out.println("[로그] controller.account.SignOutAction | [ (3/7) accountPk에 대한 WISHLIST 테이블 삭제 처리]");
	// 3. 사용자PK에 대한 모든 좋아요목록 삭제
		WishlistDAO wishlistDAO = new WishlistDAO();
		WishlistDTO wishlistDTO = new WishlistDTO();
		wishlistDTO.setAccountPk(accountPk);
		wishlistDTO.setCondition("DELETE_ALL_WISHLIST_BY_ACCOUNT_PK");
		
		System.out.println("[로그] controller.account.SignOutAction | [wishlistDAO.delete(wishlistDTO)] - wishlistDTO:["+wishlistDTO+"]");
		if(wishlistDAO.delete(wishlistDTO)) {
			System.out.println("[로그] controller.account.SignOutAction | 좋아요 목록 삭제 성공");
		}
		else {
			System.out.println("[로그] controller.account.SignOutAction | 좋아요 목록 삭제 실패");
		}
		
		
		System.out.println("[로그] controller.account.SignOutAction | [ (4/7) accountPk에 대한 ORDERS_ITEM 테이블 삭제 처리]");
	// 4-a. 사용자 pk에 대한 주문내역상세 먼저 삭제하기
		OrderItemDAO orderItemDAO = new OrderItemDAO();
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setAccountPk(accountPk);
		orderItemDTO.setCondition("DELETE_ALL_ORDER_ITEM_BY_ACCOUNT_PK");
		
		System.out.println("[로그] controller.account.SignOutAction | [orderDAO.delete(orderDTO)] - orderDTO:["+orderItemDTO+"]");
		if(orderItemDAO.delete(orderItemDTO)) {
			System.out.println("[로그] controller.account.SignOutAction | 주문 상세 삭제 성공");
		}
		else {
			System.out.println("[로그] controller.account.SignOutAction | 주문 상세 삭제 실패");
		}
		
		/* [잘못된 로직으로 데이터 삭제는 모두 하나의 요청으로 model에서 처리]
		 * 
		 * System.out.println("[로그] controller.account.SignOutAction | [상세내역 모두 삭제하기] - 시작");
		for(int i=0;i<orderDatas.size();i++) {
			// 상세내역 모두 삭제하기
			OrderItemDAO orderItemDAO = new OrderItemDAO();
			OrderItemDTO orderItemDTO = new OrderItemDTO();
			orderItemDTO.setOrderPk(orderDatas.get(i).getOrderPk());
			orderItemDTO.setCondition("DELETE_ALL_ORDER_ITEM_BY_ORDER_PK");
			
			System.out.println("[로그] controller.account.SignOutAction | [orderItemDAO.delete(orderItemDTO)] - orderItemDTO:["+orderItemDTO+"]");
			if(orderItemDAO.delete(orderItemDTO)) {
				System.out.println("[로그] controller.account.SignOutAction | orderPk:["+orderDatas.get(i).getOrderPk()+"] 의 주문 상세 내역 삭제 완료");
			}
			else {
				System.out.println("[로그] controller.account.SignOutAction | orderPk:["+orderDatas.get(i).getOrderPk()+"] 의 주문 상세 내역 삭제 실패");
			}
		}
		System.out.println("[로그] controller.account.SignOutAction | [상세내역 모두 삭제하기] - 종료");
		*/
		
		System.out.println("[로그] controller.account.SignOutAction | [ (5/7) accountPk에 대한 ORDERS 테이블 삭제 처리]");
	// 4-b. 주문 내역속 데이터들 모두 삭제 완료했으니 이제 주문 내역 모두 삭제하기
		OrderDTO orderDTO = new OrderDTO();
		OrderDAO orderDAO = new OrderDAO();
		orderDTO.setAccountPk(accountPk);
		orderDTO.setCondition("DELETE_ALL_ORDER_BY_ACCOUNT_PK");
		
		System.out.println("[로그] controller.account.SignOutAction | [orderDAO.delete(orderDTO)] - orderDTO:["+orderDTO+"]");
		if(orderDAO.delete(orderDTO)) {
			System.out.println("[로그] controller.account.SignOutAction | 주문 내역 삭제 완료");
		}
		else {
			System.out.println("[로그] controller.account.SignOutAction | 주문 내역 삭제 실패");
		}
		
		System.out.println("[로그] controller.account.SignOutAction | [ (6/7) accountPk에 대한 CART 테이블 삭제 처리]");
	// 5. 사용자PK의 모든 장바구니 삭제 
		CartDAO cartDAO = new CartDAO();
		CartDTO cartDTO = new CartDTO();
		cartDTO.setAccountPk(accountPk);
		cartDTO.setCondition("DELETE_ALL_CART_BY_ACCOUNT_PK");
		
		System.out.println("[로그] controller.account.SignOutAction | [cartDAO.delete(cartDTO)] - cartDTO:["+cartDTO+"]");
		if(cartDAO.delete(cartDTO)) {
			System.out.println("[로그] controller.account.SignOutAction | 장바구니 삭제 완료");
		}
		else {
			System.out.println("[로그] controller.account.SignOutAction | 장바구니 삭제 실패");
		}
		
		System.out.println("[로그] controller.account.SignOutAction | [ (7/7) accountPk에 대한 ADDRESS 테이블 삭제 처리]");
	// 6. 사용자PK의 모든 배송지 삭제
		AddressDAO addressDAO = new AddressDAO();
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setAccountPk(accountPk);
		addressDTO.setCondition("DELETE_ALL_ADDRESS_BY_ACCOUNT_PK");
		
		System.out.println("[로그] controller.account.SignOutAction | [addressDAO.delete(addressDTO)] - addressDTO:["+addressDTO+"]");
		if(addressDAO.delete(addressDTO)) {
			System.out.println("[로그] controller.account.SignOutAction | 사용자 배송지 삭제 완료");
		}
		else {
			System.out.println("[로그] controller.account.SignOutAction | 사용자 배송지 삭제 실패");
		}
		
		
		System.out.println("[로그] controller.account.SignOutAction | [session의 accountPk null로 바꾸기]");
		request.getSession().setAttribute("accountPk", null);
		
		System.out.println("[로그] controller.account.SignOutAction | [회원 탈퇴 로직 종료]");
		
		forward.setPath("mainPage.do");
		forward.setRedirect(true);
		
		System.out.println("[로그] controller.account.SignOutAction | [execute] - 끝");
		return forward;
	}

}
