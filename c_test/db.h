#ifndef DB_H
#define DB_H

#include "config.h"
#include <mysql/mysql.h>

/**
 * @brief 데이터베이스에 연결합니다.
 *
 * @param config 데이터베이스 설정 정보를 담은 DBConfig 구조체
 * @return MYSQL* 연결 성공 시 MYSQL 객체 포인터, 실패 시 NULL 반환
 */
MYSQL *db_connect(const DBConfig *config);

/**
 * @brief 데이터베이스 연결을 종료합니다.
 *
 * @param conn MYSQL 객체 포인터
 */
void db_disconnect(MYSQL *conn);

/**
 * @brief 프리페어드 스테이트먼트를 사용하여 쿼리를 실행하고 결과를 반환합니다.
 *
 * @param conn MYSQL 객체 포인터
 * @param query 실행할 SQL 쿼리
 * @param param 입력 파라미터 (예: WHERE 조건의 값)
 * @return MYSQL_RES* 쿼리 결과(SELECT의 경우), 실행 실패 시 NULL 반환
 */
MYSQL_RES *db_execute_prepared_query(MYSQL *conn, const char *query,
                                     const char *param);

#endif /* DB_H */
