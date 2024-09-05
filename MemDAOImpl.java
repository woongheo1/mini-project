package project;

import project.common.OracleConnectivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



public class MemDAOImpl implements MemDAO {
	
	@Override
	public int memInsert(MemVO mvo) {
		// TODO Auto-generated method stub
		System.out.println("MemDAOImpl.memInsert() 함수 진입 성공  >>> : ");
		
		//지역변수 선언 및 default 초기화
		Connection conn = null;
		PreparedStatement pstmt = null;
		int nCnt = 0;
		
		try {
			
			//1. 커넥션 연결
			conn = OracleConnectivity.getConnection();
			
			//2. 인서트 쿼리 문 만들기
			String sql = "INSERT INTO K_MEM ( KNUM, KID, KPW,DELETEYN, INSERTDATE, UPDATEDATE) "
					+ " VALUES  (    ?,   ?,   ?,    'Y',    SYSDATE,    SYSDATE)";
			
			//3. statement 를 전달하고 결과값을 받아오는 통로 만들기
			pstmt = conn.prepareStatement(sql);
			
			//4. 쿼리문에 플레이스 홀더에 입력할 데이터 바인딩 하기
			pstmt.clearParameters(); //clearParameters() 함수 항상 사용하기
			pstmt.setString(1, mvo.getKnum());
			pstmt.setString(2, mvo.getKid());
			pstmt.setString(3, mvo.getKpw());
			
			//5. statement 를 전달하고 결과값을 받기
			nCnt = pstmt.executeUpdate();
			
			System.out.println("MemDAOImpl.memInsert() 함수에서 " + nCnt  + "건 입력 되었습니다. ");
		
		}catch(Exception e) {
			System.out.println("MemDAOImpl.memInsert() 함수에서 입력 중 오류 발생 >>> : " + e.getMessage());
		}
		return nCnt;
	}
	
	@Override
	public ArrayList<MemVO> memSelectAll() {
		
		System.out.println("MemDAOImpl.memSelectAll() 함수 진입 성공 >>> : ");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsRs = null;
		ArrayList<MemVO> aList = null;
		
		try {
			
			//커넥션 연결하기
			conn = OracleConnectivity.getConnection();
			StringBuffer sb = new StringBuffer();
			
			sb.append(" SELECT 										\n");
			sb.append("     	 A.KNUM 			KNUM 			\n"); // COLUMN INDEX 1	
			sb.append("			,A.KID  			KID 			\n"); // COLUMN INDEX 2
		    sb.append("			,A.KPW  			KPW   			\n"); // COLUMN INDEX 3
		    sb.append("			,A.KEMAIL  			KEMAIL 			\n"); // COLUMN INDEX 4		    	   		
		    sb.append("			,A.DELETEYN  		DELETEYN   		\n"); // COLUMN INDEX 5
		    sb.append("			,TO_CHAR(A.INSERTDATE, 'YYYY-MM-DD')  INSERTDATE   \n"); // COLUMN INDEX 6
		    sb.append("			,TO_CHAR(A.UPDATEDATE, 'YYYY-MM-DD')  UPDATEDATE   \n"); // COLUMN INDEX 7	    
		    sb.append("	FROM 										\n");
		    sb.append("		 	K_MEM A 							\n");
		    sb.append( " WHERE 	1=1 								\n");
			sb.append( " AND   	A.DELETEYN = 'Y' 					\n");
			sb.append( " ORDER BY 1         						\n");	
			
			pstmt = conn.prepareStatement(sb.toString());
			
			//ResultSet 에 파일 받아오기
			rsRs = pstmt.executeQuery();
			
			if (rsRs != null) {
				//ArrayList 클래스 인스턴스 하기
				aList = new ArrayList<MemVO>();
				
				while (rsRs.next()) {
					//MemberVO 선언 및 인스턴스
					MemVO _mvo = new MemVO();
					// 조회된 컬럼 MemberVO 에 세팅하기
					_mvo.setKnum(rsRs.getString(1));
					_mvo.setKid(rsRs.getString(2));
					_mvo.setKpw(rsRs.getString(3));
					_mvo.setKemail(rsRs.getString(4));
					_mvo.setDeleteyn(rsRs.getString(5));
					_mvo.setInsertdate(rsRs.getString(6));
					_mvo.setUpdatedate(rsRs.getString(7));
					
					aList.add(_mvo);
				}
				System.out.println("MemDAOImpl.memSelectAll() :: aList.size() >>> : " + aList.size());
			}
		}catch(Exception e) {
			System.out.println("MemDAOImpl.memSelectAll() 조회 중 에러가 발생 >>> : " + e);
			}
		return aList;	
	}
	

