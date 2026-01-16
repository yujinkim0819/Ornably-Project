package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.AddressDTO;

public class AddressDAO {

	// 주소 모두 삭제
	private static final String DELETE_ALL_ADDRESS_BY_ACCOUNT_PK = "DELETE FROM ADDRESS WHERE ACCOUNT_PK = ?";
	// 주소 한개삭제
	private static final String DELETE_ADDRESS_BY_ADDRESS_PK = 
			"DELETE FROM ADDRESS " 
			+ "WHERE ADDRESS_PK = ?";
	// 모든주소목록
	private static final String SELECT_ALL_ADDRESS_BY_ACCOUNT_PK = "SELECT ACCOUNT_PK,ADDRESS_PK," + "ADDRESS_NAME,"
			+ "ADDRESS_IS_DEFAULT," + "ADDRESS_POSTAL_CODE," + "ADDRESS_LINE1, " + "ADDRESS_LINE2 " + "FROM ADDRESS "
			+ "WHERE ACCOUNT_PK = ?";
	// 기본주소지 보여주기
	private static final String SELECT_DEFAULT_ADDRESS = "SELECT ADDRESS_PK\r\n" + "FROM ADDRESS\r\n"
			+ "WHERE ACCOUNT_PK =  ? \r\n" + "  AND ADDRESS_IS_DEFAULT = '1'";
	private static final String SELECT_IS_DEFAULT_ADDRESS_BY_ADDRESS_PK=
			"SELECT ADDRESS_PK FROM ADDRESS WHERE ADDRESS_PK=? AND ADDRESS_IS_DEFAULT=1";

	// 기본주소지로 변경하기
	private static final String UPDATE_DEFAULT_ADDRESS =
			"UPDATE ADDRESS SET ADDRESS_IS_DEFAULT = 1 "
			+ "WHERE ADDRESS_PK = ? AND ACCOUNT_PK = ?";
	private static final String UPDATE_DEFAULT_ADDRESS_REMOVE = 
			"UPDATE ADDRESS SET ADDRESS_IS_DEFAULT = 0 "
			+ "WHERE ACCOUNT_PK=? AND ADDRESS_IS_DEFAULT = 1";

	// 주소지 등록하기
	private static final String INSERT_NEW_ADDRESS = "INSERT INTO ADDRESS ("
			+ "    ADDRESS_PK, ACCOUNT_PK, ADDRESS_NAME, ADDRESS_POSTAL_CODE,"
			+ "    ADDRESS_IS_DEFAULT, ADDRESS_LINE1, ADDRESS_LINE2)" + "VALUES (ADDRESS_SEQ.NEXTVAL, ?, ?, ?, ?, ?,?)";

	public ArrayList<AddressDTO> selectAll(AddressDTO addressDTO) {
		ArrayList<AddressDTO> datas = new ArrayList<>();
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 회원고유번호에 해당하는 모든 주소지 보여주기
			if (addressDTO.getCondition().equals("SELECT_ALL_ADDRESS_BY_ACCOUNT_PK")) {
				pstmt = conn.prepareStatement(SELECT_ALL_ADDRESS_BY_ACCOUNT_PK);
				pstmt.setInt(1, addressDTO.getAccountPk());
				System.out.println("[로그] AddressDAO SELECT_ALL_ADDRESS_BY_ACCOUNT_PK : " + datas);

				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					AddressDTO data = new AddressDTO();
					data.setAccountPk(rs.getInt("ACCOUNT_PK"));
					data.setAddressPk(rs.getInt("ADDRESS_PK"));
					data.setAddressName(rs.getString("ADDRESS_NAME"));
					data.setDefaultAddress(rs.getBoolean("ADDRESS_IS_DEFAULT"));
					data.setPostalCode(rs.getString("ADDRESS_POSTAL_CODE"));
					data.setRegionAddress(rs.getString("ADDRESS_LINE1"));
					data.setDetailAddress(rs.getString("ADDRESS_LINE2"));
					datas.add(data);
					System.out.println("[로그] AddressDAO selectAll 성공 : " + data);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[에러] AddressDAO selectAll 실패 : " + datas);
		}
		JDBCUtil.disconnect(conn, pstmt);
		return datas;
	}

