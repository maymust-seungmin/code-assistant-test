import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException; 
 
public class Config { 
    public static final String CONFIG_FILE_PATH = "config.ini"; 
 
    public String host; 
    public String user; 
    public String password; 
    public String database; 
    public int port; 
 
    /** 
     * @brief 설정 파일을 파싱하여 Config 객체를 초기화합니다. 
     * 
     * @param config 초기화할 Config 객체 
     * @return boolean 성공 시 true, 실패 시 false 반환 
     */ 
    public static boolean loadDBConfig(Config config) { 
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE_PATH))) { 
            String line; 
            while ((line = reader.readLine()) != null) { 
                if (line.startsWith("#") || line.startsWith(";") || line.trim().isEmpty()) { 
                    continue; // 주석 또는 빈 줄은 무시 
                } 
 
                String[] parts = line.split("=", 2); 
                if (parts.length == 2) { 
                    String key = parts[0].trim(); 
                    String value = parts[1].trim(); 
                    switch (key) { 
                        case "host": 
                            config.host = value; 
                            break; 
                        case "user": 
                            config.user = value; 
                            break; 
                        case "password": 
                            config.password = value; 
                            break; 
                        case "database": 
                            config.database = value; 
                            break; 
                        case "port": 
                            config.port = Integer.parseInt(value); 
                            break; 
                    } 
                } 
            } 
        } catch (IOException e) { 
            System.err.printf("[loadDBConfig] 설정 파일을 열 수 없습니다: %s\n", CONFIG_FILE_PATH); 
            e.printStackTrace(); 
            return false; 
        } 
        return true; 
    } 
}
