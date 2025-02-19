package com.example.demo;

import com.example.demo.service.DBService;
import com.example.demo.utils.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        // Spring Boot 애플리케이션 실행
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        
        // DBService 빈 가져오기
        DBService dbService = context.getBean(DBService.class);

        try {
            Logger.logMessage("데이터베이스 연결 성공!");

            // 쿼리와 파라미터 설정
            String query = "SELECT name, age FROM users WHERE age > ?";
            String param = "30";

            // DB 쿼리 실행
            List<String[]> results = dbService.executePreparedQuery(query, param);
            if (results == null) {
                Logger.logMessage("쿼리 실행 중 오류 발생!");
                return;
            }

            // 결과 출력
            for (String[] row : results) {
                Logger.logMessage("Name: %s, Age: %s", row[0], row[1]);
            }

        } catch (Exception e) {
            Logger.logMessage("데이터베이스 연결 실패: %s", e.getMessage());
        } finally {
            Logger.logMessage("프로그램 종료.");
        }
    }
}
