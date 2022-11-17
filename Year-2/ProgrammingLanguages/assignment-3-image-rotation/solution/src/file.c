#include "../include/file.h"

#include <stdint.h>

bool file_open(FILE** file, const char* name, const char* mode) {
	*file = fopen(name, mode);
	return (*file != NULL);
}

int file_close(FILE** file) {
	return fclose(*file);
}

FILE* reader_file_open(char *filename) {
	return fopen(filename, "rb");
}

FILE* writer_file_open(char *filename) {
	return fopen(filename, "wb");
}

void file_skip(FILE* file, size_t offset) {
	fseek(file, (long) offset, SEEK_CUR);
}

void file_seek(FILE* file, size_t offset) {
	fseek(file, (long) offset, SEEK_SET);
}

bool reader_file_read(FILE* file, void *buffer, size_t struct_size, size_t n, size_t padding) {
	bool status = fread(buffer, struct_size, n, file);
	file_skip(file, padding);

	return status;
}

bool writer_file_write(FILE* file, const void *buffer, size_t struct_size, size_t n, size_t padding) {
	bool write_status = fwrite(buffer, struct_size, n, file);
	uint8_t ptr[3] = {0};
	fwrite(&ptr, 1, padding, file);

	return write_status;
}
