package controller.common;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ActionFactory factory;
	
    public FrontController() {
        super();
        this.factory = new ActionFactory();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 프로젝트 명 (컨택스트 경토)
		String contextPath = request.getContextPath();
		// 사용자가 보낸 url
		String url = request.getRequestURI();
		// 명령어 추출
		String command = url.substring(contextPath.length());
		
		System.out.println("[로그] command : " + command);
		
		// 사용자가 보낸 요청 수행할 객체 꺼내기
		Action action = factory.getAction(command);
		
		// 사용자가 보낸 요청 수행
		ActionForward forward = action.execute(request, response);
		
		if(forward.isRedirect()) { // 응답 보내기
			response.sendRedirect(forward.getPath());
		}
		else { // 페이지 변경
			request.getRequestDispatcher(forward.getPath()).forward(request, response);
		}
	}
}
