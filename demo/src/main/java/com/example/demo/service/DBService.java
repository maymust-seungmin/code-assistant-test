package com.example.demo.service;

import com.example.demo.config.DBConfig;
import com.example.demo.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DBService {

    @Autowired
    private DataSource dataSource;

    public Connection connect() throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            Logger.logMessage("[connect] 연결 실패: %s", e.getMessage());
            throw e;
        }
    }

    public void disconnect(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                Logger.logMessage("[disconnect] 연결 종료 실패: %s", e.getMessage());
            }
        }
    }

    public List<String[]> executePreparedQuery(String query, String param) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, param);
            ResultSet rs = stmt.executeQuery();

            List<String[]> results = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                Logger.logMessage("Name: %s, Age: %d", name, age);
                results.add(new String[]{name, String.valueOf(age)});
            }

            return results;

        } catch (SQLException e) {
            Logger.logMessage("[executePreparedQuery] 쿼리 실행 중 오류 발생: %s", e.getMessage());
            return null;
        }
    }
}
