package controller.kakaopay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.CartDAO;
import model.dao.ItemDAO;
import model.dao.OrderDAO;
import model.dto.CartDTO;
import model.dto.ItemDTO;
import model.dto.OrderDTO;

// 결제 준비 (ready API 호출)
// 카카오페이 API 호출 (결제 준비)
// 성공적인 응답을 받으면 TID와 함께 리다이렉트 URL 반환
// 클라이언트에게 결제 페이지로 리다이렉션
//// 필요한 정보: Admin Key, CID, 결제 금액, 주문번호, 상품명 등


public class KakaoPayReadyAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
    	// 카카오페이 API 호출 코드
        // 결제 준비 요청
        // 응답에서 TID 받아서 리다이렉트 URL 생성
    	System.out.println("[로그] KakaoPayReadyAction 시작");
    	HttpSession session = request.getSession();
    	ActionForward forward = new ActionForward();
    	
    	try {
    		// 세션과 요청에서 정보 가져오기
    		int accountPk = (Integer) session.getAttribute("accountPk"); 
			int addressPk = Integer.parseInt(request.getParameter("addressPk"));
			int totalAmount = Integer.parseInt(request.getParameter("totalAmount"));
            System.out.println("accountPk" + accountPk);
            System.out.println("addressPk" + addressPk);
            System.out.println("totalAmount" + totalAmount);
            
            
            if (totalAmount <= 0) {
                throw new RuntimeException("결제 금액 오류");
            }
            ItemDAO itemDAO = new ItemDAO();
            CartDAO cartDAO = new CartDAO();
            
            // 즉시 구매 여부
            CartDTO tempCartDTO = (CartDTO) session.getAttribute("tempCartDTO");
            
            // 상품명 설정
            String itemName = "";
            
            // =========================
            // 즉시 구매
            // =========================
            if (tempCartDTO != null) {
	            int itemPk = tempCartDTO.getItemPk();
	            int count  = tempCartDTO.getCount();

	            // 1. 재고 체크
	            ItemDTO itemDTO = new ItemDTO();
	            itemDTO.setItemPk(itemPk);
	            itemDTO.setItemCount(count);
	            itemDTO.setCondition("ITEM_STOCK_ENOUGH");
	
	            ItemDTO itemData = itemDAO.selectOne(itemDTO);
	            if (itemDAO.selectOne(itemDTO) == null) {
	            	System.out.println("[로그] 즉시 구매 재고 부족");
	            	
	            	request.setAttribute("message", "해당 상품의 재고가 부족합니다.");
					request.setAttribute("location", "ornamentDetailPage.do?itemPk=" + itemDTO.getItemPk());
					forward.setPath("message.jsp");
					forward.setRedirect(false);
					return forward;
	            }
	            System.out.println("[로그] 즉시 구매 재고 감소 성공");
	            itemName = itemData.getItemName(); // itemName

	            // 2. 재고 임시 감소
	            itemDTO.setCondition("BUY_ITEM");
	            if (!itemDAO.update(itemDTO)) {
	                throw new RuntimeException("재고 선점 실패");
	            }

	            // 3. 선점 정보 세션 저장 (복구용)
	            session.setAttribute("reservedItemPk", itemPk);
	            session.setAttribute("reservedCount", count);
	        }
            // =========================
            // 장바구니 구매
            // =========================
            else {            	
	            CartDTO cartDTO = new CartDTO();
	            cartDTO.setAccountPk(accountPk);
	            cartDTO.setCondition("SELECT_ALL_ACCOUNT_CART");
	
	            // 장바구니 상품
	            ArrayList<CartDTO> cartDatas = cartDAO.selectAll(cartDTO);
	            
	            // 장바구니에 재고가 부족한 상품
	            ArrayList<String> outOfStockItems = new ArrayList<>();
	            
	            // 1. 재고 체크
	            for (CartDTO cart : cartDatas) {
	            	ItemDTO itemDTO = new ItemDTO();
	                itemDTO.setItemPk(cart.getItemPk());
	                itemDTO.setItemCount(cart.getCount());
	                itemDTO.setCondition("ITEM_STOCK_ENOUGH");
	
	                if (itemDAO.selectOne(itemDTO) == null) {
	                    // 재고 부족이면 리스트에 상품명 추가
	                    outOfStockItems.add(cart.getItemName()); // CartDTO에 itemName이 있어야 함
	                    continue; // 재고 부족 상품은 스킵
	                }
	            }
	            
	            // 2. 재고 부족 체크
	            if (!outOfStockItems.isEmpty()) {
	                itemName = outOfStockItems.get(0);
	                if (outOfStockItems.size() > 1) {
	                    itemName += " 외 " + (outOfStockItems.size() - 1) + "건";
	                }
	                request.setAttribute("outOfStockItems", outOfStockItems);
	                forward.setPath("paymentStockFail.jsp"); // 재고 부족 모달 표시용 JSP
	                forward.setRedirect(false);
	                return forward;
	            } 	            
	            
	           // itemName 설정
	           if (!cartDatas.isEmpty()) {
	            	// 장바구니 상품이 1개면 그냥 상품명
	                itemName = cartDatas.get(0).getItemName();

	                // 장바구니 상품이 2개 이상이면 "첫번째 상품명 외 N건"
	                if (cartDatas.size() > 1) {
                        itemName += " 외 " + (cartDatas.size() - 1) + "건";
                    }
	            }
	            System.out.println("[로그] 출력 상품명 확인 : " + itemName);
	            
	            // 3. 재고 충분하면 감소
	            for (CartDTO cart : cartDatas) {
	                ItemDTO itemDTO = new ItemDTO();
	                itemDTO.setItemPk(cart.getItemPk());
	                itemDTO.setItemCount(cart.getCount());
	                itemDTO.setCondition("BUY_ITEM");
	                itemDAO.update(itemDTO);
	                
	                // 재고 감소 실패
	                if (!itemDAO.update(itemDTO)) {
	                	throw new RuntimeException("재고 감소 실패");
	                }
	            }
	            // 복구용 저장
	            session.setAttribute("reservedCartDatas", cartDatas);	           
            }
            
            // =========================
            // 주문 생성
            // =========================
    		OrderDTO orderDTO = new OrderDTO();
    		orderDTO.setAccountPk(accountPk);
    		orderDTO.setAddressPk(addressPk);
    		orderDTO.setCondition("PREPARING");

    		OrderDAO orderDAO = new OrderDAO();
    		orderDAO.insert(orderDTO);

    		// 방금 생성된 orderPk 가져오기
    		orderDTO.setCondition("SELECT_ONE_ORDER_PK");
    		int orderPk = orderDAO.selectOne(orderDTO).getOrderPk();
    		session.setAttribute("orderPk", orderPk); // 세션 저장

    		System.out.println("[로그] orderPk 값 확인 : " + orderPk );
    		System.out.println("[로그] CID 값 확인 : " + KakaoPayConfig.CID );
    		System.out.println("[로그] SECRET_KEY_DEV 값 확인 : " + KakaoPayConfig.SECRET_KEY_DEV);
    		
    		
    		
    		
    		// 카카오페이 API 준비
            JsonObject body = new JsonObject();
            body.addProperty("cid", KakaoPayConfig.CID);
            body.addProperty("partner_order_id", orderPk);
            body.addProperty("partner_user_id", accountPk);
            body.addProperty("item_name", itemName);
            body.addProperty("quantity", 1);
            body.addProperty("total_amount", totalAmount);
            body.addProperty("tax_free_amount", 0);
            body.addProperty("approval_url", KakaoPayConfig.SUCCESS_URL);
            body.addProperty("cancel_url", KakaoPayConfig.CANCEL_URL);
            body.addProperty("fail_url", KakaoPayConfig.FAIL_URL);


            // Open API 호출
            URL url = new URL("https://open-api.kakaopay.com/online/v1/payment/ready");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "SECRET_KEY " + KakaoPayConfig.SECRET_KEY_DEV);
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setDoOutput(true);
            
            OutputStream os = conn.getOutputStream();
            os.write(body.toString().getBytes("UTF-8"));
            os.flush();
            os.close();
            
            // 응답 처리
            BufferedReader br;
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);  // 줄바꿈 없이 연결
            }
            br.close();
            System.out.println("응답 JSON: " + sb.toString());

            JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();

            if (json.has("tid") && json.has("next_redirect_pc_url")) {
                String tid = json.get("tid").getAsString();	// tid 발급
                String redirectUrl = json.get("next_redirect_pc_url").getAsString();
                session.setAttribute("tid", tid);			// 세션에 저장

                forward.setPath(redirectUrl);
                forward.setRedirect(true);
                
                System.out.println("<<로그>> tid : " + tid);
                System.out.println("<<로그>> redirectUrl : " + redirectUrl);
            } else {
                System.out.println("<<로그>> Ready API 호출 실패: " + json.toString());
                forward.setPath(KakaoPayConfig.BASE_URL + "/kakaoPayFail.do");
                forward.setRedirect(true);
            }
       } catch (Exception e) {
            e.printStackTrace();
            forward.setPath(KakaoPayConfig.FAIL_URL);
            forward.setRedirect(true);
        }
        return forward;
    }
}


