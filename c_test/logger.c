#include "logger.h"
#include <stdarg.h>
#include <stdio.h>
#include <time.h>

void log_message(const char *format, ...) {
  va_list args;
  va_start(args, format);

  time_t now = time(NULL);
  struct tm *t = localtime(&now);
  char time_str[20];
  strftime(time_str, sizeof(time_str), "%Y-%m-%d %H:%M:%S", t);

  fprintf(stderr, "[%s] ", time_str);
  vfprintf(stderr, format, args);
  fprintf(stderr, "\n");

  va_end(args);
}
