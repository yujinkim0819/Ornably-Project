package controller.kakaopay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;

public class KakaoPayFailAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
        ActionForward forward = new ActionForward();
        // 실패 페이지로 이동
        forward.setPath("/paymentFail.jsp"); // JSP 경로에 맞게 수정
        forward.setRedirect(false);
        return forward;
    }
}