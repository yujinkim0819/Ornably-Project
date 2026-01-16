package controller.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.AccountDAO;
import model.dto.AccountDTO;

// 아이디 중복 확인
@WebServlet("/CheckIdDuplicateServlet")
public class CheckIdDuplicateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CheckIdDuplicateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.CheckIdDuplicateServlet | [doGet메서드]");
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.CheckIdDuplicateServlet | [doPost메서드]");
		
		// 중복 검사를 할 id를 받기
        String accountId = request.getParameter("accountId");
        
        // 입력이 없을 때
        if (accountId == null || accountId.isEmpty()) {
        	System.out.println("[로그] controller.servlet.CheckIdDuplicateServlet | 아이디가 입력되지 않았습니다");
        	response.getWriter().print("accountIdEmpty");
        	return;
        }
        
        AccountDAO accountDAO = new AccountDAO();
        AccountDTO accountDTO = new AccountDTO();
        
        accountDTO.setAccountId(accountId);
        accountDTO.setCondition("SELECT_CHECK_LOGIN_ID");
        
        
        
        // 해당 아이디가 존재하는지 확인
        AccountDTO chkId = accountDAO.selectOne(accountDTO);

        if (chkId != null) {
        	System.out.println("[로그] controller.servlet.CheckIdDuplicateServlet | 이미 사용중인 아이디입니다");
        	response.getWriter().print("accountIdDuplicate"); // 중복
        } else {
        	System.out.println("[로그] controller.servlet.CheckIdDuplicateServlet | 사용 가능한 아이디입니다");
        	response.getWriter().print("accountIdUnique"); // 사용 가능
        }
        return;
	}
}
