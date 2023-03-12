#include <stdbool.h>
#include <stdio.h>

#include "common.h"
#include "file_cmp.h"
#include "io.h"

void usage(void) {
  fprintf(stderr,
          "Usage: ./" EXECUTABLE_NAME " file_name_1 file_name_2\n");
}

int main(int argc, char **argv) {
  if (argc != 3)
    return usage(), -1;

  // error handling should be here
  FILE *f1 = fopen(argv[1], "rb");
  if (!f1)
    fatal("Bad first input file\n");
  FILE *f2 = fopen(argv[2], "rb");
  if (!f2) {
    fclose(f1);
    fatal("Bad second input file\n");
  }

  const enum cmp_result status = file_cmp(f1, f2);

  fclose(f1);
  fclose(f2);

  if (status == CMP_EQUALS)
    return 0;

  fprintf(stderr, "%s\n", cmp_error_msg[status]);
  return status;
}
