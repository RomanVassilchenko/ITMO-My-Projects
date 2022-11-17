#ifndef IMAGE_H
#define IMAGE_H

#include <stdint.h>
#include <stdlib.h>

struct pixel {
	uint8_t b;
	uint8_t g;
	uint8_t r;
};

struct image {
	size_t width;
	size_t height;

	struct pixel *data;
};

enum {
	PIXEL_SIZE = sizeof(struct pixel)
};

struct image image_create(size_t width, size_t height);
struct pixel *image_pos_at(const struct image *img, size_t col, size_t row);
void image_free(const struct image img);

#endif
