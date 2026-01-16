package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.AccountDTO;


public class AccountDAO {
	
	//회원가입하기
	private static final String ACCOUNT_JOIN =
			 "INSERT INTO ACCOUNT "
			 + "(ACCOUNT_PK,ACCOUNT_ID,ACCOUNT_PASSWORD,ACCOUNT_NAME,ACCOUNT_EMAIL,ACCOUNT_PHONE)"
			+ "VALUES(ACCOUNT_SEQ.NEXTVAL,?,?,?,?,?)";
	
	//회원탈퇴
	private static final String UPDATE_SIGN_OUT= 
			"UPDATE ACCOUNT "
			+ "SET "
			+ "    ACCOUNT_ID = NULL,"
			+ "    ACCOUNT_ROLE = 'SIGNOUT',"
			+ " ACCOUNT_EMAIL = NULL, "
			+ " ACCOUNT_PHONE = NULL "
			+ "WHERE ACCOUNT_PK = ?";
	
	//마이페이지
	private static final String SELECT_MY_PAGE =
			"SELECT ACCOUNT_PK,ACCOUNT_ID,ACCOUNT_NAME,ACCOUNT_EMAIL,ACCOUNT_PHONE "
			+ "FROM ACCOUNT WHERE ACCOUNT_PK = ?";
	
	//탈퇴시 비번확인
	private static final String SELECT_CHECK_PASSWORD_BY_PK ="SELECT ACCOUNT_NAME "
			+ "FROM ACCOUNT WHERE ACCOUNT_PK = ? "
			+ "AND ACCOUNT_PASSWORD = ?";
	
	//로그인
	private static final String LOGIN = 
			"SELECT ACCOUNT_PK,ACCOUNT_ID,ACCOUNT_NAME,ACCOUNT_ROLE "
			+ "FROM ACCOUNT "
			+ "WHERE ACCOUNT_ID = ? "
			+ "AND ACCOUNT_PASSWORD = ? ";
//			+ "AND ACCOUNT_ROLE = ?";
	
	//아이디 중복검사
	private static final String SELECT_CHECK_LOGIN_ID = 
			"SELECT ACCOUNT_ID FROM ACCOUNT WHERE ACCOUNT_ID = ?";
	//이메일 중복검사
	private static final String SELECT_CHECK_LOGIN_EMAIL=""
			+ "SELECT ACCOUNT_EMAIL FROM ACCOUNT WHERE ACCOUNT_EMAIL = ?";
	//폰번호 중복검사
	private static final String SELECT_CHECK_LOGIN_PHONE = 
			"SELECT ACCOUNT_PHONE FROM ACCOUNT WHERE ACCOUNT_PHONE = ? ";
	private static final String SELECT_ACCOUNT_PK_BY_ACCOUNT_ID =
			"SELECT ACCOUNT_PK FROM ACCOUNT WHERE ACCOUNT_ID = ?";
	