	@Override
	public int memUpdate(MemVO mvo) {
		// TODO Auto-generated method stub
		System.out.println("MemDAOImpl.memUpdate() 함수 진입 성공 >>> : ");
		
		System.out.println("MemDAOImpl.memInsert() ::: mvo.getKnum() >>> : " + mvo.getKnum());	
		System.out.println("MemDAOImpl.memInsert() ::: mvo.getHemail() >>> : " + mvo.getKemail());	
		
		//지역변수 선언 및 defult 초기화
		Connection conn = null;
		PreparedStatement pstmt = null;
		int nCnt = 0;
		
		try {
			
			//1.커넥션 연결하기
			conn = OracleConnectivity.getConnection();
			
			//2.업데이트 쿼리 문 만들기
			StringBuffer sb = new StringBuffer();			
			sb.append("	UPDATE  							\n");	
			sb.append("		   	 K_MEM 			   			 \n");	
			sb.append("	SET  								\n");	
			sb.append("			 KEMAIL 		= ? 		\n"); // parameterIndex 1			
			sb.append("			,UPDATEDATE 	= SYSDATE 	\n"); 	
			sb.append("	WHERE  	 KNUM 			= ?			\n"); // parameterIndex 2
			sb.append("	AND  	 DELETEYN  		= 'Y'		\n"); 
			
			String sql = 	  "UPDATE K_MEM "
							+ "SET KEMAIL 	= ?, "
							+ "UPDATEDATE 	= SYSDATE "
							+ "WHERE KNUM 	= ? "
							+ "AND DELETEYN = 'Y' ";
			
			//3.statement 를 전달하고 결과값을 받아오는 통로 만들기
//			pstmt = conn.prepareStatement(sb.toString());
			pstmt = conn.prepareStatement(sql);			
			
			// 4. 쿼리문에 플레이스 홀더에 입력할 데이터 바인딩 하기 
			pstmt.clearParameters(); // clearParameters() 함수 항상 사용하기 
			pstmt.setString(1, mvo.getKemail());
			pstmt.setString(2, mvo.getKnum());
			
			// 5. statement 를 전달하고 결과값을 받기 
			nCnt = pstmt.executeUpdate();
						
			System.out.println("MemDAOImpl.memUpdate() 함수에서 " + nCnt  + "건 수정 되었습니다. ");
						
		}catch(Exception e) {
						System.out.println("MemDAOImpl.memUpdate() 함수에서 입력 중 오류 발생 >>> : " + e.getMessage());
		}
					
		return nCnt;
				
		}
		
	
	@Override
	public int memDelete(MemVO mvo) {
		// TODO Auto-generated method stub
		System.out.println("MemDAOImpl.memDelete() 함수 진입 성공 >>> : ");
		
		System.out.println("MemDAOImpl.memDelete() ::: mvo.getHnum() >>> : " + mvo.getKnum());	
	
		// 지역변수 선언 및 default 초기화 
		Connection conn = null;
		PreparedStatement pstmt = null;
		int nCnt = 0;
		
		try {
			
			// 1. 커넥션 연결
			conn = OracleConnectivity.getConnection();
			
			// 2. 업데이트 쿼리 문 만들기 
			StringBuffer sb = new StringBuffer();			
			sb.append("	UPDATE  							\n");	
			sb.append("		   	 K_MEM 			    		\n");	
			sb.append("	SET  								\n");	
			sb.append("			 DELETEYN 		= 'N' 		\n"); 			
			sb.append("			,UPDATEDATE 	= SYSDATE 	\n"); 	
			sb.append("	WHERE  	 KNUM 			= ?			\n"); // parameterIndex 1
			sb.append("	AND  	 DELETEYN  		= 'Y'		\n"); 
			
			String sql = 	  "UPDATE K_MEM "
							+ "SET DELETEYN = 'N', "
							+ "UPDATEDATE = SYSDATE "
							+ "WHERE KNUM = ? "
							+ "AND DELETEYN = 'Y' ";
					
			// 3. statement 를 전달하고 결과값을 받아오는 통로 만들기 
//			pstmt = conn.prepareStatement(sb.toString());
			pstmt = conn.prepareStatement(sql);			
						
			// 4. 쿼리문에 플레이스 홀더에 입력할 데이터 바인딩 하기 
			pstmt.clearParameters(); // clearParameters() 함수 항상 사용하기 			
			pstmt.setString(1, mvo.getKnum());
			
			// 5. statement 를 전달하고 결과값을 받기 
			nCnt = pstmt.executeUpdate();
			
			System.out.println("MemDAOImpl.memDelete() 함수에서 " + nCnt  + "건 삭제 되었습니다. ");
			
		}catch(Exception e) {
			System.out.println("MemDAOImpl.memDelete() 함수에서 입력 중 오류 발생 >>> : " + e.getMessage());
		}
		
		return nCnt;
	}
	
