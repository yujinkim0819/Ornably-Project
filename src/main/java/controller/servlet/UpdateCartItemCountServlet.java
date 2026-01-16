package controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CartDAO;
import model.dto.CartDTO;

@WebServlet("/UpdateCartItemCountServlet")
public class UpdateCartItemCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateCartItemCountServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.UpdateCartItemCountServlet | [doGet] - 잘못된 요청 방식 **POST방식 요청**");
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.UpdateCartItemCountServlet | [doPost] - 시작");
		
		String stringCartPk = request.getParameter("cartPk");
		String stringNewCount = request.getParameter("newCount");
		
		System.out.println("[로그] controller.servlet.UpdateCartItemCountServlet | [데이터 받기] - cartPk:["+stringCartPk+"] newCount:["+stringNewCount+"]");
		
		int cartPk = Integer.parseInt(stringCartPk);
		int newCount = Integer.parseInt(stringNewCount);
		
		CartDAO cartDAO = new CartDAO();
		CartDTO cartDTO = new CartDTO();
		cartDTO.setCartPk(cartPk);
		cartDTO.setNewCount(newCount);
		cartDTO.setCondition("UPDATE_CART_ITEM_COUNT");
		
		System.out.println("[로그] controller.servlet.UpdateCartItemCountServlet | [cartDAO.update(cartDTO)] - cartDTO:["+cartDTO+"]");
		if(cartDAO.update(cartDTO)) {
			System.out.println("[로그] controller.servlet.UpdateCartItemCountServlet | [장바구니 아이템 개수 변경] - 성공");
			response.getWriter().print(true);
		}
		else {
			response.getWriter().print(false);
			System.out.println("[로그] controller.servlet.UpdateCartItemCountServlet | [장바구니 아이템 개수 변경] - 실패");
		}
	}
}
