package controller.deliveryAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.common.Action;
import controller.common.ActionForward;
import model.dao.AddressDAO;
import model.dto.AddressDTO;

//배송지 목록 페이지 로드
// [받을 데이터]
// 사용자PK
// [줄 데이터]
// 사용자PK에 해당하는 배송지 정보[PK, 배송지명, 우편번호, 기본주소여부, 기본주소, 상세주소] 목록
public class DeliveryAddressListPageAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.deliveryAddress.DeliveryAddressListPageAction | [배송지 목록 페이지 로드]");

		ActionForward forward = new ActionForward();
		
		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		
		System.out.println("[로그] controller.deliveryAddress.DeliveryAddressListPageAction  | [execute] - accountPk:["+accountPk+"]");
		
		if (accountPk == null) {
			System.out.println("[이상] controller.cart.CartPageAction | [accountPk가 null인데 배송지 목록 접근시도]");
			
			request.setAttribute("location", "loginPage.do");
			request.setAttribute("message", "로그인 후 이용해주세요");

			// 사용자에게 알림 후 로그인 페이지로 이동
			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}
		System.out.println("[로그] controller.deliveryAddress.DeliveryAddressListPageAction | [배송지 목록 정보 가져오기]");
		// 배송지 정보 목록 가져오기
		AddressDAO addressDAO = new AddressDAO();
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setAccountPk(accountPk);
		addressDTO.setCondition("SELECT_ALL_ADDRESS_BY_ACCOUNT_PK");
		
		System.out.println("[로그] controller.deliveryAddress.DeliveryAddressListPageAction | [addressDAO.selectAll(addressDTO)] - addressDTO:["+addressDTO+"]");
		// 파라미터 accountAddressDatas (ArrayList<AddressDTO> 
		// [addressPk, postalCode, isDefaultAddress, addressName, regionAddress, detailAddress]) 넣어주기 
		
		request.setAttribute("addressDatas", addressDAO.selectAll(addressDTO));
		
		
		System.out.println("[로그] controller.deliveryAddress.DeliveryAddressListPageAction | [배송지 목록 페이지 로드 종료]");
		
		forward.setPath("deliveryAddressList.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
