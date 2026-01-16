package controller.cart;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.CartDAO;
import model.dao.ItemDAO;
import model.dto.CartDTO;
import model.dto.ItemDTO;

public class CartPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// [장바구니 페이지 로드]
		System.out.println("[로그] controller.cart.CartPageAction | execute 시작");
		
		// 세션에서 사용자 PK 꺼내오기
		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		ActionForward forward = new ActionForward();
		
		// 로그인 여부 확인
		if (accountPk == null) { // 로그인이 안 된 상태라면
			request.setAttribute("location", "login.do");
			request.setAttribute("message", "로그인 후 이용해주세요");

			// 로그인 페이지로 이동
			forward.setPath("message.jsp");
			forward.setRedirect(false);
		}
		else {
			System.out.println("[로그] controller.account.CartPageAction : 장바구니 목록 가져오기 시작");
			
			// 장바구니 상품 목록 확인
			CartDAO cartDAO = new CartDAO();
			CartDTO cartDTO = new CartDTO();
			
			// 사용자PK 기준으로 장바구니 목록 받아오기
			cartDTO.setAccountPk(accountPk);
			cartDTO.setCondition("SELECT_ALL_ACCOUNT_CART");
			
			System.out.println("[로그] controller.account.CartPageAction : cartDTO " + cartDTO);
			// cartDTO에 CART 테이블에 있는 cartPK, itemPK, cartCount 가져오기
			ArrayList<CartDTO> cartDatas = cartDAO.selectAll(cartDTO);
			System.out.println("[로그] controller.account.CartPageAction : 장바구니 목록 가져오기 끝");
			
			
			
			// 장바구니 총 금액 
			System.out.println("[로그] controller.account.CartPageAction : 장바구니 총금액 가져오기");
			cartDTO.setCondition("SELECT_ONE_ORDER_ITEMS_TOTAL_MONEY");
			CartDTO totalDTO = cartDAO.selectOne(cartDTO); // 총금액 가져옴
			
			
			// 장바구니가 비어있을 경우
			int totalPrice = 0;
			if (totalDTO != null) {
			    totalPrice = totalDTO.getTotalAmount();
			}
			
			// JSP 전달
			request.setAttribute("cartDatas", cartDatas);
			request.setAttribute("totalPrice", totalPrice);
			
			
			// 장바구니 페이지로 이동
			forward.setPath("cart.jsp");
			forward.setRedirect(false);
		}
		return forward;
	}

}