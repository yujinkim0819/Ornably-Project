package controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;

public class SignInPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.account.SignInPageAction | [회원가입 페이지 로드]");
		
		// 회원가입 페이지 로드
		ActionForward forward = new ActionForward();
		forward.setPath("signInPage.jsp");
		forward.setRedirect(true);
		return forward;
	}

}
