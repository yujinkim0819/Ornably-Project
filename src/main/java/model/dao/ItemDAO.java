package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.ItemDTO;

public class ItemDAO {

	private static final String SELECT_ALL_ITEM = "SELECT " + "ITEM_PK, " + "ITEM_NAME, " + "ITEM_PRICE, "
			+ "ITEM_STOCK	, " + "ITEM_IMAGE_URL, " + "AVG_STAR " + "FROM ( " + "SELECT " + "I.ITEM_PK, "
			+ "I.ITEM_NAME, " + "I.ITEM_PRICE, " + "I.ITEM_STOCK, " + "I.ITEM_IMAGE_URL, "
			+ "NVL(ROUND(AVG(R.REVIEW_STAR), 2), 0) AS AVG_STAR, " + "ROW_NUMBER() OVER ( "
			+ "ORDER BY (CASE WHEN I.ITEM_STOCK = 0 THEN 1 ELSE 0 END), I.ITEM_PK DESC " + ") AS RN " + "FROM ITEM I "
			+ "LEFT JOIN REVIEW R " + "ON I.ITEM_PK = R.ITEM_PK " + "GROUP BY " + "I.ITEM_PK, " + "I.ITEM_NAME, "
			+ "I.ITEM_PRICE, " + "I.ITEM_STOCK, " + "I.ITEM_IMAGE_URL " + ") " + "WHERE RN BETWEEN ? AND ?";

	private static final String SELECT_ALL_WISHLIST_ITEM = "SELECT I.ITEM_PK, I.ITEM_NAME, I.ITEM_PRICE, I.ITEM_IMAGE_URL "
			+ "FROM ITEM I " + "INNER JOIN WISHLIST W " + "ON I.ITEM_PK = W.ITEM_PK " + "WHERE W.ACCOUNT_PK = ?";

	private static final String SELECT_ALL_ITEM_NAME_SERACH = "SELECT " + "ITEM_PK, " + "ITEM_NAME, " + "ITEM_PRICE, "
			+ "ITEM_STOCK, " + "ITEM_IMAGE_URL, " + "AVG_STAR " + "FROM ( " + "SELECT " + "I.ITEM_PK, "
			+ "I.ITEM_NAME, " + "I.ITEM_PRICE, " + "I.ITEM_STOCK, " + "I.ITEM_DESCRIPTION, " + "I.ITEM_IMAGE_URL, "
			+ "NVL(ROUND(AVG(R.REVIEW_STAR), 2), 0) AS AVG_STAR, " + "ROW_NUMBER() OVER ( "
			+ "ORDER BY (CASE WHEN ITEM_STOCK = 0 THEN 1 ELSE 0 END), I.ITEM_PK DESC " + ") AS RN " + "FROM ITEM I "
			+ "LEFT JOIN REVIEW R " + "ON I.ITEM_PK = R.ITEM_PK " + "WHERE I.ITEM_NAME LIKE ?" + "GROUP BY "
			+ "I.ITEM_PK, " + "I.ITEM_NAME, " + "I.ITEM_PRICE, " + "I.ITEM_STOCK, " + "I.ITEM_DESCRIPTION, "
			+ "I.ITEM_IMAGE_URL " + ") " + "WHERE RN BETWEEN ? AND ?";

	private static final String SELECT_ALL_ITEM_REVIEW_STAR = "SELECT " + "ITEM_PK, " + "ITEM_NAME, " + "ITEM_PRICE, "
			+ "ITEM_STOCK, " + "ITEM_IMAGE_URL, " + "AVG_STAR " + "FROM ( " + "SELECT " + "I.ITEM_PK, "
			+ "I.ITEM_NAME, " + "I.ITEM_PRICE, " + "I.ITEM_STOCK, " + "I.ITEM_IMAGE_URL, "
			+ "NVL(ROUND(AVG(R.REVIEW_STAR), 2), 0) AS AVG_STAR, " + "ROW_NUMBER() OVER ( " + "ORDER BY "
			+ "(CASE WHEN I.ITEM_STOCK = 0 THEN 1 ELSE 0 END), " + "NVL(ROUND(AVG(R.REVIEW_STAR), 2), 0) DESC, "
			+ "COUNT(R.REVIEW_PK) DESC, " + "I.ITEM_PK DESC " + ") AS RN " + "FROM ITEM I " + "LEFT JOIN REVIEW R "
			+ "ON I.ITEM_PK = R.ITEM_PK " + "WHERE I.ITEM_NAME LIKE ?" + // 검색어 유지
			"GROUP BY " + "I.ITEM_PK, " + "I.ITEM_NAME, " + "I.ITEM_PRICE, " + "I.ITEM_STOCK, " + "I.ITEM_IMAGE_URL "
			+ ") " + "WHERE RN BETWEEN ? AND ?";

