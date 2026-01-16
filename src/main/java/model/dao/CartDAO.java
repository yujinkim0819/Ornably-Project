package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.CartDTO;

public class CartDAO {
	
	//장바구니PK, 아이템PK, 아이템개수, 아이템 이름, 아이템 가격, 아이템 이미지
	private static final String SELECT_ALL_CART =
		"SELECT CART_PK, C.ITEM_PK, C.CART_COUNT, ITEM_PRICE, ITEM_IMAGE_URL, ITEM_NAME "
		+ "FROM CART C INNER JOIN ITEM I "
		+ "ON C.ITEM_PK = I.ITEM_PK WHERE ACCOUNT_PK = ?";
		/*"SELECT" +
		    "C.CART_PK," +//        -- 장바구니 PK
		    "C.ITEM_PK," + //          -- 아이템 PK
		    "C.CART_COUNT," +   //     -- 아이템 개수
		    "I.ITEM_NAME," + //        -- 아이템 이름
		    "I.ITEM_IMAGE_URL," +//    -- 아이템 이미지
		    "(I.ITEM_PRICE * C.CART_COUNT) AS TOTAL_PRICE" +// -- 아이템 총 가격
		"FROM CART C" +
		"JOIN ITEM I" +
		    "ON C.ITEM_PK = I.ITEM_PK" +
		"WHERE C.ACCOUNT_PK = ?" +
		"ORDER BY C.CART_PK DESC";*/ 
	
	private static final String INSERT_CART_OR_UPDATE = 
		            "MERGE INTO CART C " +
		    	    "USING ( " +
		    	    "    SELECT ? AS ACCOUNT_PK, " +
		    	    "           ? AS ITEM_PK, " +
		    	    "           ? AS ADD_COUNT " +
		    	    "    FROM DUAL " +
		    	    ") S " +
		    	    "ON ( " +
		    	    "    C.ACCOUNT_PK = S.ACCOUNT_PK " +
		    	    "    AND C.ITEM_PK = S.ITEM_PK " +
		    	    ") " +
		    	    "WHEN MATCHED THEN " +
		    	    "    UPDATE SET C.CART_COUNT = LEAST(C.CART_COUNT + S.ADD_COUNT,99) " +
		    	    "WHEN NOT MATCHED THEN " +
		    	    "    INSERT (CART_PK, ACCOUNT_PK, ITEM_PK, CART_COUNT) " +
		    	    "    VALUES (CART_SEQ.NEXTVAL, S.ACCOUNT_PK, S.ITEM_PK, S.ADD_COUNT)";
	
	private static final String UPDATE_CART_ITEM_COUNT = 
			"UPDATE CART " +
			"SET CART_COUNT = ? " +
			"WHERE CART_PK = ?";
	
	private static final String ADD_CART_ITEM_COUNT =
			"UPDATE CART " +
			"SET CART_COUNT = CART_COUNT + ? " +
			"WHERE CART_PK = ?";
	
	private static final String DELETE_CART_BY_BUY =
			"DELETE FROM CART " +
			"WHERE ACCOUNT_PK = ?";
	
	private static final String DELETE_CART_ITEM = 
			"DELETE FROM CART " +
			"WHERE CART_PK = ?";
			
	private static final String SELECT_ONE_ORDER_ITEMS_TOTAL_MONEY =
			"SELECT " +
		    	"NVL(SUM(C.CART_COUNT * I.ITEM_PRICE), 0) AS TOTAL_AMOUNT " +
		    "FROM CART C " +
		    "JOIN ITEM I " +
		    	"ON C.ITEM_PK = I.ITEM_PK " +
		    "WHERE C.ACCOUNT_PK = ?";
	
