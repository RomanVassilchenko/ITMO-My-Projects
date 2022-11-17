#include "../include/image.h"

#include <stdlib.h>

struct image image_create(size_t width, size_t height) {
	struct image img;
	
	img.width = width;
	img.height = height;
	img.data = (struct pixel *) malloc(width * height * PIXEL_SIZE);

	return img;
}

struct pixel *image_pos_at(const struct image *const img, size_t col, size_t row) {
	return img->data + col + row * img->width;
}

void image_free(const struct image img) {
	free(img.data);
}
