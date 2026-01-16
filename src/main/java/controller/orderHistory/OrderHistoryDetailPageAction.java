package controller.orderHistory;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.OrderItemDAO;
import model.dto.OrderItemDTO;

// 주문 상세 목록 페이지 로드
// [받아오는 데이터]
// 주문PK + 세션의 accountPk
// [보내줄 데이터]
// [상품 사진url, 상품명, 상품 당시 가격, 상품개수, 리뷰 여부]
public class OrderHistoryDetailPageAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		// 상품상세 페이지 로드 로직 시작 로그
		System.out.println("[로그] controller.mainPage.OrderHistoryDetailPageAction | [execute] - 시작");

		ActionForward forward = new ActionForward();

		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");

		System.out.println(
				"[로그] controller.mainPage.OrderHistoryDetailPageAction | [execute] - accountPk:[" + accountPk + "]");

		// 비로그인 상태면 마이페이지도 들어오지 못하겠지만 일단 검수 한번 하기
		// isBlank() -> 빈 문자열 또는 공백 문자열 방어
		if (accountPk == null) {
			request.setAttribute("location", "/login.do");
			request.setAttribute("message", "로그인 후 이용해주세요");

			// 사용자에게 알림 후 로그인 페이지로 이동
			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}
		// 로그인 상태면

		System.out.println("[로그] controller.mainPage.OrderHistoryDetailPageAction | [주문상품 데이터 모두 가져오기] - 시작");

		String stringOrderPk = request.getParameter("orderPk");
		System.out.println("[로그] controller.mainPage.OrderHistoryDetailPageAction | [주문상품 데이터 모두 가져오기] - orderPk:["
				+ stringOrderPk + "]");
		if (stringOrderPk == null || stringOrderPk.trim().isBlank()) {
			forward.setPath("orderHistoryItemList.jsp");
			forward.setRedirect(false);
			return forward;
			/*request.setAttribute("message.jsp", "주문 내역이 등록되지 않았거나 소실되었습니다.");
			request.setAttribute("location", "orderHistoryList.jsp");

			forward.setPath("message.jsp");
			return forward;*/
		}

		int orderPk = Integer.parseInt(stringOrderPk);

		// 주문속 각각의 상품 정보 모두 꺼내오기
		OrderItemDAO orderItemDAO = new OrderItemDAO();
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setOrderPk(orderPk);
		orderItemDTO.setAccountPk(accountPk);
		orderItemDTO.setCondition("SELECT_ALL_ORDERS_ITEM");
			// -> 상품 사진url, 상품명, 상품 당시 가격, 상품개수, 리뷰 여부
		System.out.println(
				"[로그] controller.mainPage.OrderHistoryDetailPageAction | [orderItemDAO.selectAll(orderItemDTO)] - orderItemDTO:["
						+ orderItemDTO + "]");
		// 0. orderPk -> orderItemDTO[orderItemPk, itemImageUrl, itemName,
		// orderItemPrice, orderItemQuantity, isReviewed]
		ArrayList<OrderItemDTO> orderItemDatas = orderItemDAO.selectAll(orderItemDTO);

		/*
		 * // 1. 각각의 상품에서 [상품pk -> 상품 사진url, 상품 이름] ITEM테이블에서 가져오고 ArrayList<ItemDTO>
		 * itemDatas = new ArrayList<>();
		 * 
		 * // 2. 각각의 상품에 대한 [사용자pk, 상품pk -> 리뷰여부]를 반환 ArrayList<ReviewDTO> reviewDatas =
		 * new ArrayList<>();
		 * 
		 * System.out.
		 * println("[로그] controller.mainPage.OrderHistoryDetailPageAction | [주문별 상세정보 가져오기] - 시작"
		 * ); for(int i=0;i<orderItemDatas.size();i++) { System.out.
		 * println("[로그] controller.mainPage.OrderHistoryDetailPageAction | [주문별 상세정보 가져오기] - ("
		 * +(i+1)+"/"+orderItemDatas.size()+")");
		 * 
		 * // 1. 상품 정보 ItemDAO itemDAO = new ItemDAO(); ItemDTO itemDTO = new ItemDTO();
		 * itemDTO.setCondition("SELECT_ITEM_IMAGE_URL_AND_NAME");
		 * itemDTO.setItemPk(orderItemDatas.get(i).getItemPk()); // 파라미터로 줄 데이터에
		 * [상품사진url, 상품이름]추가하기 System.out.
		 * println("[로그] controller.mainPage.OrderHistoryDetailPageAction | [itemDAO.selectOne(itemDTO)] - itemDTO:["
		 * +itemDTO+"]"); itemDatas.add(itemDAO.selectOne(itemDTO));
		 * 
		 * // 2. 리뷰 작성했는지 확인해주기 ReviewDAO reviewDAO = new ReviewDAO(); ReviewDTO
		 * reviewDTO = new ReviewDTO();
		 * reviewDTO.setCondition("SELECT_ACCOUNT_WRITE_REVIEW");
		 * reviewDTO.setAccountPk(accountPk);
		 * 
		 * reviewDTO.setItemPk(orderItemDatas.get(i).getItemPk()); System.out.
		 * println("[로그] controller.mainPage.OrderHistoryDetailPageAction | [reviewDAO.selectOne(reviewDTO)] - reviewDTO:["
		 * +reviewDTO+"]"); // 파라미터로 줄 데이터에 [reviewExists : boolean] 넣어주기
		 * reviewDatas.add(reviewDAO.selectOne(reviewDTO)); } System.out.
		 * println("[로그] controller.mainPage.OrderHistoryDetailPageAction | [주문별 상세정보 가져오기] - 시작"
		 * );
		 * 
		 * // request 인자로 담아주기 request.setAttribute("itemDatas", itemDatas);
		 * request.setAttribute("reviewDatas", reviewDatas);
		 * request.setAttribute("orderItemDatas", orderItemDatas);
		 */
		
		//new Gson().toJson(orderItemDatas)
		request.setAttribute("orderItemDatas", orderItemDatas);
		// 경로와 방식 정해주기
		forward.setPath("orderHistoryItemList.jsp");
		forward.setRedirect(false);
		System.out.println("[로그] controller.mainPage.OrderHistoryDetailPageAction | [주문상품 데이터 모두 가져오기] - 끝");

		return forward;
	}

}
