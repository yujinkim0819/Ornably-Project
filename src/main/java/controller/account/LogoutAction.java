package controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;

public class LogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.account.LogoutAction | [로그아웃 버튼 눌림]");
		
		
		// [로그아웃 로직]
		// 센션의 accountPk 비워주기
		request.getSession().setAttribute("accountPk", null);
		
		// 메인 페이지로 이동하기
		ActionForward forward = new ActionForward();
		forward.setPath("mainPage.do");
		forward.setRedirect(true);
		System.out.println("[로그] controller.account.LogoutAction | [execute] - 종료");
		return forward;
	}

}
