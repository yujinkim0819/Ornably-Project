package controller.payment;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.CartDAO;
import model.dao.ItemDAO;
import model.dao.OrderDAO;
import model.dao.OrderItemDAO;
import model.dto.CartDTO;
import model.dto.ItemDTO;
import model.dto.OrderDTO;
import model.dto.OrderItemDTO;

public class PaymentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.payment.PaymentAction | [execute] - 실행");
		ActionForward forward = new ActionForward();

		int accountPk = (Integer) request.getSession().getAttribute("accountPk");
		int addressPk = Integer.parseInt(request.getParameter("addressPk"));
		// 결제 진행 시
		// 1. 세션에 tempCartDTO가 존재하면 tempCartDTO[상품pk, 상품 가격, 상품 개수] 를 통해 결제진행 후
		// tempCartDTO를 null로 바꿔주기
		if (request.getSession().getAttribute("tempCartDTO") != null) {
			// 즉시 구매인 경우에는 tempCart로 결제 진행하기
			// 고객의 계좌에서 돈을 빼는 것은 적용하지 않음
			// 해야할 일
			// 1. 재고 모두 충분한지 확인하기 -> 없으면 없는 리스트 message로 보내주고 결제 취소하기
			CartDTO tempCartDTO = (CartDTO) request.getSession().getAttribute("tempCartDTO");
			System.out.println("[로그] controller.payment.PaymentAction | [즉시구매] - tempCartDTO:[" + tempCartDTO + "]");

			System.out.println("[로그] controller.payment.PaymentAction | [재고 확인하기]");
			ItemDAO itemDAO = new ItemDAO();
			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setItemPk(tempCartDTO.getItemPk());
			itemDTO.setItemCount(tempCartDTO.getCount());
			itemDTO.setCondition("ITEM_STOCK_ENOUGH");
			// 재고가 부족하다면
			System.out.println(
					"[로그] controller.payment.PaymentAction | [itemDAO.selectOne(itemDTO)] - itemDTO:[" + itemDTO + "]");
			if (itemDAO.selectOne(itemDTO) == null) {
				System.out.println("[로그] controller.payment.PaymentAction | [즉시구매] - 재고부족");

				request.setAttribute("message", "해당 상품의 재고가 부족합니다.");
				request.setAttribute("location", "ornamentDetailPage.do?itemPk=" + itemDTO.getItemPk());

				forward.setPath("message.jsp");
				forward.setRedirect(false);
				return forward;
			}
			System.out.println("[로그] controller.payment.PaymentAction | [즉시구매] - 재고충분, 재고 감소 처리 후 알림창 띄우기");
			itemDTO.setCondition("BUY_ITEM");
			// 재고 감소 시키기
			System.out.println(
					"[로그] controller.payment.PaymentAction | [itemDAO.update(itemDTO)] - itemDTO:[" + itemDTO + "]");
			if (!itemDAO.update(itemDTO)) {
				System.out.println("[로그] controller.payment.PaymentAction | [즉시구매] - 재고 감소 실패");
				request.setAttribute("message", "즉시구매 실패하였습니다. 관리자에게 문의해주세요");
				request.setAttribute("location", "ornamentDetailPage.do?itemPk=" + itemDTO.getItemPk());

				forward.setPath("message.jsp");
				forward.setRedirect(false);

			}
			System.out.println("[로그] controller.payment.PaymentAction | [즉시구매] - 재고 감소 성공");

			// 주문 내역 등록하기
			System.out.println("[로그] controller.payment.PaymentAction | [즉시구매] - 주문 내역 등록하기");
			OrderDAO orderDAO = new OrderDAO();
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setAccountPk(accountPk);
			orderDTO.setAddressPk(addressPk);
			orderDTO.setCondition("PREPARING");

			System.out.println("[로그] controller.payment.PaymentAction | [orderDAO.insert(orderDTO)] - orderDTO:["
					+ orderDTO + "]");
			if (!orderDAO.insert(orderDTO)) {
				System.out.println("[로그] controller.payment.PaymentAction | [주문 내역 등록 실패]");

				request.setAttribute("message", "즉시구매 성공(재고감소) 되었습니다. 주문내역 등록 실패하였습니다. 관리자에게 문의해주세요");
				request.setAttribute("location", "orderHistoryListPage.do");

				forward.setPath("message.jsp");
				forward.setRedirect(false);

			}
			System.out.println("[로그] controller.payment.PaymentAction | [주문 내역 등록 성공] - 주문 상세 등록");
			orderDTO.setCondition("SELECT_ONE_ORDER_PK");
			int orderPk = orderDAO.selectOne(orderDTO).getOrderPk();

			OrderItemDAO orderItemDAO = new OrderItemDAO();
			OrderItemDTO orderItemDTO = new OrderItemDTO();
			orderItemDTO.setOrderPk(orderPk);
			orderItemDTO.setItemPk(tempCartDTO.getItemPk());
			orderItemDTO.setOrderItemPrice(tempCartDTO.getItemPrice());
			orderItemDTO.setOrderItemQuantity(tempCartDTO.getCount());
			orderItemDTO.setCondition("INSERT_ORDERS_ITEM");

			System.out.println(
					"[로그] controller.payment.PaymentAction | [orderItemDAO.insert(orderItemDTO)] - orderItemDTO:["
							+ orderItemDTO + "]");
			if (!orderItemDAO.insert(orderItemDTO)) {
				request.setAttribute("message", "즉시 구매 성공하였습니다. 주문 상세 저장 실패");
				request.setAttribute("location", "orderHistoryListPage.do");

				forward.setPath("message.jsp");
				forward.setRedirect(false);
			}

			request.setAttribute("message", "즉시 구매 성공하였습니다. 주문 기록 저장 성공");
			request.setAttribute("location", "orderHistoryListPage.do");

			forward.setPath("message.jsp");
			forward.setRedirect(false);

			request.getSession().setAttribute("tempCartDTO", null);
			return forward;
		}

		// 2. 장바구니 db를 통해 결제 진행시켜주기
		System.out.println("[로그] controller.payment.PaymentAction | [장바구니 구매]");
		// 장바구니DTO 리스트[ cartPk, itemPk, count, itemName, itemImageUrl, itemPrice ]
		// 가져와서 하나씩 돌려보면서 재고 충분한지 전부 확인한 뒤에
		// 모두 충분하면 전부 재고 감소시키기
		System.out.println("[로그] controller.payment.PaymentAction | [장바구니 재고 가져오기]");
		CartDAO cartDAO = new CartDAO();
		CartDTO cartDTO = new CartDTO();
		cartDTO.setAccountPk(accountPk);
		cartDTO.setCondition("SELECT_ALL_ACCOUNT_CART");
		System.out.println(
				"[로그] controller.payment.PaymentAction | [cartDAO.selectAll(cartDTO)] - cartDTO:[" + cartDTO + "]");
		ArrayList<CartDTO> cartDatas = cartDAO.selectAll(cartDTO);
		// 이제 재고 전부 충분한지 확인하기
		ArrayList<String> lackItemNames = new ArrayList<>();
		ItemDAO itemDAO = new ItemDAO();
		ItemDTO itemDTO = new ItemDTO();

		System.out.println("[로그] controller.payment.PaymentAction | [아이템 재고 체크 시작]");
		for (CartDTO cart : cartDatas) {
			itemDTO.setItemPk(cart.getItemPk());
			itemDTO.setItemCount(cart.getCount());
			itemDTO.setCondition("ITEM_STOCK_ENOUGH");
			System.out.println("[로그] controller.payment.PaymentAction | [아이템 재고 체크] - itemDTO:[" + itemDTO + "]");
			if (itemDAO.selectOne(itemDTO) == null) {
				System.out.println("재고 부족함:[" + cart + "]");
				lackItemNames.add(cart.getItemName());
			}
		}
		System.out.println("[로그] controller.payment.PaymentAction | [아이템 재고 체크 종료]");
		System.out.println("[로그] controller.payment.PaymentAction | [부족한 상품] - lackItemNames:[" + lackItemNames + "]");
		// 없는 재고가 존재하면 결제 중단 후 메세지 보내기
		if (lackItemNames.size() > 0) {
			System.out.println("[로그] controller.payment.PaymentAction | [아이템 부족] - 알림 보내기");
			request.setAttribute("message", "상품 재고가 부족합니다.부족한 상품:" + lackItemNames);
			request.setAttribute("location", "cartPage.do");

			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}

		// 모두 재고가 충분하면 전체 재고 감소 명령을 model로 보내기
		System.out.println("[로그] controller.payment.PaymentAction | [장바구니에 대한 아이템 재고 감소]");
		itemDTO.setCondition("DECREASE_ITEM_STOCK_BY_CART");
		itemDTO.setAccountPk(accountPk);
		System.out.println(
				"[로그] controller.payment.PaymentAction | [itemDAO.update(itemDTO)] - itemDTO:[" + itemDTO + "]");
		if (!itemDAO.update(itemDTO)) {
			// 상품 재고 감소에 실패하였을 때
			System.out.println("[로그] controller.payment.PaymentAction | [장바구니 구매] - 재고 감소 실패");

			request.setAttribute("message", "상품 구매(재고감소)에 문제가 생겼습니다. 관리자에게 문의해주세요");
			request.setAttribute("location", "orderHistoryListPage.do");

			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}
		System.out.println("[로그] controller.payment.PaymentAction | [장바구니 구매] - 재고 감소 성공");
		System.out.println("[로그] controller.payment.PaymentAction | [구매 내역 생성 시작]");
		// 주문 내역 등록하기
		OrderDAO orderDAO = new OrderDAO();
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setAccountPk(accountPk);
		orderDTO.setAddressPk(addressPk);
		orderDTO.setCondition("PREPARING");
		System.out.println(
				"[로그] controller.payment.PaymentAction | [orderDAO.insert(orderDTO)] - orderDTO:[" + orderDTO + "]");
		if (!orderDAO.insert(orderDTO)) {
			// 상품 구매 기록 실패
			System.out.println("[로그] controller.payment.PaymentAction | [구매내역 기록 실패]");

			request.setAttribute("message", "상품 구매(재고감소) 성공. 구매내역 기록 실패. 관리자에게 문의해주세요");
			request.setAttribute("location", "orderHistoryListPage.do");

			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}

		orderDTO.setCondition("SELECT_ONE_ORDER_PK");
		int orderPk = orderDAO.selectOne(orderDTO).getOrderPk();

		OrderItemDAO orderItemDAO = new OrderItemDAO();
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setOrderPk(orderPk);
		orderItemDTO.setCondition("INSERT_ORDERS_ITEM");
		System.out.println("[로그] controller.payment.PaymentAction | [주문 상세 삽입] - 시작");
		ArrayList<String> recordFailedItem = new ArrayList<>();
		// 장바구니DTO 리스트[ cartPk, itemPk, count, itemName, itemImageUrl, itemPrice ]
		for (CartDTO cart : cartDatas) {
			System.out.println(
					"[로그] controller.payment.PaymentAction | [주문 상세 삽입] - itemName:[" + cart.getItemName() + "]");
			orderItemDTO.setItemPk(cart.getItemPk());
			orderItemDTO.setOrderItemPrice(cart.getItemPrice());
			orderItemDTO.setOrderItemQuantity(cart.getCount());
			System.out.println(
					"[로그] controller.payment.PaymentAction | [orderItemDAO.insert(orderItemDTO)] - orderItemDTO:["
							+ orderItemDTO + "");
			if (!orderItemDAO.insert(orderItemDTO)) {
				recordFailedItem.add(cart.getItemName());
			}
		}
		if (recordFailedItem.size() > 0) {
			// 상품 구매 기록 실패
			System.out.println("[로그] controller.payment.PaymentAction | [1개 이상의 주문 상세가 기록되지 않음]");

			request.setAttribute("message", "다음 상품이 기록되지 않았습니다.["+recordFailedItem+"] 관리자에게 문의해주세요");
			request.setAttribute("location", "orderHistoryListPage.do");

			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}
		System.out.println("[로그] controller.payment.PaymentAction | [주문 상세 삽입] - 성공");

		// 재고 감소 후 account의 Cart테이블 모두 삭제하기
		System.out.println("[로그] controller.payment.PaymentAction | [장바구니 구매] - 재고 감소 성공");
		cartDTO.setCondition("DELETE_ALL_CART_BY_ACCOUNT_PK");
		if (!cartDAO.delete(cartDTO)) {
			// 상품 구매 기록 실패
			System.out.println("[로그] controller.payment.PaymentAction | [장바구니 삭제 실패]");

			request.setAttribute("message", "구매 성공. 장바구니가 비워지지 않았습니다. 관리자에게 문의해주세요");
			request.setAttribute("location", "orderHistoryListPage.do");

			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}
		request.setAttribute("message", "상품이 정상적으로 구매되었습니다.");

		request.setAttribute("location", "orderHistoryListPage.do");
		forward.setPath("message.jsp");
		forward.setRedirect(false);
		return forward;
	}
}