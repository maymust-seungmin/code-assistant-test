import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    /**
     * @brief 로그 메시지를 출력합니다.
     *
     * @param format printf와 유사한 형식 지정 문자열
     * @param args 가변 인자 목록
     */
    public static void logMessage(String format, Object... args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = sdf.format(new Date());

        System.err.printf("[%s] %s\n", timeStr, String.format(format, args));
    }
}
