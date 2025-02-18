import java.sql.ResultSet;
import java.sql.Connection;  // Connection 클래스 임포트 추가
import java.sql.SQLException;  // SQLException 클래스 임포트 추가

public class Main {
    public static void main(String[] args) {
        Config config = new Config();

        // 설정 파일 로드
        if (!Config.loadDBConfig(config)) {
            Logger.logMessage("설정 파일을 로드하는 데 실패했습니다.");
            System.exit(EXIT_FAILURE);
        }

        // 데이터베이스 연결
        Connection conn = DB.dbConnect(config);
        if (conn == null) {
            Logger.logMessage("데이터베이스에 연결하는 데 실패했습니다.");
            System.exit(EXIT_FAILURE);
        }

        Logger.logMessage("데이터베이스 연결 성공!");

        // 예제 쿼리 실행
        String query = "SELECT name, age FROM users WHERE age > ?";
        String param = "30";

        ResultSet result = null;
        try {
            result = DB.dbExecutePreparedQuery(conn, query, param);
            if (result != null) {
                while (result.next()) {
                    Logger.logMessage("Name: %s, Age: %s", result.getString("name"), result.getString("age"));
                }
            }
        } catch (SQLException e) {
            Logger.logMessage("[main] 결과 처리 중 오류 발생: %s", e.getMessage());
        }
        // 결과 출력
        try {
            while (result.next()) {
                Logger.logMessage("Name: %s, Age: %s", result.getString("name"), result.getString("age"));
            }
        } catch (SQLException e) {
            Logger.logMessage("[main] 결과 처리 중 오류 발생: %s", e.getMessage());
        } finally {
            try {
                result.close();
            } catch (SQLException e) {
                Logger.logMessage("[main] 결과셋 해제 실패: %s", e.getMessage());
            }
        }

        DB.dbDisconnect(conn);

        Logger.logMessage("프로그램 종료.");
        System.exit(EXIT_SUCCESS);
    }

    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_FAILURE = 1;
}
