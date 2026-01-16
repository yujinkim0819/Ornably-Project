package controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.AccountDAO;
import model.dto.AccountDTO;

@WebServlet("/CheckEmailDuplicateServlet")
public class CheckEmailDuplicateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckEmailDuplicateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// GET 요청도 POST와 동일 처리
		System.out.println("[로그] controller.servlet.CheckEmailDuplicateServlet | [doGet메서드]");
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.CheckEmailDuplicateServlet | [doPost메서드]");

		// 중복 검사를 할 Email 받기
		String accountEmail = request.getParameter("accountEmail");
		
		
		// 입력이 없을 때
		if(accountEmail == null || accountEmail.trim().isEmpty()) {
			System.out.println("[로그] controller.servlet.CheckEmailDuplicateServlet | 이메일이 입력되지 않았습니다");
			response.getWriter().print("accountEmailEmpty");
			return;
		}
		
		AccountDAO accountDAO = new AccountDAO();
		AccountDTO accountDTO = new AccountDTO();
		
		accountDTO.setAccountEmail(accountEmail);
		accountDTO.setCondition("SELECT_CHECK_LOGIN_EMAIL");
		System.out.println("[로그] controller.servlet.CheckEmailDuplicateServlet | [이메일 중복 확인] - accountDTO:["+accountDTO+"]");
		// 해당 이메일이 존재하는지 여부 확인
		AccountDTO chkEmail = accountDAO.selectOne(accountDTO);
		
		if(chkEmail != null) {
			System.out.println("[로그] controller.servlet.CheckEmailDuplicateServlet |이미 사용중인 이메일입니다");
			response.getWriter().print("accountEmailDuplicate"); // 중복
		} else {
			System.out.println("[로그] controller.servlet.CheckEmailDuplicateServlet | 사용 가능한 이메일입니다");
			response.getWriter().print("accountEmailUnique"); // 사용 가능
		}
	}
}