	@Override
	public ArrayList<MemVO> memSelect(MemVO mvo) {
		// TODO Auto-generated method stub
		System.out.println("MemDAOImpl.memSelect() 함수 진입 성공 >>> : ");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsRs = null;
		ArrayList<MemVO> aList = null;
		
		try {
			
			// 커넨션 연결하기
			conn = OracleConnectivity.getConnection();

			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT 										\n");
			sb.append("     	 A.KNUM 			KNUM 			\n"); // COLUMN INDEX 1	
			sb.append("			,A.KID  			KID 			\n"); // COLUMN INDEX 2
		    sb.append("			,A.KPW  			KPW   			\n"); // COLUMN INDEX 3
		    sb.append("			,A.KEMAIL  			KEMAIL 			\n"); // COLUMN INDEX 4		    	   		
		    sb.append("			,A.DELETEYN  		DELETEYN   		\n"); // COLUMN INDEX 5
		    sb.append("			,TO_CHAR(A.INSERTDATE, 'YYYY-MM-DD')  INSERTDATE   \n"); // COLUMN INDEX 6
		    sb.append("			,TO_CHAR(A.UPDATEDATE, 'YYYY-MM-DD')  UPDATEDATE   \n"); // COLUMN INDEX 7	    
		    sb.append("	FROM 										\n");
		    sb.append("		 	K_MEM A 							\n");
		    sb.append( " WHERE 	1=1 								\n");
			sb.append( " AND   	A.DELETEYN = 'Y' 					\n");
			sb.append( " AND   	A.KNUM	   = ? 						\n"); // parameterIndex 1
			sb.append( " ORDER BY 1         						\n");	
		
			pstmt = conn.prepareStatement(sb.toString());
					
			// 플레이스 홀더에 파라미터 세팅하기 
			pstmt.setString(1, mvo.getKnum());
			// ResultSet 에 파일 받아오기 
			rsRs = pstmt.executeQuery();
			
			if (rsRs !=null) {
				// ArrayList 클래스 인스턴스 하기 
				aList = new ArrayList<MemVO>();
				
				while (rsRs.next()) {					
					// MemberVO 선언 및 인스턴스
					MemVO _mvo = new MemVO();					
					// 조회된 컬럼 MemberVO 에 세팅하기 
					_mvo.setKnum(rsRs.getString(1));
					_mvo.setKid(rsRs.getString(2));
					_mvo.setKpw(rsRs.getString(3));						
					_mvo.setKemail(rsRs.getString(4));
					_mvo.setDeleteyn(rsRs.getString(5));					
					_mvo.setInsertdate(rsRs.getString(6));
					_mvo.setUpdatedate(rsRs.getString(7));	
									
					aList.add(_mvo);			
				}				
				System.out.println("MemDAOImpl.memSelectAll() :: aList.size() >>> : " + aList.size());
			}
	
		}catch(Exception e) {
			System.out.println("MemDAOImpl.memSelect() 조회 중 에러가 발생 >>> : " + e);
		}
		
		return aList;	
	}

