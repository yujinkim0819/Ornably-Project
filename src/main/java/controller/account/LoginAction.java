package controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.AccountDAO;
import model.dto.AccountDTO;

public class LoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.account.LoginAction | [로그인 시도 로직]");
		ActionForward forward = new ActionForward();
		
		// [ 로그인 작업 수행 ]
		// 아이디, 비밀번호 입력
		AccountDAO accountDAO = new AccountDAO();
		AccountDTO accountDTO = new AccountDTO();

		// 로그인 조건
		accountDTO.setCondition("LOGIN");

		// View에서 파라미터 받기
		accountDTO.setAccountId(request.getParameter("userInputId"));
		accountDTO.setAccountPassword(request.getParameter("userInputPassword"));

		System.out.println("[로그] controller.account.LoginAction | [회원 존재 확인] - accountDTO:[" + accountDTO + "]");
		// db한테 로그인 요청하기 : select One
		AccountDTO data = accountDAO.selectOne(accountDTO);
		
		// 로그인 성공 : 메인 페이지로 이동
		if (data != null) {
			
			System.out.println(
					"[로그] controller.account.LoginAction | [로그인 성공] - accountPk:[" + data.getAccountPk() + "]");
			HttpSession session = request.getSession();
			session.setAttribute("accountPk", data.getAccountPk());
			request.setAttribute("message", "환영합니다, "+data.getAccountName()+"님");
			request.setAttribute("location", "mainPage.do");

			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}
		// 로그인 실패 : 다시 로그인 페이지로 이동
		System.out.println("[로그] controller.account.LoginAction | 로그인 실패 data 값이 null입니다");

		request.setAttribute("message", "로그인 실패 - 아이디나 비번을 다시 확인해주세요.");
		request.setAttribute("location", "loginPage.do");

		forward.setPath("message.jsp");
		forward.setRedirect(false);

		return forward;
	}

}
