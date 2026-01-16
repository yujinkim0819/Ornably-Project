package controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.AccountDAO;
import model.dto.AccountDTO;

public class LoginPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.account.LoginPageAction | [로그인 페이지 로드]");
		// [로그인 페이지 로드]
		ActionForward forward = new ActionForward();

		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		
		if (accountPk != null) { 
			System.out.println("[이상] controller.account.LoginPageAction | [로그인 되어있는데 로그인 페이지 접근하려함]");
			// 로그인이 된 상태라면
			// 마이페이지로 이동
			forward.setPath("myPage.do");
			forward.setRedirect(false);
			return forward;
		}
		// 로그인이 안 된 상태라면
		forward.setPath("login.jsp");
		forward.setRedirect(true);

		
		return forward;
	}
}