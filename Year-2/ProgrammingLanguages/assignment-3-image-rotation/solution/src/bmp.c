#include "../include/bmp.h"

#include <inttypes.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

enum {
	BMP_BF_TYPE = 19778,
	BMP_BF_RESERVED = 0,
	BMP_BO_OFF_BITS = 54,
	BMP_BI_SIZE = 40,
	BMP_FILE_HEADER_SIZE = 14,
	BMP_BI_PLANES = 1,
	BMP_BI_BIT_COUNT = 24,
	BMP_BI_COMPRESSION = 0,
	BMP_BI_X_PELS_PER_METER = 2835,
	BMP_BI_Y_PELS_PER_METER = 2835,
	BMP_BI_CLR_USED = 0,
	BMP_BI_CLR_IMPORTANT = 0,
	BMP_HEADER_SIZE = sizeof(struct bmp_header)
};

static bool read_header(FILE* in, struct bmp_header *const header) {
	if (!reader_file_read(in, header, BMP_HEADER_SIZE, 1, 0)) return false;
	file_seek(in, header->bOffBits);
	return true;
}


static size_t get_padding(size_t row_size) {
	return ((row_size + 3) / 4) * 4 - row_size;
}

static bool read_data(FILE* in, const struct image *const img) {

	size_t byte_width = PIXEL_SIZE * (img->width);
	size_t padding = get_padding(byte_width);

	struct pixel *start = img->data;

	for (
		struct pixel *row = img->data;
		row - start < img->width * img->height;
		row += img->width
	) {
		reader_file_read(in, row, PIXEL_SIZE, img->width, padding);
	}

	return true;
}

enum read_status from_bmp(FILE* in, struct image *const img) {
	struct bmp_header header;

	if (!read_header(in, &header)) return READ_INVALID_HEADER;

	*img = image_create(header.biWidth, header.biHeight);

	if (!read_data(in, img)) return READ_INVALID_BITS;

	fclose(in);
	return READ_OK;
}

enum write_status to_bmp(FILE* out, const struct image *const img) {
	struct bmp_header header = (struct bmp_header) {0};

	size_t row_size = PIXEL_SIZE * img->width;
	size_t padded_row_size = row_size + get_padding(row_size);
	size_t image_size = padded_row_size * img->height;

	header.bOffBits = BMP_BO_OFF_BITS;
	header.bfReserved = BMP_BF_RESERVED;
	header.bfileSize = image_size + BMP_BO_OFF_BITS;
	header.biBitCount = BMP_BI_BIT_COUNT;
	header.biClrImportant = BMP_BI_CLR_IMPORTANT;
	header.biClrUsed = BMP_BI_CLR_USED;
	header.biCompression = BMP_BI_COMPRESSION;
	header.biHeight = img->height;
	header.biPlanes = BMP_BI_PLANES;
	header.biSize = BMP_HEADER_SIZE - BMP_FILE_HEADER_SIZE;
	header.biSizeImage = image_size;
	header.biWidth = img->width;
	header.biXPelsPerMeter = BMP_BI_X_PELS_PER_METER;
	header.biYPelsPerMeter = BMP_BI_Y_PELS_PER_METER;
	header.bfType = BMP_BF_TYPE;


	if (!writer_file_write(out, &header, BMP_HEADER_SIZE, 1, 0)) return WRITE_ERROR;

	for (size_t row_number = 0; row_number < img->height; ++row_number) {
		const struct pixel *current_row = img->data + row_number * img->width;

		if (!writer_file_write(out, current_row, PIXEL_SIZE, img->width,
		 get_padding(PIXEL_SIZE * (img->width)))) return WRITE_ERROR;
	}

	fclose(out);
	return WRITE_OK;
}
