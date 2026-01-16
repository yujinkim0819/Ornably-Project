package controller.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.dao.ReviewDAO;
import model.dto.ReviewDTO;

// 상품 상세 페이지에서 상품pk와 페이지네이션 넘버를 주면 그에맞는 
// 
@WebServlet("/ReviewListPagenationServlet")
public class ReviewListPagenationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ReviewListPagenationServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.ReviewListPagenationServlet | [doGet메서드] - 시작");
		
		// json 타입으로 응답해주기 위한 설정
		response.setContentType("application/json; charset=UTF-8");
		Gson gson = new Gson();
		Map map = new HashMap<>();
		
		// controller에서 페이지네이션 맡기로 했으니 직접 숫자 적기
		int blockSize=5; // 페이지 분할 개수 ( < 1 2 3 4 5 > )
		int pageSize=5; // 페이지당 리뷰 개수
		int	startReviewNum; // 보여줄 첫 리뷰
		int endReviewNum; // 보여줄 마지막 리뷰
		


		
		// 1) itemPk 받아오기
		String stringItemPk = request.getParameter("itemPk");
		
		System.out.println("[로그] controller.servlet.ReviewListPagenationServlet | [doGet메서드] - itemPk:["+stringItemPk+"]");
		
		if(stringItemPk==null || stringItemPk.isBlank()) {
			map.put("message", "itemPkIsNull");
			response.getWriter().write(gson.toJson(map));
			return;
		}
		
		int itemPk = Integer.parseInt(stringItemPk); 
		
		
		// 2) page 받아오기
		String stringPage = request.getParameter("page");
		
		System.out.println("[로그] controller.servlet.ReviewListPagenationServlet | [doGet메서드] - page:["+stringPage+"]");
		
		if(stringPage==null || stringPage.isBlank()) {
			map.put("message", "pageIsNull");
			response.getWriter().write(gson.toJson(map));
			return;
		}
		
		int page = Integer.parseInt(stringPage); 
		
		// 보여줄 리뷰 번호 계산
		startReviewNum = (page-1)*pageSize+1;
		endReviewNum = startReviewNum+pageSize-1;
		
		
		// 리뷰 데이터 불러오기
		ReviewDTO reviewDTO = new ReviewDTO();
		ReviewDAO reviewDAO = new ReviewDAO();
		
		
		reviewDTO.setStartReviewNum(startReviewNum);
		reviewDTO.setEndReviewNum(endReviewNum);
		reviewDTO.setItemPk(itemPk);
		reviewDTO.setCondition("SELECT_ALL_PRODUCT_REVIEW_PAGENATION");
		
		System.out.println("[로그] controller.servlet.ReviewListPagenationServlet | [reviewDAO.selectAll(reviewDTO)] - reviewDTO:["+reviewDTO+"]");
		ArrayList<ReviewDTO> reviewDatas = reviewDAO.selectAll(reviewDTO);
		
		if(reviewDatas==null || reviewDatas.size()==0) {
			map.put("message", "noReviewSelected");
			response.getWriter().write(gson.toJson(map));
			return;
		}
		
		int reviewCount=reviewDatas.size();
		
		reviewDTO = new ReviewDTO();
		reviewDTO.setItemPk(itemPk);
		reviewDTO.setCondition("SELECT_ITEM_REVIEW_COUNT");
		
		// itemPk -> reviewPk, itmePk, reviewStar, reviewTitle, reviewContent
		System.out.println("[로그] controller.servlet.ReviewListPagenationServlet | [reviewDAO.selectOne(reviewDTO)] - reviewDTO:["+reviewDTO+"]");
		int totalCount = reviewDAO.selectOne(reviewDTO).getReviewTotalCount();
		
		// 마지막 페이지 번호 계산
		int totalPage= (int) Math.ceil((double) totalCount/pageSize); 
		
		int startPage= ((page-1)/blockSize)*blockSize+1;
		int endPage= startPage+blockSize-1;
		endPage = endPage > totalPage ? totalPage : endPage;
		
		
		map.put("reviewDatas", reviewDatas);
		map.put("currentPage", page);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		map.put("totalPage", totalPage);
		
		// itemPk -> reviewPk, itmePk, reviewStar, reviewTitle, reviewContent
		/*reviewDTO.setReviewAccountName("숑숑");
		reviewDTO.setItemPk(1);
		reviewDTO.setReviewStar(9);
		reviewDTO.setReviewTitle("asdfasdfasdfasdfasdf");
		reviewDTO.setReviewContent("좋은데요? 다음에 또 살게요");
		ArrayList<ReviewDTO> reviewDatas = new ArrayList<>();
		
		for(int i=0;i<5;i++) {
			reviewDTO.setReviewPk(i+1);
			reviewDatas.add(reviewDTO);
		}
		
		
		map.put("reviewDatas", reviewDatas);
		map.put("currentPage", page);
		map.put("startPage", 1);
		map.put("endPage", 5);
		map.put("totalPage", 12);*/
		System.out.println("map:["+map+"]");
		
		response.getWriter().write(gson.toJson(map));
		
		System.out.println("[로그] controller.servlet.ReviewListPagenationServlet | [json파일 반환] - map:["+map+"]");
		System.out.println("[로그] controller.servlet.ReviewListPagenationServlet | [doGet메서드] - 종료");
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.ReviewListPagenationServlet | [doPost메서드] *잘못된 방식의 요청이에요! GET메서드를 사용해주세요!*");
		doGet(request, response);
	}

}
