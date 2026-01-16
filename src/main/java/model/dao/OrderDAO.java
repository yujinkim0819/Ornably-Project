package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.OrderDTO;


public class OrderDAO {
	
	private final static String SELECT_ALL_ORDERS = 
		    		"SELECT " +
		    		"    O.ORDERS_PK, " +
		    	    "    O.ORDERS_DATE, " +
		    	    "    O.ORDERS_STATUS, " +
		    	    "    MIN(I.ITEM_NAME) AS ITEM_NAME, " +
		    	    "    SUM(OI.ORDERS_ITEM_PRICE * OI.ORDERS_ITEM_QUANTITY) AS TOTAL_PRICE " +
		    	    "FROM ORDERS O " +
		    	    "JOIN ORDERS_ITEM OI " +
		    	    "    ON O.ORDERS_PK = OI.ORDERS_PK " +
		    	    "JOIN ITEM I " +
		    	    "    ON OI.ITEM_PK = I.ITEM_PK " +
		    	    "WHERE O.ACCOUNT_PK = ? " +
		    	    "GROUP BY " +
		    	    "    O.ORDERS_PK, " +
		    	    "    O.ORDERS_DATE, " +
		    	    "    O.ORDERS_STATUS " +
		    	    "ORDER BY O.ORDERS_DATE DESC";
	
	private final static String INSERT_ORDERS = 
			"INSERT INTO ORDERS (ORDERS_PK, ACCOUNT_PK, ORDERS_DATE, ADDRESS_NAME) " 
			+ "SELECT "
			+ "ORDERS_SEQ.NEXTVAL, "
			+ "?, "
			+ "SYSDATE, "
			+ "ADDRESS_NAME " +
			  "FROM ADDRESS WHERE ADDRESS_PK = ?";
	
	private final static String SELECT_ORDERS_PK = 
			"SELECT ORDERS_PK "
			+ "FROM ( "
			+ "    SELECT ORDERS_PK "
			+ "    FROM ORDERS "
			+ "    WHERE ACCOUNT_PK = ? "
			+ "    ORDER BY ORDERS_PK DESC "
			+ ") "
			+ "WHERE ROWNUM = 1";
			
	/*"SELECT ORDERS_PK " +
			"FROM ORDERS " +
			"WHERE ACCOUNT_PK = ? " +
			"AND ROWNUM = 1";*/
	

	
	private final static String DELETE_ONE_ORDERS = 
			"DELETE FROM ORDERS WHERE ACCOUNT_PK = ?";

	
	
	public ArrayList<OrderDTO> selectAll(OrderDTO orderDTO){
		System.out.println("[로그] OrderDAO_selectAll_orderDTO : " + orderDTO);
		ArrayList<OrderDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 마이 페이지 들어갔을 떄 주문내역 전체 출력
			if(orderDTO.getCondition().equals("SELECT_ALL_ACCOUNT_PK_ORDERS")) {
				pstmt = conn.prepareStatement(SELECT_ALL_ORDERS);
				pstmt.setInt(1, orderDTO.getAccountPk());
			}
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderDTO data = new OrderDTO();
				data.setOrderPk(rs.getInt("ORDERS_PK"));
				data.setOrderDate(rs.getDate("ORDERS_DATE"));
				data.setOrderStatus(rs.getString("ORDERS_STATUS"));
				data.setOrderSignatureItemName(rs.getString("ITEM_NAME"));
				data.setOrderTotalAmount(rs.getInt("TOTAL_PRICE"));
				datas.add(data);
			}
		} catch (SQLException e) {
			System.out.println("[로그][오류] orderDTO_selectAll_datas : " + datas);
			e.printStackTrace();
		}
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("[로그] orderDTO_selectAll_datas : " + datas);
		return datas;
	}
	
	public OrderDTO selectOne(OrderDTO orderDTO) {
		System.out.println("[로그] OrderDAO_selectOne_orderDTO : "+ orderDTO);
		OrderDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 주문내역 생성 후 해당 주문내역의 주문상세 생성을 위한 주문내역 PK 보내줌
			if(orderDTO.getCondition().equals("SELECT_ONE_ORDER_PK")) {
				pstmt = conn.prepareStatement(SELECT_ORDERS_PK);
				pstmt.setInt(1, orderDTO.getAccountPk());
				ResultSet rs = pstmt.executeQuery();

				if(rs.next()) {
					data = new OrderDTO();
					data.setOrderPk(rs.getInt("ORDERS_PK"));
				}
			}
			
		} catch(SQLException e) {
			System.out.println("[로그][오류]orderDAO_selectOne_data : " + data);
			e.printStackTrace();
		}
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("[로그] orderDAO_selectOne_data : " + data);
		return data;
	}
	
	public boolean insert(OrderDTO orderDTO) {
		System.out.println("[로그] OrderDAO_insert_orderDTO : " + orderDTO);
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 주문내역 생성
			if(orderDTO.getCondition().equals("PREPARING")) {
				pstmt = conn.prepareStatement(INSERT_ORDERS);
				pstmt.setInt(1, orderDTO.getAccountPk());
				pstmt.setInt(2, orderDTO.getAddressPk());
				int result = pstmt.executeUpdate();
				if(result <= 0) {
					System.out.println("[로그] insert 실패, orderDTO = "+orderDTO);
					return false;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] insert 실패, orderDTO = "+orderDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}
	public boolean delete(OrderDTO orderDTO) {
		System.out.println("[로그] OrderDAO_delete_orderDTO : " + orderDTO);
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try{
			// 회원 탈퇴 시 해당 회원 주문내역 전부 삭제
			if(orderDTO.getCondition().equals("DELETE_ALL_ORDER_BY_ACCOUNT_PK")) {
				pstmt = conn.prepareStatement(DELETE_ONE_ORDERS);
				pstmt.setInt(1, orderDTO.getAccountPk());
				int result = pstmt.executeUpdate();
				if(result < 0) {
					System.out.println("[로그] delete 실패, orderDTO = "+orderDTO);
					return false;
				}
			} 
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] delete 실패, orderDTO = "+orderDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}
}
