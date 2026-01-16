package controller.kakaopay;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.CartDAO;
import model.dao.ItemDAO;
import model.dao.OrderItemDAO;
import model.dao.OrderDAO;
import model.dto.CartDTO;
import model.dto.ItemDTO;
import model.dto.OrderDTO;
import model.dto.OrderItemDTO;

public class KakaoPaySuccessAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("[로그] KakaoPaySuccessAction 시작");
        ActionForward forward = new ActionForward();
        HttpSession session = request.getSession();
        
        String tid = null;
        Integer totalAmount = null;
        
        try {
        	// 결제 승인
            String pgToken = request.getParameter("pg_token");
            tid = (String) session.getAttribute("tid");
            totalAmount = (Integer) session.getAttribute("totalAmount");
            
            if (pgToken == null || tid == null) {
                throw new RuntimeException("결제 승인 정보 없음");
            }
            
            int accountPk = (Integer) session.getAttribute("accountPk");
            int orderPk = (Integer) session.getAttribute("orderPk");
            
            System.out.println("[로그] 세션 값 확인");
            System.out.println("accountPk : " + accountPk);
            System.out.println("orderPk : " + orderPk);
            System.out.println("tid : " + tid);
            System.out.println("-----------------------------");

            boolean approveSuccess = KakaoPayApproveUtil.approve(
                tid, pgToken, orderPk, accountPk
            );

        	if (!approveSuccess) {
        	    throw new RuntimeException("카카오페이 결제 승인 실패");
        	}
            
        	// 즉시 구매 정보, 없으면 장바구니 구매
            CartDTO tempCartDTO = (CartDTO) session.getAttribute("tempCartDTO");
            CartDAO cartDAO = new CartDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            
            // =========================
            // 1️. 즉시 구매
            // =========================
            if (tempCartDTO != null) {

                // 주문 상세 등록
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setOrderPk(orderPk);
                orderItemDTO.setItemPk(tempCartDTO.getItemPk());
                orderItemDTO.setOrderItemPrice(tempCartDTO.getItemPrice());
                orderItemDTO.setOrderItemQuantity(tempCartDTO.getCount());
                orderItemDTO.setCondition("INSERT_ORDERS_ITEM");
                
                if (!orderItemDAO.insert(orderItemDTO)) {
                	throw new RuntimeException("주문 상세 등록 실패");
                }
            } else {
                // =========================
                // 2️. 장바구니 구매
                // =========================
                CartDTO cartDTO = new CartDTO();
                cartDTO.setAccountPk(accountPk);
                cartDTO.setCondition("SELECT_ALL_ACCOUNT_CART");
                ArrayList<CartDTO> cartDatas = cartDAO.selectAll(cartDTO);

                // 주문 상세 등록
                for (CartDTO cart : cartDatas) {
                    OrderItemDTO orderItemDTO = new OrderItemDTO();
                    orderItemDTO.setOrderPk(orderPk);
                    orderItemDTO.setItemPk(cart.getItemPk());
                    orderItemDTO.setOrderItemPrice(cart.getItemPrice());
                    orderItemDTO.setOrderItemQuantity(cart.getCount());
                    orderItemDTO.setCondition("INSERT_ORDERS_ITEM");
                }

                // 장바구니 비우기
                cartDTO.setCondition("DELETE_ALL_CART_BY_ACCOUNT_PK");
                cartDAO.delete(cartDTO);
            }
            // 세션 정리
            session.removeAttribute("tid");
            session.removeAttribute("orderPk");
            session.removeAttribute("tempCartDTO");
            session.removeAttribute("reservedItemPk");
            session.removeAttribute("reservedCount");


            forward.setPath("/paymentSuccess.jsp"); // 결제 성공 페이지
            forward.setRedirect(false);

        } catch (Exception e) {
            // 선점 재고 복구
            Integer itemPk = (Integer) session.getAttribute("reservedItemPk");
            Integer count  = (Integer) session.getAttribute("reservedCount");

            if (itemPk != null && count != null) {
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setItemPk(itemPk);
                itemDTO.setItemCount(count);
                itemDTO.setCondition("ROLLBACK_ITEM_STOCK");

                ItemDAO itemDAO = new ItemDAO();
                itemDAO.update(itemDTO);
            }            
            
            e.printStackTrace();
            forward.setPath("/paymentFail.jsp"); // 결제 실패 페이지
            forward.setRedirect(false);
        }
        return forward;
    }
}
