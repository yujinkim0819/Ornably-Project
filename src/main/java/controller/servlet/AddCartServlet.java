package controller.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.dao.CartDAO;
import model.dto.CartDTO;

// [ 장바구니 담기 ] ▶ 필요한가? AddCartAction으로 대체 불가능?? 
@WebServlet("/AddCartServlet")
public class AddCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddCartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// GET 요청도 POST와 동일 처리
		System.out.println("[로그] controller.servlet.AddCartServlet | [doGet메서드]");
		this.doPost(request, response);
	}
		
		// pid 파라미터 읽기
		// DB에서 상품 정보 조회
		// 세션에서 cart 리스트 꺼내기
		// 없으면 새로 생성
		// 장바구니에 상품 추가하기
		// 장바구니에 상품이 추가되었습니다 알림

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.AddCartServlet | [doPost메서드]");
		
		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		
		
		// 이게 null이 들어와버리네
		String stringItemPk = request.getParameter("itemPk");
		String stringCount = request.getParameter("count");
		
		System.out.println("stringItemPk=" + stringItemPk);
		System.out.println("stringCount=" + stringCount);
		
		Integer itemPk = Integer.parseInt(stringItemPk);
		Integer count = Integer.parseInt(stringCount);
		
		System.out.println("[로그] controller.servlet.AddCartServlet | [doPost메서드] - itemPk:["+itemPk+"]");
		System.out.println("[로그] controller.servlet.AddCartServlet | [doPost메서드] - count:["+count+"]");
		
		CartDTO cartDTO = new CartDTO();
		CartDAO cartDAO = new CartDAO();
		
		cartDTO.setAccountPk(accountPk);
		cartDTO.setItemPk(itemPk);
		cartDTO.setCount(count);
		cartDTO.setCondition("ADD_CART_ITEM");
		
		if(cartDAO.insert(cartDTO)) {
			System.out.println("[로그] controller.servlet.AddCartServlet | 장바구니 추가 성공");
			response.getWriter().print(true); // 성공
		}
		else {
			System.out.println("[로그] controller.servlet.AddCartServlet | 장바구니 추가 실패");
			response.getWriter().print(false); // 실패
		}

	}

}
