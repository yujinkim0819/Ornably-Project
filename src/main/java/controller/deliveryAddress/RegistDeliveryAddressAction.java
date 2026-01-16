package controller.deliveryAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.AddressDAO;
import model.dto.AddressDTO;

public class RegistDeliveryAddressAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("controller.deliveryAddress.RegistDeliveryAddressAction | [배송지 등록 로직]");
		// [ 배송지 등록하기 로직 ]
		// 현재 로그인한 회원 PK 세션에서 가져오기
		HttpSession session = request.getSession();
		
		Integer accountPk = (Integer) session.getAttribute("accountPk");

		ActionForward forward = new ActionForward();

		if (accountPk == null) {
			// 로그인 X ▶ 로그인 페이지로 이동
			request.setAttribute("location", "loginPage.do");
			forward.setRedirect(false);
			return forward;
		}

		// 파라미터 받기
		String addressName = request.getParameter("addressName"); // 배송지 명
		String addressPostalCode = request.getParameter("postalCode"); // 우편번호
		String addressLine1 = request.getParameter("regionAddress"); // 기본주소
		String addressLine2 = request.getParameter("detailAddress"); // 상세주소
		
		AddressDAO addressDAO = new AddressDAO();
		AddressDTO addressDTO = new AddressDTO();
		
		addressDTO.setAccountPk(accountPk); // 회원PK, 현재 로그인한 사람의 장바구니
		addressDTO.setAddressName(addressName); // 배송지명
		addressDTO.setPostalCode(addressPostalCode); // 우편번호
		addressDTO.setRegionAddress(addressLine1); // 기본주소
		addressDTO.setDetailAddress(addressLine2); // 상세주소
		addressDTO.setDefaultAddress(false); // 새로 등록한 배송지는 기본 배송지로 설정 X
		System.out.println("controller.deliveryAddress.RegistDeliveryAddressAction | [회원 배송지 등록] - addressDTO:["+addressDTO+"]");
		// 페이지 이동
		if (addressDAO.insert(addressDTO)) {
			// 배송지 목록으로 이동
			if (request.getParameter("condition").equals("payment")) {
				forward.setPath("paymentPage.do");
			} 
			else if (request.getParameter("condition").equals("addressList")) {
				forward.setPath("deliveryAddressListPage.do");
			}

		} else {
			// 배송지 등록 페이지로 이동
			request.setAttribute("message", "배송지 등록에 실패하였습니다.");
			request.setAttribute("location", "mainPage.do");
			forward.setPath("message.jsp");
		}

		forward.setRedirect(false);
		return forward;
	}
}