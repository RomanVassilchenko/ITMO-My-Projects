
#ifndef FILE_H
#define FILE_H

#include <stdbool.h>
#include <stdio.h>

bool file_open(FILE** file, const char* name, const char* mode);
int file_close(FILE** file);

FILE* reader_file_open(char *filename);
FILE* writer_file_open(char *filename);
bool reader_file_read(FILE* file, void *buffer, size_t struct_size, size_t n, size_t padding);
bool writer_file_write(FILE* file, const void *buffer, size_t struct_size, size_t n, size_t padding);

void file_skip(FILE* file, size_t offset);
void file_seek(FILE* file, size_t offset);

#endif
