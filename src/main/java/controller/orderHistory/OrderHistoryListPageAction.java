package controller.orderHistory;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.OrderDAO;
import model.dto.OrderDTO;

// 주문 목록 페이지 로드
// [받아오는 데이터]
// 세션에서 사용자PK받아오기
// [주는 데이터]
// 주문정보들 [PK, 날짜, 배송상태, 대표 주문상품명, 총금액(계산된 값)]
public class OrderHistoryListPageAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("[로그] controller.mainPage.OrderHistoryListPageAction | [execute] - 시작");
		// 세션에서 사용자 정보 가져오기
		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		System.out.println("[로그] controller.mainPage.OrderHistoryListPageAction | [execute] - accountPk:["+accountPk+"]");
		ActionForward forward = new ActionForward();

		// 비로그인 상태면 마이페이지도 들어오지 못하겠지만 일단 검수 한번 하기
		// isBlank() -> 빈 문자열 또는 공백 문자	열 방어
		if (accountPk == null) {
			request.setAttribute("location", "/login.do");
			request.setAttribute("message", "로그인 후 이용해주세요");

			// 사용자에게 알림 후 로그인 페이지로 이동
			forward.setPath("message.jsp");
			forward.setRedirect(false);
		} 
		// 로그인 되어있는 상태면
		else {
			System.out.println("[로그] controller.mainPage.OrderHistoryListPageAction | [주문내역 목록 가져오기] - 시작");
			OrderDAO orderDAO = new OrderDAO();
			OrderDTO orderDTO = new OrderDTO();
			
			// DTO에 사용자PK 설정해줘서 사용자PK가 있는 모든 주문 ArrayList받아오기
			// accountPk -> [orderPk, orderDate, orderStatus, orderSignatureItemName, orderTotalAmount]
			orderDTO.setAccountPk(accountPk);
			orderDTO.setCondition("SELECT_ALL_ACCOUNT_PK_ORDERS");

			System.out.println("[로그] controller.mainPage.OrderHistoryListPageAction | [orderDAO.selectAll(orderDTO)] - orderDTO:["+orderDTO+"]");
			// orderDTO에 ORDERS테이블로부터 [accountPk -> orderPk, orderDate, orderStatus, orderSignatureItemName, orderTotalAmount] 뽑아오기
			ArrayList<OrderDTO> orderDatas = orderDAO.selectAll(orderDTO);
			
			/*
			// ArrayList속 OrderDTO속에 
			// a. 대표상품(상품개수x상품가격이 제일 높은것)
			// b. 총 금액 넣어주기
			for(int i=0;i<orderDatas.size();i++) {
				System.out.println("[로그] controller.mainPage.OrderHistoryListPageAction | [주문별 대표상품/총금액 가져오기] - ("+(i+1)+"/"+orderDatas.size()+")");
				
				// a. 주문 가격 넣어줄 변수 지정
				int totalAmount = 0;
				
				OrderItemDAO orderItemDAO = new OrderItemDAO();
				
				// 주문 정보 속 모든 상세 상품 리스트 가져오기 [ orderPk -> itemPk, orderItemQuantity, orderItemPrice ]
				OrderItemDTO orderItemDTO = new OrderItemDTO();
				orderItemDTO.setOrderPk(orderDatas.get(i).getOrderPk());
				orderItemDTO.setCondition("SELECT_ALL_ORDER_PK_ITEMS");
				System.out.println("[로그] controller.mainPage.OrderHistoryListPageAction | [orderItemDAO.selectAll(orderItemDTO)] - orderItemDTO:["+orderItemDTO+"]");
				ArrayList<OrderItemDTO> orderItemDatas =  orderItemDAO.selectAll(orderItemDTO);
				
				///// 1. 가져온 주문 상품들 리스트 돌면서 총금액 계산해서 orderDatas데이터들에 각각 넣어주고
				///// 2. 대표상품 잡아서 넣어주기
				// b. 대표상품 넣을 변수 만들어주기
				String signatureItemName;
				// 상품에 쓴 금액 저장할 변수 선언
				// 상품 가격은 0 이상이기에 0으로 지정
				int signatureItemPrice=0;
				// 상품 하나씩 돌리면서 
				System.out.println("[로그] controller.mainPage.OrderHistoryListPageAction | [주문내역 목록 대표상품, 총금액 추출] - 시작");
				for(OrderItemDTO data : orderItemDatas) {
					// 상품 하나에 쓴 총 금액 계산
					int amount = data.getOrderItemQuantity() * data.getOrderItemPrice();
					
					// 만약 이게 최고 금액이라면 [a] 대표상품 지정
					if(amount > signatureItemPrice) {
						// 이름 대표상품으로 설정해주기
						ItemDAO itemDAO = new ItemDAO();
						ItemDTO itemDTO = new ItemDTO();
						itemDTO.setItemPk = data.getItemPk();
						itemDTO.setCondition("SELECT_ITEM_NAME");
						
						System.out.println("[로그] controller.mainPage.OrderHistoryListPageAction | [itemDAO.selectOne(itemDTO)] - itemDTO:["+itemDTO+"]");
						signatureItemName = itemDAO.selectOne(itemDTO).getItemName();
						
						// 대표 상품 가격 바꿔주기
						signatureItemPrice = amount;
					}
					
					// 총 금액에 해당 상품 추가해주기
					totalAmount += amount;
				}
				System.out.println("[로그] controller.mainPage.OrderHistoryListPageAction | [주문내역 목록 대표상품, 총금액 추출] - 끝 [대표상품 명:"+signatureItemName+" ,총금액:"+totalAmount+"]");
				// a. 대표상품 이름 넣기
				orderDatas.get(i).setOrderSignatureItemName(signatureItemName);
				// b. 총 금액 넣기
				orderDatas.get(i).setOrderTotalAmount(totalAmount);
				// 주문 목록 1개 설정 완료
			}
			
			// 모든 상품의 [PK, 날짜, 배송상태, 대표 주문상품명, 총금액] 목록 orderDatas 완성
			// 이제 request에 담아서 포워딩으로 보내주기
			//
			 */
			request.setAttribute("orderDatas", orderDatas);
			forward.setPath("orderHistoryList.jsp");
			forward.setRedirect(false);
			System.out.println("[로그] controller.mainPage.OrderHistoryListPageAction | [주문내역 목록 가져오기] - 끝");
		}
		return forward;
	}
}
