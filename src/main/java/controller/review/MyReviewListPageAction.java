package controller.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.common.Action;
import controller.common.ActionForward;
import model.dao.ReviewDAO;
import model.dto.ReviewDTO;



/*
 * 사용자 pk를 세션에서 꺼내서 해당 사용자의 모든 리뷰 정보를 프론트에 주기
 * 
 */
public class MyReviewListPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 리뷰 리스트 페이지 로드
		System.out.println("[로그] controller.review.MyReviewListPageAction | [execute] - 시작");
		
		int accountPk = (Integer) request.getSession().getAttribute("accountPk");
		
		// accountPk의 리뷰들 모두 가져오기
		ReviewDAO reviewDAO = new ReviewDAO();
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setAccountPk(accountPk);
		reviewDTO.setCondition("SELECT_MY_REVIEW_LIST");
		
		// accountPk → reviewPk, itemPk, reviewTitle, reviewStar, reviewContent, itemImageUrl, itemName, itemPrice
		System.out.println("[로그] controller.review.MyReviewListPageAction | [reviewDAO.selectAll(reviewDTO)] - reviewDTO:["+reviewDTO+"]");
		request.setAttribute("reviewDatas", reviewDAO.selectAll(reviewDTO));
		// ▼▼▼▼▼ 임시코드 ▼▼▼▼▼
		/*ArrayList<ReviewDTO> reviewDatas = new ArrayList<>();
		reviewDTO.setReviewPk(1);
		reviewDTO.setItemPk(1);
		reviewDTO.setReviewTitle("이건 제목이다 1");
		reviewDTO.setReviewStar(10);
		reviewDTO.setReviewContent("이건 내용이다 1");
		reviewDTO.setItemPrice(123123123);
		reviewDTO.setItemName("이건 오너먼트다 1");
		reviewDatas.add(reviewDTO);
		reviewDTO = new ReviewDTO();
		reviewDTO.setReviewPk(2);
		reviewDTO.setItemPk(2);
		reviewDTO.setReviewTitle("이건 제목이다 2");
		reviewDTO.setReviewStar(3);
		reviewDTO.setReviewContent("이건 내용이다 2");
		reviewDTO.setItemPrice(11111111);
		reviewDTO.setItemName("이건 오너먼트다 2");
		reviewDatas.add(reviewDTO);
		
		request.setAttribute("reviewDatas", reviewDatas);*/
		// ▲▲▲▲▲ 임시코드 ▲▲▲▲▲
		ActionForward forward = new ActionForward();
		forward.setPath("myReviewList.jsp");
		forward.setRedirect(false);
		
		System.out.println("[로그] controller.review.MyReviewListPageAction | [execute] - 시작");
		return forward;
	}

}