	private ArrayList<AccountDTO> selectAll(AccountDTO accountDTO){
		return null;
	}
	public AccountDTO selectOne(AccountDTO accountDTO) {
		AccountDTO data = null;
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			//회원고유번호에 해당하는 이름,아이디,이메일,폰번호를 보여줄 마이페이지
			if(accountDTO.getCondition().equals("SELECT_MY_PAGE")) {
				pstmt = conn.prepareStatement(SELECT_MY_PAGE);
				pstmt.setInt(1,accountDTO.getAccountPk());
				System.out.println("[로그] AccountDAO select_MyPage : " + data);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					data = new AccountDTO();
					data.setAccountId(rs.getString("ACCOUNT_ID"));
//					data.setAccountPassword(rs.getString("ACCOUNT_PASSWORD"));
					data.setAccountName(rs.getString("ACCOUNT_NAME"));
					data.setAccountEmail(rs.getString("ACCOUNT_EMAIL"));
					data.setAccountPhone(rs.getString("ACCOUNT_PHONE"));
					data.setAccountPk(rs.getInt("ACCOUNT_PK"));
					System.out.println("[로그] AccountDAO selectOne 성공 : " + data);
				}
			}
			//회원 탈퇴시 회원고유번호에 해당하는 비밀번호 확인하기
			else if(accountDTO.getCondition().equals("SELECT_CHECK_PASSWORD_BY_PK")) {
				pstmt = conn.prepareStatement(SELECT_CHECK_PASSWORD_BY_PK);
				pstmt.setInt(1,accountDTO.getAccountPk());
				pstmt.setString(2,accountDTO.getAccountPassword());
				System.out.println("[로그] AccountDAO select_Check_Password_ByPk : " + data);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					data = new AccountDTO();
					data.setAccountName(rs.getString("ACCOUNT_NAME"));
					System.out.println("[로그] AccountDAO selectOne 성공 : " + data);
				}
			}
			//로그인하기
			else if(accountDTO.getCondition().equals("LOGIN")) {
				pstmt = conn.prepareStatement(LOGIN);
				pstmt.setString(1,accountDTO.getAccountId());
				pstmt.setString(2,accountDTO.getAccountPassword());
//				pstmt.setString(3,accountDTO.getAccountRole());
				System.out.println("[로그] AccountDAO login : " + data);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					data = new AccountDTO();
					data.setAccountId(rs.getString("ACCOUNT_ID"));
//					data.setAccountPassword(rs.getString("ACCOUNT_PASSWORD"));
					data.setAccountName(rs.getString("ACCOUNT_NAME"));
//					data.setAccountEmail(rs.getString("ACCOUNT_EMAIL"));
//					data.setAccountPhone(rs.getString("ACCOUNT_PHONE"));
					data.setAccountPk(rs.getInt("ACCOUNT_PK"));
					data.setAccountRole(rs.getString("ACCOUNT_ROLE"));
					System.out.println("[로그] AccountDAO selectOne 성공 : " + data);
				}
				
			}
			//아이디 중복검사
			else if(accountDTO.getCondition().equals("SELECT_CHECK_LOGIN_ID")) {
				pstmt = conn.prepareStatement(SELECT_CHECK_LOGIN_ID);
				pstmt.setString(1,accountDTO.getAccountId());
				System.out.println("[로그] AccountDAO select_CheckLogin_Id : " + data);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					data = new AccountDTO();
					System.out.println("[로그] AccountDAO selectOne 성공 : " + data);
				}
			}
			//이메일 중복검사
			else if(accountDTO.getCondition().equals("SELECT_CHECK_LOGIN_EMAIL")) {
				pstmt = conn.prepareStatement(SELECT_CHECK_LOGIN_EMAIL);
				pstmt.setString(1,accountDTO.getAccountEmail());
				System.out.println("[로그] AccountDAO select_CheckLogin_Email : " + data);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					data = new AccountDTO();
					System.out.println("[로그] AccountDAO selectOne 성공 : " + data);
				}
			}
			//폰번호 중복검사
			else if(accountDTO.getCondition().equals("SELECT_CHECK_LOGIN_PHONE")) {
				pstmt = conn.prepareStatement(SELECT_CHECK_LOGIN_PHONE);
				pstmt.setString(1,accountDTO.getAccountPhone());
				System.out.println("[로그] AccountDAO select_CheckLogin_Phone : " + data);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					data = new AccountDTO();
					System.out.println("[로그] AccountDAO selectOne 성공 : " + data);
				}
			}
			// ACCOUNT_ID로 ACCOUNT_PK 주기
			else if(accountDTO.getCondition().equals("SELECT_ACCOUNT_PK_BY_ACCOUNT_ID")) {
				pstmt = conn.prepareStatement(SELECT_ACCOUNT_PK_BY_ACCOUNT_ID);
				pstmt.setString(1,accountDTO.getAccountId());
				System.out.println("[로그] AccountDAO select_CheckLogin_Phone : " + data);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					data = new AccountDTO();
					data.setAccountPk(rs.getInt("ACCOUNT_PK"));
					System.out.println("[로그] AccountDAO selectOne 성공 : " + data);
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("[에러] AccountDAO selectOne 실패 : " + data);
		}
		JDBCUtil.disconnect(conn,pstmt);
		return data;
	}
		
	public boolean insert(AccountDTO accountDTO) {
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			//회원가입
			pstmt = conn.prepareStatement(ACCOUNT_JOIN);
			pstmt.setString(1,accountDTO.getAccountId());
			pstmt.setString(2,accountDTO.getAccountPassword());
			pstmt.setString(3,accountDTO.getAccountName());
			pstmt.setString(4,accountDTO.getAccountEmail());
			pstmt.setString(5,accountDTO.getAccountPhone());
			int result = pstmt.executeUpdate();
			if(result<=0) {
				System.out.println("[로그] AccountDAO insert 성공 : " + accountDTO);
				return false;
			}
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("[로그] AccountDAO insert 실패 : " + accountDTO);
			return false;
		}
		JDBCUtil.disconnect(conn,pstmt);
		return true;
	}
	public boolean update(AccountDTO accountDTO) {
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;
		try {
			//회원탈퇴시 회원고유번호에 해당하는 아이디와 어카운트롤 변경
			if(accountDTO.getCondition().equals("UPDATE_SIGN_OUT")) {
			pstmt = conn.prepareStatement(UPDATE_SIGN_OUT);
			pstmt.setInt(1,accountDTO.getAccountPk());
			int result = pstmt.executeUpdate();
			if(result<=0) {
				System.out.println("[로그] AccountDAO update 성공 : " + accountDTO);
				return false;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("[로그] AccountDAO update 실패 : " + accountDTO);
			return false;
		}
		JDBCUtil.disconnect(conn,pstmt);
		return true;
	}
	private boolean delete(AccountDTO accountDTO) {
		return false;
	}
	
}