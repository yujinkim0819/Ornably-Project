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

import model.dao.ItemDAO;
import model.dto.ItemDTO;

/*
 * 상품 목록 페이지에서 비동기로 페이지네이션 요청 
 * 1) 상품 정보들 전부 가져와주고
 * 2) 현재 페이지의 위치를 보내주고
 * 3) 한번에 선택할 수 있는 페이지 개수 보내주고
 * 4) 한 페이지에 보여줄 페이지 개수 보여주기
 * 5) 마지막으로 그에 맞는 상품 정보 리스트 보내주기
 */
@WebServlet("/ItemListPagenationServlet")
public class ItemListPagenationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemListPagenationServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[로그] controller.servlet.ItemListPagenationServlet | [doGet메서드] - 시작");

		// json 타입으로 응답해주기 위한 설정
		response.setContentType("application/json; charset=UTF-8");
		Gson gson = new Gson();
		Map map = new HashMap<>();

		// controller에서 페이지네이션 맡기로 했으니 직접 숫자 적기
		int blockSize = 5; // 페이지 분할 개수 ( < 1 2 3 4 5 > )
		int pageSize = 9; // 페이지당 상품 개수
		int startItemNum; // 보여줄 첫 리뷰
		int endItemNum; // 보여줄 마지막 리뷰

		// 1) page 받아오기
		String stringPage = request.getParameter("page");

		System.out
				.println("[로그] controller.servlet.ItemListPagenationServlet | [doGet메서드] - page:[" + stringPage + "]");

		if (stringPage == null || stringPage.isBlank()) {
			map.put("message", "pageIsNull");
			response.getWriter().write(gson.toJson(map));
			return;
		}

		int page = Integer.parseInt(stringPage);

		// 보여줄 리뷰 번호 계산
		startItemNum = (page - 1) * pageSize + 1;
		endItemNum = startItemNum + pageSize - 1;

		// 2) condition 받아오기
		// condition : default, keyword, starDesc
		String condition = request.getParameter("condition");

		System.out.println(
				"[로그] controller.servlet.ItemListPagenationServlet | [doGet메서드] - condition:[" + condition + "]");

		if (condition == null || condition.isBlank()) {
			map.put("message", "conditionIsNull");
			response.getWriter().write(gson.toJson(map));
			return;
		}

		// 아이템 데이터 불러오기 ItemDAO itemDAO = new ItemDAO(); ItemDTO itemDTO = new
		ItemDTO itemDTO = new ItemDTO();
		ItemDAO itemDAO = new ItemDAO();

		itemDTO.setStartItemNum(startItemNum);
		itemDTO.setEndItemNum(endItemNum);

		String keyword = (String) request.getParameter("keyword");
		System.out.println("[로그] controller.servlet.ItemListPagenationServlet | [pagenationByKeyword] - keyword:["
				+ keyword + "]");
		if(keyword!=null) {
			itemDTO.setKeyword(keyword);
		}
		
		if (condition.equals("default")) {
			itemDTO.setCondition("SELECT_ALL_ITEM");
		} else if (condition.equals("keyword")) {
			itemDTO.setCondition("SELECT_ALL_ITEM_NAME_SERACH");
		} else if (condition.equals("starDesc")) {
			itemDTO.setCondition("SELECT_ALL_ITEM_REVIEW_STAR");
		}
		itemDTO.setStartItemNum(startItemNum);
		itemDTO.setEndItemNum(endItemNum);
		
			
		System.out
				.println("[로그] controller.servlet.ItemListPagenationServlet | [itemDAO.selectAll(ItemDTO)] - ItemDTO:["
						+ itemDTO + "]");
		ArrayList<ItemDTO> itemDatas = itemDAO.selectAll(itemDTO);

		if (itemDatas == null) {
			map.put("message", "noItemSelected");
			response.getWriter().write(gson.toJson(map));
			return;
		}
		
		itemDTO.setCondition("TOTAL_ITEM_COUNT");
		
		int totalCount = itemDAO.selectOne(itemDTO).getItemTotealCount();
		System.out.println("totalCount: ["+totalCount+"]");
		// 마지막 페이지 번호 계산
		int totalPage = (int) Math.ceil((double) totalCount / pageSize);

		int startPage = ((page - 1) / blockSize) * blockSize + 1;
		int endPage = startPage + blockSize - 1;
		endPage = endPage > totalPage ? totalPage : endPage;

		map.put("itemDatas", itemDatas);
		map.put("currentPage", page);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		map.put("totalPage", totalPage);
		
		
		// 테스트용 코드 // itemPk, itemStock, itemImageUrl, itemName, itemStar,
		/*
		itemDTO = new ItemDTO(); 
		itemDTO.setItemPk(1);
		itemDTO.setItemStock(2); 
		itemDTO.setItemImageUrl("img/carousel-1.jpg");
		itemDTO.setItemPrice(18000); 
		itemDTO.setItemName("가위");
		itemDTO.setItemStar(8.15);
		
		ArrayList<ItemDTO> itemDatas = new ArrayList<>();
		
		itemDatas.add(itemDTO);
		
		map.put("itemDatas", itemDatas); 
		map.put("currentPage", page);
		map.put("startPage", 1); 
		map.put("endPage", 5); 
		map.put("totalPage", 8);
		*/
		 
		
		
		response.getWriter().write(gson.toJson(map));
		System.out.println("[로그] controller.servlet.ItemListPagenationServlet | [json파일 반환] - map:[" + map + "]");

		System.out.println("[로그] controller.servlet.ItemListPagenationServlet | [doGet메서드] - 종료");
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(
				"[로그] controller.servlet.ItemListPagenationServlet | [doPost메서드] *잘못된 방식의 요청이에요! GET메서드를 사용해주세요!*");
		doGet(request, response);
	}
}