	private static final String SELECT_ONE_ITEM = "SELECT " + "I.ITEM_PK, " + "I.ITEM_NAME, " + "I.ITEM_PRICE, "
			+ "I.ITEM_STOCK, " + "I.ITEM_DESCRIPTION, " + "I.ITEM_IMAGE_URL, "
			+ "NVL(ROUND(AVG(R.REVIEW_STAR), 2), 0) AS AVG_STAR " + "FROM ITEM I " + "LEFT JOIN REVIEW R "
			+ "ON I.ITEM_PK = R.ITEM_PK " + "WHERE I.ITEM_PK = ? " + "GROUP BY " + "I.ITEM_PK, " + "I.ITEM_NAME, "
			+ "I.ITEM_PRICE, " + "I.ITEM_STOCK, " + "I.ITEM_DESCRIPTION, " + "I.ITEM_IMAGE_URL";

	private static final String TOTAL_ITEM_COUNT = "SELECT COUNT(*) AS TOTAL_COUNT FROM ITEM WHERE ITEM_NAME LIKE ?";
	
	// 재고 조회 & 이름 조회
	private static final String ITEM_STOCK_ENOUGH = 
			"SELECT ITEM_PK, ITEM_NAME FROM ITEM "
			+ "WHERE ITEM_PK = ? AND ITEM_STOCK >= ?";

	// 재고 감소
	private static final String BUY_ITEM = 
			"UPDATE ITEM SET ITEM_STOCK = ITEM_STOCK - ? WHERE ITEM_PK = ?";
	
	// 재고 감소 복귀
	private static final String ROLLBACK_ITEM_STOCK =
			"UPDATE ITEM SET ITEM_STOCK = ITEM_STOCK + ? WHERE ITEM_PK = ?";
	
	private static final String DECREASE_ITEM_STOCK_BY_CART = "UPDATE ITEM I " + "SET ITEM_STOCK = ITEM_STOCK - ("
			+ "	SELECT CART_COUNT " + "	FROM CART C " + "	WHERE C.ITEM_PK = I.ITEM_PK " + "	AND ACCOUNT_PK = ?"
			+ ")" + "WHERE EXISTS (" + "	SELECT 1 " + "	FROM CART C " + "	WHERE C.ITEM_PK = I.ITEM_PK "
			+ "	AND ACCOUNT_PK = ? " + ")";
	
	

