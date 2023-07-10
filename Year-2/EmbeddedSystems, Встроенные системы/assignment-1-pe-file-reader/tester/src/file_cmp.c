#include <stdint.h>
#include <string.h>

#include "file_cmp.h"

#define CMP_BUFFER_SIZE (4096 * 2)

enum cmp_interm_result { CMP_INT_EQ, CMP_INT_DIFF, CMP_INT_ERROR, CMP_INT_UNDEF };

static enum cmp_interm_result cmp_chunk(uint8_t buf1[], uint8_t buf2[],
                                 size_t buf_size, size_t read1, size_t read2) {
  if (read1 == read2) {
    if (memcmp(buf1, buf2, read1) != 0)
      return CMP_INT_DIFF;

    if (read1 == buf_size)
      return CMP_INT_UNDEF;
    else
      return CMP_INT_EQ;

  } else {
    return CMP_INT_DIFF;
  }
}

enum cmp_result file_cmp(FILE *f1, FILE *f2) {
  uint8_t buffer1[CMP_BUFFER_SIZE];
  uint8_t buffer2[CMP_BUFFER_SIZE];

  for ( ;; ) {
    const size_t read1 = fread(buffer1, 1, CMP_BUFFER_SIZE, f1);
    const size_t read2 = fread(buffer2, 1, CMP_BUFFER_SIZE, f2);
    const enum cmp_interm_result result =
        cmp_chunk(buffer1, buffer2, CMP_BUFFER_SIZE, read1, read2);

    switch (result) {
    case CMP_INT_EQ: return CMP_EQUALS;
    case CMP_INT_DIFF: return CMP_DIFF;
    case CMP_INT_ERROR: return CMP_ERROR;
    case CMP_INT_UNDEF: continue;
    }
  }
}
