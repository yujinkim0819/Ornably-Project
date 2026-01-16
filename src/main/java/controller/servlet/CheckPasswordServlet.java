package controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.AccountDAO;
import model.dto.AccountDTO;

@WebServlet("/CheckPasswordServlet")
public class CheckPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CheckPasswordServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.CheckPasswordServlet | [doGet메서드] - 잘못된 전달방식 *고쳐주세요* ");
		this.doPost(request, response);
		// 잘못된 전달방식
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 비동기로 [ 사용자PK : 비밀번호 ] 매치 되는지 확인하고 true/false로 데이터 보내주기
		
		System.out.println("[로그] controller.servlet.CheckPasswordServlet | [doPost실행] - 시작");
		System.out.println("밑에 코드 4개 지워주세요");
		if(request.getParameter("inputPassword")!=null && request.getParameter("inputPassword").equals("1234")) {
			
			response.getWriter().print(true);
			return;
		}
		
		
		// 사용자 pk가져오기
		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		if (accountPk == null) {
			// 로그인 안 되어있으면 바로 false 응답
			response.getWriter().print(false);
			return;
		}

		// 비밀번호 파라미터 가져오기
		String inputPassword = request.getParameter("inputPassword");
		if (inputPassword == null || inputPassword.isBlank()) {
			// 비밀번호가 잘못되었으면 오류처리
			response.getWriter().print(false);
			return;
		}

		// request객체로부터 password파라미터 받은다음에 ACCOUNT 테이블에 존재하는지 확인
		AccountDAO accountDAO = new AccountDAO();
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAccountPk(accountPk);
		// inputPassword는 String타입
		accountDTO.setAccountPassword(request.getParameter("inputPassword"));
		accountDTO.setCondition("SELECT_CHECK_PASSWORD_BY_PK");
		
		System.out.println("[로그] controller.servlet.CheckPasswordServlet | [accountDAO.selectOne(accountDTO) 실행] - accountDTO:["+accountDTO+"]");
		if (accountDAO.selectOne(accountDTO) != null) {
			// [사용자pk:비밀번호] 데이터 존재함
			System.out.println("[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [addressDAO.update(addressDTO) 실행] - accountDTO!=null");
			response.getWriter().print(true);
		}
		else {
			// [사용자pk:비밀번호] 데이터 존재안함
			System.out.println("[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [addressDAO.update(addressDTO) 실행] - accountDTO==null");
			response.getWriter().print(false);
		}
		System.out.println("[로그] controller.servlet.CheckPasswordServlet | [doPost실행] - 끝");
	}
}
