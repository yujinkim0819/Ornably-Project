package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.common.JDBCUtil;
import model.dto.KakaoUserDTO;

public class KakaoUserDAO {
	//카카오 로그인 DB 저장 여부 확인
	public static final String KAKAOLOGINCHECK = "SELECT COUNT(*) AS CNT FROM ACCOUNT WHERE ACCOUNT_ID = ?";
	//카카오 로그인 insert 실행
	public static final String KAKAOLOGININSERT ="INSERT INTO ACCOUNT (" +
			"ACCOUNT_PK, ACCOUNT_ID, ACCOUNT_PASSWORD, " +
			"ACCOUNT_NAME, ACCOUNT_EMAIL, ACCOUNT_PHONE, ACCOUNT_ROLE" +
			") VALUES (" +
			"ACCOUNT_SEQ.NEXTVAL, ?, 'KAKAO', ?, ?, NULL, 'ACCOUNT'" +
			")";

	private ArrayList<KakaoUserDTO> selectAll(KakaoUserDTO kakaouserDTO){
		return null;
	}

	private KakaoUserDTO selectOne(KakaoUserDTO kakaouserDTO){
		return null;

	}

	public boolean insert(KakaoUserDTO kakaouserDTO){
		Connection conn = JDBCUtil.connect();
		PreparedStatement pstmt = null;

		try {
			//존재 여부 확인
	        if(kakaouserDTO.getCondition().equals("KAKAOLOGINCHECK")){

	            pstmt = conn.prepareStatement(KAKAOLOGINCHECK);
	            pstmt.setString(1, "KAKAO_" + kakaouserDTO.getKakaoId());
	            ResultSet rs = pstmt.executeQuery();

	            if(rs.next()) {
	                int cnt = rs.getInt("CNT");
	                System.out.println("[로그] 카카오 회원 존재 여부 = " + cnt);
	                return cnt > 0;   //존재하면 true, 없으면 false
	            }
	        }
			// 없으면 DB에 저장
	        else if(kakaouserDTO.getCondition().equals("KAKAOLOGININSERT")){

	            pstmt = conn.prepareStatement(KAKAOLOGININSERT);

	            pstmt.setString(1, "KAKAO_" + kakaouserDTO.getKakaoId());
	            pstmt.setString(2, kakaouserDTO.getNickname());
	            pstmt.setString(3, kakaouserDTO.getEmail());

	            int result = pstmt.executeUpdate();
	            System.out.println("[로그] 카카오 회원 INSERT 결과 = " + result);

	            return result > 0; // 성공하면 true
	        }

	    } catch(Exception e){
	        e.printStackTrace();
	    }
		JDBCUtil.disconnect(conn, pstmt);
		 return false;
	    }
	
	private boolean update(KakaoUserDTO kakaouserDTO){
		return false;

	}

	private boolean delete(KakaoUserDTO kakaouserDTO){
		return false;

	}
}