package controller.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.common.Action;
import controller.common.ActionForward;
import model.dao.ReviewDAO;
import model.dto.ReviewDTO;


// reviewPk,accountPk,itemPk,reviewTitle,reviewContent,reviewStar 를 프론트에서 받아서
// REVIEW_WRITE를 통해 INSERT하기
public class ReviewWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("[로그] controller.review.ReviewWriteAction | [execute] - 시작");
		
		String condition = request.getParameter("condition");
		int accountPk = (Integer) request.getSession().getAttribute("accountPk");
		int itemPk = Integer.parseInt(request.getParameter("itemPk"));
		String reviewTitle = request.getParameter("reviewTitle");
		String reviewContent = request.getParameter("reviewContent");
		int reviewStar = Integer.parseInt(request.getParameter("reviewStar"));
		
		
		ReviewDAO reviewDAO = new ReviewDAO();
		ReviewDTO reviewDTO = new ReviewDTO();
		// condition이 firstReview인지 updateReview인지에 따라 분기가 나뉨
		reviewDTO.setAccountPk(accountPk);
		reviewDTO.setItemPk(itemPk);
		reviewDTO.setReviewTitle(reviewTitle);
		reviewDTO.setReviewContent(reviewContent);
		reviewDTO.setReviewStar(reviewStar); // 사실 분기에 따라 넣을수도 안넣을 수도 있는데 코드의 단순함을 위해 그냥 사용
		
		String resultMsg="";
		
		if(condition.equals("firstReview")) {
			reviewDTO.setCondition("REVIEW_WRITE");
			System.out.println("[로그] controller.review.ReviewWriteAction | [reviewDAO.insert(reviewDTO)] - reviewDTO:["+reviewDTO+"]");
			if(reviewDAO.insert(reviewDTO)) {
				resultMsg = "리뷰 작성 성공";
			}
			else {
				resultMsg = "리뷰 작성 실패";
			}
			System.out.println("[로그] controller.review.ReviewWriteAction | [reviewDAO.insert(reviewDTO)] - 결과:["+resultMsg+"]");
		}
		else if(condition.equals("updateReview")) {
			reviewDTO.setReviewPk(Integer.parseInt(request.getParameter("reviewPk")));
			reviewDTO.setCondition("REVIEW_WRITE_EDIT");
			
			System.out.println("[로그] controller.review.ReviewWriteAction | [reviewDAO.update(reviewDTO)] - reviewDTO:["+reviewDTO+"]");
			if(reviewDAO.update(reviewDTO)) {
				resultMsg = "리뷰 수정 성공";
			}
			else {
				resultMsg = "리뷰 수정 실패";
			}
			System.out.println("[로그] controller.review.ReviewWriteAction | [reviewDAO.insert(reviewDTO)] - 결과:["+resultMsg+"]");
		}
		
		
		
		ActionForward forward = new ActionForward();
		
		request.setAttribute("message", resultMsg);
		request.setAttribute("location", "myPage.do");
		
		forward.setPath("message.jsp");
		forward.setRedirect(false);
		
		System.out.println("[로그] controller.review.ReviewWriteAction | [execute] - 종료");
		return forward;
	}
}
