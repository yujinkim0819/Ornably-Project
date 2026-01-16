package controller.kakaopay;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.ItemDAO;
import model.dto.ItemDTO;

public class KakaoPayCancelAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession();

    	// 세션에 넣어둔 값 꺼내기
    	Integer itemPk = (Integer) session.getAttribute("itemPk");
    	Integer count  = (Integer) session.getAttribute("count");
        ActionForward forward = new ActionForward();
        
        // 재고 복구
        if (itemPk != null && count != null) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setItemPk(itemPk);
            itemDTO.setItemCount(count);
            itemDTO.setCondition("ROLLBACK_ITEM_STOCK");

            ItemDAO itemDAO = new ItemDAO();
            itemDAO.update(itemDTO);
            System.out.println("[로그] KakaoPayCancelAction 재고 복구 완료");
        }
        
        // 세션 정리
        session.removeAttribute("itemPk");
        session.removeAttribute("count");
        System.out.println("[로그] KakaoPayCancelAction 세션 정리 완료");
        
        // 취소 페이지로 이동
        forward.setPath("/paymentCancel.jsp"); // JSP 경로에 맞게 수정
        forward.setRedirect(false);
        return forward;
    }
}