	@Override
	public ArrayList<MemVO> getKidSelect(MemVO mvo) {
		// TODO Auto-generated method stub
		System.out.println("MemDAOImpl.getHidSelect() 함수 진입 성공 >>> : ");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsRs = null;
		ArrayList<MemVO> aList = null;
		
		try {
			
			// 커넨션 연결하기
			conn = OracleConnectivity.getConnection();

			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT 										\n");
			sb.append("     	 A.KNUM 			KNUM 			\n"); // COLUMN INDEX 1	
			sb.append("			,A.KID  			KID 			\n"); // COLUMN INDEX 2
		    sb.append("			,A.KPW  			KPW   			\n"); // COLUMN INDEX 3
		    sb.append("			,A.KEMAIL  			KEMAIL 			\n"); // COLUMN INDEX 4		    	   		
		    sb.append("			,A.DELETEYN  		DELETEYN   		\n"); // COLUMN INDEX 5
		    sb.append("			,TO_CHAR(A.INSERTDATE, 'YYYY-MM-DD')  INSERTDATE   \n"); // COLUMN INDEX 6
		    sb.append("			,TO_CHAR(A.UPDATEDATE, 'YYYY-MM-DD')  UPDATEDATE   \n"); // COLUMN INDEX 7	    
		    sb.append("	FROM 										\n");
		    sb.append("		 	K_MEM A 							\n");
		    sb.append( " WHERE 	1=1 								\n");
			sb.append( " AND   	A.DELETEYN = 'Y' 					\n");
			sb.append( " AND   	A.KID LIKE '%' || ? || '%'			\n"); // parameterIndex 1
			sb.append( " ORDER BY 1         						\n");	
		
			pstmt = conn.prepareStatement(sb.toString());
					
			// 플레이스 홀더에 파라미터 세팅하기 
			pstmt.setString(1, mvo.getKid());
			// ResultSet 에 파일 받아오기 
			rsRs = pstmt.executeQuery();
			
			if (rsRs !=null) {
				// ArrayList 클래스 인스턴스 하기 
				aList = new ArrayList<MemVO>();
				
				while (rsRs.next()) {					
					// MemberVO 선언 및 인스턴스
					MemVO _mvo = new MemVO();					
					// 조회된 컬럼 MemberVO 에 세팅하기 
					_mvo.setKnum(rsRs.getString(1));
					_mvo.setKid(rsRs.getString(2));
					_mvo.setKpw(rsRs.getString(3));						
					_mvo.setKemail(rsRs.getString(4));
					_mvo.setDeleteyn(rsRs.getString(5));					
					_mvo.setInsertdate(rsRs.getString(6));
					_mvo.setUpdatedate(rsRs.getString(7));	
									
					aList.add(_mvo);			
				}				
				System.out.println("MemDAOImpl.getHidSelect() :: aList.size() >>> : " + aList.size());
			}
	
		}catch(Exception e) {
			System.out.println("MemDAOImpl.getHidSelect() 조회 중 에러가 발생 >>> : " + e);
		}
		
		return aList;	
	}

	@Override
	public ArrayList<MemVO> getKemailSelect(MemVO mvo) {
		// TODO Auto-generated method stub
		System.out.println("MemDAOImpl.getHemailSelect() 함수 진입 성공 >>> : ");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsRs = null;
		ArrayList<MemVO> aList = null;
		
		try {
			
			// 커넨션 연결하기
			conn = OracleConnectivity.getConnection();

			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT 										\n");
			sb.append("     	 A.KNUM 			KNUM 			\n"); // COLUMN INDEX 1	
			sb.append("			,A.KID  			KID 			\n"); // COLUMN INDEX 2
		    sb.append("			,A.KPW  			KPW   			\n"); // COLUMN INDEX 3
		    sb.append("			,A.KEMAIL  			KEMAIL 			\n"); // COLUMN INDEX 4		    	   		
		    sb.append("			,A.DELETEYN  		DELETEYN   		\n"); // COLUMN INDEX 5
		    sb.append("			,TO_CHAR(A.INSERTDATE, 'YYYY-MM-DD')  INSERTDATE   \n"); // COLUMN INDEX 6
		    sb.append("			,TO_CHAR(A.UPDATEDATE, 'YYYY-MM-DD')  UPDATEDATE   \n"); // COLUMN INDEX 7	    
		    sb.append("	FROM 										\n");
		    sb.append("		 	K_MEM A 							\n");
		    sb.append( " WHERE 	1=1 								\n");
			sb.append( " AND   	A.DELETEYN = 'Y' 					\n");
			sb.append( " AND   	A.KEMAIL LIKE '%' || ? || '%'		\n"); // parameterIndex 1
			sb.append( " ORDER BY 1         						\n");	
		
			pstmt = conn.prepareStatement(sb.toString());
					
			// 플레이스 홀더에 파라미터 세팅하기 
			pstmt.setString(1, mvo.getKemail());
			// ResultSet 에 파일 받아오기 
			rsRs = pstmt.executeQuery();
			
			if (rsRs !=null) {
				// ArrayList 클래스 인스턴스 하기 
				aList = new ArrayList<MemVO>();
				
				while (rsRs.next()) {					
					// MemberVO 선언 및 인스턴스
					MemVO _mvo = new MemVO();					
					// 조회된 컬럼 MemberVO 에 세팅하기 
					_mvo.setKnum(rsRs.getString(1));
					_mvo.setKid(rsRs.getString(2));
					_mvo.setKpw(rsRs.getString(3));						
					_mvo.setKemail(rsRs.getString(4));
					_mvo.setDeleteyn(rsRs.getString(5));					
					_mvo.setInsertdate(rsRs.getString(6));
					_mvo.setUpdatedate(rsRs.getString(7));	
									
					aList.add(_mvo);			
				}				
				System.out.println("MemDAOImpl.getHemailSelect() :: aList.size() >>> : " + aList.size());
			}
	
		}catch(Exception e) {
			System.out.println("MemDAOImpl.getHemailSelect() 조회 중 에러가 발생 >>> : " + e);
		}
		
		return aList;	
	}

}

