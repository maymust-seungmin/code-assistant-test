#ifndef CONFIG_H
#define CONFIG_H

#define CONFIG_FILE_PATH "config.ini"

typedef struct {
  char host[256];
  char user[256];
  char password[256];
  char database[256];
  unsigned int port;
} DBConfig;

/**
 * @brief 설정 파일을 파싱하여 DBConfig 구조체를 초기화합니다.
 *
 * @param config 초기화할 DBConfig 구조체의 포인터
 * @return int 성공 시 0, 실패 시 -1 반환
 */
int load_db_config(DBConfig *config);

#endif /* CONFIG_H */
