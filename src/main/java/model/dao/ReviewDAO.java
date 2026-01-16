package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.ReviewDTO;

public class ReviewDAO {
	// 리뷰작성
	private static final String REVIEW_WRITE = 
			"INSERT INTO REVIEW "
			+ "(REVIEW_PK,ACCOUNT_PK,ITEM_PK,REVIEW_TITLE, REVIEW_CONTENT,REVIEW_STAR) "
			+ "VALUES(REVIEW_SEQ.NEXTVAL, ?, ?, ?,? ,?)";
	// 상품별 리뷰 총개수 조회
	private static final String SELECT_ITEM_REVIEW_COUNT = "SELECT COUNT(*) AS REVIEW_TOTAL_COUNT " + "FROM REVIEW "
			+ "WHERE ITEM_PK = ?";
	// 리뷰수정 전 기존 리뷰 정보 가져오기
	private static final String SELECT_UPDATE_REVIEW_DATA = "SELECT " + "	R.REVIEW_PK," + "	A.ACCOUNT_NAME,"
			+ "	R.REVIEW_TITLE," + "	R.REVIEW_STAR," + "	R.REVIEW_CONTENT " + "FROM REVIEW R " + "JOIN ACCOUNT A "
			+ "	ON R.ACCOUNT_PK = A.ACCOUNT_PK " + "	WHERE R.REVIEW_PK = ?" + "	AND R.ACCOUNT_PK = ?";
	// 내가 쓴 리뷰목록
	private static final String SELECT_MY_REVIEW_LIST = "SELECT R.REVIEW_PK, I.ITEM_PK, R.REVIEW_TITLE, R.REVIEW_STAR, R.REVIEW_CONTENT, "
			+ "	I.ITEM_NAME, I.ITEM_IMAGE_URL, I.ITEM_PRICE " + "FROM REVIEW R INNER JOIN ITEM I "
			+ "ON R.ITEM_PK = I.ITEM_PK " + "WHERE R.ACCOUNT_PK = ?";
	// 상품리뷰 존재확인
	private static final String SELECT_EXIST_REVIEW_BY_ACCOUNT_ITEM = "SELECT COUNT(*) AS REVIEW_TOTAL_COUNT FROM REVIEW "
			+ "WHERE ACCOUNT_PK = ? " + "  AND ITEM_PK = ? ";
	// 상품별 별점
	private static final String SELECT_ALL_REVIEW_STAR_BY_ITEM_PK = "SELECT REVIEW_STAR FROM REVIEW WHERE ITEM_PK = ? ORDER BY REVIEW_PK DESC";
	// 리뷰페이지네이션
	private static final String SELECT_ALL_PRODUCT_REVIEW_PAGENATION = "SELECT REVIEW_PK, ITEM_PK, REVIEW_TITLE, REVIEW_CONTENT, REVIEW_STAR, ACCOUNT_NAME "
			+ "FROM ( "
			+ "    SELECT R.REVIEW_PK, R.ITEM_PK, R.REVIEW_TITLE, R.REVIEW_CONTENT, R.REVIEW_STAR, A.ACCOUNT_NAME, "
			+ "           ROW_NUMBER() OVER (ORDER BY R.REVIEW_PK DESC) AS RN " + "    FROM REVIEW R "
			+ "    JOIN ACCOUNT A ON R.ACCOUNT_PK = A.ACCOUNT_PK " + "    WHERE R.ITEM_PK = ? " + ") "
			+ "WHERE RN BETWEEN ? AND ?";

	// 회원 고유번호의 모든리뷰 삭제
	private static final String DELETE_ALL_REVIEW_BY_ACCOUNT_PK = "DELETE FROM REVIEW WHERE ACCOUNT_PK = ? ";
	// 회원 고유번호의 리뷰 개별삭제
	private static final String DELETE_BY_REVIEW_PK =
			"DELETE FROM REVIEW " + "WHERE REVIEW_PK = ? ";
	// 리뷰수정
	private static final String REVIEW_WRITE_EDIT = 
			"UPDATE REVIEW SET REVIEW_TITLE = ?,"
			+ " REVIEW_CONTENT =  ?, REVIEW_STAR = ?" 
			+ " WHERE ACCOUNT_PK = ? AND ITEM_PK= ?";

