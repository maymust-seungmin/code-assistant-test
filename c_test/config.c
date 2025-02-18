#include "config.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int load_db_config(DBConfig *config) {
  FILE *file = fopen(CONFIG_FILE_PATH, "r");
  if (file == NULL) {
    fprintf(stderr, "[load_db_config] 설정 파일을 열 수 없습니다: %s\n",
            CONFIG_FILE_PATH);
    return -1;
  }

  char line[512];
  while (fgets(line, sizeof(line), file)) {
    if (line[0] == '#' || line[0] == ';' || line[0] == '\n') {
      continue; // 주석 또는 빈 줄은 무시
    }

    char key[256], value[256];
    if (sscanf(line, "%255[^=]=%255s", key, value) == 2) {
      if (strcmp(key, "host") == 0) {
        strncpy(config->host, value, sizeof(config->host) - 1);
      } else if (strcmp(key, "user") == 0) {
        strncpy(config->user, value, sizeof(config->user) - 1);
      } else if (strcmp(key, "password") == 0) {
        strncpy(config->password, value, sizeof(config->password) - 1);
      } else if (strcmp(key, "database") == 0) {
        strncpy(config->database, value, sizeof(config->database) - 1);
      } else if (strcmp(key, "port") == 0) {
        config->port = (unsigned int)atoi(value);
      }
    }
  }

  fclose(file);
  return 0;
}
