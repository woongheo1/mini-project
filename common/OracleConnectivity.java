package project.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class OracleConnectivity {

    // 데이터 소스 datasource
    public static final String JDBC_ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String JDBC_ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    public static final String JDBC_ORACLE_USER = "scott";
    public static final String JDBC_ORACLE_PW = "tiger";

    public static Connection getConnection() {

        Connection conn = null;

        try {
            Class.forName(OracleConnectivity.JDBC_ORACLE_DRIVER);
            conn = DriverManager.getConnection( OracleConnectivity.JDBC_ORACLE_URL,
                    OracleConnectivity.JDBC_ORACLE_USER,
                    OracleConnectivity.JDBC_ORACLE_PW);
        }catch(Exception e) {
            System.out.println("OralceConnectivity >>> : " + e);
        }

        return conn;
    }

    // select 연결 해제하는 함수
    public static void conClose(Connection conn, PreparedStatement pstmt, ResultSet rsRs) {
        try{
            if (rsRs != null) try { rsRs.close(); rsRs = null; } catch(Exception ex){}
            if (pstmt != null) try { pstmt.close(); pstmt = null; } catch(Exception ex){}
            if (conn != null) try { conn.close(); conn = null; } catch(Exception ex){}
        }catch (Exception e2){}
    }

    // insert, update, delete 연결 해제하는 함수
    public static void conClose(Connection conn, PreparedStatement pstmt) {
        try{
            if (pstmt != null) try { pstmt.close(); pstmt = null; } catch(Exception ex){}
            if (conn != null) try { conn.close(); conn = null; } catch(Exception ex){}
        }catch (Exception e2){}
    }
}