	public ArrayList<ReviewDTO> selectAll(ReviewDTO reviewDTO) {

		ArrayList<ReviewDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 아이템 고유번호 가져와서 상품별 별점 보여주기
			if (reviewDTO.getCondition().equals("SELECT_ALL_REVIEW_STAR_BY_ITEM_PK")) {
				pstmt = conn.prepareStatement(SELECT_ALL_REVIEW_STAR_BY_ITEM_PK);
				pstmt.setInt(1, reviewDTO.getItemPk());
				System.out.println("[로그] ReviewDAO SELECT_ALL_REVIEW_STAR_BY_ITEM_PK : " + datas);
			}
			// 리뷰 페이지 페이지 넘기기 (페이지 네이션)
			else if (reviewDTO.getCondition().equals("SELECT_ALL_PRODUCT_REVIEW_PAGENATION")) {
				pstmt = conn.prepareStatement(SELECT_ALL_PRODUCT_REVIEW_PAGENATION);
				pstmt.setInt(1, reviewDTO.getItemPk());
				pstmt.setInt(2, reviewDTO.getStartReviewNum());
				pstmt.setInt(3, reviewDTO.getEndReviewNum());
				System.out.println("[로그] ReviewDAO SELECT_ALL_PRODUCT_REVIEW_PAGENATION : " + datas);

				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					// reviewPk, itemPk, reviewTitle, reviewStar,
					// reviewContent, itemName, itemImageUrl, itemPrice
					ReviewDTO data = new ReviewDTO();
					data.setReviewPk(rs.getInt("REVIEW_PK"));
					data.setItemPk(rs.getInt("ITEM_PK"));
					data.setReviewTitle(rs.getString("REVIEW_TITLE"));
					data.setReviewContent(rs.getString("REVIEW_CONTENT"));
					data.setReviewStar(rs.getInt("REVIEW_STAR"));
					data.setReviewAccountName(rs.getString("ACCOUNT_NAME"));
					System.out.println("[로그] ReviewDAO selectAll  성공 : " + reviewDTO);
					datas.add(data);
				}
			}
			// 내가 작성한 리뷰 목록보기
			else if (reviewDTO.getCondition().equals("SELECT_MY_REVIEW_LIST")) {
				pstmt = conn.prepareStatement(SELECT_MY_REVIEW_LIST);
				pstmt.setInt(1, reviewDTO.getAccountPk());
				System.out.println("[로그] ReviewDAO SELECT_MY_REVIEW_LIST : " + datas);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					// reviewPk, itemPk, reviewTitle, reviewStar,
					// reviewContent, itemName, itemImageUrl, itemPrice
					ReviewDTO data = new ReviewDTO();
					data.setReviewPk(rs.getInt("REVIEW_PK"));
					data.setItemPk(rs.getInt("ITEM_PK"));
					data.setReviewTitle(rs.getString("REVIEW_TITLE"));
					data.setReviewContent(rs.getString("REVIEW_CONTENT"));
					data.setReviewStar(rs.getInt("REVIEW_STAR"));
					data.setItemName(rs.getString("ITEM_NAME"));
					data.setItemImageUrl(rs.getString("ITEM_IMAGE_URL"));
					data.setItemPrice(rs.getInt("ITEM_PRICE"));
					System.out.println("[로그] ReviewDAO selectAll  성공 : " + data);
					datas.add(data);
				}
			}
			/*
			 * ResultSet rs = pstmt.executeQuery(); while(rs.next()) { //reviewPk, itemPk,
			 * reviewTitle, reviewStar, //reviewContent, itemName, itemImageUrl, itemPrice
			 * ReviewDTO data = new ReviewDTO(); data.setReviewPk(rs.getInt("REVIEW_PK"));
			 * data.setItemPk(rs.getInt("ITEM_PK"));
			 * data.setReviewTitle(rs.getString("REVIEW_TITLE"));
			 * data.setReviewContent(rs.getString("REVIEW_CONTENT"));
			 * data.setReviewStar(rs.getInt("REVIEW_STAR"));
			 * data.setItemName(rs.getString("ITEM_NAME"));
			 * data.setItemImageUrl(rs.getString("ITEM_IMAGE_URL"));
			 * data.setItemPrice(rs.getInt("Item_price"));
			 * System.out.println("[로그] ReviewDAO selectAll  성공 : " + reviewDTO);
			 * datas.add(data); }
			 */

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[에러] ReviewDAO selectAll 실패 : " + reviewDTO);
		}
		JDBCUtil.disconnect(conn, pstmt);
		return datas;
	}

	public ReviewDTO selectOne(ReviewDTO reviewDTO) {
		ReviewDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			// 상품별 리뷰 목록 가져오기
			if (reviewDTO.getCondition().equals("SELECT_ITEM_REVIEW_COUNT")) {
				pstmt = conn.prepareStatement(SELECT_ITEM_REVIEW_COUNT);
				pstmt.setInt(1, reviewDTO.getItemPk());
				System.out.println("[로그] ReviewDAO SELECT_ITEM_REVIEW_COUNT" + data);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					data = new ReviewDTO();
					data.setReviewTotalCount(rs.getInt("REVIEW_TOTAL_COUNT"));
					System.out.println("[로그] AddressDAO selectOne 성공 : " + data);
				}
			}
			// 리뷰수정전 기존 리뷰 정보 가져오기
			else if (reviewDTO.getCondition().equals("SELECT_UPDATE_REVIEW_DATA")) {
				pstmt = conn.prepareStatement(SELECT_UPDATE_REVIEW_DATA);
				pstmt.setInt(1, reviewDTO.getReviewPk());
				pstmt.setInt(2, reviewDTO.getAccountPk());
				ResultSet rs = pstmt.executeQuery();
				System.out.println("[로그] ReviewDAO SELECT_UPDATE_REVIEW_DATA" + data);
				if (rs.next()) {
					data = new ReviewDTO();
					data.setReviewAccountName(rs.getString("ACCOUNT_NAME"));
					data.setReviewTitle(rs.getString("REVIEW_TITLE"));
					data.setReviewStar(rs.getInt("REVIEW_STAR"));
					data.setReviewContent(rs.getString("REVIEW_CONTENT"));
					System.out.println("[로그] AddressDAO selectOne 성공 : " + data);
				}

			}
			// 상품 리뷰 존재 확인
			else if (reviewDTO.getCondition().equals("SELECT_EXIST_REVIEW_BY_ACCOUNT_ITEM")) {
				pstmt = conn.prepareStatement(SELECT_EXIST_REVIEW_BY_ACCOUNT_ITEM);
				pstmt.setInt(2, reviewDTO.getAccountPk());
				pstmt.setInt(1, reviewDTO.getItemPk());
				System.out.println("[로그] ReviewDAO SELECT_EXIST_REVIEW_BY_ACCOUNT_ITEM" + data);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					data = new ReviewDTO();
					data.setReviewTotalCount(rs.getInt("REVIEW_TOTAL_COUNT"));
					System.out.println("[로그] AddressDAO selectOne 성공 : " + data);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[에러] ReviewDAO selectOne 실패 : " + data);
		}
		JDBCUtil.disconnect(conn, pstmt);
		return data;
	}

	public boolean insert(ReviewDTO reviewDTO) {
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 리뷰 등록하기
			if (reviewDTO.getCondition().equals("REVIEW_WRITE")) {
				pstmt = conn.prepareStatement(REVIEW_WRITE);
				pstmt.setInt(1, reviewDTO.getAccountPk());
				pstmt.setInt(2, reviewDTO.getItemPk());
				pstmt.setString(3, reviewDTO.getReviewTitle());
				pstmt.setString(4, reviewDTO.getReviewContent());
				pstmt.setInt(5, reviewDTO.getReviewStar());
				
				int result = pstmt.executeUpdate();
				if (result <= 0) {
					System.out.println("[로그] ReviewDTO insert 성공 : " + reviewDTO);
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] AddressDTO insert 실패 : " + reviewDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}

	public boolean update(ReviewDTO reviewDTO) {
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 리뷰 수정하기
			if (reviewDTO.getCondition().equals("REVIEW_WRITE_EDIT")) {
				pstmt = conn.prepareStatement(REVIEW_WRITE_EDIT);
				pstmt.setString(1, reviewDTO.getReviewTitle());
				pstmt.setString(2, reviewDTO.getReviewContent());
				pstmt.setInt(3, reviewDTO.getReviewStar());
				pstmt.setInt(4, reviewDTO.getAccountPk());
				pstmt.setInt(5, reviewDTO.getItemPk());
				int result = pstmt.executeUpdate();
				if (result < 0) {
					System.out.println("[로그] ReviewDAO update 성공 : " + reviewDTO);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] ReviewDAO update 실패 : " + reviewDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}

	public boolean delete(ReviewDTO reviewDTO) {
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 회원고유번호에 대한 리뷰 모두삭제
			if (reviewDTO.getCondition().equals("DELETE_ALL_REVIEW_BY_ACCOUNT_PK")) {
				pstmt = conn.prepareStatement(DELETE_ALL_REVIEW_BY_ACCOUNT_PK);
				pstmt.setInt(1, reviewDTO.getAccountPk());

				System.out.println("[로그] ReviewDAO DELETE_ALL_REVIEW_BY_ACCOUNT_PK : " + reviewDTO);
			}
			// 회원고유번호에 대한 리뷰 개별삭제
			else if (reviewDTO.getCondition().equals("DELETE_BY_REVIEW_PK")) {
				pstmt = conn.prepareStatement(DELETE_BY_REVIEW_PK);
				pstmt.setInt(1, reviewDTO.getReviewPk());
				System.out.println("[로그] ReviewDAO DELETE_BY_REVIEW_PK : " + reviewDTO);
			}
			int result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] reviewDTO delete 실패 : " + reviewDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}

}
