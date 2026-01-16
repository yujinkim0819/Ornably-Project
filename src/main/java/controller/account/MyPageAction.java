package controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.AccountDAO;
import model.dto.AccountDTO;

public class MyPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.account.MyPageAction | [마이페이지 로드] - 시작");
		/* [개발중 테스트를 위한 임시 코드]
		 * System.out.
		 * println("[로그] controller.account.MyPageAction | [execute] - 임시 코드 적용 / 17~21라인을 지워주고 밑의 주석을 풀어주세요!"
		 * ); ActionForward forward = new ActionForward();
		 * forward.setPath("myPage.jsp"); forward.setRedirect(false); return forward;
		 */

		// [받아오는 데이터]
		// 사용자 센션
		// [넣어 주는 데이터]
		// 사용자 정보[이름, 이메일, 전화번호]

		// 사용자 pk 세션에서 뽑기
		// 세션에서 사용자 정보 가져오기
		ActionForward forward = new ActionForward();

		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");

		System.out.println("[로그] controller.account.MyPageAction | [execute] - accountPk:[" + accountPk + "]");
		// 로그인이 되어있지 않은 상태면
		if (accountPk == null) {
			System.out.println("[이상] controller.account.MyPageAction | [accountPk가 null인데 마이페이지 접근 시도]");
			request.setAttribute("location", "loginPage.do");
			request.setAttribute("message", "로그인 후 이용해주세요");

			// 사용자에게 알림 후 로그인 페이지로 이동
			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}
		// 로그인이 되어있는 상태면

		// dao에서 이름, 이메일, 전화번호 꺼내오기
		AccountDAO accountDAO = new AccountDAO();
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAccountPk(accountPk);
		accountDTO.setCondition("SELECT_MY_PAGE");

		System.out.println("[로그] controller.account.MyPageAction | [accountDAO.selectOne(accountDTO)] - accountDTO:["
				+ accountDTO + "]");
		accountDTO = accountDAO.selectOne(accountDTO);
		System.out.println(
				"[로그] controller.account.MyPageAction | [accountDAO.selectOne(accountDTO)] - 결과:[" + accountDTO + "]");
		request.setAttribute("accountData", accountDTO);

		// 마이페이지로 이동
		forward.setPath("myPage.jsp");
		forward.setRedirect(false);

		System.out.println("[로그] controller.account.MyPageAction | [execute] - 종료");
		return forward;
	}
}
