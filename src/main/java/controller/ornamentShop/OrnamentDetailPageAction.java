package controller.ornamentShop;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.ItemDAO;
import model.dao.ReviewDAO;
import model.dao.WishlistDAO;
import model.dto.ItemDTO;
import model.dto.ReviewDTO;
import model.dto.WishlistDTO;

// 아이템 상세 페이지 로드
// 아이템 상세 정보[PK, 이름, 상품평점, 가격, 설명, 이미지, 재고]
// 로그인이 되어있다면 좋아요 눌렀는지 여부까지 보내주기 ( 로그인 안되어있으면 안눌림처리 )
public class OrnamentDetailPageAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.ornamentShop.OrnamentDetailPageAction | [execute] - 시작");
		
		ActionForward forward = new ActionForward();
		
		// 상품 정보 가져오기
		Integer itemPk = Integer.parseInt(request.getParameter("itemPk"));
		System.out.println("[로그] controller.ornamentShop.OrnamentDetailPageAction | [execute] - itemPk:["+itemPk+"]");
		if(itemPk < 0) {
			request.setAttribute("message", "잘못된 경로로 들어오셨습니다. 이상이 있을 시 관리자에게 문의해주세요");
			request.setAttribute("location", "ornamentPage.jsp");
			
			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}
		
		// 1) 출력할 상품 정보 보내주기
		ItemDAO itemDAO = new ItemDAO();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemPk(itemPk);
		itemDTO.setCondition("SELECT_ITEM_DETAIL_INFO");
		
		System.out.println("[로그] controller.ornamentShop.OrnamentDetailPageAction | [itemDAO.selectOne(itemDTO)] - itemDTO:["+itemDTO+"]");
		itemDTO = itemDAO.selectOne(itemDTO);
		
		// 2) 사용자가 로그인되어있는 상황이라면
		//		찜한 상태 값 보내주기
		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		System.out.println("[로그] controller.ornamentShop.OrnamentDetailPageAction | [execute] - accountPk:["+accountPk+"]");
		boolean isWished=false;
		if(accountPk!=null) {
			System.out.println("[로그] controller.ornamentShop.OrnamentDetailPageAction | [사용자가 해당 상품 찜했는지 여부 확인] - 시작");
			WishlistDAO wishlistDAO = new WishlistDAO();
			WishlistDTO wishlistDTO = new WishlistDTO();
			wishlistDTO.setCondition("SELECT_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK");
			wishlistDTO.setAccountPk(accountPk);
			wishlistDTO.setItemPk(itemPk);
			
			System.out.println("[로그] controller.ornamentShop.OrnamentDetailPageAction | [wishlistDAO.selectOne(wishlistDTO)] - wishlistDTO:["+wishlistDTO+"]");
			isWished = wishlistDAO.selectOne(wishlistDTO) != null;
		}
		
		/* 폐기 코드
		// 2) 상품에 대한 사용자들의 리뷰 보내주기 - 비동기 처리를 하기로 결정 -> 폐기
		// 리뷰[reviewPk, itmePk, reviewStar, reviewTitle, reviewContent]
		reviewDTO.setCondition("SELECT_ALL_REVIEW_DATAS_BY_ITEM_PK");
		
		System.out.println("[로그] controller.ornamentShop.OrnamentDetailPageAction | [reviewDAO.selectAll(reviewDTO)] - reviewDTO:["+reviewDTO+"]");
		ArrayList<ReviewDTO> reviewDatas = reviewDAO.selectAll(reviewDTO);
		
		request.setAttribute("reviewDatas", reviewDatas);
		*/
		request.setAttribute("isWished", isWished);
		request.setAttribute("itemData", itemDTO);
		
		forward.setPath("ornamentDetailPage.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
