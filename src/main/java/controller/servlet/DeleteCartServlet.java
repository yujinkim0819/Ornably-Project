package controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CartDAO;
import model.dto.CartDTO;

// 상품 삭제
@WebServlet("/DeleteCartServlet")
public class DeleteCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteCartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.DeleteCartServlet | [doGet메서드]");
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.DeleteCartServlet | [doPost메서드]");
		
		// 삭제할 상품이 들어 있는 cartPK 받기
		Integer cartPk = Integer.parseInt(request.getParameter("cartPk"));
		
		CartDAO cartDAO = new CartDAO();
		CartDTO cartDTO = new CartDTO();
		cartDTO.setCartPk(cartPk);
		
		// 상품 삭제하기
		cartDTO.setCondition("DELETE_BY_CART_PK"); 
		
		if(cartDAO.delete(cartDTO)) {
			System.out.println("[로그] controller.servlet.DeleteCartServlet | 상품 삭제");
			response.getWriter().print(true); // 성공
		}
		else {
			System.out.println("[로그] controller.servlet.DeleteCartServlet | 상품 삭제 실패");
			response.getWriter().print(false); // 실패
		}
		return;
	}

}
