#include "config.h"
#include "db.h"
#include "logger.h"
#include <mysql/mysql.h>
#include <stdio.h>
#include <stdlib.h>

int main() {
  DBConfig config;

  // 설정 파일 로드
  if (load_db_config(&config) != 0) {
    log_message("설정 파일을 로드하는 데 실패했습니다.");
    return EXIT_FAILURE;
  }

  // 데이터베이스 연결
  MYSQL *conn = db_connect(&config);
  if (conn == NULL) {
    log_message("데이터베이스에 연결하는 데 실패했습니다.");
    return EXIT_FAILURE;
  }

  log_message("데이터베이스 연결 성공!");

  // 예제 쿼리 실행
  const char *query = "SELECT name, age FROM users WHERE age > ?";
  const char *param = "30";

  MYSQL_RES *result = db_execute_prepared_query(conn, query, param);
  if (result == NULL) {
    log_message("쿼리 실행 중 오류 발생!");
    db_disconnect(conn);
    return EXIT_FAILURE;
  }

  // 결과 출력
  MYSQL_ROW row;
  while ((row = mysql_fetch_row(result))) {
    log_message("Name: %s, Age: %s", row[0], row[1]);
  }

  mysql_free_result(result);
  db_disconnect(conn);

  log_message("프로그램 종료.");
  return EXIT_SUCCESS;
}
