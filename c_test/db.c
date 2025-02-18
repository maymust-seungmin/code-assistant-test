#include "db.h"
#include "logger.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

MYSQL *db_connect(const DBConfig *config) {
  MYSQL *conn = mysql_init(NULL);
  if (conn == NULL) {
    log_message("[db_connect] mysql_init() 실패");
    return NULL;
  }

  if (mysql_real_connect(conn, config->host, config->user, config->password,
                         config->database, config->port, NULL, 0) == NULL) {
    log_message("[db_connect] 연결 실패: %s", mysql_error(conn));
    mysql_close(conn);
    return NULL;
  }

  return conn;
}

void db_disconnect(MYSQL *conn) {
  if (conn != NULL) {
    mysql_close(conn);
  }
}

MYSQL_RES *db_execute_prepared_query(MYSQL *conn, const char *query,
                                     const char *param) {
  MYSQL_STMT *stmt = mysql_stmt_init(conn);
  if (stmt == NULL) {
    log_message("[db_execute_prepared_query] mysql_stmt_init 실패: %s",
                mysql_error(conn));
    return NULL;
  }

  if (mysql_stmt_prepare(stmt, query, strlen(query)) != 0) {
    log_message("[db_execute_prepared_query] mysql_stmt_prepare 실패: %s",
                mysql_error(conn));
    mysql_stmt_close(stmt);
    return NULL;
  }

  MYSQL_BIND bind[1];
  memset(bind, 0, sizeof(bind));

  bind[0].buffer_type = MYSQL_TYPE_STRING;
  bind[0].buffer = (char *)param;
  bind[0].buffer_length = strlen(param);

  if (mysql_stmt_bind_param(stmt, bind) != 0) {
    log_message("[db_execute_prepared_query] mysql_stmt_bind_param 실패: %s",
                mysql_error(conn));
    mysql_stmt_close(stmt);
    return NULL;
  }

  if (mysql_stmt_execute(stmt) != 0) {
    log_message("[db_execute_prepared_query] mysql_stmt_execute 실패: %s",
                mysql_error(conn));
    mysql_stmt_close(stmt);
    return NULL;
  }

  // 결과 메타데이터 가져오기
  MYSQL_RES *result = mysql_stmt_result_metadata(stmt);
  if (result == NULL) {
    log_message(
        "[db_execute_prepared_query] 결과 메타데이터를 가져올 수 없습니다: %s",
        mysql_error(conn));
    mysql_stmt_close(stmt);
    return NULL;
  }

  // 결과 바인딩
  MYSQL_BIND result_bind[2];  // name과 age를 바인딩
  memset(result_bind, 0, sizeof(result_bind));

  char name[100];
  unsigned long name_len;
  int age;  // long 대신 int로 변경
  
  result_bind[0].buffer_type = MYSQL_TYPE_STRING;
  result_bind[0].buffer = name;
  result_bind[0].buffer_length = sizeof(name);
  result_bind[0].length = &name_len;
  
  result_bind[1].buffer_type = MYSQL_TYPE_LONG;  // MYSQL_TYPE_INT로 변경할 수도 있음
  result_bind[1].buffer = &age;
  result_bind[1].is_null = 0;

  if (mysql_stmt_bind_result(stmt, result_bind) != 0) {
    log_message("[db_execute_prepared_query] mysql_stmt_bind_result 실패: %s",
                mysql_error(conn));
    mysql_stmt_close(stmt);
    return NULL;
  }

  // 결과 페칭
  while (mysql_stmt_fetch(stmt) == 0) {
    log_message("Name: %s, Age: %d", name, age);  // Age 출력 포맷을 %d로 수정
  }

  mysql_stmt_free_result(stmt);
  mysql_stmt_close(stmt);

  return result;
}
