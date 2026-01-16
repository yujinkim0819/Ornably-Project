package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.WishlistDTO;


public class WishlistDAO {
	//좋아요 목록
	private static final String SELECT_ALL_WISHLIST_BY_ACCOUNT_PK = 
			"SELECT ACCOUNT_PK,ITEM_PK,WISHLIST_PK FROM WISHLIST "
			+ "WHERE ACCOUNT_PK = ?";
	//좋아요 존재 여부확인
	private static final String SELECT_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK =
			"SELECT ITEM_PK, WISHLIST_PK, ACCOUNT_PK FROM WISHLIST "
			+ "WHERE ACCOUNT_PK = ?	AND ITEM_PK = ?";
	//좋아요 존재시 삭제
	private static final String DELETE_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK =
			"DELETE FROM WISHLIST "
			+ "WHERE ACCOUNT_PK = ? AND ITEM_PK = ?";
	//좋아요 목록 전체삭제
	private static final String DELETE_ALL_WISHLIST_BY_ACCOUNT_PK = 
			"DELETE FROM WISHLIST WHERE ACCOUNT_PK = ? ";
	//좋아요 만들기
	private static final String INSERT_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK =
			"INSERT INTO WISHLIST (WISHLIST_PK,ACCOUNT_PK,ITEM_PK) "
			+ "VALUES (WISHLIST_SEQ.NEXTVAL,?,?)";
	
	public ArrayList<WishlistDTO> selectAll(WishlistDTO wishlistDTO){
		ArrayList<WishlistDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {//회원고유번호 가져와서 해당 회원의 좋아요 목록 보여주기
			if(wishlistDTO.getCondition().equals("SELECT_ALL_WISHLIST_BY_ACCOUNT_PK")) {
				pstmt = conn.prepareStatement(SELECT_ALL_WISHLIST_BY_ACCOUNT_PK);
				pstmt.setInt(1, wishlistDTO.getAccountPk());
				System.out.println("[로그] WishlistDAO SELECT_ALL_WISHLIST_BY_ACCOUNT_PK : " + datas);
				
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					WishlistDTO data = new WishlistDTO();
					data.setAccountPk(rs.getInt("ACCOUNT_PK"));
					data.setWishlistPk(rs.getInt("WISHLIST_PK"));
					data.setItemPk(rs.getInt("ITEM_PK"));
					datas.add(data);
					System.out.println("[로그] WishlistDAO selectAll 성공 : " + data);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[에러] WishlistDAO selectAll 실패 : " + datas);
		}
		JDBCUtil.disconnect(conn, pstmt);
		return datas;
	}
	public WishlistDTO selectOne(WishlistDTO wishlistDTO) {
		WishlistDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		
		try {//회원고유번호가져와서 해당 회원이 좋아요 누른 상품고유번호가 존재하는지 확인
			if(wishlistDTO.getCondition().equals("SELECT_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK")) {
				pstmt = conn.prepareStatement(SELECT_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK);
				pstmt.setInt(1, wishlistDTO.getAccountPk());
				pstmt.setInt(2, wishlistDTO.getItemPk());
				System.out.println("[로그] WishlistDAO SELECT_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK : " + data);
				
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					data = new WishlistDTO();
					data.setAccountPk(rs.getInt("ACCOUNT_PK"));
					data.setItemPk(rs.getInt("ITEM_PK"));
					System.out.println("[로그] WishlistDAO selectOne 성공 : " + data);
				}
			}
	}catch (SQLException e) {
		e.printStackTrace();
		System.out.println("[에러] WishlistDAO selectOne 실패 : " + data);
	}
	JDBCUtil.disconnect(conn, pstmt);
	return data;
	}

	public boolean insert(WishlistDTO wishlistDTO) {
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {//회원이 좋아요 누른 아이템 생성하기
			pstmt = conn.prepareStatement(INSERT_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK);
			pstmt.setInt(1,wishlistDTO.getAccountPk());
			pstmt.setInt(2,wishlistDTO.getItemPk());
			System.out.println("[로그] WishlistDAO INSERT_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK : " + wishlistDTO);
			int result = pstmt.executeUpdate();
			if(result<=0) {
				System.out.println("[로그] WishlistDTO insert 성공 : " + wishlistDTO);
				return false;
			}
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("[로그] WishlistDTO insert 실패 : " + wishlistDTO);
			return false;
		}
		JDBCUtil.disconnect(conn,pstmt);
		return true;
	}
	private boolean update(WishlistDTO wishlistDTO) {
		return false;
	}
	public boolean delete(WishlistDTO wishlistDTO) {
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			//해당아이템에 회원의 좋아요가 있을시 삭제하기
			if(wishlistDTO.getCondition().equals("DELETE_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK")) {
				pstmt = conn.prepareStatement(DELETE_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK);
				pstmt.setInt(1,wishlistDTO.getAccountPk());
				pstmt.setInt(2,wishlistDTO.getItemPk());
				System.out.println("[로그] WishlistDAO DELETE_WISHLIST_BY_ACCOUNT_PK_AND_ITEM_PK : " + wishlistDTO);
			}
			//회원고유번호에 대한 모든 좋아요 삭제하기
			else if(wishlistDTO.getCondition().equals("DELETE_ALL_WISHLIST_BY_ACCOUNT_PK")) {
				pstmt = conn.prepareStatement(DELETE_ALL_WISHLIST_BY_ACCOUNT_PK);
				pstmt.setInt(1,wishlistDTO.getAccountPk());
				System.out.println("[로그] WishlistDAO DELETE_ALL_WISHLIST_BY_ACCOUNT_PK : " + wishlistDTO);
			}
			int result = pstmt.executeUpdate();
			if(result<0) {
				System.out.println("[로그] WishlistDTO delete 성공 : " + wishlistDTO);
				return false;
			}
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("[로그] WishlistDTO delete 실패 : " + wishlistDTO);
			return false;
		}
		JDBCUtil.disconnect(conn,pstmt);
		return true;
	}

}
