package controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.AddressDAO;
import model.dto.AddressDTO;

@WebServlet("/ChangeDefaultDeliveryAddressServlet")
public class ChangeDefaultDeliveryAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChangeDefaultDeliveryAddressServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(
				"[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [doGet메서드] - 잘못된 전달방식 *고쳐주세요* ");
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [doPost메서드] - 시작");
		// view에서 넘겨준 기본값으로 설정 할 배송지pk
		// 이미 기본배송지로 되어있는걸 잘못 넘겨주어도 그냥 로직 실행되게 만들어주기
		
		String stringAddressPk= request.getParameter("addressPk");
		
		System.out.println("[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [doPost메서드] - addressPk:["+stringAddressPk+"]");
		
		if (stringAddressPk == null || stringAddressPk.isBlank()) {
			// 배송지pk가 잘못왔으면 바로 false 응답
			response.getWriter().print(false);
			return;
		}
		
		Integer addressPk = Integer.parseInt(stringAddressPk);
		
		// 사용자 pk가져오기
		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		if (accountPk == null) {
			System.out.println("[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [accountPk] - null값 들어왔음");
			// 로그인 안 되어있으면 바로 false 응답
			response.getWriter().print(false);
			return;
		}
		
		// 기본 주소 없애주기
		AddressDAO addressDAO = new AddressDAO();
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setAccountPk(accountPk);
		
		addressDTO.setCondition("UPDATE_DEFAULT_ADDRESS_REMOVE");
		
		System.out.println("[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [기본 주소 없애주기] - addressDTO:["+addressDTO+"]");
		if (addressDAO.update(addressDTO)) {
			System.out.println("[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [기본 주소 없애주기] - 성공");
		}
		else {
			System.out.println("[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [기본 주소 없애주기] - 실패");
			response.getWriter().print(false);
			return;
		}
		
		
		
		// 기본 주소 설정해주기
		addressDTO.setAddressPk(addressPk);
		
		addressDTO.setCondition("UPDATE_DEFAULT_ADDRESS");
		
		System.out.println("[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [addressDAO.update(addressDTO) 실행] - addressDTO:["+addressDTO+"]");
		if (addressDAO.update(addressDTO)) {
			System.out.println("[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [addressDAO.update(addressDTO) 실행] - 성공");
			response.getWriter().print(true);
		}
		else {
			System.out.println("[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [addressDAO.update(addressDTO) 실행] - 실패");
			response.getWriter().print(false);
		}
		System.out.println("[로그] controller.servlet.ChangeDefaultDeliveryAddressServlet | [doPost메서드] - 끝");
	}
}
