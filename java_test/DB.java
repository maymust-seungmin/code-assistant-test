import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
 
public class DB { 
    /** 
     * @brief 데이터베이스에 연결합니다. 
     * 
     * @param config 데이터베이스 설정 정보를 담은 Config 객체 
     * @return Connection 연결 성공 시 Connection 객체, 실패 시 null 반환 
     */ 
    public static Connection dbConnect(Config config) { 
        Connection conn = null; 
        try { 
            String url = String.format("jdbc:mysql://%s:%d/%s", config.host, config.port, config.database); 
            System.out.println(url);
            conn = DriverManager.getConnection(url, config.user, config.password); 
        } catch (SQLException e) { 
            Logger.logMessage("[dbConnect] 연결 실패: %s", e.getMessage()); 
            if (conn != null) { 
                try { 
                    conn.close(); 
                } catch (SQLException ex) { 
                    Logger.logMessage("[dbConnect] 연결 해제 실패: %s", ex.getMessage()); 
                } 
            } 
        } 
        return conn; 
    } 
 
    /** 
     * @brief 데이터베이스 연결을 종료합니다. 
     * 
     * @param conn Connection 객체 
     */ 
    public static void dbDisconnect(Connection conn) { 
        if (conn != null) { 
            try { 
                conn.close(); 
            } catch (SQLException e) { 
                Logger.logMessage("[dbDisconnect] 연결 해제 실패: %s", e.getMessage()); 
            } 
        } 
    } 
 
    /** 
     * @brief 프리페어드 스테이트먼트를 사용하여 쿼리를 실행하고 결과를 반환합니다. 
     * 
     * @param conn Connection 객체 
     * @param query 실행할 SQL 쿼리 
     * @param param 입력 파라미터 (예: WHERE 조건의 값) 
     * @return ResultSet 쿼리 결과(SELECT의 경우), 실행 실패 시 null 반환 
     */ 
    public static ResultSet dbExecutePreparedQuery(Connection conn, String query, String param) {
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, param);
            return stmt.executeQuery(); // ResultSet만 반환
        } catch (SQLException e) {
            Logger.logMessage("[dbExecutePreparedQuery] 쿼리 실행 실패: %s", e.getMessage());
            return null;
        }
    }
}
