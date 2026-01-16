package controller.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.ItemDAO;
import model.dao.ReviewDAO;
import model.dto.ItemDTO;
import model.dto.ReviewDTO;


// 리뷰 작성 페이지 로드
// 2가지 경우가 존재

// 1. 리뷰를 작성하려 할 때
// [받는 데이터]
// 상품pk, pageType
// [주는 데이터]
// 상품정보[PK, 이미지, 이름, 가격, 상품평점], reviewType="firstReview"

// 2. 리뷰를 수정하려 할 때
// [받는 데이터]
// 상품pk, pageType
// [주는 데이터]
// 상품정보[PK, 이미지, 이름, 가격, 상품평점], reviewType="updateReview", reviewDTO[reviewPk, reviewAccountName, reviewTitle, reviewContent] 
// 
public class ReviewWritePageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.review.ReviewWritePageAction | [execute] - 시작");
		
		ActionForward forward = new ActionForward();
		
		Integer accountPk = (Integer) request.getSession().getAttribute("accountPk");
		
		System.out.println("[로그] controller.review.ReviewWritePageAction | [로그인 검사] - accountPk:["+accountPk+"]");
		if(accountPk == null) {
			
			request.setAttribute("message", "리뷰를 작성하려면 로그인이 되어있어야합니다.");
			request.setAttribute("location", "mainPage.do");
			
			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}
		
		String stringItemPk = request.getParameter("itemPk");
		
		String pageType = request.getParameter("pageType"); // 최초작성:"firstReview", 리뷰수정:"updateReview"
		
		System.out.println("[로그] controller.review.ReviewWritePageAction | [execute] - itemPk:["+stringItemPk+"]");
		System.out.println("[로그] controller.review.ReviewWritePageAction | [execute] - pageType:["+pageType+"]");
		
		
		// 파라미터 값이 들어오지 않으면
		if(stringItemPk==null || stringItemPk.isBlank() || pageType==null || pageType.isBlank()) {
			// 오류띄우고 마이페이지 보내기
			request.setAttribute("message", "데이터에 오류가 있습니다. 관리자에게 문의해주세요");
			request.setAttribute("loaction", "/myPage.do");
			
			forward.setPath("message.jsp");
			forward.setRedirect(false);
			return forward;
		}
		Integer itemPk = Integer.parseInt(stringItemPk);
		
		// 1. 상품pk정보 넣어주기
		ItemDAO itemDAO = new ItemDAO();
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemPk(itemPk);
		itemDTO.setCondition("SELECT_ITEM_REVIEW_INFO");
					
		System.out.println("[로그] controller.review.ReviewWritePageAction | [itemDAO.selectOne(itemDTO)] - itemDTO:["+itemDTO+"]");
		itemDTO = itemDAO.selectOne(itemDTO);
		if(itemDTO!=null) {
			System.out.println("[로그] controller.review.ReviewWritePageAction | [itemDAO.selectOne(itemDTO)] - itemData:["+itemDTO+"]");
			request.setAttribute("itemData", itemDTO);
		}
		else {
			System.out.println("[로그] controller.review.ReviewWritePageAction | [itemDAO.selectOne(itemDTO)] - 실패");
			request.setAttribute("itemData", null);
		}
		
		// 첫 리뷰 vs 리뷰 수정인 경우 나눠주기
		if(pageType.equals("firstReview")) {
			// reviewType="firstReview"
			// 반환해주기
			System.out.println("[로그] controller.review.ReviewWritePageAction | pageType:[firstReview] - 실행");
			
			// 2. reviewType 넣어주기
			request.setAttribute("reviewType", "firstReview");
		}
		else if(pageType.equals("updateReview")) {
			// reviewType="firstReview"
			System.out.println("[로그] controller.review.ReviewWritePageAction | pageType:[updateReview] - 실행");
			
			// [리뷰PK, 사용자이름, 제목, 평점, 리뷰내용] 보내주기
			request.setAttribute("reviewType", "updateReview");
			
			String stringReviewPk = request.getParameter("reviewPk");
			
			System.out.println("[로그] controller.review.ReviewWritePageAction | pageType:[updateReview] - reviewPk:["+stringReviewPk+"]");
			// 리뷰PK가 NULL이면
			if(stringReviewPk==null || stringReviewPk.isBlank()) {
				forward.setPath("reviewWrite.jsp");
				forward.setRedirect(false);
				return forward;
			}
			
			int reviewPk = Integer.parseInt(stringReviewPk);
			
			ReviewDAO reviewDAO = new ReviewDAO();
			ReviewDTO reviewDTO = new ReviewDTO();
			reviewDTO.setReviewPk(reviewPk);
			reviewDTO.setAccountPk(accountPk);
			reviewDTO.setCondition("SELECT_UPDATE_REVIEW_DATA");
			
			System.out.println("[로그] controller.review.ReviewWritePageAction | [reviewDAO.selectOne(reviewDTO)] - reviewDTO:["+reviewDTO+"]");
			
			// reviewPk -> reviewPk, accountName, reviewTitle, reviewStar, reviewContent
			request.setAttribute("reviewData", reviewDAO.selectOne(reviewDTO));
		}
		
		
		/*	최종 데이터 : 
		 * [공통] : 
		 * 		
		 * 		itemData[itemPk, itemImageUrl, itemName, itemPrice, itemStar]
		 * 		reviewType : firstReview / updateReview
		 * [firstReview] : 없음
		 * [updateReview] : 
		 * 		reviewData[reviewPk, accountName, reviewTitle, reviewStar, reviewContent]
		 * 
		 */
		
		forward.setPath("reviewWrite.jsp");
		forward.setRedirect(false);
		System.out.println("[로그] controller.review.ReviewWritePageAction | [execute] - 종료");
		return forward;
	}

}