	public ArrayList<CartDTO> selectAll(CartDTO cartDTO){
		System.out.println("[로그] CartDAO_selectAll_cartDTO : " + cartDTO);
		ArrayList<CartDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 사용자의 장바구니에 존재하는 상품 데이터
			if(cartDTO.getCondition().equals("SELECT_ALL_ACCOUNT_CART")) {
				pstmt = conn.prepareStatement(SELECT_ALL_CART);
				pstmt.setInt(1, cartDTO.getAccountPk());
			}
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				CartDTO data = new CartDTO();
				data.setCartPk(rs.getInt("CART_PK"));
				data.setItemPk(rs.getInt("ITEM_PK"));
				data.setCount(rs.getInt("CART_COUNT"));
				data.setItemName(rs.getString("ITEM_NAME"));
				data.setItemImageUrl(rs.getString("ITEM_IMAGE_URL"));
				data.setItemPrice(rs.getInt("ITEM_PRICE"));
				datas.add(data);
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("[로그][오류] CartDAO_selectAll_datas : "+ datas);
		}
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("[로그] CartDAO_selectAll_datas : "+ datas);
		return datas;
	}
	
	public CartDTO selectOne(CartDTO cartDTO) {
		System.out.println("[로그] CartDAO_selectOne_cartDTO : "+ cartDTO);
		CartDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 장바구니의 총 금액
			if(cartDTO.getCondition().equals("SELECT_ONE_ORDER_ITEMS_TOTAL_MONEY")) {
				pstmt = conn.prepareStatement(SELECT_ONE_ORDER_ITEMS_TOTAL_MONEY);
				pstmt.setInt(1, cartDTO.getAccountPk());
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					data = new CartDTO();
					data.setTotalAmount(rs.getInt("TOTAL_AMOUNT"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.disconnect(conn, pstmt);
		return data;
	}
	
	public boolean insert(CartDTO cartDTO) {
		System.out.println("[로그] CartDAO_insert_cartDTO : " + cartDTO);
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 장바구니 추가 (이미 같은 상품 존재 시 개수만 +)
			if(cartDTO.getCondition().equals("ADD_CART_ITEM")) {
				pstmt = conn.prepareStatement(INSERT_CART_OR_UPDATE);	
				pstmt.setInt(1, cartDTO.getAccountPk());
				pstmt.setInt(2, cartDTO.getItemPk());
				pstmt.setInt(3, cartDTO.getCount());
				int result = pstmt.executeUpdate();
				if(result <= 0) {
					return false;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] insert 실패, cartDTO = "+cartDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}
	
	public boolean update(CartDTO cartDTO) {
		System.out.println("[로그] CartDAO_update_cartDTO : " + cartDTO);
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 장바구니 안에서 상품 개수 조정
			if(cartDTO.getCondition().equals("UPDATE_CART_ITEM_COUNT")) {
				pstmt = conn.prepareStatement(UPDATE_CART_ITEM_COUNT);
				pstmt.setInt(1, cartDTO.getNewCount());
				pstmt.setInt(2, cartDTO.getCartPk());
			}
			else if(cartDTO.getCondition().equals("ADD_CART_ITEM_COUNT")) {
				pstmt = conn.prepareStatement(ADD_CART_ITEM_COUNT);
				pstmt.setInt(1, cartDTO.getCount());
				pstmt.setInt(2, cartDTO.getCartPk());
			}
			int result = pstmt.executeUpdate();
			if(result <= 0) {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] update 실패, cartDTO = "+cartDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}
	
	public boolean delete(CartDTO cartDTO) {
		System.out.println("[로그] CartDAO_delete_cartDTO : "+cartDTO);
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			if(cartDTO.getCondition().equals("DELETE_ALL_CART_BY_ACCOUNT_PK")) {
				pstmt = conn.prepareStatement(DELETE_CART_BY_BUY);
				pstmt.setInt(1, cartDTO.getAccountPk());
			}
			else if(cartDTO.getCondition().equals("DELETE_BY_CART_PK")) {
				pstmt = conn.prepareStatement(DELETE_CART_ITEM);
				pstmt.setInt(1, cartDTO.getCartPk());
			}
			int result = pstmt.executeUpdate();
			if(result < 0) {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] delete 실패, cartDTO = "+cartDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}
	
}
