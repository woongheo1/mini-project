package project;

import project.common.OracleConnectivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



public class LoginDAOImpl implements LoginDAO {

	@Override
	public ArrayList<MemVO> loginCheck(MemVO mvo) {
		// TODO Auto-generated method stub
		System.out.println("LoginDAOImpl.loginCheck() 함수 진입 성공 >>> : ");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsRs = null;
		ArrayList<MemVO> aList = null;
		
		try {
			
			// 커넨션 연결하기
			conn = OracleConnectivity.getConnection();

			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT 										\n");
			sb.append("     	 A.KID 				KID 			\n"); // COLUMN INDEX 1	
			sb.append("			,A.KPW  			KPW 			\n"); // COLUMN INDEX 2		  
		    sb.append("	FROM 										\n");
		    sb.append("		 	K_MEM A 							\n");
		    sb.append( " WHERE 	1=1 								\n");
			sb.append( " AND   	A.DELETEYN = 'Y' 					\n");
			sb.append( " AND   	A.KID	   = ? 						\n"); // parameterIndex 1
			sb.append( " AND   	A.KPW	   = ? 						\n"); // parameterIndex 2
				
		
			pstmt = conn.prepareStatement(sb.toString());
					
			// 플레이스 홀더에 파라미터 세팅하기 
			pstmt.setString(1, mvo.getKid());
			pstmt.setString(2, mvo.getKpw());
			
			// ResultSet 에 파일 받아오기 
			rsRs = pstmt.executeQuery();
			
			if (rsRs !=null) {
				// ArrayList 클래스 인스턴스 하기 
				aList = new ArrayList<MemVO>();
				
				while (rsRs.next()) {					
					// MemberVO 선언 및 인스턴스
					MemVO _mvo = new MemVO();					
					// 조회된 컬럼 MemberVO 에 세팅하기 

					_mvo.setKid(rsRs.getString(1));
					_mvo.setKpw(rsRs.getString(2));						
									
					aList.add(_mvo);			
				}				
				System.out.println("LoginDAOImpl.loginCheck() :: aList.size() >>> : " + aList.size());
			}
	
		}catch(Exception e) {
			System.out.println("LoginDAOImpl.loginCheck() 조회 중 에러가 발생 >>> : " + e);
		}
		
		return aList;	
	}

}
