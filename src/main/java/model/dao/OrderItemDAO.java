package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.OrderItemDTO;



public class OrderItemDAO {
	
	private static final String SELECT_ALL_ORDERS_ITEM = 
			 "SELECT DISTINCT ORD.ORDERS_PK, ORD.ORDERS_ITEM_PK, I.ITEM_NAME, I.ITEM_PK, " +
					    "       ORD.ORDERS_ITEM_QUANTITY, ORD.ORDERS_ITEM_PRICE, " +
					    "       I.ITEM_IMAGE_URL, " +
					    "       CASE " +
					    "           WHEN EXISTS ( " +
					    "               SELECT 1 FROM REVIEW R " +
					    "               WHERE R.ITEM_PK = I.ITEM_PK " +
					    "               AND R.ACCOUNT_PK = ? " +
					    "           ) THEN 1 " +
					    "           ELSE 0 " +
					    "       END AS ISREVIEWED " +
					    "FROM ( " +
					    "    SELECT O.ORDERS_PK, OI.ORDERS_ITEM_PK, OI.ITEM_PK, " +
					    "           OI.ORDERS_ITEM_QUANTITY, OI.ORDERS_ITEM_PRICE " +
					    "    FROM ORDERS O " +
					    "    INNER JOIN ORDERS_ITEM OI " +
					    "    ON O.ORDERS_PK = OI.ORDERS_PK " +
					    ") ORD " +
					    "INNER JOIN ITEM I " +
					    "ON ORD.ITEM_PK = I.ITEM_PK "+
					    "WHERE ORD.ORDERS_PK = ? " +
						"ORDER BY ORD.ORDERS_ITEM_PK";
					/*"SELECT " +
					   " OI.ORDERS_ITEM_PK, " +
					   " OI.ORDERS_PK, " +
					   " I.ITEM_PK, " +
					   " I.ITEM_IMAGE_URL, " +      // 상품 이미지
					   " I.ITEM_NAME, " +       // 상품 이름
					   " OI.ORDERS_ITEM_PRICE, " + // 주문 시점 가격
					   " OI.ORDERS_ITEM_QUANTITY, " +  // 주문 수량
					   " CASE " +
					   "   WHEN EXISTS ( " +
					   "     SELECT 1 " +
					   "     FROM REVIEW R " +
					   "     WHERE R.ITEM_PK = I.ITEM_PK " +
					   "     AND R.ACCOUNT_PK = ? " +
					   "   ) THEN 1 " +
					   "   ELSE 0 " +
					   " END AS ISREVIEWED " +
					   "FROM ORDERS_ITEM OI " +
					   "JOIN ITEM I " +
					   " ON OI.ITEM_PK = I.ITEM_PK " +
					   "WHERE OI.ORDERS_PK = ? " +
					   "ORDER BY OI.ORDERS_ITEM_PK";*/
	
	private static final String INSERT_ORDERS_ITEM = 
			"INSERT INTO ORDERS_ITEM (" +
				    "ORDERS_ITEM_PK," + // PK
				    "ORDERS_PK," +   //  주문내역 PK
				    "ITEM_PK," + // 상품 PK
				    "ORDERS_ITEM_QUANTITY," +  // 수량
				    "ORDERS_ITEM_PRICE" +     // 주문 시점 총 가격
				") VALUES (" +
				    "ORDERS_ITEM_SEQ.NEXTVAL," +
				    "?," + //  ordersPk,
				    "?," + //   itemPk,
				    "?," + //   quantity,
				    "?" +    // itemPrice * quantity
				")";
	
	private static final String DELETE_OREDRS_ITEM =
			"DELETE FROM ORDERS_ITEM " +
			"WHERE ORDERS_PK IN (" + 
			    "SELECT ORDERS_PK FROM ORDERS WHERE ACCOUNT_PK = ?" +
			")";


	
	public ArrayList<OrderItemDTO> selectAll(OrderItemDTO orderItemDTO){
		System.out.println("[로그] OrderItemDAO_selectAll_orderItemDTO : " + orderItemDTO);
		ArrayList<OrderItemDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 주문내역에 해당 주문상세들 전체 출력
			// orderPk -> orderItemDTO[orderItemPk, itemImageUrl, 
			// itemName, orderItemPrice, orderItemQuantity, isReviewed]
			if(orderItemDTO.getCondition().equals("SELECT_ALL_ORDERS_ITEM")) {
				pstmt = conn.prepareStatement(SELECT_ALL_ORDERS_ITEM);
				pstmt.setInt(1, orderItemDTO.getAccountPk());
				pstmt.setInt(2, orderItemDTO.getOrderPk());
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					OrderItemDTO data = new OrderItemDTO();
					data.setOrderItemPk(rs.getInt("ORDERS_ITEM_PK"));
					data.setOrderPk(rs.getInt("ORDERS_PK"));
					data.setItemPk(rs.getInt("ITEM_PK"));
					data.setItemImageUrl(rs.getString("ITEM_IMAGE_URL"));
					data.setItemName(rs.getString("ITEM_NAME"));
					data.setOrderItemPrice(rs.getInt("ORDERS_ITEM_PRICE"));
					data.setOrderItemQuantity(rs.getInt("ORDERS_ITEM_QUANTITY"));
					data.setReviewed(rs.getInt("ISREVIEWED")==1 ? true : false);
					datas.add(data);
					System.out.println("OrderItemDTO:["+data+"]");
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("[로그][오류] OrderItemDAO_selectAll_datas : " + datas);
		}
		JDBCUtil.disconnect(conn, pstmt);
		System.out.println("[로그] OrderItemDAO_selectAll_datas : " + datas);
		return datas;
	}
	
	
	
	public boolean insert(OrderItemDTO orderItemDTO) {
		System.out.println("[로그] OrderItemDAO_insert_orderItemDTO :" + orderItemDTO);
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			if(orderItemDTO.getCondition().equals("INSERT_ORDERS_ITEM")) {
				pstmt = conn.prepareStatement(INSERT_ORDERS_ITEM);
				pstmt.setInt(1, orderItemDTO.getOrderPk());
				pstmt.setInt(2, orderItemDTO.getItemPk());
				pstmt.setInt(3, orderItemDTO.getOrderItemQuantity());
				pstmt.setInt(4, orderItemDTO.getOrderItemQuantity() * orderItemDTO.getOrderItemPrice());
			}
			int result = pstmt.executeUpdate();
			if(result<0) {
				return false;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] insert 실패, orderItemDTO = "+orderItemDTO);
			return false;
		}	
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}
	
	public boolean delete(OrderItemDTO orderItemDTO) {
		System.out.println("[로그] OrderItemDAO_delete_orderItemDTO : " + orderItemDTO);
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			if(orderItemDTO.getCondition().equals("DELETE_ALL_ORDER_ITEM_BY_ACCOUNT_PK")) {
				pstmt = conn.prepareStatement(DELETE_OREDRS_ITEM);
				pstmt.setInt(1, orderItemDTO.getAccountPk());
				int result = pstmt.executeUpdate();
				if(result <= 0) {
					return false;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] delete 실패, orderItemDTO = "+orderItemDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}

	private OrderItemDTO selectOne(OrderItemDTO orderItemDTO) {
		return null;
	}
	private boolean update(OrderItemDTO orderItemDTO) {
		return false;
	}
	
}

