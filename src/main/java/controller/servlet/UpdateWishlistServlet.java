package controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.WishlistDAO;
import model.dto.WishlistDTO;

@WebServlet("/UpdateWishlistServlet")
public class UpdateWishlistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateWishlistServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.UpdateWishlistServlet | [doGet메서드] - 잘못된 전달방식 *고쳐주세요* ");
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.UpdateWishlistServlet | [좋아요 버튼 눌림 로직] - 시작");
		
		
		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		System.out.println("[로그] controller.servlet.UpdateWishlistServlet | [doPost메서드] - accountPk:["+accountPk+"]");
		if(accountPk==null) {
			response.getWriter().print("needToLogin");
			return;
		}
		
		String stringItemPk = request.getParameter("itemPk");
		System.out.println("[로그] controller.servlet.UpdateWishlistServlet | [doPost메서드] - itemPk:["+stringItemPk+"]");
		if(stringItemPk==null || stringItemPk.isBlank()) {
			response.getWriter().print("itemPkMissing");
			return;
		}
		Integer itemPk = Integer.parseInt(stringItemPk);
		
		WishlistDAO wishlistDAO = new WishlistDAO();
		WishlistDTO wishlistDTO = new WishlistDTO();
		wishlistDTO.setAccountPk(accountPk);
		wishlistDTO.setItemPk(itemPk);
		wishlistDTO.setCondition("SELECT_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK");
		
		System.out.println("[로그] controller.servlet.UpdateWishlistServlet | [wishlistDAO.selectOne(wishlistDTO)] - wishlistDTO:["+wishlistDTO+"]");
		if(wishlistDAO.selectOne(wishlistDTO)==null) {
			// 만약 사용자가 해당 상품의 좋아요를 누르지 않았었다면
			wishlistDTO.setCondition("INSERT_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK");
			
			System.out.println("[로그] controller.servlet.UpdateWishlistServlet | [wishlistDAO.insert(wishlistDTO)] - wishlistDTO:["+wishlistDTO+"]");
			if(wishlistDAO.insert(wishlistDTO)) {
				// 성공적으로 좋아요가 눌렸으면
				System.out.println("[로그] controller.servlet.UpdateWishlistServlet | [wishlistDAO.insert(wishlistDTO)] - 좋아요 활성화 성공");
				response.getWriter().print(true);
			}
			else {
				// 성공적으로 좋아요가 누르는데 실패했다면
				System.out.println("[로그] controller.servlet.UpdateWishlistServlet | [wishlistDAO.insert(wishlistDTO)] - 좋아요 활성화 실패");
				response.getWriter().print(false);
			}
		}
		else {
			// 사용자가 해당 상품의 좋아요를 눌렀었다면
			wishlistDTO.setCondition("DELETE_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK");
			
			System.out.println("[로그] controller.servlet.UpdateWishlistServlet | [wishlistDAO.delete(wishlistDTO)] - wishlistDTO:["+wishlistDTO+"]");
			if(wishlistDAO.delete(wishlistDTO)) {
				// 성공적으로 좋아요가 비활성화 됐다면
				System.out.println("[로그] controller.servlet.UpdateWishlistServlet | [wishlistDAO.delete(wishlistDTO)] - 좋아요 비활성화 성공");
				response.getWriter().print(true);
			}
			else {
				// 성공적으로 좋아요가 비활성화 되는데 실패했다면
				System.out.println("[로그] controller.servlet.UpdateWishlistServlet | [wishlistDAO.delete(wishlistDTO)] - 좋아요 비활성화 실패");
				response.getWriter().print(false);
			}
		}
		System.out.println("[로그] controller.servlet.UpdateWishlistServlet | [좋아요 버튼 눌림 로직] - 종료");
		return;
	}

}
