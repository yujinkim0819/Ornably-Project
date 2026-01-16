package controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.AddressDAO;
import model.dto.AddressDTO;


// 삭제할 addressPk 받아서 삭제시켜주기
@WebServlet("/DeleteDeliveryAddressServlet")
public class DeleteDeliveryAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteDeliveryAddressServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.DeleteDeliveryAddressServlet | [doGet메서드] - 잘못된 전달방식 *고쳐주세요* ");
		this.doPost(request, response);
	}

	// addressPk 받아서 삭제 성공/실패에 따라 true/false 반환
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.DeleteDeliveryAddressServlet | [doPost메서드] - 시작");
		
		// 삭제할 배송지pk를 받아서 int타입으로 바꿔주기
		String stringAddressPk = request.getParameter("addressPk");
		
		System.out.println("[로그] controller.servlet.DeleteDeliveryAddressServlet | [doPost메서드] - stringAddressPk:["+stringAddressPk+"]");
		
		if(stringAddressPk==null || stringAddressPk.isBlank()) {
			response.getWriter().print(false);
			return;
		}
		
		Integer addressPk = Integer.parseInt(stringAddressPk);
		
		AddressDAO addressDAO = new AddressDAO();
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setAddressPk(addressPk);
		
		addressDTO.setCondition("SELECT_IS_DEFAULT_ADDRESS_BY_ADDRESS_PK");
		// 앞단에서 처리를 해주겠지만 만약 기본배송지가 넘어오게 되면 실패처리 해주기
		
		if(addressDAO.selectOne(addressDTO)!=null) {
			System.out.println("[로그] controller.servlet.DeleteDeliveryAddressServlet | [유효성 문제] - 기본배송지 삭제요청 들어옴");
			response.getWriter().print(false);
			return;
		}
		
		addressDTO.setAddressPk(addressPk);
		// 이외의 주소라면 그냥 삭제시켜주기 [condition이외의 인자는 위에서 설정한 값 그대로 사용(addressPk)]
		addressDTO.setCondition("DELETE_ADDRESS_BY_ADDRESS_PK");
		// 
		if(addressDAO.delete(addressDTO)) {
			System.out.println("[로그] controller.servlet.DeleteDeliveryAddressServlet | [doPost메서드] - 배송지 삭제 완료");
			response.getWriter().print(true);
		}
		else {
			System.out.println("[로그] controller.servlet.DeleteDeliveryAddressServlet | [doPost메서드] - 배송지 삭제 실패");
			response.getWriter().print(false);
		}
		return;
	}

}
