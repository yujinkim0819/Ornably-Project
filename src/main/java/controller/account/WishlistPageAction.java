package controller.account;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.ItemDAO;
import model.dao.WishlistDAO;
import model.dto.ItemDTO;
import model.dto.WishlistDTO;

//찜한상품 페이지 로드
// [받는 데이터]
// 사용자Pk(세션)
// [주는 데이터]
// 상품정보 목록[itemPk, itemImageUrl, itemName, itemPrice]
public class WishlistPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("[로그] controller.account.WishlistPageAction | [좋아요 목록 페이지 로드]");
		
		ActionForward forward = new ActionForward();
		
		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		
		System.out.println("[로그] controller.account.WishlistPageAction | accountPk:["+accountPk+"]");
		
		if (accountPk == null) {
			System.out.println("[이상] controller.account.WishlistPageAction | [accountPk 가 null인 상황에서 좋아요 목록 접근]");
			request.setAttribute("location", "/loginPage.do");
			request.setAttribute("message", "로그인 후 이용해주세요");

			// 사용자에게 알림 후 로그인 페이지로 이동
			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}
		
		
		/* 코드 비효율로 인해 아래 코드는 폐기
		// 사용자pk가 가지고 있는 모든 좋아요 목록 가져오기 - wishlistDatas
		WishlistDAO wishlistDAO = new WishlistDAO();
		WishlistDTO wishlistDTO = new WishlistDTO();
		wishlistDTO.setAccountPk(accountPk);
		wishlistDTO.setCondition("SELECT_ALL_WISHLIST_BY_ACCOUNT_PK");
		
		System.out.println("[로그] controller.account.WishlistPageAction | [wishlistDAO.selectAll(wishlistDTO)] - wishlistDTO:["+wishlistDTO+"]");
		ArrayList<WishlistDTO> wishlistDatas = wishlistDAO.selectAll(wishlistDTO);
		request.setAttribute("wishlistDatas", wishlistDatas);
		
		// itemPk itemName itemPrice itemImageUrl
		// 좋아요 누른 상품 정보 추출하기
		System.out.println("[로그] controller.account.WishlistPageAction | [좋아요 누른 상품들 추출] - 시작");
		ArrayList<ItemDTO> itemDatas = new ArrayList<>();
		for(int i=0;i<wishlistDatas.size();i++) {
			System.out.println("[로그] controller.account.WishlistPageAction | [좋아요 누른 상품들 추출] - wishlistPk:["+wishlistDatas.get(i).getWishlistPk()+"]");
			
			// 찜한 아이템 정보 뽑아오기
			ItemDAO itemDAO = new ItemDAO();
			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setItemPk(wishlistDatas.get(i).getItemPk());
			itemDTO.setCondition("SELECT_ITEM_REVIEW_INFO");
			System.out.println("[로그] controller.account.WishlistPageAction | [itemDAO.selectOne(itemDTO)] - itemDTO:["+itemDTO+"]");
			
			itemDatas.add(itemDAO.selectOne(itemDTO));
		}
		System.out.println("[로그] controller.account.WishlistPageAction | [좋아요 누른 상품들 추출] - 종료");
		*/
		
		// [수정 코드]
		// accountPk를 통해 WishList테이블에 존재하는 itemPk의
		// itemPk itemName itemPrice itemImageUrl 가져오기
		System.out.println("[로그] controller.account.WishlistPageAction | [좋아요 상품 정보 가져오기]");
		ItemDAO itemDAO = new ItemDAO();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setAccountPk(accountPk);
		itemDTO.setCondition("SELECT_ALL_WISHLIST_ITEM");
		
		System.out.println("[로그] controller.account.WishlistPageAction | [itemDAO.selectAll(itemDTO)] - itemDTO:["+itemDTO+"]");
		request.setAttribute("itemList", itemDAO.selectAll(itemDTO));
		
		forward.setPath("myWishlist.jsp");
		forward.setRedirect(false);
		System.out.println("[로그] controller.account.WishlistPageAction | [execute] - 종료");
		return forward;
	}

}
