package controller.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ReviewDAO;
import model.dto.ReviewDTO;

@WebServlet("/DeleteReviewServlet")
public class DeleteReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteReviewServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.DeleteReviewServlet | [doGet메서드] - 잘못된 전달방식 *고쳐주세요* ");
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.DeleteReviewServlet | [doPost메서드] - 시작");
		
		// 리뷰pk 받아서 해당 리뷰 삭제시켜주기
		String stringReviewPk = request.getParameter("reviewPk");
		
		System.out.println("[로그] controller.servlet.DeleteReviewServlet | [doPost메서드] - reviewPk:["+stringReviewPk+"]");
		
		if(stringReviewPk==null || stringReviewPk.isBlank()) {
			response.getWriter().print(false);
			return;
		}
		Integer reviewPk = Integer.parseInt(stringReviewPk);
		
		ReviewDAO reviewDAO = new ReviewDAO();
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setReviewPk(reviewPk);
		reviewDTO.setCondition("DELETE_BY_REVIEW_PK");
		
		System.out.println("[로그] controller.servlet.DeleteReviewServlet | [reviewDAO.delete(reviewDTO)] - reviewDTO:["+reviewDTO+"]");
		if(reviewDAO.delete(reviewDTO)) {
			System.out.println("[로그] controller.servlet.DeleteReviewServlet | [reviewDAO.delete(reviewDTO)] - 댓글 삭제 완료");
			response.getWriter().print(true);
		}
		else {
			System.out.println("[로그] controller.servlet.DeleteReviewServlet | [reviewDAO.delete(reviewDTO)] - 댓글 삭제 실패");
			response.getWriter().print(false);
		}
		
		System.out.println("[로그] controller.servlet.DeleteReviewServlet | [doPost메서드] - 끝");
	}

}
