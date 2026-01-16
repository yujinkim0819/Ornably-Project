package controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;

// 회원탈퇴 페이지 로드
// [줄 데이터]
// 탈퇴페이지를 불러올 때에는 특별히 필요한 데이터가 없다.
public class SignOutPageAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.account.SignOutPageAction | [회원탈퇴 페이지 로드]");
		ActionForward forward = new ActionForward();
		forward.setPath("signOut.jsp");
		forward.setRedirect(true);
		return forward;
	}
}
