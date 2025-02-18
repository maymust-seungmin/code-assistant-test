#ifndef LOGGER_H
#define LOGGER_H

/**
 * @brief 로그 메시지를 출력합니다.
 *
 * @param format printf와 유사한 형식 지정 문자열
 * @param ... 가변 인자 목록
 */
void log_message(const char *format, ...);

#endif /* LOGGER_H */
