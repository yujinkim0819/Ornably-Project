package controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;

public class JoinPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// [ 회원가입 페이지 로드]
		System.out.println("[로그] controller.account.JoinPageAction | [회원가입 페이지 로드]");
		ActionForward forward = new ActionForward();
		forward.setPath("joinForm.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
