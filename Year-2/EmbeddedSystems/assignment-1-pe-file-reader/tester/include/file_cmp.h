#pragma once
#ifndef _FILE_CMP_H_
#define _FILE_CMP_H_

#include <stdbool.h>
#include <stdio.h>

enum cmp_result {
  CMP_EQUALS = 0,
  CMP_DIFF,
  CMP_ERROR,
};

static const char *const cmp_error_msg[] = {
    [CMP_EQUALS] = "Files are similar",
    [CMP_DIFF] = "Files are different",
    [CMP_ERROR] = "Internal error"};

enum cmp_result file_cmp(FILE *f1, FILE *f2);

#endif
