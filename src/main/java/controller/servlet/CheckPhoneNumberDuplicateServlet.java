package controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.AccountDAO;
import model.dto.AccountDTO;

@WebServlet("/CheckPhoneNumberDuplicateServlet")
public class CheckPhoneNumberDuplicateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckPhoneNumberDuplicateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// GET 요청도 POST와 동일 처리
		System.out.println("[로그] controller.servlet.CheckPhoneNumberDuplicateServlet | doGet메서드 실행");
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.CheckPhoneNumberDuplicateServlet | doPost 메서드 실행");

		// 중복 검사를 할 Phone 받아오기
		String accountPhone = request.getParameter("accountPhone");
		
		// 입력이 없을 때
		if(accountPhone == null || accountPhone.trim().isEmpty()) {
			System.out.println("[로그] controller.servlet.CheckPhoneNumberDuplicateServlet | 폰번호가 입력되지 않았습니다");
			response.getWriter().print("accountPhoneEmpty");
        	return;
		}
		
		AccountDAO accountDAO = new AccountDAO();
		AccountDTO accountDTO = new AccountDTO();
		
		accountDTO.setAccountPhone(accountPhone);
		accountDTO.setCondition("SELECT_CHECK_LOGIN_PHONE");
		
		
		// 해당 폰번호 존재 여부 확인 메서드
		AccountDTO chkPhone = accountDAO.selectOne(accountDTO);
		
		
		if(chkPhone != null) {
			System.out.println("[로그] controller.servlet.CheckPhoneNumberDuplicateServlet | 이미 사용중인 폰번호입니다");
			response.getWriter().print("accountPhoneDuplicate"); // 중복
		} else {
			System.out.println("[로그] controller.servlet.CheckPhoneNumberDuplicateServlet | 사용 가능한 폰번호입니다");
			response.getWriter().print("accountPhoneUnique"); // 사용 가능
		}
	}
}
