package controller.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.AccountDAO;
import model.dao.AddressDAO;
import model.dto.AccountDTO;
import model.dto.AddressDTO;

public class JoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		// [ 회원가입 수행 로직 ]
		AccountDTO accountDTO = new AccountDTO();
		AccountDAO accountDAO = new AccountDAO();

		// joinForm에서 사용자 정보 받아오기
		// 사용자 : 이름, 아이디, 비번, 이메일, 전화번호
		// 기본배송지 정보 : 배송지명, 우편번호, 기본주소, 상세주소
		accountDTO.setAccountId(request.getParameter("accountId"));
		accountDTO.setAccountPassword(request.getParameter("accountPassword"));
		accountDTO.setAccountName(request.getParameter("accountName"));
		accountDTO.setAccountEmail(request.getParameter("accountEmail"));
		accountDTO.setAccountPhone(request.getParameter("accountPhone"));
		accountDTO.setCondition("ACCOUNT_JOIN");
		
		int accountPk = -999;

		if (accountDAO.insert(accountDTO)) {
			System.out.println("[로그] controller.account.JoinAction | [회원 추가 성공]");
			accountDTO.setCondition("SELECT_ACCOUNT_PK_BY_ACCOUNT_ID");
			accountPk = accountDAO.selectOne(accountDTO).getAccountPk();
		} else {
			System.out.println("[로그] controller.account.JoinAction | [회원 추가 실패]");
		}

		ActionForward forward = new ActionForward();
		if (accountPk != -999) {

			AddressDTO addressDTO = new AddressDTO();
			AddressDAO addressDAO = new AddressDAO();

			// 주소 정보 DTO에 저장
			addressDTO.setAccountPk(accountPk);
			addressDTO.setAddressName(request.getParameter("addressName"));
			addressDTO.setDefaultAddress(true); // 회원가입 시 주소는 기본 배송지
			addressDTO.setPostalCode(request.getParameter("postalCode"));
			addressDTO.setDetailAddress(request.getParameter("address1"));
			addressDTO.setRegionAddress(request.getParameter("address2"));
			
			System.out.println("[로그] controller.ornamentShop.JoinAction | 회원주소 추가 addressDTO:["+addressDTO+"]");
			// M에게 기본배송지 정보 insert 요청
			if (addressDAO.insert(addressDTO)) {
				// 회원가입 성공
				System.out.println("[로그] controller.ornamentShop.JoinAction | 회원주소 추가 성공");
				forward.setPath("message.jsp");
				request.setAttribute("location", "mainPage.do");
				request.setAttribute("message", "회원가입 성공");
				
			} else {
				// 회원가입 실패
				System.out.println("[로그] controller.ornamentShop.JoinAction | 회원주소 추가 실패 ");
				forward.setPath("message.jsp");
				request.setAttribute("location", "mainPage.do");
				request.setAttribute("message", "회원가입 성공, 주소 생성 실패. 시스템 오류입니다");
			}
		} else {
			// 회원가입 실패
			System.out.println("[로그] controller.ornamentShop.JoinAction | 회원가입 실패 accountPk : " + accountPk);
			System.out.println("회원테이블에는 insert 되었을 것입니다 ▶ 삭제 처리 필요");
			forward.setPath("message.jsp");
			request.setAttribute("location", "mainPage.do");
			request.setAttribute("message", "회원가입 실패. 시스템 오류입니다");
		}
		forward.setRedirect(false);
		return forward;
	}
}


// Connection의 .commit() 과 .rollback() 를 통해 트랜잭션 처리를 하려 했으나 
// 프로젝트 설계의 구조 한계로 일단 보류 처리 (최프;Spring 때 적용할 예정)
/*
 * @Override public ActionForward execute(HttpServletRequest request,
 * HttpServletResponse response) { Connection conn = JDBCUtil.connect(); //
 * Connection 직접 얻기 ActionForward forward = new ActionForward(); int accountPk =
 * 0;
 * 
 * try { conn.setAutoCommit(false); // 트랜잭션 시작
 * 
 * // [ 회원가입 수행 로직 ] AccountDTO accountDTO = new AccountDTO(); AccountDAO
 * accountDAO = new AccountDAO(); // joinForm에서 사용자 정보 받아오기 // 사용자 : 이름, 아이디,
 * 비번, 이메일, 전화번호 // 기본배송지 정보 : 배송지명, 우편번호, 기본주소, 상세주소
 * accountDTO.setAccountId(request.getParameter("accountId"));
 * accountDTO.setAccountPassword(request.getParameter("accountPassword"));
 * accountDTO.setAccountName(request.getParameter("accountName"));
 * accountDTO.setAccountEmail(request.getParameter("accountEmail"));
 * accountDTO.setAccountPhone(request.getParameter("accountPhone"));
 * 
 * // M에게 회원 insert ▶ 성공 시 회원 PK 리턴 accountPk = accountDAO.insert(conn,
 * accountDTO); // conn 넘김
 * 
 * if(accountPk == 0) { conn.rollback(); request.setAttribute("location",
 * "joinPage.do"); forward.setRedirect(false); return forward; }
 * 
 * 
 * // 주소 등록 AddressDTO addressDTO = new AddressDTO(); AddressDAO addressDAO =
 * new AddressDAO();
 * 
 * // 주소 정보 DTO에 저장 addressDTO.setAccountPk(accountPk);
 * addressDTO.setAddressName(request.getParameter("addressName"));
 * addressDTO.setAddressIsDefault(true); // 회원가입 시 주소는 기본 배송지
 * addressDTO.setAddressPostalCode(request.getParameter("postalCode"));
 * addressDTO.setAddressLine1(request.getParameter("address1"));
 * addressDTO.setAddressLine2(request.getParameter("address2"));
 * 
 * if(!addressDAO.insert(addressDTO)) { conn.rollback();
 * request.setAttribute("location", "joinPage.do"); forward.setRedirect(false);
 * return forward; } conn.commit(); // 둘 다 성공하면 commit
 * 
 * request.setAttribute("location", "mainPage.do"); forward.setRedirect(false);
 * return forward;
 * 
 * } catch(Exception e) { try { conn.rollback(); } catch(Exception ignore) {
 * 
 * } // 회원가입 실패 System.out.
 * println("[로그] controller.ornamentShop.JoinAction | 회원가입 실패 accountPk : " +
 * accountPk);
 * 
 * request.setAttribute("location", "joinPage.do"); forward.setRedirect(false);
 * return forward;
 * 
 * } finally { JDBCUtil.disconnect(conn); } }
 */
