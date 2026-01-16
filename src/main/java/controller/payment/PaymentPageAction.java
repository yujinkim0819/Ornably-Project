package controller.payment;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.AddressDAO;
import model.dao.CartDAO;
import model.dto.AddressDTO;
import model.dto.CartDTO;

public class PaymentPageAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// [ 종 결제 페이지 로드 ]
		System.out.println("[로그] controller.payment.PaymentPageAction | execute 시작");
		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		System.out.println("[로그] controller.payment.PaymentPageAction | accountPk : " + accountPk);
		ActionForward forward = new ActionForward();

		// 로그인 여부 확인
		if (accountPk == null) {
			request.setAttribute("location", "login.do");
			request.setAttribute("message", "로그인 후 이용해주세요");

			// 사용자에게 알림 후 로그인 페이지로 이동
			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}

		// 기본 배송지 조회
		AddressDAO addressDAO = new AddressDAO();
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setCondition("SELECT_ALL_ADDRESS_BY_ACCOUNT_PK");
		addressDTO.setAccountPk(accountPk);
		
		
		System.out.println("[로그] controller.payment.PaymentPageAction | [addressDAO.selectAll(addressDTO)] - addressDTO:["+addressDTO+"]");
		ArrayList<AddressDTO> addressDatas = addressDAO.selectAll(addressDTO); // DTO에 배송지 정보 포함
		
		request.setAttribute("addressDatas", new Gson().toJson(addressDatas));
		
		// 즉시구매인지 먼저 확인하기
		
		String condition = request.getParameter("condition");
		
		// 즉시 구매인 경우 세션에
		// tempCartDTO[상품pk, 상품 가격, 상품 개수] 저장해주기
		// 출력할 totalAmount도 함께 보내주기
		// 아닌경우에는 평범하게 장바구니 db를 사용해야하므로 총 가격만 request객체에 담아 페이지로 전송해주기
		if (condition != null && condition.equals("buyNow")) {
			System.out.println("[로그] controller.payment.PaymentPageAction | [바로구매하기]");
			CartDTO cartDTO = new CartDTO();

			String itemPk = request.getParameter("itemPk");
			String itemPrice = request.getParameter("itemPrice");
			String itemCount = request.getParameter("itemCount");
			System.out.println("[로그] controller.payment.PaymentPageAction | 센션에 임시 tempCart에 넣기 - itemPk:[" + itemPk
					+ "] itemPrice:[" + itemPrice + "] itemCount:[" + itemCount + "]");
			cartDTO.setItemPk(Integer.parseInt(itemPk));
			cartDTO.setCount(Integer.parseInt(itemCount));
			cartDTO.setItemPrice(Integer.parseInt(itemPrice));
			request.getSession().setAttribute("tempCartDTO", cartDTO);
			request.setAttribute("totalAmount", Integer.parseInt(itemCount)*Integer.parseInt(itemPrice));

			forward.setPath("payment.jsp");
			forward.setRedirect(false);
			System.out.println("[로그] controller.payment.PaymentPageAction | 바로구매 로직 끝");
			
			
			return forward;
		}
		System.out.println("[로그] controller.payment.PaymentPageAction | 총금액 가져오기");
		CartDAO cartDAO = new CartDAO();
		CartDTO cartDTO = new CartDTO();
		cartDTO.setAccountPk(accountPk);

		// 총 주문 금액
		cartDTO.setCondition("SELECT_ONE_ORDER_ITEMS_TOTAL_MONEY");
		int totalAmount = cartDAO.selectOne(cartDTO).getTotalAmount();
		System.out.println(
				"[로그] controller.payment.PaymentPageAction | orderDAO.selectAll(orderDTO) 총금액 : " + totalAmount);

		// DTO에 담아 JSP로 전달
		request.setAttribute("totalAmount", totalAmount); // 주문 총 가격
		

		forward.setPath("payment.jsp");
		forward.setRedirect(false);
		System.out.println("[로그] controller.payment.PaymentPageAction | 총금액 가져오기 끝");

		return forward;
	}
}
// 세션 저장, 
// 상세보기 -> 즉시 구매 -> buyNow -> patment에서 session에 buynow 상태 저장 -> 행 하나 짜리 DTO 저장 -> total amount 계산 -> 프론트에서 출력
// 결제 페이지에서는 배송지, 총 금액 저장
// 결제하기 버튼 -> payMentAction에서 buyNOW 검사 -> 있으면 결제 결제 진행
// 없으면 장바구니에 있는 상품 구매