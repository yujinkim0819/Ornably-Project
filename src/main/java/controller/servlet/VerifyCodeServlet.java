package controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/VerifyCodeServlet")
public class VerifyCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public VerifyCodeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.VerifyCodeServlet | [doGet] 잘못된 요청 doPost를 이용해주세요");
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String inputCode = request.getParameter("inputCode");
	    HttpSession session = request.getSession();
	    
	    String serverCode = (String) session.getAttribute("authCode");

	    if (serverCode != null && serverCode.equals(inputCode)) {
	        session.removeAttribute("authCode"); // 성공 시 세션 비우기
	        response.getWriter().write("verified");
	    } else {
	        response.getWriter().write("failed");
	    }
	}

}
