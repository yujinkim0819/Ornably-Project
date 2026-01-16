package controller.deliveryAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;

public class RegistDeliveryAddressPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.deliveryAddress.RegistDeliveryAddressPageAction | [배송지 등록 페이지 로드]");
		// [ 배송지 등록 페이지 로드 ]
		ActionForward forward = new ActionForward();
		forward.setPath("deliveryAddressForm.jsp");
		
		System.out.println("[로그] controller.deliveryAddress.RegistDeliveryAddressPageAction | [등록 후 되돌아갈 페이지] condition:["+request.getParameter("condition")+"]");
		if (request.getParameter("condition") == null) {
			request.setAttribute("message", "잘못된 경로입니다.");
			request.setAttribute("location", "mainPage.do");
			forward.setPath("message.jsp");
		} 
		else if (request.getParameter("condition").equals("payment")) {
			request.setAttribute("condition", "payment");
		} 
		else if (request.getParameter("condition").equals("addressList")) {
			request.setAttribute("condition", "addressList");
		}
		forward.setRedirect(false);
		return forward;
	}

}