	public ArrayList<ItemDTO> selectAll(ItemDTO itemDTO) {
		System.out.println("[로그] ItemDAO_selectAll_itemDTO : " + itemDTO);
		ArrayList<ItemDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 상품 전체 보기 (pk 순으로)
			if (itemDTO.getCondition().equals("SELECT_ALL_ITEM")) {
				pstmt = conn.prepareStatement(SELECT_ALL_ITEM);
				pstmt.setInt(1, itemDTO.getStartItemNum());// 한 페이지에 시작 아이템
				pstmt.setInt(2, itemDTO.getEndItemNum()); // 한 페이지에 마지막 아이템
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					ItemDTO data = new ItemDTO();
					data.setItemPk(rs.getInt("ITEM_PK"));
					data.setItemName(rs.getString("ITEM_NAME"));
					data.setItemStock(rs.getInt("ITEM_STOCK"));
					data.setItemPrice(rs.getInt("ITEM_PRICE"));
					data.setItemImageUrl(rs.getString("ITEM_IMAGE_URL"));
					data.setItemStar(rs.getDouble("AVG_STAR"));
					datas.add(data);
				}
			}
			// 검색 아이템 전부 보기 (pk 순으로)
			else if (itemDTO.getCondition().equals("SELECT_ALL_ITEM_NAME_SERACH")) {
				pstmt = conn.prepareStatement(SELECT_ALL_ITEM_NAME_SERACH);
				pstmt.setString(1, "%" + itemDTO.getKeyword() + "%");
				pstmt.setInt(2, itemDTO.getStartItemNum());
				pstmt.setInt(3, itemDTO.getEndItemNum());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					ItemDTO data = new ItemDTO();
					data.setItemPk(rs.getInt("ITEM_PK"));
					data.setItemName(rs.getString("ITEM_NAME"));
					data.setItemStock(rs.getInt("ITEM_STOCK"));
					data.setItemPrice(rs.getInt("ITEM_PRICE"));
					data.setItemImageUrl(rs.getString("ITEM_IMAGE_URL"));
					data.setItemStar(rs.getDouble("AVG_STAR"));
					datas.add(data);
				}
			} else if (itemDTO.getCondition().equals("SELECT_ALL_ITEM_REVIEW_STAR")) {
				pstmt = conn.prepareStatement(SELECT_ALL_ITEM_REVIEW_STAR);
				pstmt.setString(1, "%" + itemDTO.getKeyword() + "%");
				pstmt.setInt(2, itemDTO.getStartItemNum());
				pstmt.setInt(3, itemDTO.getEndItemNum());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					ItemDTO data = new ItemDTO();
					data.setItemPk(rs.getInt("ITEM_PK"));
					data.setItemName(rs.getString("ITEM_NAME"));
					data.setItemStock(rs.getInt("ITEM_STOCK"));
					data.setItemPrice(rs.getInt("ITEM_PRICE"));
					data.setItemImageUrl(rs.getString("ITEM_IMAGE_URL"));
					data.setItemStar(rs.getDouble("AVG_STAR"));
					datas.add(data);
				}
			} else if (itemDTO.getCondition().equals("SELECT_ALL_WISHLIST_ITEM")) {
				pstmt = conn.prepareStatement(SELECT_ALL_WISHLIST_ITEM);
				pstmt.setInt(1, itemDTO.getAccountPk());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					ItemDTO data = new ItemDTO();
					data.setItemPk(rs.getInt("ITEM_PK"));
					data.setItemName(rs.getString("ITEM_NAME"));
					data.setItemPrice(rs.getInt("ITEM_PRICE"));
					data.setItemImageUrl(rs.getString("ITEM_IMAGE_URL"));
					datas.add(data);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[로그][오류] ItemDAO_selectAll_datas : " + datas);
			return datas = null;
		}

		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("[로그] ItemDAO_selectAll_datas : " + datas);
		return datas;
	}

	public ItemDTO selectOne(ItemDTO itemDTO) {
		System.out.println("[로그] ItemDAO_selectOne_itemDTO : " + itemDTO);
		ItemDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			if (itemDTO.getCondition().equals("ITEM_STOCK_ENOUGH")) {
				System.out.println();
				pstmt = conn.prepareStatement(ITEM_STOCK_ENOUGH);
				pstmt.setInt(1, itemDTO.getItemPk());
				pstmt.setInt(2, itemDTO.getItemCount());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					data = new ItemDTO();
					data.setItemPk(rs.getInt("ITEM_PK"));
			        data.setItemName(rs.getString("ITEM_NAME")); // 이름 세팅!
				}
			} else if (itemDTO.getCondition().equals("TOTAL_ITEM_COUNT")) {
				pstmt = conn.prepareStatement(TOTAL_ITEM_COUNT);
				pstmt.setString(1, '%' + itemDTO.getKeyword() + '%');
				rs = pstmt.executeQuery();
				if (rs.next()) {
					data = new ItemDTO();
					data.setItemTotalCount(rs.getInt("TOTAL_COUNT"));
				}
			}
			// 상품 상세보기
			else {
				pstmt = conn.prepareStatement(SELECT_ONE_ITEM);
				pstmt.setInt(1, itemDTO.getItemPk());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					data = new ItemDTO();
					if (itemDTO.getCondition().equals("SELECT_ITEM_DETAIL_INFO")) { // 상품 상세 보기
						data.setItemPk(rs.getInt("ITEM_PK"));
						data.setItemName(rs.getNString("ITEM_NAME"));
						data.setItemPrice(rs.getInt("ITEM_PRICE"));
						data.setItemStock(rs.getInt("ITEM_STOCK"));
						data.setItemContent(rs.getString("ITEM_DESCRIPTION"));
						data.setItemImageUrl(rs.getString("ITEM_IMAGE_URL"));
						data.setItemStar(rs.getDouble("AVG_STAR"));

					} else if (itemDTO.getCondition().equals("SELECT_ITEM_NAME")) { // 상품 이름만 전송
						data.setItemName(rs.getNString("ITEM_NAME"));

					} else if (itemDTO.getCondition().equals("SELECT_ITEM_REVIEW_INFO")) { // 리뷰 작성 시 상품 정보
						data.setItemPk(rs.getInt("ITEM_PK"));
						data.setItemImageUrl(rs.getString("ITEM_IMAGE_URL"));
						data.setItemName(rs.getString("ITEM_NAME"));
						data.setItemPrice(rs.getInt("ITEM_PRICE"));
						data.setItemStar(rs.getDouble("AVG_STAR"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[로그][오류] ItemDAO_selectOne : data " + data);
		}
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("[로그] ItemDAO_selectOne : data " + data);
		return data;
	}

	public boolean update(ItemDTO itemDTO) {
		System.out.println("[로그] ItemDAO_update_itemDTO : " + itemDTO);
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			if (itemDTO.getCondition().equals("BUY_ITEM")) {
				pstmt = conn.prepareStatement(BUY_ITEM);
				pstmt.setInt(1, itemDTO.getItemCount());
				pstmt.setInt(2, itemDTO.getItemPk());
			} 
			else if (itemDTO.getCondition().equals("DECREASE_ITEM_STOCK_BY_CART")) {
				pstmt = conn.prepareStatement(DECREASE_ITEM_STOCK_BY_CART);
				pstmt.setInt(1, itemDTO.getAccountPk());
				pstmt.setInt(2, itemDTO.getAccountPk());
			}
			else if (itemDTO.getCondition().equals("ROLLBACK_ITEM_STOCK")) {
			    pstmt = conn.prepareStatement(ROLLBACK_ITEM_STOCK);
			    pstmt.setInt(1, itemDTO.getItemCount());
			    pstmt.setInt(2, itemDTO.getItemPk());
			}
			int result = pstmt.executeUpdate();
			if (result < 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] update 실패, itemDTO = " + itemDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}

	private boolean insert(ItemDTO itemDTO) {
		return false;
	}

	private boolean delete(ItemDTO itemDTO) {
		return false;
	}

}