	public AddressDTO selectOne(AddressDTO addressDTO) {
		AddressDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			// 회원고유번호가져와서 해당 회원의 기본주소지 보여주기
			if (addressDTO.getCondition().equals("SELECT_DEFAULT_ADDRESS")) {
				pstmt = conn.prepareStatement(SELECT_DEFAULT_ADDRESS);
				pstmt.setInt(1, addressDTO.getAccountPk());
				System.out.println("[로그] AddressDAO 기본주소 조회 실행 - accountPk=" + addressDTO.getAccountPk());

				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					data = new AddressDTO();
					data.setAddressPk(rs.getInt("ADDRESS_PK"));
					System.out.println("[로그] AddressDAO selectOne 성공 : " + data);
				}
			} 
			else if (addressDTO.getCondition().equals("SELECT_IS_DEFAULT_ADDRESS_BY_ADDRESS_PK")) {
				pstmt = conn.prepareStatement(SELECT_IS_DEFAULT_ADDRESS_BY_ADDRESS_PK);
				pstmt.setInt(1, addressDTO.getAddressPk());
				System.out.println("[로그] AddressDAO 기본주소 조회 실행 - accountPk=" + addressDTO.getAccountPk());

				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					data = new AddressDTO();
					data.setAddressPk(rs.getInt("ADDRESS_PK"));
					System.out.println("[로그] AddressDAO selectOne 성공 : " + data);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[에러] AddressDAO selectOne 실패 : " + data);
		}
		JDBCUtil.disconnect(conn, pstmt);
		return data;
	}

	public boolean insert(AddressDTO addressDTO) {
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 주소지 새로 등록하기
			pstmt = conn.prepareStatement(INSERT_NEW_ADDRESS);
			pstmt.setInt(1, addressDTO.getAccountPk());
			pstmt.setString(2, addressDTO.getAddressName());
			pstmt.setString(3, addressDTO.getPostalCode());
			pstmt.setBoolean(4, addressDTO.isDefaultAddress());
			pstmt.setString(5, addressDTO.getRegionAddress());
			pstmt.setString(6, addressDTO.getDetailAddress());
			System.out.println("[로그] AddressDAO INSERT_NEW_ADDRESS : " + addressDTO);
			int result = pstmt.executeUpdate();
			if (result <= 0) {
				System.out.println("[로그] AddressDTO insert 성공 : " + addressDTO);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] AddressDTO insert 실패 : " + addressDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}

	public boolean update(AddressDTO addressDTO) {
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 기본주소지 변경하기
			if (addressDTO.getCondition().equals("UPDATE_DEFAULT_ADDRESS")) {
				pstmt = conn.prepareStatement(UPDATE_DEFAULT_ADDRESS);
				pstmt.setInt(1, addressDTO.getAddressPk());
				pstmt.setInt(2, addressDTO.getAccountPk());
				System.out.println("[로그] AddressDAO UPDATE_DEFAULT_ADDRESS : " + addressDTO);
				int result = pstmt.executeUpdate();
				if (result < 0) {
					System.out.println("[로그] AddressDAO update 성공 : " + addressDTO);
					return false;
				}
			} else if (addressDTO.getCondition().equals("UPDATE_DEFAULT_ADDRESS_REMOVE")) {
				pstmt = conn.prepareStatement(UPDATE_DEFAULT_ADDRESS_REMOVE);
				pstmt.setInt(1, addressDTO.getAccountPk());
				System.out.println("[로그] AddressDAO UPDATE_DEFAULT_ADDRESS_REMOVE : " + addressDTO);
				int result = pstmt.executeUpdate();
				if (result < 0) {
					System.out.println("[로그] AddressDAO update 성공 : " + addressDTO);
					return false;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] AddressDAO update 실패 : " + addressDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}

	public boolean delete(AddressDTO addressDTO) {
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			// 회원고유번호에 대한 주소지 개별삭제
			if (addressDTO.getCondition().equals("DELETE_ADDRESS_BY_ADDRESS_PK")) {
				pstmt = conn.prepareStatement(DELETE_ADDRESS_BY_ADDRESS_PK);
				pstmt.setInt(1, addressDTO.getAddressPk());
				System.out.println("[로그] AddressDAO DELETE_ADDRESS_BY_ACCOUNT_PK : " + addressDTO);
			}
			// 회원고유번호에 대한 모든 주소지 삭제
			else if (addressDTO.getCondition().equals("DELETE_ALL_ADDRESS_BY_ACCOUNT_PK")) {
				pstmt = conn.prepareStatement(DELETE_ALL_ADDRESS_BY_ACCOUNT_PK);
				pstmt.setInt(1, addressDTO.getAccountPk());
				System.out.println("[로그] AddressDAO DELETE_ALL_ADDRESS_BY_ACCOUNT_PK : " + addressDTO);
			}
			int result = pstmt.executeUpdate();
			if (result < 0) {
				System.out.println("[로그] AddressDAO delete 성공 : " + addressDTO);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[로그] AddressDAO update 실패 : " + addressDTO);
			return false;
		}
		JDBCUtil.disconnect(conn, pstmt);
		return true;
	}

}
