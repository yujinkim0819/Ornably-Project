package controller.ornamentShop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.ItemDAO;
import model.dto.ItemDTO;


// 1. PK내림차순
// 2. 검색어로 탐색
// 3. 평점순 내림차순
// 방식으로 상품정보 목록[PK, 재고, 이미지, 이름, 가격, 평점] 주기
public class OrnamentListPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.ornamentShop.OrnamentListPageAction | [execute] - 시작");
		
		ActionForward forward = new ActionForward();
		
		// 우선 출력 조건 받기
		//String itemListCondition=request.getParameter("itemListCondition");
		
		//System.out.println("[로그] controller.ornamentShop.OrnamentListPageAction | [execute] - itemListCondition:["+itemListCondition+"]");
		ItemDAO itemDAO = new ItemDAO();
		ItemDTO itemDTO = new ItemDTO();

		// 요청에 따라 출력 조건과 파라미터 넣기
		/*
		if(itemListCondition==null || itemListCondition.equals("default")) {
			itemDTO.setCondition("SELECT_ALL_ITEM_DATAS_DEFAULT");
		}
		else if(itemListCondition.equals("keyword")) {
			itemDTO.setCondition("SELECT_ALL_ITEM_DATAS_KEYWORD");
			itemDTO.setKeyword(request.getParameter("keyword"));
		}
		else if(itemListCondition.equals("starDesc")) {
			itemDTO.setCondition("SELECT_ALL_ITEM_DATAS_KEYWORD");
		}
		*/
		// 조건에 따라 model에게 selectAll 요청 보내서 ArrayList<ItemDTO> 바로 view에 'itemDatas' 로 보내기
		// 상품정보 목록[PK, 이미지, 이름, 가격]
		/*
		System.out.println("[로그] controller.ornamentShop.OrnamentListPageAction | [itemDAO.selectAll(itemDTO)] - itemDTO:["+itemDTO+"]");
		ArrayList<ItemDTO> itemDatas = itemDAO.selectAll(itemDTO);
		
		// 상품별 평점 구하는 로직
		System.out.println("[로그] controller.ornamentShop.OrnamentListPageAction | [상품별 평점 평균 구하기] - 시작");
		for(int i=0;i<itemDatas.size();i++) {
			// 아이템 리스트의 상품pk 받기
			int itemPk = itemDatas.get(i).getItemPk();
			System.out.println("[로그] controller.ornamentShop.OrnamentListPageAction | [상품별 평점 평균 구하기] - itemPK:["+itemPk+"]");
			
			// itemPK에 대한 모든 리뷰 평점 가져오기
			ReviewDAO reviewDAO = new ReviewDAO();
			ReviewDTO reviewDTO = new ReviewDTO();
			reviewDTO.setItemPk(itemPK);
			reviewDTO.setCondition("SELECT_REVIEW_STAR_BY_ITEM_PK");
			
			// forEach문을 통해 상품pk에 대한 모든 평점 평균 내기
			ArrayList<ReviewDTO> reviewDatas = reviewDAO.selectAll(reviewDTO);
			double reviewAverage=0;
			for(ReviewDTO reviewData:reviewDatas) {
				reviewAverage+=reviewData.getReviewStar();
			}
			reviewAverage/=reviewDatas.size();
			System.out.println("[로그] controller.ornamentShop.OrnamentListPageAction | [상품별 평점 평균 구하기] - reviewAverage:["+reviewAverage+"]");
			itemDatas.get(i).setItemStar(reviewAverage);
		}
		System.out.println("[로그] controller.ornamentShop.OrnamentListPageAction | [상품별 평점 평균 구하기] - 종료");
		
		request.setAttribute("itemDatas", itemDatas);
		*/
		
		forward.setPath("ornamentPage.jsp");
		forward.setRedirect(false);
		return forward;
	}
}
